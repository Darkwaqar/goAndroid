package com.growonline.gomobishop.fragment;


import android.animation.Animator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.adapter.StoreCarouselAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.FetchAllVendorsFromDbAsyncTask;
import com.growonline.gomobishop.asynctask.RemoveStoreFromDbAsyncTask;
import com.growonline.gomobishop.control.StoreCarouselPageTransformer;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class MyStore extends Fragment {


    private static final String OPEN_STORE_TAB = "openStoreTab";
    private boolean leftScroll = true;
    private float currentViewPagerOffset = 0;
    private int indexOfStartStore;
    private boolean isNewlyDownloadedStore;
    private int startUpProduct, startUpCategoryId;
    private boolean createShortCut;
    private Vendor startUpStore;
    private boolean openStoreTab;
    private ViewPager storeCarouselViewPager;
    private int totalCountStore;
    private StoreCarouselAdapter carouselAdapter;
    private LinearLayout indicatorLayout;
    private int nextStoreIndexAfterRemoval = -1;
    private TextView noStoreFound;
    private FrameLayout frame_layout_store;
    private List<Fragment> listCarouselFragment = new ArrayList<>();
    private FetchAllVendorsFromDbAsyncTask backgroundTask;

    public MyStore() {

    }


    public static MyStore newInstance(Vendor vendor, Boolean openStoreTab) {
        MyStore fragment = new MyStore();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        args.putBoolean(OPEN_STORE_TAB, openStoreTab);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            startUpStore = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
            openStoreTab = getArguments().getBoolean("openStoreTab", false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_store, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frame_layout_store = (FrameLayout) view.findViewById(R.id.frame_layout_store);
        storeCarouselViewPager = (ViewPager) view.findViewById(R.id.my_store_viewPager);
        noStoreFound = (TextView) view.findViewById(R.id.no_StoreAvailable);
        indicatorLayout = (LinearLayout) view.findViewById(R.id.indicator_layout);
        frame_layout_store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        updateStoreCarousel();
    }


    public void updateStoreCarousel() {
        backgroundTask = new FetchAllVendorsFromDbAsyncTask(getActivity());
        backgroundTask.addOnVendorsLoadedListener(new AsyncTaskResultListener<List<Vendor>>() {
            @Override
            public void response(AsyncTaskResult<List<Vendor>> response) {
                if (!response.hasException()) {
                    List<Vendor> tempList = response.getResult();
                    listCarouselFragment = new ArrayList<>();
                    for (Vendor vendor : tempList) {
                        if (startUpStore == null) {
                            listCarouselFragment.add(StoreCarouselFragment.newInstance(vendor, tempList.indexOf(vendor)));
                        } else {
                            if (vendor.getId().equals(startUpStore.getId()))
                                listCarouselFragment.add(0, StoreCarouselFragment.newInstance(vendor, tempList.indexOf(vendor)));
                            else
                                listCarouselFragment.add(StoreCarouselFragment.newInstance(vendor, tempList.indexOf(vendor)));
                        }
                    }
                    createStoreCarousel();
                } else {
                    launchException("Database not valid!", response.getException());
                }
            }
        });
        backgroundTask.execute();
    }

    void createStoreCarousel() {
        if (!isAdded()) {
            return;
        }
        totalCountStore = listCarouselFragment.size();
        carouselAdapter = new StoreCarouselAdapter(getChildFragmentManager());
        carouselAdapter.addAll(listCarouselFragment);
        storeCarouselViewPager.setAdapter(carouselAdapter);

        if (carouselAdapter.getCount() < 1) {
            noStoreFound.setVisibility(View.VISIBLE);
        } else
            noStoreFound.setVisibility(View.GONE);

        storeCarouselViewPager.setPageTransformer(false, new StoreCarouselPageTransformer());

        storeCarouselViewPager.setClipChildren(false);
        storeCarouselViewPager.setClipToPadding(false);

        int focusOutStoreVisibleWidth = (int) getResources().getDimensionPixelSize(R.dimen.carousel_focus_out_store_visible_width);
        storeCarouselViewPager.setPadding(focusOutStoreVisibleWidth, 0, focusOutStoreVisibleWidth, 0);

        int pageMargins = (int) getResources().getDimensionPixelSize(R.dimen.carousel_page_margin);
        storeCarouselViewPager.setPageMargin(pageMargins);

        if (nextStoreIndexAfterRemoval >= 0) {
            storeCarouselViewPager.setCurrentItem(nextStoreIndexAfterRemoval, true);
        }

        createIndicators(0, totalCountStore);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(GoMobileApp.getScreenWidth(),
                getValuePercentageVertical(70));
        storeCarouselViewPager.setLayoutParams(params);

        storeCarouselViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createIndicators(position, totalCountStore);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }


    private void createIndicators(int selected, int total) {

        indicatorLayout.removeAllViews();
        for (int i = 0; i < total; i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(0, 0, 0, 5);

            if (i == selected) {
                imageView.setImageResource(R.drawable.ic_check_box_indeterminat);
            } else {
                imageView.setImageResource(R.drawable.ic_check_box_empty);
            }

            indicatorLayout.addView(imageView);
        }

    }

    void launchException(String message, Throwable exception) {
        Log.e("MyStore", message, exception);
    }

    private int getValuePercentageVertical(float rawValue) {
        return Math.round((rawValue * GoMobileApp.getScreenHeight()) / 100);
    }

    public void startRemoveStore(final int storeId, final int fragmentIndex) {

        if (carouselAdapter.getCount() == 1) {
            ((StoreCarouselFragment) carouselAdapter.getItem(fragmentIndex)).BeginRemoveAnimation();

            // wait for remove animation to complete
            int delayInMilliSeconds = 200;
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    removeStoreFromDb(storeId);
                }
            }, delayInMilliSeconds);

        } else {

            if (fragmentIndex == carouselAdapter.getCount() - 1) {
                leftScroll = false;
                nextStoreIndexAfterRemoval = fragmentIndex - 1;
            } else {
                nextStoreIndexAfterRemoval = fragmentIndex;
            }

            final int pageWidth = storeCarouselViewPager.getWidth();
            currentViewPagerOffset = 0;

            storeCarouselViewPager.beginFakeDrag();

            try {
                ValueAnimator va = ValueAnimator.ofFloat(0f, 100f);
                va.setInterpolator(new AccelerateDecelerateInterpolator());
                va.setDuration(500);
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {

                        float offset = (pageWidth / 100) * (float) valueAnimator.getAnimatedValue();
                        float dragBy = leftScroll ? -1 * (offset - currentViewPagerOffset) : (offset - currentViewPagerOffset);
                        storeCarouselViewPager.fakeDragBy(dragBy);
                        currentViewPagerOffset = offset;

                    }
                });
                va.start();

                va.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        storeCarouselViewPager.endFakeDrag();

                        //remove store animation delay
                        Handler delayForSmoothEffect = new Handler();
                        delayForSmoothEffect.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                ((StoreCarouselFragment) carouselAdapter.getItem(fragmentIndex)).BeginRemoveAnimation();
                            }
                        }, 100);

                        // wait for remove animation to complete plus 100 milliseconds
                        int delayInMilliSeconds = 300;
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                removeStoreFromDb(storeId);
                            }
                        }, delayInMilliSeconds);
                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void removeStoreFromDb(int storeId) {

        RemoveStoreFromDbAsyncTask backgroundTask = new RemoveStoreFromDbAsyncTask(getActivity());
        backgroundTask.addOnResultsListener(new AsyncTaskResultListener<String>() {
            @Override
            public void response(AsyncTaskResult<String> response) {
                updateStoreCarousel();
            }
        });
        backgroundTask.execute(storeId);
    }

    public void launchStartupStore(boolean IsNewlyDownloadedStore, int StartUpProduct, int StartUpCategoryId, boolean CreateShortCut) {
        updateStoreCarousel();
        this.isNewlyDownloadedStore = IsNewlyDownloadedStore;
        this.startUpProduct = StartUpProduct;
        this.createShortCut = CreateShortCut;
        this.startUpCategoryId = StartUpCategoryId;
        int delayInMilliSeconds = 500;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (carouselAdapter.getCount() != 0)
                    ((StoreCarouselFragment) carouselAdapter.getItem(0)).StartStore(true, isNewlyDownloadedStore, startUpProduct, startUpCategoryId, createShortCut);
            }
        }, delayInMilliSeconds);
        startUpStore = null;
    }

    public void startStore(int vendorId, final int startupProductId, final int startUpCategoryId, final boolean createShortCut) {
        if (GoMobileApp.getDownloadedVendors() != null && GoMobileApp.getDownloadedVendors().size() > 0) {
            for (int i = 0; i < GoMobileApp.getDownloadedVendors().size(); i++) {
                Vendor cur = GoMobileApp.getDownloadedVendors().get(i);
                if (cur.getId() == vendorId) {
                    if (startUpStore != null) i = 0;
                    indexOfStartStore = i;
                    if (carouselAdapter != null && carouselAdapter.getCount() > 0) {
                        storeCarouselViewPager.setCurrentItem(i, true);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (carouselAdapter != null && carouselAdapter.getCount() > 0) {
                                    StoreCarouselFragment frag = (StoreCarouselFragment) carouselAdapter.getItem(indexOfStartStore);
                                    frag.StartStore(true, false, startupProductId, startUpCategoryId, createShortCut);
                                    //reset index
                                    indexOfStartStore = 0;
                                }
                            }
                        }, 200);
                    }
                    break;
                }
            }
        }
    }


    public void setIdentity(String identity, int tabIndex) {
        if (carouselAdapter != null && carouselAdapter.getCount() > 0) {
            for (int i = 0; i < carouselAdapter.getCount(); i++) {
                StoreCarouselFragment frag = (StoreCarouselFragment) carouselAdapter.getItem(i);
                if (frag.GetIdentity().contentEquals(identity)) {
                    frag.Refresh();
                    frag.SetTabIndex(tabIndex);
                    break;
                }
            }
        }
    }
}
