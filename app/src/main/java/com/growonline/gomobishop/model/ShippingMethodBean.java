package com.growonline.gomobishop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Basit on 11/2/2016.
 */

public class ShippingMethodBean {

    @SerializedName("ShippingRateComputationMethodSystemName")
    @Expose
    private String ShippingRateComputationMethodSystemName;
    @SerializedName("Name")
    @Expose
    private String Name;
    @SerializedName("Description")
    @Expose
    private String Description;
    @SerializedName("Fee")
    @Expose
    private String Fee;
    @SerializedName("Selected")
    @Expose
    private Boolean Selected;
    @SerializedName("ShippingOption")
    @Expose
    private ShippingOption ShippingOption;

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

    /**
     *
     * @return
     *     The Fee
     */
    public String getFee() {
        return Fee;
    }

    /**
     *
     * @param Fee
     *     The Fee
     */
    public void setFee(String Fee) {
        this.Fee = Fee;
    }

    /**
     *
     * @return
     *     The Selected
     */
    public Boolean getSelected() {
        return Selected;
    }

    /**
     *
     * @param Selected
     *     The Selected
     */
    public void setSelected(Boolean Selected) {
        this.Selected = Selected;
    }

    /**
     *
     * @return
     *     The ShippingOption
     */
    public ShippingOption getShippingOption() {
        return ShippingOption;
    }

    /**
     *
     * @param ShippingOption
     *     The ShippingOption
     */
    public void setShippingOption(ShippingOption ShippingOption) {
        this.ShippingOption = ShippingOption;
    }
}
