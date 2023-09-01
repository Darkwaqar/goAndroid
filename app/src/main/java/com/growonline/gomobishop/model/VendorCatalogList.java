package com.growonline.gomobishop.model;

import java.util.ArrayList;

/**
 * Created by imac on 02/02/17.
 */
public class VendorCatalogList {
    private int id;
    private String title;
    private ArrayList<CatalogPageModel> pages;

    public int getmId() {
        return id;
    }

    public String getmTitle() {
        return title;
    }

    public ArrayList<CatalogPageModel> getmCatalogPages() {
        return pages;
    }

    public void setmCatalogPages(ArrayList<CatalogPageModel> mCatalogPages) {
        this.pages = mCatalogPages;
    }
}