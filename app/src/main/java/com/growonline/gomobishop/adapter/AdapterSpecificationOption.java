package com.growonline.gomobishop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.SpecificationAttributeOption;

import java.util.List;

public class AdapterSpecificationOption extends RecyclerView.Adapter<AdapterSpecificationOption.SpecificationOptionViewHolder> {

    private AppCompatActivity mActivity;
    private List<SpecificationAttributeOption> mSpecificationOptions;
    private ProductListingFragment mProductListingFrag;

    public AdapterSpecificationOption(AppCompatActivity activity, List<SpecificationAttributeOption> specificationOptions,
            ProductListingFragment productListingFrag) {
        this.mActivity = activity;
        this.mSpecificationOptions = specificationOptions;
        this.mProductListingFrag = productListingFrag;
    }

    @Override
    public AdapterSpecificationOption.SpecificationOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_specification_option, parent, false);

        return new AdapterSpecificationOption.SpecificationOptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSpecificationOption.SpecificationOptionViewHolder holder, int position) {

        final SpecificationAttributeOption temp = mSpecificationOptions.get(position);

        holder.id = temp.getId();
        holder.chk_title.setText(temp.getName());

        holder.chk_title.setOnCheckedChangeListener(null);
        holder.chk_title.setChecked(temp.isSelected());

        holder.chk_title.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                temp.setSelected(b);
                mProductListingFrag.notifyDataSetChanged();
                mProductListingFrag.notifySelectedOptionId(temp.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSpecificationOptions.size();
    }

    class SpecificationOptionViewHolder extends RecyclerView.ViewHolder {

        public int id;
        CheckBox chk_title;

        SpecificationOptionViewHolder(View itemView) {
            super(itemView);

            chk_title = (CheckBox) itemView.findViewById(R.id.chk_name);
        }
    }

}
