package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.fragment.SingleProductDetailFragment;
import com.growonline.gomobishop.fragment.SingleProductDetailFragmentPush;
import com.growonline.gomobishop.model.AttributeValue;
import com.growonline.gomobishop.model.ProductAttribute;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class AdapterAttributeDetail extends BaseAdapter {
    private ProductAttribute mProductAttribute;
    private Fragment mFragment;
    private List<AttributeValue> mList;
    private ArrayList<AttributeValue> mSelectedList;
    private LayoutInflater mLayoutInflator;
    private ArrayList<RadioButton> mRadioList;

    private RadioButton mProductAttrImageView;
    private TextView mProductAttrPrice;
    private View mAttributeColor;
    private LinearLayout mAttributeItem;
    private TextView mAttributeName;

    public AdapterAttributeDetail(FragmentActivity activity, ProductAttribute productAttribute, Fragment fragmentDetailParent) {
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mFragment = fragmentDetailParent;
        this.mProductAttribute = productAttribute;
        this.mList = productAttribute.getValues();
        this.mRadioList = new ArrayList<>();
        this.mSelectedList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mList.get(i).getId();
    }

    @Override
    public View getView(int pos, View view, ViewGroup viewGroup) {
        if (view == null)
            view = mLayoutInflator.inflate(R.layout.adapter_attribute, viewGroup, false);

        mAttributeItem = (LinearLayout) view.findViewById(R.id.layout_root_adapter_attribute);
        mAttributeColor = view.findViewById(R.id.attribute_color);
        mProductAttrImageView = (RadioButton) view.findViewById(R.id.detail_parent_product_attr_imgview);
        mAttributeName = (TextView) view.findViewById(R.id.attribute_name);
        mProductAttrPrice = (TextView) view.findViewById(R.id.detail_parent_product_attr_price);

        AttributeValue bean = mList.get(pos);


        if (bean.getColorSquaresRgb() != null) {
            mAttributeColor.setBackgroundColor(Color.parseColor(bean.getColorSquaresRgb()));
        } else {
            mAttributeColor.setVisibility(View.GONE);
            mProductAttrImageView.setVisibility(View.VISIBLE);
        }

        mAttributeName.setText(bean.getName());
        view.setTag(pos);

        mProductAttrImageView.setTag(pos);
        mRadioList.add(mProductAttrImageView);

        if (bean.getPriceAdjustment() != null)
            mProductAttrPrice.setText("[".concat(bean.getPriceAdjustment()).concat("]"));
        else
            mProductAttrPrice.setVisibility(View.GONE);

        if (mProductAttribute.getAttributeControlType().equals(AppConstant.CHECKBOX)) {
            mAttributeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton a = view.findViewById(R.id.detail_parent_product_attr_imgview);
                    int pos = (Integer) view.getTag();
                    if (!a.isChecked()) {
                        a.setChecked(true);
                        mSelectedList.add(mList.get(pos));
                    } else {
                        a.setChecked(false);
                        mSelectedList.remove(mList.get(pos));
                    }

                    if (mFragment instanceof SingleProductDetailFragment) {
                        SingleProductDetailFragment singleProductDetailFragment = (SingleProductDetailFragment) mFragment;
                        singleProductDetailFragment.showNextButton(mSelectedList.size() > 0);
                    } else if (mFragment instanceof SingleProductDetailFragmentPush) {
                        SingleProductDetailFragmentPush singleProductDetailFragmentPush = (SingleProductDetailFragmentPush) mFragment;
                        singleProductDetailFragmentPush.showNextButton(mSelectedList.size() > 0);
                    }
                }
            });
        } else {
            mAttributeItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (Integer) view.getTag();
                    if (mFragment instanceof SingleProductDetailFragment) {
                        SingleProductDetailFragment a = (SingleProductDetailFragment) mFragment;
                        a.onAttrbutemarked(mList.get(pos), mProductAttribute);
                    } else if (mFragment instanceof SingleProductDetailFragmentPush) {
                        SingleProductDetailFragmentPush a = (SingleProductDetailFragmentPush) mFragment;
                        a.onAttrbutemarked(mList.get(pos), mProductAttribute);
                    }

                }
            });
        }

//        mProductAttrImageView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                mAttributeItem.performClick();
//            }
//        });


        return view;
    }

    public ArrayList<AttributeValue> getSelectedList() {
        return mSelectedList;
    }
}