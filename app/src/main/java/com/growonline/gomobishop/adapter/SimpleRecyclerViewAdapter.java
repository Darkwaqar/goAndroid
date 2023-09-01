package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.CategorySimpleModel;

import java.util.List;

/**
 * Created by asifrizvi on 4/4/2018.
 */

public class SimpleRecyclerViewAdapter extends RecyclerView.Adapter<SimpleRecyclerViewAdapter.ItemViewHolder> {
    private Activity MActivity;
    private List<CategorySimpleModel> categories;
    private int width;

    public SimpleRecyclerViewAdapter(FragmentActivity activity, List<CategorySimpleModel> categories, int width) {
        this.MActivity = activity;
        this.categories = categories;
        this.width = width;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simplecategoryitem, parent, false);

        return new ItemViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(SimpleRecyclerViewAdapter.ItemViewHolder holder, int position) {
        holder.categoryName.setText(categories.get(position).getName());
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(categories.get(position).getImage()), holder.categoryImage);
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryName;

        public ItemViewHolder(View view) {
            super(view);
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            categoryName = (TextView) view.findViewById(R.id.categoryName);
            if (width > 0)
                categoryImage.getLayoutParams().width = width;
        }

    }
}
