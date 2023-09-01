package com.growonline.gomobishop.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.model.BeanProductDetail;

/**
 * Created by asifrizvi on 4/12/2019.
 */

public class ShopTheLookFragment extends ExpandingFragment {

    static final String ARG_TRAVEL = "ARG_TRAVEL";
    BeanProductDetail product;

    public static ShopTheLookFragment newInstance(BeanProductDetail product) {
        ShopTheLookFragment fragment = new ShopTheLookFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TRAVEL, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            product = args.getParcelable(ARG_TRAVEL);
        }
    }

    /**
     * include TopFragment
     *
     * @return
     */
    @Override
    public Fragment getFragmentTop() {
        return ShopTheLookFragmentTop.newInstance(product);
    }

    /**
     * include BottomFragment
     *
     * @return
     */
    @Override
    public Fragment getFragmentBottom() {
        return ShopTheLookFragmentBottom.newInstance(product);
    }

    @Override
    public BeanProductDetail getProduct() {
        return product;
    }


}