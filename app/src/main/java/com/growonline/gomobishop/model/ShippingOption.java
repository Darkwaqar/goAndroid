package com.growonline.gomobishop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Basit on 11/2/2016.
 */

public class ShippingOption {

    @SerializedName("ShippingRateComputationMethodSystemName")
    @Expose
    private String ShippingRateComputationMethodSystemName;
    @SerializedName("Rate")
    @Expose
    private Integer Rate;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;

    /**
     *
     * @return
     *     The ShippingRateComputationMethodSystemName
     */
    public String getShippingRateComputationMethodSystemName() {
        return ShippingRateComputationMethodSystemName;
    }

    /**
     *
     * @param ShippingRateComputationMethodSystemName
     *     The ShippingRateComputationMethodSystemName
     */
    public void setShippingRateComputationMethodSystemName(String ShippingRateComputationMethodSystemName) {
        this.ShippingRateComputationMethodSystemName = ShippingRateComputationMethodSystemName;
    }

    /**
     *
     * @return
     *     The Rate
     */
    public Integer getRate() {
        return Rate;
    }

    /**
     *
     * @param Rate
     *     The Rate
     */
    public void setRate(Integer Rate) {
        this.Rate = Rate;
    }

    /**
     *
     * @return
     *     The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     *     The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     *     The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     *     The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }
}
