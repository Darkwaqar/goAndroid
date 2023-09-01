package com.growonline.gomobishop.control;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.fragment.NotificationBadge;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

public class BaseToolbar {

    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private RelativeLayout mBtnShopCart;
    private FontTextView mTitleTextView;
    private NotificationBadge badge;
    private LinearLayout mTitleStorePanel;
    private ToolBarType mToolbarType;
    private ImageView mLeftImageView, mArrowMinimize;
    private Vendor mVendor;
    private LinearLayout mCustomLayout;

    public enum ToolBarType {DEFAULT, TITLE_IMAGE, CUSTOMIZABLE}

    public BaseToolbar(AppCompatActivity activity, ToolBarType type) {
        this.mActivity = activity;
        this.mToolbarType = type;
        initToolBar();
    }

    private void initToolBar() {
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View view = layoutInflater.inflate(R.layout.base_toolbar, null);
        mToolbar = (Toolbar) view.findViewById(R.id.base_toolbar_layout);
        mCustomLayout = (LinearLayout) view.findViewById(R.id.toolbar_custom_layout);
        mTitleTextView = (FontTextView) view.findViewById(R.id.toolbar_title_textview);
        mTitleStorePanel = (LinearLayout) view.findViewById(R.id.title_store_panel);
        mLeftImageView = (ImageView) view.findViewById(R.id.toolbar_left_nav_imageview);
        mBtnShopCart = (RelativeLayout) view.findViewById(R.id.btn_shopCart);
        badge = (NotificationBadge) view.findViewById(R.id.base_toolbar_badge);
        mArrowMinimize = (ImageView) view.findViewById(R.id.icon_minimize);

        if (mToolbarType != ToolBarType.DEFAULT)
            mCustomLayout.setVisibility(View.VISIBLE);

        mBtnShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, ShopCartActivity.class);
                i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                mActivity.startActivity(i);
            }
        });
    }

    public void setToolBarTitle(String title) {
        mTitleTextView.setText(mVendor.getName().toUpperCase());
    }

    public void setToolBarTitle(@StringRes @DrawableRes final int title) {
        String type = mActivity.getResources().getResourceTypeName(title);

        if (type.equalsIgnoreCase("string")) {
            if (mToolbarType == ToolBarType.DEFAULT) {
                mToolbar.setTitle(mActivity.getString(title));
                mTitleTextView.setVisibility(View.GONE);
                //mTitleImageView.setVisibility(View.GONE);
            } else {
                mToolbar.setTitle("");
                mTitleTextView.setText(mActivity.getString(title));
                //mTitleImageView.setVisibility(View.GONE);
                mTitleTextView.setVisibility(View.VISIBLE);
            }
        } else if (type.equalsIgnoreCase("drawable")) {
            mToolbar.setTitle("");
            //mTitleImageView.setImageResource(title);
            //mTitleImageView.setVisibility(View.VISIBLE);
            mTitleTextView.setVisibility(View.GONE);
        }
    }

    public void setNavigationProps(@DrawableRes int drawable, View.OnClickListener listener) {
        mLeftImageView.setVisibility(View.VISIBLE);
        mLeftImageView.setImageResource(drawable);
        mLeftImageView.setOnClickListener(listener);
    }

    public void install(AppBarLayout mAppBarLayout) {

        if (mAppBarLayout != null) mAppBarLayout.addView(mToolbar);

        mActivity.setSupportActionBar(mToolbar);
        mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    public Toolbar exportToolBar() {
        return mToolbar;
    }

    public void setBackgroundColor(int white) {
        mToolbar.setBackgroundColor(white);
    }

    public void setBackgroundColor(String hexCode) {
        mToolbar.setBackgroundColor(Color.parseColor(hexCode));
    }

    public void setMinimizeStoreClickListener(View.OnClickListener clickListener) {
        mTitleStorePanel.setOnClickListener(clickListener);
    }

    public void setArrowDirection(boolean up) {
        if (up)
            mArrowMinimize.setImageResource(R.drawable.ic_maximize);
        else
            mArrowMinimize.setImageResource(R.drawable.ic_minimize);
    }

    public void setVendor(Vendor vendor) {
        mVendor = vendor;
    }

    public void setShoppingCartItemCount(int count) {
        badge.setNumber(count);
    }

    public void refreshShopCartBadge() {
        badge.setNumber(GoMobileApp.getShoppingCartItemsCount());
    }

}
