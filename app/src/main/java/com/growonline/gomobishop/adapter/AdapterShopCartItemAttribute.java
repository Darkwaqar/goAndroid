package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.AttributeValue;

import java.util.List;


class AdapterShopCartItemAttribute extends ArrayAdapter<AttributeValue> {

    private Context mContext;
    private List<AttributeValue> mListItems;


    AdapterShopCartItemAttribute(Context context, int textViewResourceId, List<AttributeValue> objects) {
        super(context, textViewResourceId, objects);
        init(context);
        mListItems = objects;
    }

    private void init(Context c) {
        this.mContext = c;
    }


    @Override
    public int getCount() {
        return mListItems.size();
    }

    @Nullable
    @Override
    public AttributeValue getItem(int position) {
        return mListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mListItems.get(position).getId());
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        TextView label = new TextView(mContext);
        label.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        label.setText(mListItems.get(position).getName());

        return label;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                @NonNull ViewGroup parent) {

        TextView label = new TextView(mContext);
        label.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        label.setText(mListItems.get(position).getName());

        return label;
    }

}
