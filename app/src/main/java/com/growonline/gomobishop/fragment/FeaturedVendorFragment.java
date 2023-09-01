package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.adapter.FeaturedVendorListingAdapter;
import com.growonline.gomobishop.alphabetsindexfastscrollrecycler.IndexFastScrollRecyclerView;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetAllVendorsAsyncTask;
import com.growonline.gomobishop.control.SimpleDividerItemDecoration;
import com.growonline.gomobishop.model.VendorList;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class FeaturedVendorFragment extends Fragment {
    private static final String ARG_PARAM1 = "type";
    private final int mPageSize = 6;
    int currentLoadingPage = 0;
    private int type;
    private int mPageNumber = 1, mTotalPages;
    private FeaturedVendorListingAdapter adapter;
    private IndexFastScrollRecyclerView recyclerView;
    private GetAllVendorsAsyncTask getAllVendorsAsyncTask;
    private MainActivity mActivity;
    private View networkError;
    private List<VendorList> vendors = new ArrayList<>();


    public FeaturedVendorFragment() {
    }


    public static FeaturedVendorFragment newInstance(int type) {
        FeaturedVendorFragment fragment = new FeaturedVendorFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            type = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_featured_vendor_single_layout, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = (MainActivity) getActivity();
        recyclerView = (IndexFastScrollRecyclerView) view.findViewById(R.id.recycler_view_featured_products);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mActivity, SimpleDividerItemDecoration.VERTICAL_LIST));
        recyclerView.setIndexBarTextColor(R.color.colorPrimary);
        recyclerView.setIndexbarHighLateTextColor(R.color.light_transparent);
        recyclerView.setIndexBarHighLateTextVisibility(true);
        recyclerView.setIndexBarColor(R.color.colorPrimary);
        recyclerView.setIndexBarCornerRadius(0);
        recyclerView.setIndexBarTransparentValue((float) 0.1);
        Button Reload = (Button) view.findViewById(R.id.reload);
        networkError = view.findViewById(R.id.network_error);
        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkError.setVisibility(View.GONE);
                loadVendors();
            }
        });
        adapter = new FeaturedVendorListingAdapter(vendors, (AppCompatActivity) getActivity(), type);
        if (type == AppConstant.ALL_VENDOR) {
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(mActivity, SimpleDividerItemDecoration.HORIZONTAL_LIST));
            recyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
        } else
            recyclerView.setLayoutManager(new GridLayoutManager(null, 1, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        loadVendors();
    }


    void loadVendors() {

        getAllVendorsAsyncTask = new GetAllVendorsAsyncTask(type);
        getAllVendorsAsyncTask.addOnResultListener(new AsyncTaskResultListener<List<VendorList>>() {
            @Override
            public void response(final AsyncTaskResult<List<VendorList>> response) {
                mActivity.setLoading(false);
                if (!response.hasException()) {
                    vendors.clear();
                    vendors.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }
                currentLoadingPage = 0;
            }
        });
        mActivity.setLoading(true);
        getAllVendorsAsyncTask.execute();


    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        networkError.setVisibility(View.VISIBLE);
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

}
