package com.growonline.gomobishop.model;

/**
 * Created by Basit on 2/8/2017.
 */

public class SpecificationAttributeOption {

    private int id, displayOrder;
    private String name;
    private boolean isSelected;

    public SpecificationAttributeOption() {
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

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
