package com.es.core.entity.order.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class ClientOrderDto {

    @NotEmpty(message = "Value is required")
    private String firstName;
    @NotEmpty(message = "Value is required")
    private String surname;
    @NotEmpty(message = "Value is required")
    private String deliveryAddress;
    @NotEmpty(message = "Value is required")
    @Pattern(regexp = "\\+\\d{12}", message = "Wrong format of number")
    private String contactjewelryNumber;
    private String additionalInformation;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getContactjewelryNumber() {
        return contactjewelryNumber;
    }

    public void setContactjewelryNumber(String contactjewelryNumber) {
        this.contactjewelryNumber = contactjewelryNumber;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
}
