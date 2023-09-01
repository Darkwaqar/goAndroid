package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 3/25/2019.
 */

public class ExpendableModel {

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public String Name;

    public String Value;

    public Boolean getExpended() {
        return Expended;
    }

    public void setExpended(Boolean expended) {
        Expended = expended;
    }

    public ExpendableModel(String name, String value, Boolean expended) {
        Name = name;
        Value = value;
        Expended = expended;
    }

    public Boolean Expended;


}
