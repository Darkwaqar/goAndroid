package com.growonline.gomobishop.fragment;

/**
 * Created by asifrizvi on 4/12/2019.
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.BeanProductDetail;

public class ShopTheLookFragmentBottom extends Fragment {
    static final String ARG_TRAVEL = "PRODUCT";
    private BeanProductDetail product;

    private TextView productDescription;

    public static ShopTheLookFragmentBottom newInstance(BeanProductDetail product) {
        Bundle args = new Bundle();
        ShopTheLookFragmentBottom fragmentBottom = new ShopTheLookFragmentBottom();
        args.putParcelable(ARG_TRAVEL, product);
        fragmentBottom.setArguments(args);
        return fragmentBottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            product = args.getParcelable(ARG_TRAVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shop_the_look_fragment_bottom, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        productDescription = view.findViewById(R.id.product_description);

        if (product != null) {
            productDescription.setText(product.getShortDescription());
        }

    }

}