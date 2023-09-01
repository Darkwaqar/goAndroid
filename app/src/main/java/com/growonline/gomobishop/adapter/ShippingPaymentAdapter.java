package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.PaymentMethod;
import com.growonline.gomobishop.model.ShippingMethodBean;

import java.util.List;

public class ShippingPaymentAdapter extends PagerAdapter {

    private boolean isShippingAdapter = false;
    private Context mContext;
    private List<PaymentMethod> mPaymentMethods;
    private List<ShippingMethodBean> mShippingMethods;


    public ShippingPaymentAdapter(Context context, List<PaymentMethod> paymentMethods, List<ShippingMethodBean> shippingMethods) {

        this.mContext = context;

        if (paymentMethods == null)
            this.isShippingAdapter = true;

        if (isShippingAdapter) {
            this.mShippingMethods = shippingMethods;

            //add fake item at 0 index
            this.mShippingMethods.add(0, new ShippingMethodBean());
        } else {
            this.mPaymentMethods = paymentMethods;

            //add fake item at 0 index
            this.mPaymentMethods.add(0, new PaymentMethod());
        }

    }

    @Override
    public int getCount() {
        if (isShippingAdapter)
            return mShippingMethods.size();
        else
            return mPaymentMethods.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewGroup layout;

        if (position == 0) {
            layout = (ViewGroup) inflater.inflate(R.layout.empty_layout, container, false);
            return layout;
        }

        if (isShippingAdapter) {

            ShippingMethodBean tempItem = mShippingMethods.get(position);

            layout = (ViewGroup) inflater.inflate(R.layout.single_shipping_option, container, false);
            TextView txt_title = (TextView) layout.findViewById(R.id.lbl_title);
            TextView txt_desc = (TextView) layout.findViewById(R.id.lbl_desc);
            TextView txt_fee = (TextView) layout.findViewById(R.id.lbl_fee);
            txt_title.setText(tempItem.getName());
            txt_desc.setText(tempItem.getDescription());
            txt_fee.setText(tempItem.getFee());
        } else {

            PaymentMethod tempItem = mPaymentMethods.get(position);
            layout = (ViewGroup) inflater.inflate(R.layout.single_payment_option, container, false);
            TextView txt_title = (TextView) layout.findViewById(R.id.lbl_title);
            txt_title.setText(tempItem.getName());

        }

        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }
}
