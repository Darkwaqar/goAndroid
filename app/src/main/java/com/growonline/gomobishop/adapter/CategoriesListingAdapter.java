package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.HorizontalListView;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.ParallaxBaseAdapter;
import com.growonline.gomobishop.model.Category;

import java.util.List;


public class CategoriesListingAdapter extends ParallaxBaseAdapter {

    private final String[] overlayColors = new String[]{"#e06f06", "#5bb714",
            "#0c667a", "#68061d", "#680962", "#edd207"};

    private final Activity mActivity;
    private final LayoutInflater mInflator;
    private final List<Category> mCategories;
    private final int screenWidth = GoMobileApp.getScreenWidth();
    private final float mGetPercentageTextSize = (screenWidth * 0.06f);

    public CategoriesListingAdapter(Activity activity, List<Category> categories) {
        mActivity = activity;
        mInflator = LayoutInflater.from(activity);
        mCategories = categories;
    }

    @Override
    public int getCount() {
        return mCategories.size();
    }

    @Override
    public Object getItem(int position) {
        return mCategories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mCategories.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.category_single_layout, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.lbl_category);
            viewHolder.itemView = convertView;
            viewHolder.mImageView = (ImageView) convertView.findViewById(R.id.img_category);
//            viewHolder.subCategoryListView=(HorizontalListView) convertView.findViewById(R.id.subCategoryListView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Category tempCategory = mCategories.get(position);


        GoMobileApp.getmCacheManager().loadImage(Uri.parse(tempCategory.getPictureModel().getImageUrl()), viewHolder.mImageView);
        formatTitle(tempCategory.getName(), viewHolder.textView);

//        View overlay = convertView.findViewById(R.id.overlay);
//
//        int colorsCount = overlayColors.length;
//        overlay.setBackgroundColor(Color.parseColor(overlayColors[position % colorsCount]));
//
//        overlay.setAlpha(0.70f);
        return convertView;
    }


    private void formatTitle(String title, TextView txtView) {
        txtView.setText(title);
        title = title.trim();

        if (title.length() > 0) {
            SpannableStringBuilder ss = new SpannableStringBuilder(title);
            StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
            ss.setSpan(bss, 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            GoMobileApp.convertPixelsToDp(mGetPercentageTextSize);
            txtView.setText(ss);
        }
    }

    @Override
    public int getParallaxViewId(int pos) {
        return R.id.img_category;
    }

    static class ViewHolder {

        public View itemView;
        ImageView mImageView;
        HorizontalListView subCategoryListView;
        private TextView textView;
    }


}
