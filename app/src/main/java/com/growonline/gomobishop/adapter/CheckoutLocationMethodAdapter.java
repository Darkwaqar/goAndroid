package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.ShippingMethodBean;

import java.util.ArrayList;
import java.util.List;

public class CheckoutLocationMethodAdapter extends BaseAdapter {
    private final LayoutInflater mInflator;
    private List<ShippingMethodBean> mMethods;
    private ArrayList<RadioButton> mRadioList;

    public CheckoutLocationMethodAdapter(Activity activityOnePageCheckout, List<ShippingMethodBean> methods) {
        mMethods = methods;
        mInflator = LayoutInflater.from(activityOnePageCheckout);
        mRadioList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mMethods.size();
    }

    @Override
    public Object getItem(int i) {
        return mMethods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = mInflator.inflate(R.layout.adapter_checkout_lmethod, viewGroup, false);

        RadioButton radioView = (RadioButton) view.findViewById(R.id.method_rButton);
        TextView MethodName = (TextView) view.findViewById(R.id.method_txtView);
        TextView description = (TextView) view.findViewById(R.id.method_description);
        TextView shippingFee = (TextView) view.findViewById(R.id.shipping_fee);
        description.setText(mMethods.get(i).getDescription());
        MethodName.setText(mMethods.get(i).getName());

        String fee = mMethods.get(i).getFee();

        if (fee != null && !fee.equalsIgnoreCase("")) {

            if (fee.contains(" ")) {
                String[] fees = fee.split(" ");
                if (fees.length > 1)
                    fee = fees[1].equalsIgnoreCase("0") ? "FREE" : fee;
            }
        }
        shippingFee.setText(fee);
        shippingFee.setVisibility(View.GONE);

        mRadioList.add(radioView);
        view.setTag(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (Integer) view.getTag();
                mRadioList.get(i).performClick();
            }
        });
        if (i == 0)
            radioView.setChecked(true);
        return view;
    }

    public ArrayList<RadioButton> getmRadioList() {
        return mRadioList;
    }
}
