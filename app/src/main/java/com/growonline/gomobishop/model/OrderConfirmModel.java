package com.growonline.gomobishop.model;

import com.google.gson.annotations.SerializedName;

public class OrderConfirmModel {
    @SerializedName("RedirectUrl")
    private final String redirectUrl;

    @SerializedName("PaymentMethodType")
    private final int paymentMethodType;

    @SerializedName("OrderId")
    private final int orderId;

    public OrderConfirmModel(String redirectUrl, int paymentMethodType, int orderId) {
        this.redirectUrl = redirectUrl;
        this.paymentMethodType = paymentMethodType;
        this.orderId = orderId;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public int getPaymentMethodType() {
        return paymentMethodType;
    }

    public int getOrderId() {
        return orderId;
    }
}
