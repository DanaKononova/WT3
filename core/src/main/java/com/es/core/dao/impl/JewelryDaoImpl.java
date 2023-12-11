package com.es.core.dao.impl;

import com.es.core.dao.JewelryDao;
import com.es.core.entity.jewelry.Jewelry;
import com.es.core.entity.jewelry.sort.SortField;
import com.es.core.entity.jewelry.sort.SortOrder;
import com.es.core.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Component
public class JewelryDaoImpl implements JewelryDao {

    public JewelryDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }
    private final SessionFactory sessionFactory;

    @Override
    public Optional<Jewelry> get(Long key) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Jewelry.class, key));
        }
    }

    @Override
    public Long numberByQuery(String query) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
            Root<Jewelry> root = countQuery.from(Jewelry.class);

            countQuery.select(criteriaBuilder.count(root));
            if (query != null && !query.isEmpty()) {
                countQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + query.toLowerCase() + "%"));
            }

            return session.createQuery(countQuery).getSingleResult();
        }
    }

    @Override
    public List<Jewelry> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String query) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Jewelry> criteriaQuery = criteriaBuilder.createQuery(Jewelry.class);
            Root<Jewelry> root = criteriaQuery.from(Jewelry.class);

            criteriaQuery.select(root);
            if (query != null && !query.isEmpty()) {
                criteriaQuery.where(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), "%" + query.toLowerCase() + "%"));
            }

            if (sortField != null && sortOrder != null) {
                Order order = sortOrder == SortOrder.ASC ? criteriaBuilder.asc(root.get(sortField.name().toLowerCase()))
                        : criteriaBuilder.desc(root.get(sortField.name().toLowerCase()));
                criteriaQuery.orderBy(order);
            }

            Query<Jewelry> queryResult = session.createQuery(criteriaQuery);
            queryResult.setFirstResult(offset);
            queryResult.setMaxResults(limit);

            return queryResult.getResultList();
        }
    }
}
