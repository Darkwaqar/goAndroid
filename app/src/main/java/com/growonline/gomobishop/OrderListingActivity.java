package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.AdapterOrderList;
import com.growonline.gomobishop.asynctask.AsyncTaskGetOrderList;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.OrderListing;
import com.growonline.gomobishop.model.SimpleOrderModel;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class OrderListingActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private AdapterOrderList offerRecyclerAdapter;
    private List<SimpleOrderModel> orderList = new ArrayList<>();
    private LinearLayout loadingLayout;
    private int currentLoadingPage = 0;
    private int mPageNumber = 1, mTotalPages;
    private boolean noProductFound = false;
    private FrameLayout mImageNoProductFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_listing);
        setToolBarTitle(getString(R.string.title_order));
        recyclerView = (RecyclerView) findViewById(R.id.activity_order_list_gridview);
        loadingLayout = (LinearLayout) findViewById(R.id.review_loading);
        mImageNoProductFound = (FrameLayout) findViewById(R.id.img_no_prod_found);
        offerRecyclerAdapter = new AdapterOrderList(OrderListingActivity.this, orderList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(offerRecyclerAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                SimpleOrderModel order = orderList.get(position);
                Intent i = new Intent(OrderListingActivity.this, OrderDetailActivity.class);
                i.putExtra(AppConstant.INTENT_ORDER_ID, order.getId());
                i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                startActivity(i);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!noProductFound &&
                            ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == offerRecyclerAdapter.getItemCount() - 1) {
                        if (mPageNumber == mTotalPages) {
                            GoMobileApp.Toast("no more items available to load");
                        } else {
                            if (currentLoadingPage != mPageNumber + 1) {
                                currentLoadingPage = mPageNumber + 1;
                                LoadOrders(currentLoadingPage);
                            }
                        }
                    }
                }

            }


        });

        LoadOrders(currentLoadingPage);
    }

    private void LoadOrders(int currentLoadingPage) {
        mImageNoProductFound.setVisibility(View.GONE);
        noProductFound = false;
        new AsyncTaskGetOrderList(OrderListingActivity.this, false, currentLoadingPage).execute();
    }


    public void onListObtained(OrderListing orderListing) {
        loadingLayout.setVisibility(View.GONE);
        if (orderListing == null) return;
        mPageNumber = orderListing.getPageNumber();
        mTotalPages = orderListing.getTotalPages();
        if (currentLoadingPage == 0) {
            this.orderList.clear();
            this.orderList.addAll(orderListing.getOrders());
            offerRecyclerAdapter.notifyDataSetChanged();
            if (orderListing.getOrders().size() <= 0)
                noProductFound();

        } else {
            this.orderList.addAll(orderListing.getOrders());
            offerRecyclerAdapter.notifyDataSetChanged();
        }
    }

    private void noProductFound() {
        noProductFound = true;
        mImageNoProductFound.setVisibility(View.VISIBLE);
    }
}