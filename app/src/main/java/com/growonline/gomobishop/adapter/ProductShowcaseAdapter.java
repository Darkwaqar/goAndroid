package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.fragment.SingleProductDetailFragment;
import com.growonline.gomobishop.fragment.SingleProductDetailFragmentPush;
import com.growonline.gomobishop.model.DefaultPictureModel;
import com.growonline.gomobishop.model.ProductPointer;
import com.growonline.gomobishop.model.Vendor;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

public class ProductShowcaseAdapter extends PagerAdapter {

    private final String mProductName;
    private Context mContext;
    private Fragment mFragment;
    private List<DefaultPictureModel> mImages = new ArrayList<>();
    private Vendor mVendor;
    private PhotoViewAttacher mAttacher;
    private ArrayList<ProductPointer> mProductPointers;


    public ProductShowcaseAdapter(Context context, List<DefaultPictureModel> images, ArrayList<ProductPointer> mProductPointers, String proName, Vendor vendor, Fragment fragment) {
        mImages = images;
        mContext = context;
        mProductName = proName;
        mVendor = vendor;
        mFragment = fragment;
        this.mProductPointers = mProductPointers;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.single_product_showcase_item, container, false);

        ImageView imgView = (ImageView) layout.findViewById(R.id.single_prd_item_image);
        ProgressBar pBar = (ProgressBar) layout.findViewById(R.id.single_prd_item_image_loader);
        final FrameLayout hotSpotContainer = (FrameLayout) layout.findViewById(R.id.frame_layout_hotspots);
        final DefaultPictureModel tempItem = mImages.get(position);
        if (mProductPointers.size() == 0) {
            mAttacher = new PhotoViewAttacher(imgView);
            mAttacher.setOnViewTapListener(new PhotoViewAttacher.OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    if (mFragment instanceof SingleProductDetailFragment)
                        ((SingleProductDetailFragment) mFragment).HideInfoAndAttributes();
                    else
                        ((SingleProductDetailFragmentPush) mFragment).HideInfoAndAttributes();
                }
            });
        }
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFragment instanceof SingleProductDetailFragment)
                    ((SingleProductDetailFragment) mFragment).HideShopTheLook();
                else
                    ((SingleProductDetailFragmentPush) mFragment).HideShopTheLook();
            }
        });

        GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(tempItem.getFullSizeImageUrl()), imgView, pBar);

        hotSpotContainer.post(new Runnable() {
            @Override
            public void run() {
                if (mProductPointers != null && mProductPointers.size() > 0) {
                    placeHotSpots(tempItem.getId(), mProductPointers, hotSpotContainer);
                } else {
                    hotSpotContainer.setVisibility(View.GONE);
                }

            }
        });


        layout.setTag(position);
        container.addView(layout);
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        container.removeView((View) view);
    }

    private void placeHotSpots(Integer pictureId, ArrayList<ProductPointer> pointers, FrameLayout hotSpotContainer) {
        int baseWidth = hotSpotContainer.getWidth();
        int baseHeight = hotSpotContainer.getHeight();

        for (int i = 0; i < pointers.size(); i++) {
            final ProductPointer tempHotSpot = pointers.get(i);
            if (!tempHotSpot.getPictureId().equals(pictureId)) continue;
            int viewCalculatedMarginLeft = getValuePercentageHorizontal(tempHotSpot.getX(), baseWidth);
            int viewCalculatedMarginTop = getValuePercentageVertical(tempHotSpot.getY(), baseHeight);

            int size = Math.round(GoMobileApp.convertDpToPixel(35));
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(size, size);
            params.setMargins(viewCalculatedMarginLeft, viewCalculatedMarginTop, 0, 0);


            ImageView img_thumb = new ImageView(mContext);
            img_thumb.setTag(tempHotSpot.getId());
            img_thumb.setImageResource(R.drawable.single_image_hotspot);
            img_thumb.setLayoutParams(params);

            hotSpotContainer.addView(img_thumb);

            Animation pulse = AnimationUtils.loadAnimation(mContext, R.anim.pulse);
            img_thumb.startAnimation(pulse);

            img_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView tempImageView = (ImageView) v;
                    if (mFragment instanceof SingleProductDetailFragment)
                        ((SingleProductDetailFragment) mFragment).ShowBottomSheetShopTheLook(tempHotSpot.getTaggedProductId());
                    else
                        ((SingleProductDetailFragmentPush) mFragment).ShowBottomSheetShopTheLook(tempHotSpot.getTaggedProductId());
                }
            });

        }

    }


    private int getValuePercentageHorizontal(Double rawValue, int mBaseWidth) {
        return (int) Math.round((rawValue * mBaseWidth) / 100);
    }

    private int getValuePercentageVertical(Double rawValue, int mBaseHeight) {
        return (int) Math.round((rawValue * mBaseHeight) / 100);
    }

}