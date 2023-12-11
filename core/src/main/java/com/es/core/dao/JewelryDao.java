package com.es.core.dao;

import com.es.core.entity.jewelry.Jewelry;
import com.es.core.entity.jewelry.sort.SortField;
import com.es.core.entity.jewelry.sort.SortOrder;

import java.util.List;
import java.util.Optional;

public interface JewelryDao {

    Optional<Jewelry> get(Long key);
    Long numberByQuery(String query);
    List<Jewelry> findAll(int offset, int limit, SortField sortField, SortOrder sortOrder, String query);
}
