package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.model.Product;
import com.yayandroid.parallaxlistview.ParallaxImageView;
import com.yayandroid.parallaxlistview.ParallaxViewHolder;

import java.util.List;


public class DiscoverFeatureProductAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Product> mItems;
    private Context mContext;
    private AppCompatActivity mActivity;

    public DiscoverFeatureProductAdapter(Context context, List<Product> items, AppCompatActivity activity) {
        this.mItems = items;
        this.mContext = context;
        this.inflater = LayoutInflater.from(context);
        this.mActivity = activity;
    }


    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mItems.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Product p = mItems.get(position);
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.discover_feature_product_item, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.lbl_title);
            viewHolder.itemView = convertView;

            viewHolder.setBackgroundImage((ParallaxImageView) convertView.findViewById(R.id.parallaxImageView));
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (p.getDefaultPictureModel() != null)
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(p.getDefaultPictureModel().getFullSizeImageUrl()), viewHolder.getBackgroundImage());

        if (p.getName() != null) {
            viewHolder.textView.setText(p.getName());
        }

        // # CAUTION:
        // Important to call this method
//        viewHolder.getBackgroundImage().setParallaxRatio();
        viewHolder.getBackgroundImage().reuse();

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ((StoreActivity) mContext).setStoreTab(getCategoryId(p.getId()));
                StoreActivity sActivity = ((StoreActivity) mActivity);
                sActivity.openProductDetailFragment(p.getId(), p.getName(), mItems);
            }
        });

        return convertView;
    }

    static class ViewHolder extends ParallaxViewHolder {

        private TextView textView;

    }

}
