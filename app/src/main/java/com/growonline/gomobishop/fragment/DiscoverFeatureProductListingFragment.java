package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.DiscoverFeatureProductAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetFeaturedItemsAsyncTask;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.yayandroid.parallaxlistview.ParallaxListView;

import java.util.List;

public class DiscoverFeatureProductListingFragment extends Fragment {


    private Vendor mVendor;
    private GetFeaturedItemsAsyncTask backgroundTask;
    private com.yayandroid.parallaxlistview.ParallaxListView listView;


    public DiscoverFeatureProductListingFragment() {
        // Required empty public constructor
    }

    public static DiscoverFeatureProductListingFragment newInstance(Vendor vendor) {
        DiscoverFeatureProductListingFragment fragment = new DiscoverFeatureProductListingFragment();
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
        View v = inflater.inflate(R.layout.fragment_discover_product_listing, container, false);
        initUi(v);
        return v;
    }

    void initUi(View view) {
        listView = (ParallaxListView) view.findViewById(R.id.parallax_list_view);
        loadData();
    }


    void loadFeaturedItems() {
        ((StoreActivity) getActivity()).setLoadingAnimation(true);

        backgroundTask = new GetFeaturedItemsAsyncTask(mVendor.getId());
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<List<Product>>() {
            @Override
            public void response(final AsyncTaskResult<List<Product>> response) {
                if (!response.hasException()) {

                    bindData(response.getResult());

                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }
                ((StoreActivity) getActivity()).setLoadingAnimation(false);
            }
        });
        backgroundTask.execute();
    }

    private void loadData() {
        loadFeaturedItems();
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    void bindData(List<Product> items) {
        DiscoverFeatureProductAdapter adapter = new DiscoverFeatureProductAdapter(getActivity(), items, (AppCompatActivity) getActivity());
        listView.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        super.onDestroyView();
    }

}
