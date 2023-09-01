package com.growonline.gomobishop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.growonline.gomobishop.adapter.DashboardAdapter;
import com.growonline.gomobishop.adapter.OfferRecyclerAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetOffers;
import com.growonline.gomobishop.control.BackStackFragment;
import com.growonline.gomobishop.control.BottomSheetUtils;
import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.control.ViewPagerBottomSheetBehavior;
import com.growonline.gomobishop.fragment.MarketPlaceCategoryFragment;
import com.growonline.gomobishop.fragment.MarketPlaceHomeFragment;
import com.growonline.gomobishop.fragment.MyStore;
import com.growonline.gomobishop.model.BannerPicture;
import com.growonline.gomobishop.model.Offer;
import com.growonline.gomobishop.model.SearchItemModel;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

public class MainActivity extends AppCompatActivity {

    private boolean exitModeActivated = false;
    private boolean isNewlyDownloadedStore = false;
    private boolean createShortCut;
    private boolean storeTab;

    private Vendor startUpStore;
    private int actionBarHeight;
    private int startUpProduct;
    private int startupCategoryId;
    private TextView appTitleTextView;
    private RelativeLayout btnSearch;
    private CoordinatorLayout mainLayout;
    private LinearLayout indicatorLayout;
    private LinearLayout bottomSheet;
    private ViewPagerBottomSheetBehavior behavior;
    private ViewPager2 fragmentDashboard;
    private DashboardAdapter dashboardAdapter;
    private TabLayout tabLayout;
    private MyStore myStore;
    private RecyclerView offerRecyclerView;
    private OfferRecyclerAdapter offerRecyclerAdapter;
    private Context context;
    private List<BannerPicture> offerList = new ArrayList<>();
    private View networkError;
    private Toolbar toolbar;
    private Button Reload;
    private LinearLayout topMain;
    private int statusBarHeight = 0;
    private ProgressBar loading;
    private CircleIndicator2 indicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_main);
        context = this;
        Intent i = getIntent();
        startUpStore = i.getParcelableExtra(AppConstant.INTENT_VENDOR);
        isNewlyDownloadedStore = i.getBooleanExtra("newlyDownloadStore", false);
        startUpProduct = i.getIntExtra(AppConstant.INTENT_PRODUCT_ID, 0);
        createShortCut = i.getBooleanExtra(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, false);
        storeTab = i.getBooleanExtra("storeTab", false);
        initUi();
    }


    void initUi() {

        appTitleTextView = (TextView) findViewById(R.id.lbl_search_title);
        mainLayout = (CoordinatorLayout) findViewById(R.id.main_layout);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicator_layout);
        btnSearch = (RelativeLayout) findViewById(R.id.btn_search);
        tabLayout = (TabLayout) findViewById(R.id.dashboard_title);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        Reload = (Button) findViewById(R.id.reload);
        networkError = findViewById(R.id.network_error);
        bottomSheet = (LinearLayout) findViewById(R.id.dashboard_bottomsSheet);
        topMain = (LinearLayout) findViewById(R.id.top_main);

        AppHelper.applyRobotoLightFont(appTitleTextView);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.long_fade_in);
        mainLayout.startAnimation(fadeIn);

        actionBarHeight = (int) getResources().getDimension(R.dimen.action_bar_size_search_store_screen);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(null);
        }

        OfferLayoutInit();
        InitBottomSheet();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(i, AppConstant.CODE_SEARCH_ACTIVITY);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //check top height
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

    }

    public void setLoading(boolean enable) {
        if (enable)
            loading.setVisibility(View.VISIBLE);
        else
            loading.setVisibility(View.GONE);
    }

    public void InitBottomSheet() {
        fragmentDashboard = (ViewPager2) findViewById(R.id.fragment_dashboard);
        loading = (ProgressBar) findViewById(R.id.loading);
        dashboardAdapter = new DashboardAdapter(getSupportFragmentManager(), getLifecycle(), getBaseContext(), startUpStore, storeTab);
        fragmentDashboard.setAdapter(dashboardAdapter);
        fragmentDashboard.setOffscreenPageLimit(5);
        BottomSheetUtils.setupViewPager(fragmentDashboard);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, fragmentDashboard, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                LinearLayout linearLayout = (LinearLayout)
                        LayoutInflater.from(context).inflate(R.layout.single_tab_header_item, tabLayout, false);

                TextView tabTextView = (TextView) linearLayout.findViewById(R.id.tab_title);
                tabTextView.setText(dashboardAdapter.getPageTitle(position));
                tab.setCustomView(linearLayout);
            }
        });
        tabLayoutMediator.attach();

        Fragment myStoreFragment = dashboardAdapter.createFragment(4);
        if (myStoreFragment instanceof MyStore)
            myStore = ((MyStore) myStoreFragment);

        behavior = ViewPagerBottomSheetBehavior.from(bottomSheet);
        ViewTreeObserver observer = topMain.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        if ((statusBarHeight > GoMobileApp.convertDpToPixel(24)))
                            behavior.setPeekHeight((int) (GoMobileApp.getScreenHeight() - (topMain.getHeight() + statusBarHeight)));
                        else
                            behavior.setPeekHeight((int) (GoMobileApp.getScreenHeight() - topMain.getHeight()));
                    }
                });


        if (storeTab) {
            fragmentDashboard.setCurrentItem(4);
            ShowBottomSheet();
            storeTab = false;
        } else {
            fragmentDashboard.setCurrentItem(1);
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (behavior != null)
                    ShowBottomSheet();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (behavior != null)
                    ToggleBottomSheet();
            }
        });
    }

    public void ReplaceMarketplaceCategory(int categoryId, int type) {
        HostFragment hostFragment = (HostFragment) dashboardAdapter.createFragment(fragmentDashboard.getCurrentItem());
//        hostFragment.replaceFragment((MarketPlaceCategoryFragment.newInstance(categoryId, type)), true);
        hostFragment.replaceFragment((MarketPlaceHomeFragment.newInstance(categoryId, type)), true);
    }

    private void OfferLayoutInit() {
        indicator = findViewById(R.id.indicator);
        offerRecyclerView = (RecyclerView) findViewById(R.id.offer_RecyclerView);
        offerRecyclerAdapter = new OfferRecyclerAdapter(offerList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        offerRecyclerView.setLayoutManager(mLayoutManager);
        offerRecyclerView.setAdapter(offerRecyclerAdapter);
        new AsyncTaskGetOffers(MainActivity.this, false).execute();
        offerRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), offerRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                final BannerPicture offer = offerList.get(position);
                fragmentDashboard.setCurrentItem(4);
                ShowBottomSheet();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (offer.getVendorId() != 0) {
                            StartOrLoadStore(offer.getVendorId(), offer.getCategoryId(), offer.getProductId());
                        }
                    }
                }, 500);

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(offerRecyclerView);
        indicator.attachToRecyclerView(offerRecyclerView, pagerSnapHelper);
        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AsyncTaskGetOffers(MainActivity.this, false).execute();
            }
        });


    }

    private void LoadStartupStore() {
        if (startUpStore != null) {
            StartOrLoadStore(startUpStore.getId(), startupCategoryId, startUpProduct, startUpStore.getName(), startUpStore.getMarketPlaceLogoURL());
            startUpStore = null;
        }

    }

    private boolean isStoreAlreadyDownloaded(int vendorId) {
        boolean exist = false;
        if (GoMobileApp.getDownloadedVendors() != null && GoMobileApp.getDownloadedVendors().size() > 0) {
            List<Vendor> mDownloadedVendors = GoMobileApp.getDownloadedVendors();
            for (int i = 0; i < mDownloadedVendors.size(); i++) {
                Vendor cur = mDownloadedVendors.get(i);
                if (cur.getId() == vendorId) {
                    exist = true;
                    break;
                }
            }
        }
        return exist;
    }

    public void LoadOffers(List<BannerPicture> offerList) {
        if (offerList == null) {
            networkError.setVisibility(View.VISIBLE);
            return;
        }
        this.offerList.clear();
        this.offerList.addAll(offerList);
        offerRecyclerAdapter.notifyDataSetChanged();

//        if (offerRecyclerAdapter.getItemCount() == 0) {
//            networkError.setVisibility(View.VISIBLE);
//        } else {
//            networkError.setVisibility(View.GONE);
//        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        exitModeActivated = false;
    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {

            if (behavior.getState() == ViewPagerBottomSheetBehavior.STATE_EXPANDED) {
                behavior.setState(ViewPagerBottomSheetBehavior.STATE_COLLAPSED);
                return;
            }
            if (exitModeActivated) {
//            super.onBackPressed();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                finish();

            } else {
                GoMobileApp.Toast("Press again to exit");
            }
            exitModeActivated = true;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (behavior.getState() == ViewPagerBottomSheetBehavior.STATE_EXPANDED) {

                Rect outRect = new Rect();
                bottomSheet.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY()))
                    behavior.setState(ViewPagerBottomSheetBehavior.STATE_COLLAPSED);
            }
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            switch (resultCode) {
                case AppConstant.CODE_STORE_ACTIVITY:
                    String identity = data.getStringExtra("identity");
                    int tabIndex = data.getIntExtra("selectedTabInex", 0);
                    if (myStore != null)
                        myStore.setIdentity(identity, tabIndex);
                    break;
                case AppConstant.CODE_DOWNLOAD_ACTIVITY:
                    startUpStore = data.getParcelableExtra(AppConstant.INTENT_VENDOR);
                    startupCategoryId = data.getIntExtra(AppConstant.INTENT_CATEGORY_ID, 0);
                    startUpProduct = data.getIntExtra("mStartupProductId", 0);
                    isNewlyDownloadedStore = data.getBooleanExtra("newlyDownloadStore", false);
                    launchUpStore(isNewlyDownloadedStore, startUpProduct, startupCategoryId, createShortCut);
                    startUpStore = null;
                    break;
                case AppConstant.CODE_SEARCH_ACTIVITY:
                    SearchItemModel downloadStartVendor = data.getParcelableExtra(AppConstant.INTENT_VENDOR);
                    StartOrLoadStore(downloadStartVendor.getVendorId(), downloadStartVendor.getCategoryId(), downloadStartVendor.getProductId(), downloadStartVendor.getVendorName(), downloadStartVendor.getVendorImage());
                    break;
                case AppConstant.CODE_SEARCH_ACTIVITY_CATEGORY:
                    int categoryId = data.getIntExtra("categoryId", 0);
                    LoadCategoryProduct(categoryId);
                    break;
                case AppConstant.CODE_NOTIFICATION_CENTER:
                    Offer offer = data.getParcelableExtra(AppConstant.INTENT_NOTIFICATION);
                    if (offer != null && offer.getVendorId() != 0) {
                        StartOrLoadStore(offer.getVendorId(), offer.getCategoryId(), offer.getProductId(), offer.getVendorName(), offer.getImage());
                    }
                    break;
                default:
                    break;
            }

        }
    }

    public void ShowBottomSheet() {
        if (behavior.getState() == ViewPagerBottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(ViewPagerBottomSheetBehavior.STATE_EXPANDED);
        }
    }

    public void ToggleBottomSheet() {
        if (behavior.getState() == ViewPagerBottomSheetBehavior.STATE_COLLAPSED) {
            behavior.setState(ViewPagerBottomSheetBehavior.STATE_EXPANDED);
        } else if (behavior.getState() == ViewPagerBottomSheetBehavior.STATE_EXPANDED)
            behavior.setState(ViewPagerBottomSheetBehavior.STATE_COLLAPSED);
    }


    void launchException(String exception) {
        AppHelper.LogEvent(exception);
    }


    public void startRemoveStore(int id, int mFragmentIndex) {
        if (myStore != null)
            myStore.startRemoveStore(id, mFragmentIndex);
    }

    public void launchUpStore(final boolean IsNewlyDownloadedStore, final int startupProductId, final int startupCategoryId, final boolean createShortCut) {
        if (myStore != null) {
            fragmentDashboard.setCurrentItem(4);
            ShowBottomSheet();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myStore.launchStartupStore(IsNewlyDownloadedStore, startupProductId, startupCategoryId, createShortCut);
                }
            }, 500);
        }

    }

    public void startStore(final int vendorId) {
        startStore(vendorId, 0, 0);
    }

    public void startStore(final int vendorId, final int startupProductId, final int startupCategoryId) {
        if (myStore != null) {
            fragmentDashboard.setCurrentItem(4);
            ShowBottomSheet();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    myStore.startStore(vendorId, startupProductId, startupCategoryId, createShortCut);
                    createShortCut = false;
                }
            }, 500);
        }

    }

    public void StartOrLoadStore(int vendorId, int categoryId, int productId) {
        StartOrLoadStore(vendorId, categoryId, productId, "", "");
    }

    public void StartOrLoadStore(int vendorId, int productId, String vendorName, String vendorImage) {
        StartOrLoadStore(vendorId, 0, productId, vendorName, vendorImage);
    }

    public void StartOrLoadStore(int VendorId, int CategoryId, int ProductId, String VendorName, String VendorImage) {
        if (isStoreAlreadyDownloaded(VendorId)) {
            startStore(VendorId, ProductId, CategoryId);
        } else {
            Intent i = new Intent(MainActivity.this, StoreDownloadActivity.class);
            i.putExtra("mStoreId", VendorId);
            i.putExtra("mStoreTitle", VendorName);
            i.putExtra("mStoreLogoUrl", VendorImage);
            i.putExtra("mStartupProductId", ProductId);
            MainActivity.this.startActivityForResult(i, AppConstant.CODE_DOWNLOAD_ACTIVITY);
            MainActivity.this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        }

    }

    public void LoadCategoryProduct(int CategoryId) {
        Fragment mMarketPlaceCategoryFragment = dashboardAdapter.createFragment(1);
        if (mMarketPlaceCategoryFragment instanceof MarketPlaceCategoryFragment) {
            if (mMarketPlaceCategoryFragment.getView() != null) {
                MarketPlaceCategoryFragment bf = (MarketPlaceCategoryFragment) mMarketPlaceCategoryFragment;
                fragmentDashboard.setCurrentItem(1);
                bf.loadDataRestartPaging(CategoryId);
            }
        }

    }

}