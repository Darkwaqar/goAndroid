package com.growonline.gomobishop.model;

import java.util.ArrayList;
import java.util.List;

public class SpecificationAttribute {

    private int id;
    private String name, displayOrder;
    private List<SpecificationAttributeOption> options;

    public SpecificationAttribute() {
        options = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(String displayOrder) {
        this.displayOrder = displayOrder;
    }

    public List<SpecificationAttributeOption> getOptions() {
        return options;
    }

    public void setOptions(List<SpecificationAttributeOption> options) {
        this.options = options;
    }

}
