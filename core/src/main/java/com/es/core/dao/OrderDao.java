package com.es.core.dao;

import com.es.core.entity.order.Order;
import com.es.core.entity.order.OrderStatus;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Optional<Order> getById(Long key);
    List<Order> findOrders();
    void save(Order order);
    Optional<Order> getBySecureID(String secureID);
    void changeStatus(Long id, OrderStatus status);
}
