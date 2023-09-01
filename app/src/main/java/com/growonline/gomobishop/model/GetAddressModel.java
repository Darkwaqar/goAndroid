package com.growonline.gomobishop.model;

/**
 * Created by Basit on 11/2/2016.
 */

public class GetAddressModel {

    private Address shippingAddress, billingAddress;

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
}
