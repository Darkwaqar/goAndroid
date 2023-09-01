package com.growonline.gomobishop.model;

import androidx.fragment.app.Fragment;

public class TabFragment {

    private String title;
    private Fragment fragment;

    public TabFragment(String title, Fragment fragment) {
        this.title = title;
        this.fragment = fragment;
    }

    public String getTitle() {
        return title;
    }

    public Fragment getFragment() {
        return fragment;
    }

}
