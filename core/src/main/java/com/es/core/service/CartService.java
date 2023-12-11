package com.es.core.service;

import com.es.core.entity.cart.Cart;
import com.es.core.entity.jewelry.Jewelry;
import com.es.core.entity.order.OutOfStockException;

import java.math.BigDecimal;

public interface CartService {

    Cart getCart();

    void addjewelry(Long jewelryId, Long quantity) throws OutOfStockException;

    /**
     * @param items
     * key: {@link Jewelry#id}
     * value: quantity
     */
    void update(Long jewelryId, Long quantity);
    void clear();
    void remove(Long jewelryId);
    long getTotalQuantity();

    BigDecimal getTotalCost();
}
