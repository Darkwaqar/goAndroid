package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.OnePageCheckoutActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPaymentAdapter extends BaseAdapter {
    private final Activity mActivity;
    private final LayoutInflater mLayoutInflator;
    private List<PaymentMethod> mPaymentMethods;
    private ArrayList<RadioButton> mRadioList;

    public CheckoutPaymentAdapter(Activity activity, List<PaymentMethod> paymentMethods) {
        this.mActivity = activity;
        mPaymentMethods = paymentMethods;
        mRadioList = new ArrayList<>();
        mLayoutInflator = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return mPaymentMethods.size();
    }

    @Override
    public Object getItem(int i) {
        return mPaymentMethods.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null)
            view = mLayoutInflator.inflate(R.layout.adapter_checkout_payment, viewGroup, false);

        RadioButton radioView = (RadioButton) view.findViewById(R.id.payment_rButton);
        ImageView imgView = (ImageView) view.findViewById(R.id.payment_imgView);
        TextView txtView = (TextView) view.findViewById(R.id.payment_txtView);
        TextView description = (TextView) view.findViewById(R.id.payment_description);
        txtView.setText(mPaymentMethods.get(i).getName());
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(mPaymentMethods.get(i).getLogoUrl()), imgView);
        description.setText(mPaymentMethods.get(i).getDescription());
        mRadioList.add(radioView);
        view.setTag(i);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (Integer) view.getTag();
                mRadioList.get(i).performClick();
                if (mActivity instanceof OnePageCheckoutActivity) {
                    ((OnePageCheckoutActivity) mActivity).OnPaymentOptionSelect(mPaymentMethods.get(i).getPaymentMethodSystemName());
                }
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
