package com.es.core.entity.cart.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CartItemDto {

    private Long jewelryId;
    @NotNull(message = "Quantity was empty")
    @Min(value = 1, message = "Quantity must be more then 0")
    private Long quantity;

    public CartItemDto() {}

    public CartItemDto(Long jewelryId, Long quantity) {
        this.jewelryId = jewelryId;
        this.quantity = quantity;
    }

    public Long getJewelryId() {
        return jewelryId;
    }
    public void setJewelryId(Long jewelryId) {
        this.jewelryId = jewelryId;
    }
    public Long getQuantity() {
        return quantity;
    }
    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
