package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 6/12/2020.
 */

public class CountryAdapter extends BaseAdapter {
    private final Activity mActivity;
    private final LayoutInflater mLayoutInflator;
    private List<PaymentMethod> mPaymentMethods;
    private ArrayList<RadioButton> mRadioList;
    private List<View> allViews;

    public CountryAdapter(Activity activity, List<PaymentMethod> paymentMethods) {
        this.mActivity = activity;
        mPaymentMethods = paymentMethods;
        mRadioList = new ArrayList<>();
        allViews = new ArrayList<>();

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
    public View getView(final int position, View view, ViewGroup viewGroup) {

        if (view == null)
            view = mLayoutInflator.inflate(R.layout.country_item, viewGroup, false);

        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = GoMobileApp.getScreenWidth() / mPaymentMethods.size() - 50;
        view.setLayoutParams(params);
        final RadioButton radioView = (RadioButton) view.findViewById(R.id.payment_rButton);
        ImageView imgView = (ImageView) view.findViewById(R.id.payment_imgView);
        final LinearLayout background = (LinearLayout) view.findViewById(R.id.thumbnail);
        TextView txtView = (TextView) view.findViewById(R.id.payment_txtView);
        txtView.setText(mPaymentMethods.get(position).getName());
        if (mPaymentMethods.get(position).getImageId() != 0)
            GoMobileApp.getmCacheManager().loadImage(mPaymentMethods.get(position).getImageId(), imgView, null);
        else
            imgView.setVisibility(View.GONE);
        mRadioList.add(radioView);
        allViews.add(view);
        view.setTag(position);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (Integer) view.getTag();
                mRadioList.get(i).performClick();
                for (View v : allViews) {
                    if ((Integer) v.getTag() == position) {
                        v.findViewById(R.id.thumbnail).setBackground(mActivity.getResources().getDrawable(R.drawable.country_gey_rounded_border_filed));
                    } else {
                        v.findViewById(R.id.thumbnail).setBackground(mActivity.getResources().getDrawable(R.drawable.country_grey_rounded_border));
                    }
                }
//                if (mPaymentMethods.get(position).getName().equals("United Kingdom")) {
//                    GoMobileApp.addToSharedPrefs(AppConstant.BASE_URL_SET, "https://www.appnabazaar.com/");
//                    ((CountrySelectorActivity) mActivity).populateCurrency();
//                } else if (mPaymentMethods.get(position).getId() == -1) {
//                    GoMobileApp.addToSharedPrefs(AppConstant.BASE_URL_SET, mActivity.getString(R.string.BASE_DOMAIN));
//                    ((CountrySelectorActivity) mActivity).populateCurrency();
//                }
            }
        });
        if (position == 0) {
            radioView.setChecked(true);
            background.setBackground(mActivity.getResources().getDrawable(R.drawable.country_gey_rounded_border_filed));

        }

        return view;
    }

    public ArrayList<RadioButton> getmRadioList() {
        return mRadioList;
    }

}
