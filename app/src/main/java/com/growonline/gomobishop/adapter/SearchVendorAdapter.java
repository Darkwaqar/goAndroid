package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.SearchActivity;
import com.growonline.gomobishop.model.AvailableCategory;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class SearchVendorAdapter extends RecyclerView.Adapter<SearchVendorAdapter.SearchVendorViewHolder> {

    private SearchActivity mContext;
    private List<AvailableCategory> mSearchModel = new ArrayList<>();
    private int type;

    public SearchVendorAdapter(Context context, int type) {
        this.mContext = (SearchActivity) context;
        this.type = type;
    }


    @Override
    public SearchVendorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_selectd_specification_option, parent, false);

        return new SearchVendorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SearchVendorViewHolder holder, int position) {
        AvailableCategory category = mSearchModel.get(position);

        AppHelper.applyRobotoRegularFont(holder.txt_title);

        holder.txt_title.setText(category.getText().toUpperCase());
        holder.id = category.getValue();
        if (category.getSelected()) {
            holder.btn_remove.setVisibility(View.VISIBLE);
            holder.single_selected_item_container.setBackground(mContext.getResources().getDrawable(R.drawable.single_selected_filter_bg));
//            holder.txt_title.setTextColor(mContext.getResources().getColor(R.color.white));
        }


        holder.setIsRecyclable(false);
    }


    @Override
    public int getItemCount() {
        return mSearchModel.size();
    }

    public void setVendorList(List<AvailableCategory> searchModel) {
        this.mSearchModel.clear();
        this.mSearchModel.addAll(searchModel);
    }

    class SearchVendorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView txt_title;
        ImageView btn_remove;
        int id = 0;
        LinearLayout single_selected_item_container;

        SearchVendorViewHolder(View itemView) {
            super(itemView);
            txt_title = (TextView) itemView.findViewById(R.id.txt_name);
            btn_remove = (ImageView) itemView.findViewById(R.id.btn_remove);
            single_selected_item_container = (LinearLayout) itemView.findViewById(R.id.single_selected_item_container);
            btn_remove.setVisibility(View.GONE);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (type == AppConstant.TYPE_CATEGORY)
                mContext.LoadCategory(id);
            else if (type == AppConstant.TYPE_VENDOR)
                mContext.LoadVendor(id);
        }
    }


}
