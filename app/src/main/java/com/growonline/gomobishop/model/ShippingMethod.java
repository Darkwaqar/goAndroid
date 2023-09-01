package com.growonline.gomobishop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Basit on 11/2/2016.
 */

public class ShippingMethod {
    @SerializedName("ShippingMethods")
    @Expose
    private List<ShippingMethodBean> ShippingMethods = new ArrayList<ShippingMethodBean>();
    @SerializedName("NotifyCustomerAboutShippingFromMultipleLocations")
    @Expose
    private Boolean NotifyCustomerAboutShippingFromMultipleLocations;
    @SerializedName("Warnings")
    @Expose
    private List<Object> Warnings = new ArrayList<Object>();

    /**
     *
     * @return
     *     The ShippingMethods
     */
    public List<ShippingMethodBean> getShippingMethods() {
        return ShippingMethods;
    }

    /**
     *
     * @param ShippingMethods
     *     The ShippingMethods
     */
    public void setShippingMethods(List<ShippingMethodBean> ShippingMethods) {
        this.ShippingMethods = ShippingMethods;
    }

    /**
     *
     * @return
     *     The NotifyCustomerAboutShippingFromMultipleLocations
     */
    public Boolean getNotifyCustomerAboutShippingFromMultipleLocations() {
        return NotifyCustomerAboutShippingFromMultipleLocations;
    }

    /**
     *
     * @param NotifyCustomerAboutShippingFromMultipleLocations
     *     The NotifyCustomerAboutShippingFromMultipleLocations
     */
    public void setNotifyCustomerAboutShippingFromMultipleLocations(Boolean NotifyCustomerAboutShippingFromMultipleLocations) {
        this.NotifyCustomerAboutShippingFromMultipleLocations = NotifyCustomerAboutShippingFromMultipleLocations;
    }

    /**
     *
     * @return
     *     The Warnings
     */
    public List<Object> getWarnings() {
        return Warnings;
    }

    /**
     *
     * @param Warnings
     *     The Warnings
     */
    public void setWarnings(List<Object> Warnings) {
        this.Warnings = Warnings;
    }
}
