package com.growonline.gomobishop;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.growonline.gomobishop.adapter.MainFragmentsAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetCurrency;
import com.growonline.gomobishop.asynctask.AsyncTaskSetCurrency;
import com.growonline.gomobishop.asynctask.AsyncTaskSubscribe;
import com.growonline.gomobishop.control.AVLoadingIndicatorView;
import com.growonline.gomobishop.control.BackStackFragment;
import com.growonline.gomobishop.control.BaseToolbar;
import com.growonline.gomobishop.control.HostFragment;
import com.growonline.gomobishop.fragment.NavigationDrawerFragment;
import com.growonline.gomobishop.fragment.ProductListingFragment;
import com.growonline.gomobishop.model.AllAvailableCurrencies;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.MaxMinAnimation;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StoreActivity extends AppCompatActivity {

    private final int durationMinMaxAnimation = 300;
    private final int minMaxAnimationStartOffset = 200;
    boolean doubleBackToExitPressedOnce = false;
    boolean createShortCut;
    private LinearLayout mLayoutRoot, mControlsLayout;
    private TabLayout mTabLayout;
    private ImageView mPreviewPane;
    private BaseToolbar mBaseToolbar;
    private AppBarLayout mAppBarLayout;
    private String mIdentity;
    private int initWidth, initHeight, startUpProduct;
    private int startUpCategory;
    private boolean enableMaximizeAnimation;
    private DrawerLayout mDrawerLayout;
    private Vendor mVendor;
    protected ActionBarDrawerToggle toggle;
    private MainFragmentsAdapter mainPagerAdapter;
    private Intent intent;
    private AVLoadingIndicatorView loader;
    private LinearLayout blackOut;
    private NavigationDrawerFragment navDrawerFragment;
    private AllAvailableCurrencies allAvailableCurrencies;
    private boolean storeMinimizingAnimationRunning = false;
    private ViewPager2 mainPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_store);

        intent = this.getIntent();
        startUpProduct = intent.getIntExtra(AppConstant.INTENT_PRODUCT_ID, 0);
        startUpCategory = intent.getIntExtra(AppConstant.INTENT_CATEGORY_ID, 0);
        createShortCut = intent.getBooleanExtra(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, false);
        enableMaximizeAnimation = intent.getBooleanExtra(AppConstant.INTENT_MAXIMIZE_ANIMATION, true);
        mIdentity = intent.getStringExtra("identity");
        mVendor = intent.getParcelableExtra(AppConstant.INTENT_VENDOR);

        if (enableMaximizeAnimation) {
            initWidth = intent.getIntExtra("initWidth", 0);
            initHeight = intent.getIntExtra("initHeight", 0);

            if (GoMobileApp.getInitStoreFrameWidth() == 0) {
                GoMobileApp.setInitStoreFrameWidth(initWidth);
                GoMobileApp.setInitStoreFrameHeight(initHeight);
            }
        }

        initUi();
        maximizeScreen();

    }

    void initUi() {

        mAppBarLayout = (AppBarLayout) findViewById(R.id.base_appbar_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayoutRoot = (LinearLayout) findViewById(R.id.layout_root);
        mControlsLayout = (LinearLayout) findViewById(R.id.control_panel);
        mPreviewPane = (ImageView) findViewById(R.id.img_preview);
        mTabLayout = (TabLayout) findViewById(R.id.app_tab_bar);

        setupToolBar();
        setDrawerAnimation();

        mLayoutRoot.setBackgroundColor(Color.parseColor(mVendor.getThemeColor()));
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        // Commit all fragments
        navDrawerFragment = NavigationDrawerFragment.newInstance(mVendor, createShortCut);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.left_drawer, navDrawerFragment);
        ft.commit();

        setUpTabLayout();


        if (startUpProduct > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadStartupProduct();
                }
            }, 1000);
        }
        // TODO: 12/8/2018  GotoCategoryWill be added
        if (startUpCategory > 0) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    setStoreTab(startUpCategory);
                }
            }, 1000);
        }
    }

    @Override
    public void onBackPressed() {
        if (!BackStackFragment.handleBackPressed(getSupportFragmentManager())) {
            if (mainPager.getCurrentItem() != 0) {
                mainPager.setCurrentItem(0, true);
            } else {
                if (doubleBackToExitPressedOnce) {
                    if (!storeMinimizingAnimationRunning)
                        if (BuildConfig.market) {
                            minimizeScreen();
                            return;
                        } else {
                            finish();
                        }
                }
                this.doubleBackToExitPressedOnce = true;
                GoMobileApp.Toast("Please click BACK again to exit the Shop");

                if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            doubleBackToExitPressedOnce = false;
                        }
                    }, 2000);

                }

            }
        }
    }

    void maximizeScreen() {

        if (enableMaximizeAnimation) {
            setPreview();

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(initWidth, initHeight);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.addRule(RelativeLayout.CENTER_HORIZONTAL);
            mLayoutRoot.setLayoutParams(params);

            int deviceHeight = GoMobileApp.getScreenHeight();
            int deviceWidth = GoMobileApp.getScreenWidth();

            MaxMinAnimation max = new MaxMinAnimation(mLayoutRoot);
            max.setDuration(durationMinMaxAnimation);
            max.setStartOffset(minMaxAnimationStartOffset);
            max.setParams(initWidth, deviceWidth, initHeight, deviceHeight);

            mLayoutRoot.startAnimation(max);

            max.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    unHidePreviewMask();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        } else {
            unHidePreviewMask();
        }
    }

    public void minimizeScreen() {
        mBaseToolbar.setArrowDirection(true);
        createAndSavePreview();
        setPreview();
        mPreviewPane.setVisibility(View.VISIBLE);
        mControlsLayout.setVisibility(View.GONE);
        int deviceHeight = GoMobileApp.getScreenHeight();
        int deviceWidth = GoMobileApp.getScreenWidth();

        MaxMinAnimation min = new MaxMinAnimation(mLayoutRoot);
        min.setDuration(durationMinMaxAnimation);
        min.setParams(deviceWidth, GoMobileApp.getInitStoreFrameWidth(),
                deviceHeight, GoMobileApp.getInitStoreFrameHeight());
        mLayoutRoot.startAnimation(min);

        min.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                storeMinimizingAnimationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                storeMinimizingAnimationRunning = false;
                Intent i = new Intent();
                i.putExtra("identity", mIdentity);
                i.putExtra("selectedTabInex", mTabLayout.getSelectedTabPosition());
                setResult(AppConstant.CODE_STORE_ACTIVITY, i);
                finish();
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    void createAndSavePreview() {
        try {
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + getPackageName() + "/" + mVendor.getId() + ".jpg";

//            mLayoutRoot.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(mLayoutRoot.getDrawingCache());
//            mLayoutRoot.setDrawingCacheEnabled(false);

            Bitmap bitmap = getBitmapFromView(mLayoutRoot);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);

            int quality = 60;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    void setPreview() {
        String mPath = Environment.getExternalStorageDirectory().toString() + "/" + getPackageName() + "/" + mVendor.getId() + ".jpg";
        File image = new File(mPath);
        if (image.exists()) {
            mPreviewPane.setVisibility(View.VISIBLE);
            mPreviewPane.setBackground(Drawable.createFromPath(mPath));
        }
    }

    void setUpTabLayout() {

        int initTabIndex = intent.getIntExtra("initTabIndex", 0);

        mainPager = (ViewPager2) findViewById(R.id.fragment_container);
        mainPagerAdapter = new MainFragmentsAdapter(getSupportFragmentManager(), getLifecycle(), getBaseContext(), mVendor);
        mainPager.setAdapter(mainPagerAdapter);
        mainPager.setOffscreenPageLimit(5);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(mTabLayout, mainPager, true, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mainPagerAdapter.getPageTitle(position));
            }
        });
        tabLayoutMediator.attach();

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            LinearLayout linearLayout = (LinearLayout)
                    LayoutInflater.from(this).inflate(R.layout.single_tab_header_item, mTabLayout, false);

            TextView tabTextView = (TextView) linearLayout.findViewById(R.id.tab_title);
            tabTextView.setText(tab.getText());
            tab.setCustomView(linearLayout);
        }

    }

    private void hideKeyboard() {
        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getCurrentFocus() != null)
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshShopCartBadge();
        navDrawerFragment.createNavMenu();
    }

    void setTabWeight(ViewGroup slidingTabStrip, int tabIndex, float weight) {
        View tab = slidingTabStrip.getChildAt(tabIndex);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tab.getLayoutParams();
        layoutParams.weight = weight;
        tab.setLayoutParams(layoutParams);
    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    public void setTabFromFragment(int tabIndex) {
        mainPager.setCurrentItem(tabIndex);
        mTabLayout.getTabAt(tabIndex).select();
    }

    public void setTabFromFragment(AppConstant.MainTabs tab) {

        int tempTabIndex = 0;
        int xTabTitle;

        switch (tab) {
            case HOME:
                xTabTitle = AppConstant.HOME_TAB_TITLE;
                break;
            case CATEGORIES:
                xTabTitle = AppConstant.COLLECTION_TAB_TITLE;
                break;
            case PRODUCTS:
                xTabTitle = AppConstant.PRODUCTS_TAB_TITLE;
                break;
            case SALE:
                xTabTitle = AppConstant.SALE_TAB_TITLE;
                break;
            case LATEST:
                xTabTitle = AppConstant.LATEST_TAB_TITLE;
                break;
            case ABOUT:
                xTabTitle = AppConstant.ABOUT_TAB_TITLE;
                break;
            default:
                xTabTitle = AppConstant.HOME_TAB_TITLE;
                break;
        }

        for (int i = 0; i < mainPagerAdapter.getItemCount(); i++) {
            if (mainPagerAdapter.getPageTitle(i).toString().contentEquals(getBaseContext().getString(xTabTitle))) {
                tempTabIndex = i;
            }
        }

        mTabLayout.getTabAt(tempTabIndex).select();

    }

    public void setStoreTab(int categoryId) {
        mTabLayout.getTabAt(2).select();
        openProductListFragment(categoryId);
    }

    public void setStoreTab(AppConstant.ProductListFragmentMode mode) {
        mTabLayout.getTabAt(2).select();

        switch (mode) {
            case ALL_PRODUCT:
                openAllProductListFragment();
                break;
            case SALE:
                openSaleProductListFragment();
                break;
            case WHATS_NEW:
                openWhatsNewProductListFragment();
                break;
            case SHOP_THE_LOOK:
                openShopTheLookFragment();
                break;
            default:
                break;
        }
    }

    public void openProductSearch() {
        mainPager.setCurrentItem(2);
        mTabLayout.getTabAt(2).select();
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.SEARCH_PRODUCT), true);
    }

    private void openProductListFragment(int categoryId) {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, categoryId, AppConstant.ProductListFragmentMode.SINGLE_CATEGORY), true);
    }

    private void openAllProductListFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.ALL_PRODUCT), false);
    }

    private void openShopTheLookFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.SHOP_THE_LOOK), true);
    }

    private void openSaleProductListFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.SALE), false);
    }

    private void openWhatsNewProductListFragment() {
        HostFragment hostFragment = (HostFragment) mainPagerAdapter.createFragment(mainPager.getCurrentItem());
        hostFragment.replaceFragment(ProductListingFragment.newInstance(mVendor, 0, AppConstant.ProductListFragmentMode.WHATS_NEW), false);
    }

    public void openProductDetailFragment(int productId, String title, List<Product> allProduct) {
        Intent i = new Intent(this, RelatedProductsActivity.class);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putParcelableArrayListExtra(AppConstant.INTENT_PRODUCT, new ArrayList<Product>(allProduct));
        i.putExtra(AppConstant.INTENT_PRODUCT_ID, productId);
        i.putExtra(AppConstant.INTENT_TITLE, title);
        startActivity(i);
        overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public void setLoadingAnimation(boolean show) {

        if (loader == null) {
            loader = (AVLoadingIndicatorView) findViewById(R.id.loader);
            blackOut = (LinearLayout) findViewById(R.id.blackOut);

            blackOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //dummy click listener to disable click behind
                }
            });
        }

        if (show) {
            blackOut.setVisibility(View.VISIBLE);
            blackOut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            loader.setVisibility(View.VISIBLE);
            loader.show();
        } else {
            loader.smoothToHide();
            Animation fadeOut = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
            blackOut.startAnimation(fadeOut);

            fadeOut.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    blackOut.setVisibility(View.GONE);
                    loader.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    public void setupToolBar() {
        if (mBaseToolbar == null) {
            mBaseToolbar = new BaseToolbar(StoreActivity.this, BaseToolbar.ToolBarType.CUSTOMIZABLE);

            mBaseToolbar.setBackgroundColor(mVendor.getThemeColor());
            mBaseToolbar.setNavigationProps(R.drawable.ic_menu_white, new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    AppHelper.toggleNavigation(mDrawerLayout, Gravity.START);
                }
            });
            mBaseToolbar.setVendor(mVendor);
//            mBaseToolbar.setMinimizeStoreClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    minimizeScreen();
//                }
//            });
            mBaseToolbar.install(mAppBarLayout);
        }
    }

    public void refreshShopCartBadge() {
        mBaseToolbar.refreshShopCartBadge();
    }

    public void unHidePreviewMask() {
        mPreviewPane.setVisibility(View.GONE);
        mControlsLayout.setVisibility(View.VISIBLE);
        mBaseToolbar.setArrowDirection(false);
    }

    public void setToolBarTitle(String title) {
        if (mBaseToolbar != null)
            mBaseToolbar.setToolBarTitle(title);
    }

    public Vendor getmVendor() {
        return mVendor;
    }

    void setDrawerAnimation() {

        final float MIN_SCALE = 0.75f;
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

                float moveFactor = (drawerView.getWidth() * slideOffset);
                float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(slideOffset));

                mLayoutRoot.setPivotY(mLayoutRoot.getHeight() / 2);

                if (mLayoutRoot.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    mLayoutRoot.setPivotX(drawerView.getWidth());
                    mLayoutRoot.setTranslationX((1 - MIN_SCALE) - moveFactor);
                } else {
                    mLayoutRoot.setPivotX(0);
                    mLayoutRoot.setTranslationX(moveFactor);
                }
                mLayoutRoot.setScaleX(scaleFactor);
                mLayoutRoot.setScaleY(scaleFactor);
                Log.e("onDrawerSlide", "onDrawerSlide: " + scaleFactor);

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {
                hideKeyboard();
            }
        });


    }

    private void loadStartupProduct() {

        ArrayList<Product> tempList = new ArrayList<>();
        Product p = new Product();
        p.setId(startUpProduct);
        p.setName("");
        tempList.add(p);

        Intent i = new Intent(this, RelatedProductsActivity.class);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putExtra(AppConstant.INTENT_PRODUCT_ID, startUpProduct);
        i.putExtra(AppConstant.INTENT_PRODUCT, new Gson().toJson(tempList, new TypeToken<List<Product>>() {
        }.getType()));

        startActivity(i);
    }

    public void SubscribeToShop() {

        if (GoMobileApp.IsUserLogin()) {
            String mSignedInEmail = GoMobileApp.getStringPrefs(AppConstant.USER_EMAIL_PREFS_KEY);
            new AsyncTaskSubscribe(StoreActivity.this, false, mSignedInEmail, mVendor.getId()).execute();
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(StoreActivity.this, R.style.AppTheme_Dialog);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.subscribe, null);
            alertDialogBuilder.setView(dialogView);
            final EditText emailEditView = (EditText) dialogView.findViewById(R.id.email);
            alertDialogBuilder.setMessage("SUBSCRIBE TO " + mVendor.getName()).setPositiveButton(R.string.subscribe, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    String email = emailEditView.getText().toString();
                    if (GoMobileApp.isValidEmail(email))
                        new AsyncTaskSubscribe(StoreActivity.this, false, email, mVendor.getId()).execute();
                    else {
                        GoMobileApp.Toast("Invalid Email");
                    }
                }
            }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).show();
        }


    }

    public void ChangeCurrency() {
        new AsyncTaskGetCurrency(StoreActivity.this, false).execute();
    }

    public void updateCurrency(AllAvailableCurrencies mAllAvailableCurrencies) {
        if (mAllAvailableCurrencies == null) return;
        allAvailableCurrencies = mAllAvailableCurrencies;
        ArrayList<String> countries = new ArrayList<>();
        for (AllAvailableCurrencies.AvailableCurrency s : mAllAvailableCurrencies.getAvailableCurrencies()) {
            countries.add(s.getName());
        }
        mAllAvailableCurrencies.getAvailableCurrencies().get(0).getName();
        AlertDialog.Builder builder = new AlertDialog.Builder(StoreActivity.this, R.style.AppTheme_Dialog);
        builder.setTitle(getString(R.string.title_change_currency));
        CharSequence[] cs = countries.toArray(new CharSequence[countries.size()]);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int currencyId = allAvailableCurrencies.getAvailableCurrencies().get(which).getId();
                SetCurrency(currencyId);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void SetCurrency(int currencyId) {
        new AsyncTaskSetCurrency(StoreActivity.this, false, currencyId).execute();
    }

    public void ChangeLanguage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(StoreActivity.this, R.style.AppTheme_Dialog);
        builder.setTitle(getString(R.string.title_change_language));
        ArrayList<String> languages = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.language_array)));
        CharSequence[] cs = languages.toArray(new CharSequence[languages.size()]);
        builder.setItems(cs, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Locale myLocale = new Locale(getResources().getStringArray(R.array.language_local_array)[which]);
                Resources res = getResources();
                DisplayMetrics dm = res.getDisplayMetrics();
                Configuration conf = res.getConfiguration();
                conf.locale = myLocale;
                res.updateConfiguration(conf, dm);
                updatedCurrency(R.string.lanuage_updated);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void updatedCurrency(int mMessage) {
        GoMobileApp.Toast(mMessage);
        Intent intent = new Intent();
        intent = intent.setClass(StoreActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}