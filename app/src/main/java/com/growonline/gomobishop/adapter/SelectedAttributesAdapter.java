package com.growonline.gomobishop.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.UserSelectedAttribute;

import java.util.List;


public class SelectedAttributesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<UserSelectedAttribute> mSelectedAttributes;

    private int mTextType = 0, mColorType = 1;

    public SelectedAttributesAdapter(List<UserSelectedAttribute> selectedAttributes){
        mSelectedAttributes = selectedAttributes;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == mTextType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_selected_attributes_text, parent, false);

            return new SelectedAttributesAdapter.SelectedAttributeTextViewHolder(itemView);
        } else {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_selected_attribute_color, parent, false);

            return new SelectedAttributesAdapter.SelectedAttributeColorViewHolder(itemView);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        UserSelectedAttribute currentAttributeSelected = mSelectedAttributes.get(position);
        if(currentAttributeSelected.getType().equalsIgnoreCase("40")){

            SelectedAttributeColorViewHolder hld = (SelectedAttributeColorViewHolder) holder;
            hld.title.setText(currentAttributeSelected.getAttributeName());
            hld.value.setBackgroundColor(Color.parseColor(currentAttributeSelected.getValueText()));

        } else {
            SelectedAttributeTextViewHolder hld = (SelectedAttributeTextViewHolder) holder;
            hld.title.setText(currentAttributeSelected.getAttributeName());
            hld.value.setText(currentAttributeSelected.getValueText());

        }
    }

    @Override
    public int getItemCount() {
        return mSelectedAttributes.size();
    }

    @Override
    public int getItemViewType(int position) {

        UserSelectedAttribute temp = mSelectedAttributes.get(position);
        if(temp.getType().equalsIgnoreCase("40"))
            return mColorType;
        else
            return mTextType;
    }

    private class SelectedAttributeTextViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView value;

        SelectedAttributeTextViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_title);
            value = (TextView) itemView.findViewById(R.id.lbl_selected_value);
        }
    }

    private class SelectedAttributeColorViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public View value;

        SelectedAttributeColorViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.lbl_title);
            value = itemView.findViewById(R.id.lbl_selected_value);
        }
    }

}