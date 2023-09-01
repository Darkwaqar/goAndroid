package com.growonline.gomobishop.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AllAvailableCurrencies {
    @SerializedName("AvailableCurrencies")
    @Expose
    private List<AvailableCurrency> availableCurrencies = null;
    @SerializedName("CurrentCurrencyId")
    @Expose
    private Integer currentCurrencyId;

    public List<AvailableCurrency> getAvailableCurrencies() {
        return availableCurrencies;
    }

    public void setAvailableCurrencies(List<AvailableCurrency> availableCurrencies) {
        this.availableCurrencies = availableCurrencies;
    }

    public Integer getCurrentCurrencyId() {
        return currentCurrencyId;
    }

    public void setCurrentCurrencyId(Integer currentCurrencyId) {
        this.currentCurrencyId = currentCurrencyId;
    }

    public static class AvailableCurrency {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("CurrencySymbol")
        @Expose
        private String currencySymbol;
        @SerializedName("Id")
        @Expose
        private Integer id;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCurrencySymbol() {
            return currencySymbol;
        }

        public void setCurrencySymbol(String currencySymbol) {
            this.currencySymbol = currencySymbol;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

    }


}
