package com.es.core.entity.jewelry;

public class JewelryNotFoundException extends RuntimeException {

    public String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public JewelryNotFoundException() {
        this.errorMessage = "Jewelry not found!";
    }
}
