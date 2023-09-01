package com.growonline.gomobishop.fragment;

/**
 * Created by asifrizvi on 4/12/2019.
 */


import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.BeanProductDetail;


public class ShopTheLookFragmentTop extends Fragment {

    static final String ARG_TRAVEL = "PRODUCT";
    private BeanProductDetail product;

    private ImageView image;
    private TextView title;
    private TextView productName;
    private TextView price;

    public static ShopTheLookFragmentTop newInstance(BeanProductDetail product) {
        Bundle args = new Bundle();
        ShopTheLookFragmentTop fragmentTop = new ShopTheLookFragmentTop();
        args.putParcelable(ARG_TRAVEL, product);
        fragmentTop.setArguments(args);
        return fragmentTop;
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
        return inflater.inflate(R.layout.shop_the_look_fragment_front, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = view.findViewById(R.id.image);
        title = view.findViewById(R.id.title);
        productName = view.findViewById(R.id.product_name);
        price = view.findViewById(R.id.product_price);

        if (product != null) {
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(product.getDefaultPictureModel().getFullSizeImageUrl()), image);
//            title.setText(product.getName());
            productName.setText(product.getName());
            if (product.getProductType() != 5)
                price.setText(R.string.call_for_price);
            else
                price.setText(product.getProductPrice().getPrice());
        }

    }

//    @SuppressWarnings("unchecked")
//    private void startInfoActivity(View view, Travel travel) {
//        FragmentActivity activity = getActivity();
//        ActivityCompat.startActivity(activity,
//                InfoActivity.newInstance(activity, travel),
//                ActivityOptionsCompat.makeSceneTransitionAnimation(
//                        activity,
//                        new Pair<>(view, getString(R.string.transition_image)))
//                        .toBundle());
//    }
}
