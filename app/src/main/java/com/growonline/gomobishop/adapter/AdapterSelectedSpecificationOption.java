package com.growonline.gomobishop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.SpecificationAttributeOption;

import java.util.ArrayList;
import java.util.List;


class AdapterSelectedSpecificationOption extends RecyclerView.Adapter<AdapterSelectedSpecificationOption.AdapterSelectedSpecificationOptionViewHolder> {

    private List<SpecificationAttributeOption> mOptions = new ArrayList<>();
    private ProductListingFragment mProductLisingFrag;

    AdapterSelectedSpecificationOption(List<SpecificationAttributeOption> options, ProductListingFragment productListingFrag) {
        if (options != null)
            this.mOptions = options;

        this.mProductLisingFrag = productListingFrag;
    }

    @Override
    public AdapterSelectedSpecificationOptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_selectd_specification_option, parent, false);

        return new AdapterSelectedSpecificationOption.AdapterSelectedSpecificationOptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterSelectedSpecificationOptionViewHolder holder, int position) {

        SpecificationAttributeOption temp = mOptions.get(position);
        holder.txt_title.setText(temp.getName());
        holder.position = position;
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    class AdapterSelectedSpecificationOptionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public int id, position;
        public TextView txt_title;
        ImageView btn_remove;
        public LinearLayout container;

        AdapterSelectedSpecificationOptionViewHolder(View itemView) {
            super(itemView);

            txt_title = (TextView) itemView.findViewById(R.id.txt_name);
            btn_remove = (ImageView) itemView.findViewById(R.id.btn_remove);
            container = (LinearLayout) itemView.findViewById(R.id.single_selected_item_container);

            container.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mOptions.get(position).setSelected(false);
            mProductLisingFrag.notifyDataSetChanged();
            mProductLisingFrag.loadDataRestartPaging();
        }
    }

}
