package com.es.core.dao;

public interface StockDao {

    Integer availableStock(Long jewelryId);
    void reserve(Long jewelryId, Long quantity);
}
