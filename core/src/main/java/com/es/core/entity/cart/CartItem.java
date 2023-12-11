package com.es.core.entity.cart;

import com.es.core.entity.jewelry.Jewelry;

public class CartItem {

    private Jewelry jewelry;
    private Long quantity;

    public Jewelry getJewelry() {
        return jewelry;
    }

    public void setJewelry(Jewelry jewelry) {
        this.jewelry = jewelry;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
