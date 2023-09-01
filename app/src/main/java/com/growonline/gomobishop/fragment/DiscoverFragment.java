package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.animation.Animator;
import android.animation.ValueAnimator;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetFeaturedItemsAsyncTask;
import com.growonline.gomobishop.control.SwipeableRelativeLayout;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.model.FeaturedCategory;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class DiscoverFragment extends Fragment {


    private static List<Integer> AVAILABLE_FRAMES_IDS = new ArrayList<>();
    final int ANIMATION_DURATION = 500;
    private final Integer SLIDE_SHOW_SLIDE_DELAY = 4000;
    private final Integer SLIDE_SHOW_SLIDE_DURATION = 400;
    SwipeableRelativeLayout layoutRoot;
    int mBaseWidth, mBaseHeight;
    boolean isSelect;
    List<View> currentOnScreenViews = new ArrayList<>();
    List<View> nextScrollableViews = new ArrayList<>();
    int mNextScrollIndex = 1;
    List<View> frames = new ArrayList<>();
    int mLeft = 0, mRight = 1, mTop = 2, mBottom = 3, mDirection = 0;
    private Vendor mVendor;
    private int mBorderSize;
    private GetFeaturedItemsAsyncTask backgroundTask;
    private Handler loadingAnimationHandler;
    private Runnable loadingAnimationRunnable;
    private View mSelectedView;
    private StoreActivity mActivity;
    private List<FeaturedCategory> categories = new ArrayList<>();
    private List<CategorySimpleModel> categoriesProduct = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    ///slide-show
    private boolean mStopSlideShow = false;
    private ValueAnimator slideShowAnimator;
    private Handler slideShowMainTimer;
    private Runnable slideShowMainTimerTask = new Runnable() {
        @Override
        public void run() {
            try {
                beginSlideShow();
            } finally {
                if (slideShowMainTimer != null)
                    slideShowMainTimer.postDelayed(this, SLIDE_SHOW_SLIDE_DELAY);
            }
        }
    };

    public DiscoverFragment() {
        // Required empty public constructor
    }

    public static DiscoverFragment newInstance(Vendor vendor) {
        DiscoverFragment fragment = new DiscoverFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);

        /*AVAILABLE_FRAMES_IDS.add(R.layout.frame_one_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_two_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_three_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_four_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_five_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_six_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_seven_discover);*/

        AVAILABLE_FRAMES_IDS.add(R.layout.frame_eight_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_nine_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_ten_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_eleven_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_twelve_discover);
        AVAILABLE_FRAMES_IDS.add(R.layout.frame_thirteen_discover);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover, container, false);
        initUi(v);
        return v;

    }

    void initUi(View view) {
        mActivity = (StoreActivity) getActivity();
        mBorderSize = mActivity.getResources().getDimensionPixelSize(R.dimen.square_anim_border_size);
        layoutRoot = (SwipeableRelativeLayout) view.findViewById(R.id.main_discover_layout);
        loadData();
    }

    void setUpDynamicFrames(int count) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < count; i++) {
            View frame = inflater.inflate(AVAILABLE_FRAMES_IDS.get(i % AVAILABLE_FRAMES_IDS.size()), null);
            layoutRoot.addView(frame);
            frames.add(frame);
        }

        reArrangeDynamicViews(0, frames);

        if (frames.size() > 1)
            setNextScrollingDynamicViews(frames.get(1));

        layoutRoot.addOnSwipeListener(new SwipeableRelativeLayout.SwipeableRelativeLayoutListener() {
            @Override
            public void onSlideRight(float position) {
                drag(mRight, position);
            }

            @Override
            public void onSlideLeft(float position) {
                drag(mLeft, position);
            }

            @Override
            public void onSlideUp(float position) {
                drag(mTop, position);
            }

            @Override
            public void onSlideDown(float position) {
                drag(mBottom, position);
            }

            @Override
            public void onPress() {
                resetSlideShowTimer();
            }

            @Override
            public void onRelease() {
                startSlideShowTimer();
            }

            @Override
            public void onScrollCompleted() {
                reArrangeDynamicViews(mNextScrollIndex, frames);

                mNextScrollIndex += 1;
                if (mNextScrollIndex >= frames.size()) {
                    mNextScrollIndex = 0;
                }

                setNextScrollingDynamicViews(frames.get(mNextScrollIndex));
            }

        });
    }

    void moveRight(View v, float pos) {
        v.setTranslationY(0);
        v.setTranslationX(pos * v.getWidth() * -1);
    }

    void moveLeft(View v, float pos) {
        v.setTranslationY(0);
        v.setTranslationX(pos * v.getWidth());
    }

    void moveTop(View v, float pos) {
        v.setTranslationX(0);
        v.setTranslationY(pos * v.getHeight());
    }

    void moveBottom(View v, float pos) {
        v.setTranslationX(0);
        v.setTranslationY(pos * v.getHeight() * -1);
    }

    void changeAlpha(View v, float pos) {
        v.setAlpha(Math.abs(1 - pos));
    }

    void loadFeaturedItems() {

        ((StoreActivity) getActivity()).setLoadingAnimation(true);
        backgroundTask = new GetFeaturedItemsAsyncTask(mVendor.getId());
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<List<Product>>() {
            @Override
            public void response(final AsyncTaskResult<List<Product>> response) {
                if (!response.hasException()) {
                    mBaseWidth = layoutRoot.getWidth();
                    mBaseHeight = layoutRoot.getHeight();

                    setUpDynamicFrames(response.getResult().size() / 7);
                    prepareLayoutResponsiveDynamic(frames);
                    BindDataDynamic(response.getResult(), frames);
                    //startSlideShowTimer();

                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }

                loadingAnimationHandler = new Handler();
                loadingAnimationRunnable = new Runnable() {
                    @Override
                    public void run() {
                        ((StoreActivity) getActivity()).setLoadingAnimation(false);
                    }
                };
                loadingAnimationHandler.postDelayed(loadingAnimationRunnable, 2000);
            }
        });
        backgroundTask.execute();
    }

    private void loadData() {
        loadFeaturedItems();
    }

    @Override
    public void onDestroyView() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        if (loadingAnimationHandler != null && loadingAnimationRunnable != null)
            loadingAnimationHandler.removeCallbacks(loadingAnimationRunnable);

        if (slideShowMainTimer != null && slideShowMainTimerTask != null)
            slideShowMainTimer.removeCallbacks(slideShowMainTimerTask);

        super.onDestroyView();
    }

    /// SELECT AND DE-SELECT
    void registerClickEvents(View v) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSelect) {
                    layoutRoot.setEnableSwipe(false);
                    select(view);
                    isSelect = true;
                    mSelectedView = view;
                } else {

                    deSelect(mSelectedView);
                    isSelect = false;
                    layoutRoot.setEnableSwipe(true);
                }
            }
        });
    }

    void deSelect(View selectedView) {
        selectedView.setPadding(mBorderSize, mBorderSize, 0, 0);

        String[] viewOriginalDimension = selectedView.getTag().toString().split(":");
        int viewOriginalWidthPercent = getValuePercentageHorizontal(Float.parseFloat(viewOriginalDimension[0]));
        int viewOriginalHeightPercent = getValuePercentageVertical(Float.parseFloat(viewOriginalDimension[1]));

        int viewOriginalMarginLeft = getValuePercentageHorizontal(Float.parseFloat(viewOriginalDimension[2]));
        int viewOriginalMarginTop = getValuePercentageVertical(Float.parseFloat(viewOriginalDimension[3]));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) selectedView.getLayoutParams();

        animateWidth(selectedView, params, viewOriginalWidthPercent);
        animateHeight(selectedView, params, viewOriginalHeightPercent);
        if (viewOriginalMarginLeft > 0)
            animateMarginLeft(selectedView, params, viewOriginalMarginLeft);
        if (viewOriginalMarginTop > 0)
            animateMarginTop(selectedView, params, viewOriginalMarginTop);

        for (int i = 0; i < currentOnScreenViews.size(); i++) {
            View tempView = currentOnScreenViews.get(i);
            if (tempView.getId() != selectedView.getId()) {
                endAnimation(tempView);
            }

            tempView.setEnabled(true);
        }


        //All Category View
        if (selectedView.getTag().toString().contains("p")) {
            TextView lblAllProduct = (TextView) selectedView.findViewById(R.id.lbl_all_product);
            lblAllProduct.setAlpha(0f);
            lblAllProduct.setVisibility(View.VISIBLE);
            animateViewAlpha(lblAllProduct, 0f, 1f, false);

            TextView btnAllCategory = (TextView) selectedView.findViewById(R.id.btn_all_categories);
            animateViewAlpha(btnAllCategory, 1f, 0f, true);
        } else {
            TextView btnAllProducts = (TextView) ((ViewGroup) selectedView.getParent()).findViewById(R.id.btn_all_products);
            animateViewAlpha(btnAllProducts, 1f, 0f, false);
        }
    }

    void select(View selectedView) {

        selectedView.setPadding(0, 0, 0, 0);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) selectedView.getLayoutParams();

        animateWidth(selectedView, params, mBaseWidth);
        animateHeight(selectedView, params, getValuePercentageVertical(60f));
        if (params.leftMargin > 0)
            animateMarginLeft(selectedView, params, 0);
        if (params.topMargin > 0)
            animateMarginTop(selectedView, params, 0);

        int index = 0;
        for (int i = 0; i < currentOnScreenViews.size(); i++) {
            View tempView = currentOnScreenViews.get(i);
            if (tempView.getId() != selectedView.getId()) {
                startAnimation(tempView, index);
                index += 1;
            }
        }

        //All Category View
        if (selectedView.getTag().toString().contains("p")) {
            TextView lblAllProduct = (TextView) selectedView.findViewById(R.id.lbl_all_product);
            animateViewAlpha(lblAllProduct, 1f, 0f, false);

            TextView btnAllCategory = (TextView) selectedView.findViewById(R.id.btn_all_categories);
            btnAllCategory.setAlpha(0);
            animateViewAlpha(btnAllCategory, 0f, 1f, true);
        } else {
            TextView btnAllProducts = (TextView) ((ViewGroup) selectedView.getParent()).findViewById(R.id.btn_all_products);
            btnAllProducts.setAlpha(0);
            animateViewAlpha(btnAllProducts, 0f, 1f, false);
        }
    }

    void startAnimation(View v, int index) {

        int defaultSettleDownWidth = getValuePercentageHorizontal(33.4f);
        int defaultSettleDownHeight = getValuePercentageVertical(20);

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        animateWidth(v, params, defaultSettleDownWidth);
        animateHeight(v, params, defaultSettleDownHeight);

        int settleDownMarginLeft = 0;
        int settleDownMarginTop = getValuePercentageVertical(60);
        switch (index) {
            case 0:
                break;
            case 1:
                settleDownMarginLeft = defaultSettleDownWidth;
                break;
            case 2:
                settleDownMarginLeft = defaultSettleDownWidth * 2;
                break;
            case 3:
                settleDownMarginTop += defaultSettleDownHeight;
                break;
            case 4:
                settleDownMarginLeft = defaultSettleDownWidth;
                settleDownMarginTop += defaultSettleDownHeight;
                break;
            case 5:
                settleDownMarginLeft = defaultSettleDownWidth * 2;
                settleDownMarginTop += defaultSettleDownHeight;
                break;
        }

        animateMarginLeft(v, params, settleDownMarginLeft);
        animateMarginTop(v, params, settleDownMarginTop);
    }

    void endAnimation(View v) {

        String[] viewOriginalDimension = v.getTag().toString().split(":");
        int viewOriginalWidthPercent = getValuePercentageHorizontal(Integer.parseInt(viewOriginalDimension[0]));
        int viewOriginalHeightPercent = getValuePercentageVertical(Integer.parseInt(viewOriginalDimension[1]));

        int viewOriginalMarginLeft = getValuePercentageHorizontal(Integer.parseInt(viewOriginalDimension[2]));
        int viewOriginalMarginTop = getValuePercentageVertical(Integer.parseInt(viewOriginalDimension[3]));

        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) v.getLayoutParams();
        animateWidth(v, params, viewOriginalWidthPercent);
        animateHeight(v, params, viewOriginalHeightPercent);
        animateMarginLeft(v, params, viewOriginalMarginLeft);
        animateMarginTop(v, params, viewOriginalMarginTop);

    }

    void animateWidth(final View v, final FrameLayout.LayoutParams params, int to) {

        ValueAnimator anim = new ValueAnimator().ofInt(v.getWidth(), to);
        anim.setDuration(ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.width = (int) valueAnimator.getAnimatedValue();
                v.setLayoutParams(params);
            }
        });
        anim.start();
    }

    void animateHeight(final View v, final FrameLayout.LayoutParams params, int to) {

        ValueAnimator anim = new ValueAnimator().ofInt(v.getHeight(), to);
        anim.setDuration(ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.height = (int) valueAnimator.getAnimatedValue();
                v.setLayoutParams(params);
            }
        });
        anim.start();

    }

    void animateMarginLeft(final View v, final FrameLayout.LayoutParams params, int to) {

        ValueAnimator anim = new ValueAnimator().ofInt(params.leftMargin, to);
        anim.setDuration(ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.leftMargin = (int) valueAnimator.getAnimatedValue();
                v.setLayoutParams(params);
            }
        });
        anim.start();

    }

    void animateMarginTop(final View v, final FrameLayout.LayoutParams params, int to) {
        ValueAnimator anim = new ValueAnimator().ofInt(params.topMargin, to);
        anim.setDuration(ANIMATION_DURATION);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                params.topMargin = (int) valueAnimator.getAnimatedValue();
                v.setLayoutParams(params);
            }
        });
        anim.start();
    }

    void animateViewAlpha(final View v, float from, float to, final boolean hide) {
        ValueAnimator va = ValueAnimator.ofFloat(from, to);
        va.setDuration(ANIMATION_DURATION);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setAlpha((float) animation.getAnimatedValue());
            }
        });

        va.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (hide)
                    v.setVisibility(View.GONE);
                else
                    v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        va.start();
    }

    void drag(int direction, float position) {

        for (int i = 0; i < nextScrollableViews.size(); i++) {

            if (direction == mLeft) {
                moveLeft(nextScrollableViews.get(i), position);
            } else if (direction == mRight) {
                moveRight(nextScrollableViews.get(i), position);
            } else if (direction == mTop) {
                moveTop(nextScrollableViews.get(i), position);
            } else if (direction == mBottom) {
                moveBottom(nextScrollableViews.get(i), position);
            }

            if (nextScrollableViews.get(i).getId() == R.id.lbl_all_product) {
                changeAlpha(nextScrollableViews.get(i), position);
            }
        }

    }

    //// RESPONSIVE LAYOUT CALCULATION ////
    private void prepareLayoutResponsiveDynamic(List<View> parentViews) {

        for (int p = 0; p < parentViews.size(); p++) {

            View singleParentView = parentViews.get(p);

            List<View> allViews = new ArrayList<View>();
            allViews.add(singleParentView.findViewById(R.id.l1));
            allViews.add(singleParentView.findViewById(R.id.l2));
            allViews.add(singleParentView.findViewById(R.id.l3));
            allViews.add(singleParentView.findViewById(R.id.l4));
            allViews.add(singleParentView.findViewById(R.id.l5));
            allViews.add(singleParentView.findViewById(R.id.l6));
            allViews.add(singleParentView.findViewById(R.id.l7));

            for (int i = 0; i < allViews.size(); i++) {

                View v = allViews.get(i);

                v.setPadding(mBorderSize, mBorderSize, 0, 0);

                String[] viewOriginalDimension = v.getTag().toString().split(":");
                int viewCalculatedWidth = getValuePercentageHorizontal(Float.parseFloat(viewOriginalDimension[0]));
                int viewCalculatedHeight = getValuePercentageVertical(Float.parseFloat(viewOriginalDimension[1]));

                int viewCalculatedMarginLeft = getValuePercentageHorizontal(Float.parseFloat(viewOriginalDimension[2]));
                int viewCalculatedMarginTop = getValuePercentageVertical(Float.parseFloat(viewOriginalDimension[3]));

                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(viewCalculatedWidth, viewCalculatedHeight);
                params.setMargins(viewCalculatedMarginLeft, viewCalculatedMarginTop, 0, 0);

                v.setLayoutParams(params);
            }

            RelativeLayout overlay = (RelativeLayout) singleParentView.findViewById(R.id.overlay);
            FrameLayout.LayoutParams overlayParams = new FrameLayout.LayoutParams(mBaseWidth, mBaseHeight);
            overlay.setLayoutParams(overlayParams);
        }
    }

    private int getValuePercentageHorizontal(float rawValue) {
        return Math.round((rawValue * mBaseWidth) / 100);
    }

    private int getValuePercentageVertical(float rawValue) {
        return Math.round((rawValue * mBaseHeight) / 100);
    }

    void BindDataDynamic(final List<Product> items, List<View> views) {

        if (items == null) return;
        int indexer = 0;
        for (int i = 0; i < items.size() / 7; i++) {

            View parent = views.get(i);
            if (parent != null) {
                TextView btnAllCategory = (TextView) parent.findViewById(R.id.btn_all_categories);
                TextView btnAllProducts = (TextView) parent.findViewById(R.id.btn_all_products);
                btnAllProducts.setTag(items.get(i).getId());

                btnAllCategory.setVisibility(View.GONE);


                btnAllProducts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StoreActivity sActivity = ((StoreActivity) mActivity);
                        sActivity.openProductDetailFragment(products.get(0).getId(), products.get(0).getName(), products);
                    }
                });

                List<ImageView> allImageViews = new ArrayList<>();
                allImageViews.add((ImageView) parent.findViewById(R.id.s1));
                allImageViews.add((ImageView) parent.findViewById(R.id.s2));
                allImageViews.add((ImageView) parent.findViewById(R.id.s3));
                allImageViews.add((ImageView) parent.findViewById(R.id.s4));
                allImageViews.add((ImageView) parent.findViewById(R.id.s5));
                allImageViews.add((ImageView) parent.findViewById(R.id.s6));
                allImageViews.add((ImageView) parent.findViewById(R.id.s7));

                for (int v = 0; v < allImageViews.size(); v++) {
                    ImageView tempImageView = allImageViews.get(v);
                    if (items.size() > indexer) {
                        Product product = items.get(indexer);
                        products.add(product);
                        GoMobileApp.getmCacheManager().loadImageForDiscoverTab(Uri.parse(product.getDefaultPictureModel().getImageUrl()), tempImageView, null);
                        indexer += 1;
                    }
                }
            }
        }
    }

    void setNextScrollingDynamicViews(View scrollingView) {
        nextScrollableViews.clear();

        nextScrollableViews.add(scrollingView.findViewById(R.id.s1));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s2));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s3));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s4));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s5));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s6));
        nextScrollableViews.add(scrollingView.findViewById(R.id.s7));
        nextScrollableViews.add(scrollingView.findViewById(R.id.lbl_all_product));

    }

    void reArrangeDynamicViews(Integer selectedIndex, List<View> views) {

        //re-arrange views
        for (int i = 0; i < views.size(); i++) {
            View tempV1 = views.get(i).findViewById(R.id.s1);
            View tempV2 = views.get(i).findViewById(R.id.s2);
            View tempV3 = views.get(i).findViewById(R.id.s3);
            View tempV4 = views.get(i).findViewById(R.id.s4);
            View tempV5 = views.get(i).findViewById(R.id.s5);
            View tempV6 = views.get(i).findViewById(R.id.s6);
            View tempV7 = views.get(i).findViewById(R.id.s7);
            View tempHdAllProducts = views.get(i).findViewById(R.id.lbl_all_product);

            if (i == selectedIndex) {

                currentOnScreenViews.clear();
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l1));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l2));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l3));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l4));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l5));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l6));
                currentOnScreenViews.add(views.get(i).findViewById(R.id.l7));

                for (int k = 0; k < currentOnScreenViews.size(); k++) {
                    currentOnScreenViews.get(k).setClickable(true);
                    registerClickEvents(currentOnScreenViews.get(k));
                }

                tempV1.setTranslationX(0);
                tempV2.setTranslationX(0);
                tempV3.setTranslationX(0);
                tempV4.setTranslationX(0);
                tempV5.setTranslationX(0);
                tempV6.setTranslationX(0);
                tempV7.setTranslationX(0);
                tempHdAllProducts.setTranslationX(0);

                tempV1.setTranslationY(0);
                tempV2.setTranslationY(0);
                tempV3.setTranslationY(0);
                tempV4.setTranslationY(0);
                tempV5.setTranslationY(0);
                tempV6.setTranslationY(0);
                tempV7.setTranslationY(0);
                tempHdAllProducts.setTranslationY(0);
            } else {

                List<View> viewsNotRequireClick = new ArrayList<>();
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l1));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l2));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l3));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l4));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l5));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l6));
                viewsNotRequireClick.add(views.get(i).findViewById(R.id.l7));

                for (int k = 0; k < viewsNotRequireClick.size(); k++) {
                    viewsNotRequireClick.get(k).setOnClickListener(null);
                    viewsNotRequireClick.get(k).setClickable(false);
                }

                views.get(i).bringToFront();

                tempV1.setTranslationX(mBaseWidth * -1);
                tempV2.setTranslationX(mBaseWidth * -1);
                tempV3.setTranslationX(mBaseWidth * -1);
                tempV4.setTranslationX(mBaseWidth * -1);
                tempV5.setTranslationX(mBaseWidth * -1);
                tempV6.setTranslationX(mBaseWidth * -1);
                tempV7.setTranslationX(mBaseWidth * -1);

                tempV1.setTranslationY(mBaseHeight * -1);
                tempV2.setTranslationY(mBaseHeight * -1);
                tempV3.setTranslationY(mBaseHeight * -1);
                tempV4.setTranslationY(mBaseHeight * -1);
                tempV5.setTranslationY(mBaseHeight * -1);
                tempV6.setTranslationY(mBaseHeight * -1);
                tempV7.setTranslationY(mBaseHeight * -1);
                tempHdAllProducts.setTranslationY(mBaseHeight * -1);
                tempHdAllProducts.setAlpha(0);
            }
        }
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    public void RenewVendor(Vendor vendor) {
        mVendor = vendor;
    }

    void beginSlideShow() {

        slideShowAnimator = ValueAnimator.ofFloat(1, 0);
        slideShowAnimator.setDuration(SLIDE_SHOW_SLIDE_DURATION);
        slideShowAnimator.setInterpolator(new AccelerateInterpolator());
        slideShowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                drag(mDirection, (float) animation.getAnimatedValue());
            }
        });
        slideShowAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                reArrangeDynamicViews(mNextScrollIndex, frames);

                mNextScrollIndex += 1;
                if (mNextScrollIndex >= frames.size()) {
                    mNextScrollIndex = 0;
                }

                setNextScrollingDynamicViews(frames.get(mNextScrollIndex));

                if (mDirection == mLeft)
                    mDirection = mTop;
                else if (mDirection == mTop)
                    mDirection = mRight;
                else if (mDirection == mRight)
                    mDirection = mBottom;
                else if (mDirection == mBottom)
                    mDirection = mLeft;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        slideShowAnimator.start();
    }

    void stopSlideShow() {
        mStopSlideShow = true;
        resetSlideShowTimer();
    }

    void startSlideShowTimer() {

        if (slideShowMainTimer == null)
            slideShowMainTimer = new Handler();

        slideShowMainTimer.postDelayed(slideShowMainTimerTask, SLIDE_SHOW_SLIDE_DELAY);
    }

    void resetSlideShowTimer() {
        if (slideShowMainTimer != null) {
            slideShowMainTimer.removeCallbacks(slideShowMainTimerTask);
            slideShowMainTimer = null;
        }
    }

}
