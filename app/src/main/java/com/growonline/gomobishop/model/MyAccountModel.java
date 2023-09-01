package com.growonline.gomobishop.model;

/**
 * Created by asifrizvi on 2/26/2019.
 */

public class MyAccountModel {


    String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIcon() {
        return Icon;
    }

    public void setIcon(int icon) {
        Icon = icon;
    }

    int Icon;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    int Id;

    public MyAccountModel(String name, int icon, int id) {
        Name = name;
        Icon = icon;
        Id = id;
    }

    public MyAccountModel() {

    }

}
