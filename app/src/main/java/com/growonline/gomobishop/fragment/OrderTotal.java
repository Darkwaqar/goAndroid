package com.growonline.gomobishop.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.OnePageCheckoutActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.model.Total;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderTotal extends Fragment {
    private static final String ARG_PARAM1 = "OrderTotal";
    private LinearLayout subtotalLayout;
    private FontTextView txtTotal;
    private LinearLayout discountLayout;
    private FontTextView txtDiscount;
    private LinearLayout rewardLayout;
    private LinearLayout rewardLayoutRedeem;
    private FontTextView txtReward;
    private FontTextView txtRewardRedeem;
    private LinearLayout deliveryLayout;
    private FontTextView txtDelivery;
    private LinearLayout taxLayout;
    private FontTextView txtTax;
    private LinearLayout totalPayableLayout;
    private FontTextView txtPayable;
    private Total orderTotal;


    public OrderTotal() {
        // Required empty public constructor
    }

    public static OrderTotal newInstance(Total param1) {
        OrderTotal fragment = new OrderTotal();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderTotal = getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_total, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        subtotalLayout = (LinearLayout) view.findViewById(R.id.subtotal_layout);
        txtTotal = (FontTextView) view.findViewById(R.id.txt_total);
        discountLayout = (LinearLayout) view.findViewById(R.id.discount_layout);
        txtDiscount = (FontTextView) view.findViewById(R.id.txt_discount);
        rewardLayout = (LinearLayout) view.findViewById(R.id.reward_layout);
        rewardLayoutRedeem = (LinearLayout) view.findViewById(R.id.reward_layout_redeem);
        txtReward = (FontTextView) view.findViewById(R.id.txt_reward);
        txtRewardRedeem = (FontTextView) view.findViewById(R.id.txt_reward_redeem);
        deliveryLayout = (LinearLayout) view.findViewById(R.id.delivery_layout);
        txtDelivery = (FontTextView) view.findViewById(R.id.txt_delivery);
        taxLayout = (LinearLayout) view.findViewById(R.id.tax_layout);
        txtTax = (FontTextView) view.findViewById(R.id.txt_tax);
        totalPayableLayout = (LinearLayout) view.findViewById(R.id.totalPayable_layout);
        txtPayable = (FontTextView) view.findViewById(R.id.txt_payable);
        if (orderTotal.getSubTotal() != null) {
            txtTotal.setText(orderTotal.getSubTotal());
            if (orderTotal.getOrderTotal() != null)
                txtPayable.setText(orderTotal.getOrderTotal());
            else
                txtPayable.setText(orderTotal.getSubTotal());

            if (orderTotal.getOrderTotalDiscount() != null) {
                txtDiscount.setText(orderTotal.getOrderTotalDiscount());
            } else {
                discountLayout.setVisibility(View.GONE);
            }
            if (orderTotal.getRedeemedRewardPointsAmount() != null)
                txtRewardRedeem.setText(orderTotal.getRedeemedRewardPointsAmount());
            else
                rewardLayoutRedeem.setVisibility(View.GONE);

            if (orderTotal.getWillEarnRewardPoints() > 0) {
                txtReward.setText(String.valueOf(orderTotal.getWillEarnRewardPoints()));
            } else {
                rewardLayout.setVisibility(View.GONE);
            }
            if (orderTotal.getShipping() != null) {
                txtDelivery.setText(orderTotal.getShipping());
            } else {
                deliveryLayout.setVisibility(View.GONE);
            }
            if (orderTotal.getTax() != null) {
                txtTax.setText(orderTotal.getTax());
                if (getActivity() instanceof OnePageCheckoutActivity)
                    taxLayout.setVisibility(View.VISIBLE);
                else
                    taxLayout.setVisibility(View.GONE);
            } else {
                taxLayout.setVisibility(View.GONE);
            }

        }
    }

}
