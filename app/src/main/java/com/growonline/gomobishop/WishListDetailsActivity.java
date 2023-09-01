package com.growonline.gomobishop;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.CartDetailItemsAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetWishList;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;

public class WishListDetailsActivity extends BaseActivity {

    private ArrayList<ShoppingCartItem> mWishListDetailBean = new ArrayList<>();
    private FrameLayout mWishListEmptyProduct;
    private int returnUri;
    private RecyclerView recyclerView;
    private CartDetailItemsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list_details);
        returnUri = getIntent().getIntExtra(AppConstant.INTENT_WISH_LIST, 0);
        setToolBarTitle(getString(R.string.title_wish_list));

        mWishListEmptyProduct = (FrameLayout) findViewById(R.id.no_wishlist_item_found);

        recyclerView = (RecyclerView) findViewById(R.id.WishList_RecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));

        adapter = new CartDetailItemsAdapter(WishListDetailsActivity.this, mWishListDetailBean, new ArrayList<VendorItem>(), null);
        recyclerView.setAdapter(adapter);

        new AsyncTaskGetWishList(WishListDetailsActivity.this, true).execute();
    }

    public void onWishListObtained(ArrayList<ShoppingCartItem> mWishListModels, String mCustomerGuId) {

        if (mWishListModels == null) {
            mWishListEmptyProduct.setVisibility(View.VISIBLE);
            return;
        }
        if (mWishListModels.size() == 0)
            mWishListEmptyProduct.setVisibility(View.VISIBLE);

        this.mWishListDetailBean.clear();
        this.mWishListDetailBean.addAll(mWishListModels);
        adapter.setData(mWishListModels, new ArrayList<VendorItem>());
        adapter.notifyDataSetChanged();
    }

    public void updateList(boolean isProgressEnable) {
        new AsyncTaskGetWishList(WishListDetailsActivity.this, isProgressEnable).execute();
    }
}