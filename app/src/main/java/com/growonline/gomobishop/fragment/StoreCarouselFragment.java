package com.growonline.gomobishop.fragment;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.io.File;
import java.util.UUID;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StoreCarouselFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StoreCarouselFragment extends Fragment {


    private static final String ARG_PARAM_INDEX = "paramFragmentIndex";
    private final String mIdentity = UUID.randomUUID().toString();
    //private static final int REQUEST_CODE_ACTIVITY_TAB = 1;
    private int mTabIndex, mFragmentIndex;
    private boolean mEnable = true;

    private Context mContext;
    private Vendor mVendor;
    private ImageView previewPane;
    private RelativeLayout panelRemove;
    private LinearLayout layoutFragment;
    private ImageButton btnRemove;

    public StoreCarouselFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param vendor Parameter 1.
     * @return A new instance of fragment StoreCarouselFragment.
     */
    public static StoreCarouselFragment newInstance(Vendor vendor, Integer fragmentIndex) {
        StoreCarouselFragment fragment = new StoreCarouselFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        args.putInt(ARG_PARAM_INDEX, fragmentIndex);
        fragment.setArguments(args);
        return fragment;
    }

    public void Refresh() {
        setPreview();
    }

    public String GetIdentity() {
        return mIdentity;
    }

    public Integer GetFragmentIndex() {
        return mFragmentIndex;
    }

    public void SetTabIndex(int index) {
        mTabIndex = index;
    }

    public void Enable(boolean enable) {
        mEnable = enable;
    }

    public void BeginRemoveAnimation() {
        AnimationSet setRemoveAnimation = new AnimationSet(true);
        setRemoveAnimation.setInterpolator(new AccelerateInterpolator());
        setRemoveAnimation.setDuration(200);
        setRemoveAnimation.setFillAfter(true);

        AlphaAnimation fadeOut = new AlphaAnimation(1f, 0f);
        TranslateAnimation translateY = new TranslateAnimation(0, 0, 0, 200);

        setRemoveAnimation.addAnimation(fadeOut);
        setRemoveAnimation.addAnimation(translateY);

        layoutFragment.startAnimation(setRemoveAnimation);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getActivity();
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
            mFragmentIndex = getArguments().getInt(ARG_PARAM_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carousel_store, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
    }

    void initUi(View view) {

        layoutFragment = (LinearLayout) view.findViewById(R.id.fragmentLayout);
        previewPane = (ImageView) view.findViewById(R.id.preview_store_img);
        previewPane.setBackgroundColor(Color.parseColor(mVendor.getThemeColor()));
        panelRemove = (RelativeLayout) view.findViewById(R.id.panel_remove_store);
        btnRemove = (ImageButton) view.findViewById(R.id.btn_remove_store);
        TextView lblTitle = (TextView) view.findViewById(R.id.lbl_title);

        setPreview();

        lblTitle.setText(mVendor.getName());
        AppHelper.applyRobotoLightFont(lblTitle);

        ImageView imgLogo = (ImageView) view.findViewById(R.id.logo_store);
        GoMobileApp.getmCacheManager().loadImageWithFit(Uri.parse(mVendor.getLogoUrl()), imgLogo);

        applyStoreRemovalDrag(imgLogo);
        onViewClick(view);
        onViewLongPress(view);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).startRemoveStore(mVendor.getId(), mFragmentIndex);
            }
        });

        //empty click listener
        panelRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    void applyStoreRemovalDrag(View dragPointer) {
        dragPointer.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Intent x = new Intent();
                x.putExtra("storeId", mVendor.getId());
                x.putExtra("indexFragment", mFragmentIndex);
                ClipData data = ClipData.newIntent("mdata", x);
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, View.DRAWING_CACHE_QUALITY_LOW);

                return true;
            }
        });
    }

    void onViewClick(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEnable) {
                    ((MainActivity) mContext).ShowBottomSheet();
                    StartStore(true, false, 0, 0, false);
                }
            }
        });
    }

    void onViewLongPress(View v) {
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                panelRemove.setVisibility(View.VISIBLE);
                Animation reveal = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_start_anim);
                btnRemove.startAnimation(reveal);
                return true;
            }
        });
    }

    void setPreview() {
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + mContext.getPackageName() + "/" + mVendor.getId() + ".jpg";
        GoMobileApp.getmCacheManager().loadImageWithFitFromFile(new File(mPath), previewPane, true);
    }

    public void StartStore(boolean maximizeAnimation, boolean newlyDownloadStore, int startupProductId, int startupCategoryId, boolean createShortCut) {
        Intent i = new Intent(mContext, StoreActivity.class);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putExtra("identity", mIdentity);
        i.putExtra(AppConstant.INTENT_MAXIMIZE_ANIMATION, maximizeAnimation);
        i.putExtra("initWidth", previewPane.getWidth());
        i.putExtra("initHeight", previewPane.getHeight());
        i.putExtra("initTabIndex", mTabIndex);
        i.putExtra("newlyDownloadStore", newlyDownloadStore);
        i.putExtra(AppConstant.INTENT_PRODUCT_ID, startupProductId);
        i.putExtra(AppConstant.INTENT_CATEGORY_ID, startupCategoryId);
        i.putExtra(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, createShortCut);
        startActivityForResult(i, AppConstant.CODE_STORE_ACTIVITY);

        if (maximizeAnimation) {
            ((Activity) mContext).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        } else {
            ((Activity) mContext).overridePendingTransition(R.anim.scale_out, R.anim.fade_out);
        }
    }


    private void renewVendor(Vendor vendor) {
        mVendor = vendor;
    }

}
