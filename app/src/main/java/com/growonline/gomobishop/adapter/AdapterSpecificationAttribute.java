package com.growonline.gomobishop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.CustomLinearLayoutManager;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.model.SpecificationAttributeOption;

import java.util.ArrayList;
import java.util.List;

public class AdapterSpecificationAttribute extends RecyclerView.Adapter<AdapterSpecificationAttribute.SpecificationAttributeViewHolder> {

    private AppCompatActivity mActivity;
    private ProductListingFragment mProductListingFrag;
    private List<SpecificationAttribute> mSpecificationAttributes;

    public AdapterSpecificationAttribute(AppCompatActivity activity, ProductListingFragment productListingFrag, List<SpecificationAttribute> mSpecificationAttributes) {
        this.mActivity = activity;
        this.mProductListingFrag = productListingFrag;
        this.mSpecificationAttributes = mSpecificationAttributes;
    }

    @Override
    public SpecificationAttributeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_specification_attribute, parent, false);

        return new SpecificationAttributeViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SpecificationAttributeViewHolder holder, int position) {

        SpecificationAttribute temp = mSpecificationAttributes.get(position);

        holder.id = temp.getId();
        holder.txt_title.setText(temp.getName());


        CustomLinearLayoutManager lMgr = new CustomLinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false);
        holder.rvOptions.setLayoutManager(lMgr);
        AdapterSelectedSpecificationOption adapter = new AdapterSelectedSpecificationOption(getSelectedAttributes(temp.getId()), mProductListingFrag);
        holder.rvOptions.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return mSpecificationAttributes.size();
    }

    class SpecificationAttributeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int id;
        public TextView txt_title;
        RecyclerView rvOptions;
        private int[] lastTouchDownXY = new int[2];

        SpecificationAttributeViewHolder(View itemView) {
            super(itemView);

            txt_title = (TextView) itemView.findViewById(R.id.txt_name);
            rvOptions = (RecyclerView) itemView.findViewById(R.id.rv_option_selected);
            txt_title.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.txt_name) {
                view.getLocationOnScreen(lastTouchDownXY);
                int x = lastTouchDownXY[0];
                int y=view.getWidth();
                mProductListingFrag.showFilterOption(id,x,y);
            }
        }


    }

    private List<SpecificationAttributeOption> getSelectedAttributes(int specificationAttributeId) {
        List<SpecificationAttributeOption> temp = new ArrayList<>();

        for (int i = 0; i < mSpecificationAttributes.size(); i++) {
            SpecificationAttribute tempAttr = mSpecificationAttributes.get(i);

            if (tempAttr.getId() == specificationAttributeId) {

                for (int j = 0; j < tempAttr.getOptions().size(); j++) {
                    SpecificationAttributeOption tempOption = tempAttr.getOptions().get(j);

                    if (tempOption.isSelected())
                        temp.add(tempOption);
                }

                break;
            }
        }
        return temp;
    }
}