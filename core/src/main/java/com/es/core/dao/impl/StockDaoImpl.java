package com.es.core.dao.impl;

import com.es.core.dao.StockDao;
import com.es.core.entity.jewelry.stock.Stock;
import com.es.core.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Optional;

@Component
public class StockDaoImpl implements StockDao {

    public StockDaoImpl() {
        sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
    }
    private final SessionFactory sessionFactory;

    @Override
    public void reserve(Long jewelryId, Long quantity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Stock> criteriaQuery = criteriaBuilder.createQuery(Stock.class);
            Root<Stock> root = criteriaQuery.from(Stock.class);

            criteriaQuery.select(root);
            criteriaQuery.where(criteriaBuilder.equal(root.get("jewelryId"), jewelryId));

            Optional<Stock> stockOptional = session.createQuery(criteriaQuery).uniqueResultOptional();

            stockOptional.ifPresent(stock -> {
                int reserved = stock.getReserved() + quantity.intValue();
                stock.setReserved(reserved);
                session.update(stock);
            });

            session.getTransaction().commit();
        }
    }

    @Override
    public Integer availableStock(Long jewelryId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Stock> criteriaQuery = criteriaBuilder.createQuery(Stock.class);
            Root<Stock> root = criteriaQuery.from(Stock.class);

            criteriaQuery.where(criteriaBuilder.equal(root.get("jewelryId"), jewelryId));

            Stock availableStock = session.createQuery(criteriaQuery).uniqueResult();
            return availableStock != null ? availableStock.getStock() - availableStock.getReserved() : 0;
        }
    }
}
