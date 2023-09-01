package com.growonline.gomobishop;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.CartDetailItemsAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetOrderDetails;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.fragment.SingleAddressFragment;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.model.OrderDetail;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;

public class OrderDetailActivity extends BaseActivity {

    private int orderId;
    private TextView mOrderId;
    private TextView mOrderDate;
    private TextView mOrderStatus;
    private TextView mOrderTotal;
    private FrameLayout mBillingFragment;
    private FrameLayout mShippingFragment;
    private TextView mPaymentMethod;
    private TextView mPaymentStatus;
    private TextView mOrderShippingMethod;
    private TextView mOrderShippingStatus;
    private LinearLayout mSubtotalLayout;
    private FontTextView mTotal;
    private LinearLayout mDiscountLayout;
    private FontTextView mDiscount;
    private LinearLayout mRewardLayout;
    private FontTextView mReward;
    private LinearLayout mTotalPayableLayout;
    private FontTextView mTxtPayable;
    private RecyclerView recyclerView;
    private LinearLayout rewardLayoutRedeem;
    private FontTextView txtRewardRedeem;

    private SingleAddressFragment fragShippingAddress, fragBillingAddress;

    private CartDetailItemsAdapter adapter;
    private ArrayList<ShoppingCartItem> ShoppingCartItems = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        setToolBarTitle(getString(R.string.title_order_detail));
        orderId = getIntent().getIntExtra(AppConstant.INTENT_ORDER_ID, 0);
        InitUI();
        new AsyncTaskGetOrderDetails(OrderDetailActivity.this, String.valueOf(orderId), true).execute();
    }

    private void InitUI() {
        mOrderId = (TextView) findViewById(R.id.order_id);
        mOrderDate = (TextView) findViewById(R.id.order_date);
        mOrderStatus = (TextView) findViewById(R.id.order_status);
        mOrderTotal = (TextView) findViewById(R.id.order_total);
        mBillingFragment = (FrameLayout) findViewById(R.id.billing_fragment);
        mShippingFragment = (FrameLayout) findViewById(R.id.shipping_fragment);
        mPaymentMethod = (TextView) findViewById(R.id.payment_method);
        mPaymentStatus = (TextView) findViewById(R.id.payment_status);
        mOrderShippingMethod = (TextView) findViewById(R.id.order_shipping_method);
        mOrderShippingStatus = (TextView) findViewById(R.id.order_shipping_status);
        mSubtotalLayout = (LinearLayout) findViewById(R.id.subtotal_layout);
        mTotal = (FontTextView) findViewById(R.id.txt_total);
        mDiscountLayout = (LinearLayout) findViewById(R.id.discount_layout);
        mDiscount = (FontTextView) findViewById(R.id.txt_discount);
        mRewardLayout = (LinearLayout) findViewById(R.id.reward_layout);
        mReward = (FontTextView) findViewById(R.id.txt_reward);
        mTotalPayableLayout = (LinearLayout) findViewById(R.id.totalPayable_layout);
        mTxtPayable = (FontTextView) findViewById(R.id.txt_payable);
        rewardLayoutRedeem = (LinearLayout) findViewById(R.id.reward_layout_redeem);
        txtRewardRedeem = (FontTextView) findViewById(R.id.txt_reward_redeem);

        recyclerView = (RecyclerView) findViewById(R.id.Order_Items);
        recyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        recyclerView.setNestedScrollingEnabled(false);

        adapter = new CartDetailItemsAdapter(OrderDetailActivity.this, ShoppingCartItems, new ArrayList<VendorItem>(), null);
        recyclerView.setAdapter(adapter);

    }

    public void onDetailReceived(OrderDetail mOrderDetail) {

        if (mOrderDetail == null) return;

        mOrderId.setText(String.format(getString(R.string.order_id), String.valueOf(mOrderDetail.getId())));
        mOrderDate.setText(String.format(getString(R.string.order_date_time), GoMobileApp.ConvertUtcToDate(mOrderDetail.getCreatedOn())));
        mOrderStatus.setText(String.format(getString(R.string.order_status), mOrderDetail.getOrderStatus()));
        mOrderTotal.setText(String.format(getString(R.string.order_total), mOrderDetail.getOrderTotal()));
        mPaymentMethod.setText(String.format(getString(R.string.payment_method), mOrderDetail.getPaymentMethod()));
        mPaymentStatus.setText(String.format(getString(R.string.payment_status), mOrderDetail.getPaymentMethodStatus()));
        mOrderShippingMethod.setText(String.format(getString(R.string.shipping_method), mOrderDetail.getShippingMethod()));
        mOrderShippingStatus.setText(String.format(getString(R.string.shipping_status), mOrderDetail.getShippingStatus()));

        mTotal.setText(mOrderDetail.getOrderTotal());
        if (mOrderDetail.getOrderTotalDiscount() != null)
            mDiscount.setText(mOrderDetail.getOrderTotalDiscount());
        else
            mDiscountLayout.setVisibility(View.GONE);
        if (mOrderDetail.getRedeemedRewardPointsAmount() != null)
            mReward.setText(mOrderDetail.getRedeemedRewardPointsAmount());
        else
            mRewardLayout.setVisibility(View.GONE);

        if (mOrderDetail.getRedeemedRewardPointsAmount() != null)
            txtRewardRedeem.setText(mOrderDetail.getRedeemedRewardPointsAmount());
        else
            rewardLayoutRedeem.setVisibility(View.GONE);

        mTxtPayable.setText(mOrderDetail.getOrderTotal());


        fragShippingAddress = SingleAddressFragment.newInstance(mOrderDetail.getShippingAddress(),
                AppConstant.AddressType.SHIPPING, new ArrayList<AvalibleCountry>());
        getSupportFragmentManager().beginTransaction().replace(R.id.shipping_fragment, fragShippingAddress).commit();

        fragBillingAddress = SingleAddressFragment.newInstance(mOrderDetail.getBillingAddress(),
                AppConstant.AddressType.BILLING, new ArrayList<AvalibleCountry>());
        getSupportFragmentManager().beginTransaction().replace(R.id.billing_fragment, fragBillingAddress).commit();

        this.ShoppingCartItems.clear();
        this.ShoppingCartItems.addAll(mOrderDetail.getItems());
        adapter.notifyDataSetChanged();
    }


}