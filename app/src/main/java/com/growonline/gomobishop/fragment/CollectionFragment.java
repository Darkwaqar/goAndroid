package com.growonline.gomobishop.fragment;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.CategoriesListingAdapter;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.ParallaxListView;
import com.growonline.gomobishop.control.ScrollViewText;
import com.growonline.gomobishop.model.Category;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;


public class CollectionFragment extends Fragment {



    private Vendor mVendor;
    private ParallaxListView listView;
    private ScrollViewText ticker;
    private LinearLayout blackOut;
    private List<Category> mCategoryList;
    private ViewGroup mRelatedCategoryLayout;
    private ImageView mBackGround;


    public CollectionFragment() {
        // Required empty public constructor
    }

    public static CollectionFragment newInstance(Vendor vendor) {
        CollectionFragment fragment = new CollectionFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_collection, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ParallaxListView) view.findViewById(R.id.parallax_home_listview);
        ticker = (ScrollViewText) view.findViewById(R.id.ticker);
        blackOut = (LinearLayout) view.findViewById(R.id.blackOut);
        mBackGround = (ImageView) view.findViewById(R.id.opacityFilter);
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(mVendor.getLogoUrl()), mBackGround);
        mRelatedCategoryLayout = (ViewGroup) view.findViewById(R.id.detail_parent_bottom_related_layout);
        listView.setOnTapListener(new ParallaxListView.TapListener() {
            @Override
            public void onTap(ParallaxListView parallaxListView, int pos, View view) {
                if (mCategoryList != null)
                    if (mCategoryList.get(pos).getSubCategories() != null && mCategoryList.get(pos).getSubCategories().size() > 0) {
                        blackOut.setVisibility(View.VISIBLE);

                        UpdateSubCategories(mCategoryList.get(pos));
                    } else {
                        ((StoreActivity) getActivity()).setStoreTab(mCategoryList.get(pos).getId());
                    }
            }

        });

        blackOut.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                mRelatedCategoryLayout.removeAllViews();
                blackOut.setVisibility(View.GONE);
                return false;
            }
        });
        loadData();
    }

    void loadAllCategories(int vendorId) {
        mCategoryList = mVendor.getCategories();
        CategoriesListingAdapter adapter = new CategoriesListingAdapter(getActivity(), mCategoryList);
        listView.setAdapter(adapter);
    }


    private void UpdateSubCategories(Category subCategories) {
        final List<Category> bean = new ArrayList<>();


        mRelatedCategoryLayout.removeAllViews();
        bean.add(subCategories);
        bean.addAll(subCategories.getSubCategories());
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < bean.size(); i++) {
            View view = inflater.inflate(R.layout.fragment_detail_related_product, null);
            AspectRatioImageView imageView = (AspectRatioImageView) view.findViewById(R.id.detail_related_product_imgview);
            imageView.setHeightRatio(1f);
            imageView.getLayoutParams().width = AppHelper.dpToPx(150, getActivity());
            TextView textView = (TextView) view.findViewById(R.id.subCategoryName);
            textView.setVisibility(View.VISIBLE);
            view.setTag(i);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mRelatedCategoryLayout.removeAllViews();
                    int tag = (Integer) view.getTag();
                    blackOut.setVisibility(View.GONE);
                    ((StoreActivity) getActivity()).setStoreTab(bean.get(tag).getId());


                }
            });
            GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(bean.get(i).getPictureModel().getImageUrl()), imageView, null);
            textView.setText(bean.get(i).getName());
            mRelatedCategoryLayout.addView(view);
        }
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    private void loadData() {
        loadAllCategories(mVendor.getId());
        if (mVendor.getVendorNotes().size() > 0) {
            ticker.setText(mVendor.getVendorNotes().get(0));
            ticker.setSelected(true);
            ticker.startScroll();

            //Pause ACTION_DOWN, resume scroll otherwise.
            ticker.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    //https://developer.android.com/training/gestures/detector.html
                    if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                        ticker.pauseScroll();
                        return true;
                    } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        ticker.resumeScroll();
                        return true;
                    }
                    return false;
                }
            });
        } else {
            ticker.setVisibility(View.GONE);
        }


    }

}
