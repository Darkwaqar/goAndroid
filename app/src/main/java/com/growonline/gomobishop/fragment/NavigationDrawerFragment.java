package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.AddressActivity;
import com.growonline.gomobishop.BuildConfig;
import com.growonline.gomobishop.CatalogActivity;
import com.growonline.gomobishop.ChangePasswordActivity;
import com.growonline.gomobishop.ContactActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.LoginSignUpActivity;
import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.OrderListingActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RatingActivity;
import com.growonline.gomobishop.ReviewsActivity;
import com.growonline.gomobishop.RewardPointsActivity;
import com.growonline.gomobishop.SplashScreen;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.TutorialActivity;
import com.growonline.gomobishop.WebViewActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetProductsAsyncTask;
import com.growonline.gomobishop.model.Category;
import com.growonline.gomobishop.model.PagedProducts;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.network.NetworkUtils;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.SystemIntents;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NavigationDrawerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationDrawerFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final int CATALOG_ID = -16;

    private Vendor mVendor;
    private LinearLayout categoryListView;
    private List<Category> mCategoryList;
    private List<Category> mCatalogList;
    private StoreActivity mActivity;
    private Boolean haveShopTheLook = false;
    private Boolean createShortCut;
    private ScrollView mainScrollView;
    private RatingBar vendorRating;
    private TextView total_reviews;
    int OrderBy = AppConstant.ProductSortingEnum.Position.getValue();
    private LinearLayout ratingLayout;
    private GetProductsAsyncTask backgroundTask;
    private int mPageSize = 14;
    private int pageNumber = 1, mTotalPages;
    private List<SpecificationAttribute> mSpecList = new ArrayList<>();
    private Context mContext;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param vendor         Parameter 1.
     * @param createShortCut Boolean CreateShortCut
     * @return A new instance of fragment NavigationDrawerFragment.
     */
    public static NavigationDrawerFragment newInstance(Vendor vendor, boolean createShortCut) {
        NavigationDrawerFragment fragment = new NavigationDrawerFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        args.putBoolean(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN, createShortCut);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
            createShortCut = getArguments().getBoolean(AppConstant.INTENT_CREATE_SHORTCUT_BOOLEAN);
        }
        mContext = getActivity().getBaseContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mContext = getActivity().getBaseContext();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivity().getBaseContext();
        initUi(view);
    }

    void initUi(View view) {
        mActivity = (StoreActivity) getActivity();

        ImageView img_logo = (ImageView) view.findViewById(R.id.img_store);
        vendorRating = (RatingBar) view.findViewById(R.id.ratingBar);
        total_reviews = (TextView) view.findViewById(R.id.total_reviews);
        ratingLayout = (LinearLayout) view.findViewById(R.id.rating_layout);

        GoMobileApp.getmCacheManager().loadImageWithFit(Uri.parse(mVendor.getLogoUrl()), img_logo);
        categoryListView = (LinearLayout) view.findViewById(R.id.lst_category);
        mainScrollView = (ScrollView) view.findViewById(R.id.fragment_nav_scrollview);
        loadAllCategories();
        TextView btnSearch = (TextView) view.findViewById(R.id.btn_search);
        btnSearch.setText(BuildConfig.market ? String.format("%s %s", btnSearch.getText(), mVendor.getName()) : btnSearch.getText());
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearch();
            }
        });
        if (createShortCut) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    CreateShortCut();
                }
            }, 1000);
        }

        initRating();
    }

    private void initRating() {
        vendorRating.setRating(mVendor.getRating() != null ? mVendor.getRating().getAverageRating() : 5);
        total_reviews.setText(mVendor.getRating() != null ? String.valueOf(mVendor.getRating().getTotalRatings()) : "0");
        ratingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rating = new Intent(mActivity, RatingActivity.class);
                rating.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                rating.putExtra("Type", AppConstant.VENDOR_REVIEW);
                rating.putExtra("Id", mVendor.getId());
                startActivity(rating);
            }
        });
    }


    void loadAllCategories() {
        mCategoryList = mVendor.getCategories();
        haveShopTheLook = mVendor.getHaveShoptheLook();
    }

    public void openSearch() {
        StoreActivity StoreActivity = (StoreActivity) getActivity();
        StoreActivity.getmDrawerLayout().closeDrawer(GravityCompat.START);
        StoreActivity.openProductSearch();
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    private void addRecursiveCategory(List<Category> mBeanLeftMenuModel, LayoutInflater inflater, ViewGroup resource, boolean isRootItem) {

        for (int i = 0; i < mBeanLeftMenuModel.size(); i++) {

            final Category bean = mBeanLeftMenuModel.get(i);

            if (bean.getIsVisible()) {
                final LinearLayout v = (LinearLayout) inflater.inflate(R.layout.single_category_nav_drawer_item, null);
                TextView txtview = (TextView) v.findViewById(R.id.adapter_left_menu_txtview);
                final LinearLayout mSubCategoryLayout = (LinearLayout) v.findViewById(R.id.sub_category_layout);
                final ImageView imgView = (ImageView) v.findViewById(R.id.arrow_dropdown);
                final View rootCategoryIcon = v.findViewById(R.id.root_item_icon);

                txtview.setText(bean.getName());
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {

                        if (bean.getSubCategories() != null && bean.getSubCategories().size() != 0) {
                            if (mSubCategoryLayout.getVisibility() == View.VISIBLE) {
                                mSubCategoryLayout.setVisibility(View.GONE);
                                imgView.setImageResource(R.drawable.ic_add_black);
                                imgView.setColorFilter(ContextCompat.getColor(mActivity,
                                        R.color.colorPrimaryDark));
                            } else {
                                mSubCategoryLayout.setVisibility(View.VISIBLE);
                                imgView.setImageResource(R.drawable.ic_remove_black);
                                imgView.setColorFilter(ContextCompat.getColor(mActivity,
                                        R.color.colorPrimaryDark));
                            }
                            mainScrollView.post(new Runnable() {
                                @Override
                                public void run() {
                                    mainScrollView.smoothScrollTo(0, view.getTop());
                                }
                            });
                        } else {
                            internalClickEvent(bean);
                        }
                    }

                    private void internalClickEvent(Category bean) {
                        mActivity.getmDrawerLayout().closeDrawer(GravityCompat.START);

                        if (bean.getParentId() == CATALOG_ID) {
                            Intent i = new Intent(getActivity(), CatalogActivity.class);
                            i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                            i.putExtra("pos", bean.getId());
                            startActivity(i);
                            return;
                        }

                        if (bean.getId() > 0) {
                            mActivity.setStoreTab(bean.getId());
                        } else {
                            switch (bean.getId()) {
                                case AppConstant.HOME_TAB:
                                    mActivity.setTabFromFragment(AppConstant.MainTabs.HOME);
                                    break;

                                case AppConstant.CATEGORIES:
                                    break;

                                case AppConstant.ABOUT:
                                    mActivity.setTabFromFragment(AppConstant.MainTabs.ABOUT);
                                    break;

                                case AppConstant.BLOG:
                                    String url = mVendor.getBlogUrl();
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    startActivity(i);
                                    break;

                                case AppConstant.ALL_PRODUCTS:
                                    mActivity.setStoreTab(AppConstant.ProductListFragmentMode.ALL_PRODUCT);
                                    break;

                                case AppConstant.SALES:
                                    mActivity.setTabFromFragment(AppConstant.MainTabs.SALE);
                                    break;

                                case AppConstant.NEW:
                                    mActivity.setTabFromFragment(AppConstant.MainTabs.LATEST);
                                    break;

                                case AppConstant.USER_INFO:
                                    if (GoMobileApp.IsUserLogin()) {
                                        Intent myAccount = new Intent(mActivity, MyAccountActivity.class);
                                        myAccount.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        startActivity(myAccount);
                                    } else {
                                        Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                        login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_MY_ACCOUNT);
                                        startActivity(login);
                                    }

                                    break;

                                case AppConstant.WISH_LIST:
                                    if (!GoMobileApp.IsUserLogin()) {
                                        Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                        login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_WISH_LIST);
                                        startActivity(login);
                                        return;
                                    }
                                    Intent iLike = new Intent(mActivity, WishListDetailsActivity.class);
                                    iLike.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(iLike);
                                    break;

                                case AppConstant.MY_ORDER:
                                    if (!GoMobileApp.IsUserLogin()) {
                                        Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                        login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_ORDER_LIST);
                                        startActivity(login);
                                        return;
                                    }
                                    Intent order = new Intent(mActivity, OrderListingActivity.class);
                                    order.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(order);
                                    break;


                                case AppConstant.REVIEW_CENTER:
                                    if (!GoMobileApp.IsUserLogin()) {
                                        Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                        login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_REVIEWS);
                                        startActivity(login);
                                        return;
                                    }
                                    Intent Reviews = new Intent(mActivity, ReviewsActivity.class);
                                    Reviews.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(Reviews);
                                    break;

                                case AppConstant.LOYALTY_PROGRAM:
                                    Intent address = new Intent(mActivity, RewardPointsActivity.class);
                                    address.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(address);
                                    break;

                                case AppConstant.FAQ:
                                    Intent activityFaqIntent = new Intent(mActivity, WebViewActivity.class);
                                    activityFaqIntent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    activityFaqIntent.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getFaqUrl() + "/true");
                                    activityFaqIntent.putExtra(AppConstant.INTENT_TITLE, AppConstant.FAQ_TITLE);
                                    startActivity(activityFaqIntent);
                                    break;

                                case AppConstant.TERM_AND_CONDITION:
                                    Intent activityTermAndConditionIntent = new Intent(mActivity, WebViewActivity.class);
                                    activityTermAndConditionIntent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    activityTermAndConditionIntent.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getTermAndConditionsUrl() + "/true");
                                    activityTermAndConditionIntent.putExtra(AppConstant.INTENT_TITLE, AppConstant.TERM_AND_CONDITION_TITLE);
                                    startActivity(activityTermAndConditionIntent);
                                    break;

                                case AppConstant.RETURN_AND_REFUND:
                                    Intent returnAndRefund = new Intent(mActivity, WebViewActivity.class);
                                    returnAndRefund.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    returnAndRefund.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getReturnAndRefundUrl() + "/true");
                                    returnAndRefund.putExtra(AppConstant.INTENT_TITLE, AppConstant.RETURN_AND_REFUND_TITLE);
                                    startActivity(returnAndRefund);
                                    break;

                                case AppConstant.DELIVERY_POLICY:
                                    Intent deliveryPolicy = new Intent(mActivity, WebViewActivity.class);
                                    deliveryPolicy.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    deliveryPolicy.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getDeliveryPolicyUrl() + "/true");
                                    deliveryPolicy.putExtra(AppConstant.INTENT_TITLE, AppConstant.DELIVERY_POLICY_TITLE);
                                    startActivity(deliveryPolicy);
                                    break;

                                case AppConstant.SHIPPING_INFORMATION:
                                    Intent shippingInformation = new Intent(mActivity, WebViewActivity.class);
                                    shippingInformation.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    shippingInformation.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getShippingInformationUrl() + "/true");
                                    shippingInformation.putExtra(AppConstant.INTENT_TITLE, AppConstant.SHIPPING_INFORMATION_TITLE);
                                    startActivity(shippingInformation);
                                    break;

                                case AppConstant.RETURN_AND_EXCHANGE_POLICY:
                                    Intent returnAndExchangePolicy = new Intent(mActivity, WebViewActivity.class);
                                    returnAndExchangePolicy.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    returnAndExchangePolicy.putExtra(AppConstant.INTENT_URL, AppConstant.T_POP_UP_URL + mVendor.getReturnAndExchangePolicyUrl() + "/true");
                                    returnAndExchangePolicy.putExtra(AppConstant.INTENT_TITLE, AppConstant.RETURN_AND_EXCHANGE_POLICY_TITLE);
                                    startActivity(returnAndExchangePolicy);
                                    break;

                                case AppConstant.CONTACT:
                                    Intent contactScreenIntent = new Intent(mActivity, ContactActivity.class);
                                    contactScreenIntent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(contactScreenIntent);
                                    break;

                                case AppConstant.APPOINTMENT:
                                    Intent appointmentScreenIntent = new Intent(mActivity, ContactActivity.class);
                                    appointmentScreenIntent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    appointmentScreenIntent.putExtra(AppConstant.INTENT_SHOW_CONTACT, true);
                                    startActivity(appointmentScreenIntent);
                                    break;

                                case AppConstant.FOLLOW:
                                    Intent contactScreenIntentFollow = new Intent(mActivity, ContactActivity.class);
                                    contactScreenIntentFollow.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    contactScreenIntentFollow.putExtra(AppConstant.INTENT_SHOW_FOLLOW_LINK, true);
                                    startActivity(contactScreenIntentFollow);
                                    break;


                                case AppConstant.CHANGE_PASSWORD:
                                    if (!GoMobileApp.IsUserLogin()) {
                                        Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                        login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_CHANGE_PASSWORD);
                                        startActivity(login);
                                        return;
                                    }
                                    Intent changePassword = new Intent(mActivity, ChangePasswordActivity.class);
                                    changePassword.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(changePassword);
                                    break;

                                case AppConstant.LOGOUT:
                                    AppHelper.Logout();
                                    if (BuildConfig.market)
                                        mActivity.minimizeScreen();
                                    else {
                                        createNavMenu();
                                    }
                                    break;

                                case AppConstant.LOGIN:
                                    Intent login = new Intent(mActivity, LoginSignUpActivity.class);
                                    login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(login);
                                    break;

                                case AppConstant.HOW_TO_USE:
                                    Intent tutorialActivityIntent = new Intent(mActivity, TutorialActivity.class);
                                    startActivity(tutorialActivityIntent);
                                    break;

                                case AppConstant.SUBSCRIBE:
                                    mActivity.SubscribeToShop();
                                    break;

                                case AppConstant.SHARE_STORE:
                                    String sharingLink = AppConstant.GET_SHARE_STORE + mVendor.getId();
                                    SystemIntents.share(getActivity(), sharingLink, null,
                                            NetworkUtils.PLAIN_TEXT);
                                    break;

                                case AppConstant.Create_SHORTCUT:
                                    CreateShortCut();
                                    break;

                                case AppConstant.SHOP_THE_LOOK:
                                    openShopTheLook();
//                                    mActivity.setStoreTab(AppConstant.ProductListFragmentMode.SHOP_THE_LOOK);
                                    break;

                                case AppConstant.EXIT_STORE:
                                    mActivity.minimizeScreen();
                                    break;

                                case AppConstant.CURRENCY:
                                    mActivity.ChangeCurrency();
                                    break;

                                case AppConstant.LANGUAGE:
                                    mActivity.ChangeLanguage();
                                    break;

                                case AppConstant.MY_ADDRESS:
                                    if (!GoMobileApp.IsUserLogin()) {
                                        Intent intent = new Intent(mActivity, LoginSignUpActivity.class);
                                        intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                        intent.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_ADDRESS);
                                        startActivity(intent);
                                        return;
                                    }
                                    Intent intent = new Intent(mActivity, AddressActivity.class);
                                    intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    startActivity(intent);
                                    break;


                            }
                        }
                    }
                });

                if (bean.getSubCategories() != null && bean.getSubCategories().size() > 0) {
                    addRecursiveCategory(mBeanLeftMenuModel.get(i).getSubCategories(), inflater, mSubCategoryLayout, false);
                    mSubCategoryLayout.setVisibility(View.GONE);
                    imgView.setVisibility(View.VISIBLE);


                } else {
                    imgView.setVisibility(View.GONE);
                }

                if (isRootItem) {
                    rootCategoryIcon.setVisibility(View.VISIBLE);
                } else {
//                    rootCategoryIcon.getLayoutParams().width = AppHelper.dpToPx(60, getActivity());
                    rootCategoryIcon.setVisibility(View.INVISIBLE);
                    txtview.setTextColor(Color.parseColor("#666666"));
                }

                resource.addView(v);
            }
        }

    }

    public void openShopTheLook() {
        final StoreActivity sActivity = ((StoreActivity) mActivity);
        backgroundTask = new GetProductsAsyncTask(mVendor.getId(), pageNumber, mPageSize, OrderBy,
                AppHelper.GetSelectedSpecificationAttributes(mSpecList), AppConstant.ProductListFragmentMode.SHOP_THE_LOOK);
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<PagedProducts>() {
            @Override
            public void response(final AsyncTaskResult<PagedProducts> response) {
                sActivity.setLoadingAnimation(false);
                if (!response.hasException()) {
                    List<Product> products = response.getResult().getProducts();
                    if (products.size() > 0) {
                        sActivity.openProductDetailFragment(products.get(0).getId(), products.get(0).getName(), products);
                    }

                } else {
                    if (response.getException() instanceof NetworkErrorException) {
                        launchException(response.getException().getMessage(), response.getException(), false);
                    } else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }
            }
        });
        backgroundTask.execute();
        sActivity.setLoadingAnimation(true);
    }

    public void createNavMenu() {

        categoryListView.removeAllViews();

        ArrayList<Category> menuList = new ArrayList<>();


        //Signin or signout
        Category loginStatusMenu = new Category();
        loginStatusMenu.setIsVisible(true);

        if (GoMobileApp.IsUserLogin()) {
            loginStatusMenu.setId(AppConstant.LOGOUT);
            loginStatusMenu.setName(mContext.getResources().getString(AppConstant.LOGOUT_TITLE));
        } else {
            loginStatusMenu.setId(AppConstant.LOGIN);
            loginStatusMenu.setName(mContext.getResources().getString(AppConstant.LOGIN_TITLE));
        }
        menuList.add(loginStatusMenu);


        if (mVendor.getMobileAppSetting().getShortcutEnabled()) {
            Category shortcut = new Category();
            shortcut.setIsVisible(true);
            shortcut.setName(mContext.getResources().getString(AppConstant.Create_SHORTCUT_TITLE));
            shortcut.setId(AppConstant.Create_SHORTCUT);
            menuList.add(shortcut);
        }

//        Category store = new Category();
//        store.setId(AppConstant.ALL_PRODUCTS);
//        store.setName();(AppConstant.ALL_PRODUCTS_TITLE);
//        menuList.add(store);

        if (mVendor.getMobileAppSetting().getNewTabEnabled()) {
            Category newProducts = new Category();
            newProducts.setId(AppConstant.NEW);
            newProducts.setName(mContext.getResources().getString(AppConstant.NEW_TITLE));
            menuList.add(newProducts);
        }


        Category collection = new Category();
        collection.setId(AppConstant.CATEGORIES);
        collection.setName(mContext.getResources().getString(AppConstant.ALL_PRODUCTS_TITLE));
        collection.setHasSubCategory(true);

        if (mCategoryList != null)
            collection.setSubCategories(mCategoryList);

        menuList.add(collection);

        if (mVendor.getMobileAppSetting().getSalesTabEnabled()) {
            Category sale = new Category();
            sale.setId(AppConstant.SALES);
            sale.setName(mContext.getResources().getString(AppConstant.SALES_TITLE));
            menuList.add(sale);
        }

        if (haveShopTheLook) {
            Category shopTheLook = new Category();
            shopTheLook.setId(AppConstant.SHOP_THE_LOOK);
            shopTheLook.setName(mContext.getResources().getString(AppConstant.SHOP_THE_LOOK_TITLE));
            menuList.add(shopTheLook);
        }


        //Loyalty Program menu
//        if (mVendor.getMobileAppSetting().getLoyalityEnabled()) {
//            Category loyaltyProgram = new Category();
//            loyaltyProgram.setId(AppConstant.LOYALTY_PROGRAM);
//            loyaltyProgram.setName();(AppConstant.LOYALTY_PROGRAM_TITLE);
//            menuList.add(loyaltyProgram);
//        }

        //Catalogue
        if (mVendor.getMobileAppSetting().getCallToOrderEnabled() &&
                mCatalogList != null &&
                mCatalogList.size() > 0) {

            Category cat = new Category();
            cat.setId(CATALOG_ID);
            cat.setName("Catalog");
            cat.setHasSubCategory(true);

            if (mCatalogList != null)
                cat.setSubCategories(mCatalogList);
            menuList.add(cat);
        }


        //About

        //Blog
        if (mVendor.getBlogUrl() != null &&
                mVendor.getBlogUrl().trim().length() > 0) {
            Category blog = new Category();
            blog.setId(AppConstant.BLOG);
            blog.setName("Blog");
            menuList.add(blog);
        }


        //region My Account
        ArrayList<Category> myAccountSub = new ArrayList<>();

        //myinfo
        Category myInfo = new Category();
        myInfo.setId(AppConstant.USER_INFO);
        myInfo.setName(mContext.getResources().getString(AppConstant.USER_INFO_TITLE));
        myAccountSub.add(myInfo);


        //Order
        Category Orders = new Category();
        Orders.setId(AppConstant.MY_ORDER);
        Orders.setName(mContext.getResources().getString(AppConstant.MY_ORDER_TITLE));
        myAccountSub.add(Orders);

        //Change Password
//        Category ChangePassword = new Category();
//        ChangePassword.setId(AppConstant.CHANGE_PASSWORD);
//        ChangePassword.setName();(AppConstant.CHANGE_PASSWORD_TITLE);
//        myAccountSub.add(ChangePassword);

        //Addresses
        Category address = new Category();
        address.setId(AppConstant.MY_ADDRESS);
        address.setName(mContext.getResources().getString(AppConstant.MY_ADDRESS_TITLE));
        myAccountSub.add(address);

        Category Reviews = new Category();
        Reviews.setId(AppConstant.REVIEW_CENTER);
        Reviews.setName(mContext.getResources().getString(AppConstant.REVIEW_CENTER_TITLE));
        myAccountSub.add(Reviews);


        Category myAccount = new Category();
        myAccount.setName(mContext.getResources().getString(AppConstant.MY_ACCOUNT_NAVIGATION_TITLE));
        myAccount.setHasSubCategory(true);
        myAccount.setSubCategories(myAccountSub);
        menuList.add(myAccount);

        //endregion


        //Wishlist
        Category iLike = new Category();
        iLike.setId(AppConstant.WISH_LIST);
        iLike.setName(mContext.getResources().getString(AppConstant.WISH_LIST_TITLE));
        menuList.add(iLike);


        //MORE

        ArrayList<Category> More = new ArrayList<>();

        Category about = new Category();
        about.setId(AppConstant.ABOUT);
        about.setName(mContext.getResources().getString(AppConstant.ABOUT_NAVIGATION_TITLE));
        More.add(about);

        Category tutorialMenu = new Category();
        tutorialMenu.setIsVisible(true);
        tutorialMenu.setName(mContext.getResources().getString(AppConstant.HOW_TO_USE_TITLE));
        tutorialMenu.setId(AppConstant.HOW_TO_USE);
        More.add(tutorialMenu);

        //FAQs
        if (mVendor.getFaqUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.FAQ);
            faq.setName(mContext.getResources().getString(AppConstant.FAQ_TITLE));
            More.add(faq);
        }

        if (mVendor.getTermAndConditionsUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.TERM_AND_CONDITION);
            faq.setName(mContext.getResources().getString(AppConstant.TERM_AND_CONDITION_TITLE));
            More.add(faq);
        }

        if (mVendor.getReturnAndRefundUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.RETURN_AND_REFUND);
            faq.setName(mContext.getResources().getString(AppConstant.RETURN_AND_REFUND_TITLE));
            More.add(faq);
        }

        if (mVendor.getDeliveryPolicyUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.DELIVERY_POLICY);
            faq.setName(mContext.getResources().getString(AppConstant.DELIVERY_POLICY_TITLE));
            More.add(faq);
        }

        if (mVendor.getShippingInformationUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.SHIPPING_INFORMATION);
            faq.setName(mContext.getResources().getString(AppConstant.SHIPPING_INFORMATION_TITLE));
            More.add(faq);
        }

        if (mVendor.getReturnAndExchangePolicyUrl() != null) {
            Category faq = new Category();
            faq.setId(AppConstant.RETURN_AND_EXCHANGE_POLICY);
            faq.setName(mContext.getResources().getString(AppConstant.RETURN_AND_EXCHANGE_POLICY_TITLE));
            More.add(faq);
        }
        //Contact
        Category contact = new Category();
        contact.setId(AppConstant.CONTACT);
        contact.setName(mContext.getResources().getString(AppConstant.CONTACT_TITLE));
        More.add(contact);


        //Follow
        Category follow = new Category();
        follow.setId(AppConstant.FOLLOW);
        follow.setName(mContext.getResources().getString(AppConstant.FOLLOW_TITLE));
        More.add(follow);


        Category MoreCategory = new Category();
        MoreCategory.setName(mContext.getResources().getString(AppConstant.MORE_TITLE));
        MoreCategory.setHasSubCategory(true);
        MoreCategory.setSubCategories(More);
        menuList.add(MoreCategory);


        Category currency = new Category();
        currency.setId(AppConstant.CURRENCY);
        currency.setName(mContext.getResources().getString(AppConstant.CURRENCY_TITLE));
        menuList.add(currency);

        Category language = new Category();
        language.setId(AppConstant.LANGUAGE);
        language.setName(mContext.getResources().getString(AppConstant.LANGUAGE_TITLE));
        menuList.add(language);


//        Category settings = new Category();
//        settings.setId(AppConstant.SETTINGS);
//        settings.setName();(AppConstant.SETTINGS_TITLE);
//        menuList.add(settings);

        Category shareStore = new Category();
        shareStore.setIsVisible(true);
        shareStore.setName(mContext.getResources().getString(AppConstant.SHARE_STORE_TITLE));
        shareStore.setId(AppConstant.SHARE_STORE);
        menuList.add(shareStore);

        //appointment
        if (mVendor.getMobileAppSetting().getAppintmentEnable()) {
            Category appointment = new Category();
            appointment.setId(AppConstant.APPOINTMENT);
            appointment.setName(mContext.getResources().getString(AppConstant.APPOINTMENT_TITLE));
            menuList.add(appointment);
        }
        //VendorSubscription
        Category vendorSubscription = new Category();
        vendorSubscription.setId(AppConstant.SUBSCRIBE);
        vendorSubscription.setName(mContext.getResources().getString(AppConstant.SUBSCRIBE_TITLE));
        menuList.add(vendorSubscription);

        if (BuildConfig.market) {
            Category close = new Category();
            close.setId(AppConstant.EXIT_STORE);
            close.setName(mContext.getResources().getString(AppConstant.EXIT_STORE_TITLE));
            menuList.add(close);
        }
        addRecursiveCategory(menuList, LayoutInflater.from(getActivity()), categoryListView, true);
    }

    public void CreateShortCut() {
        Picasso.with(getActivity()).load(mVendor.getLogoUrl()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Intent shortcutIntent = new Intent(getActivity(), SplashScreen.class);
                shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                shortcutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                shortcutIntent.setData(Uri.parse(String.format(AppConstant.WIDGET_URI, mVendor.getId())));

                Intent addIntent = new Intent();
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, mVendor.getName());

                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 128, 128, true);
                addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, scaledBitmap);
//                                            addIntent.putExtra("duplicate", true);
                addIntent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    ShortcutManager mShortcutManager =
                            mActivity.getSystemService(ShortcutManager.class);

                    shortcutIntent.setAction(Intent.ACTION_CREATE_SHORTCUT);


                    ShortcutInfo shortcut = new ShortcutInfo.Builder(mActivity, String.valueOf(mVendor.getId()))
                            .setShortLabel(mVendor.getName())
                            .setLongLabel(mVendor.getName())

                            .setIcon(Icon.createWithBitmap(scaledBitmap))
                            .setIntent(shortcutIntent)
                            .build();
                    mShortcutManager.setDynamicShortcuts(Arrays.asList(shortcut));

                    if (mShortcutManager.isRequestPinShortcutSupported()) {

                        ShortcutInfo pinShortcutInfo =
                                new ShortcutInfo.Builder(mActivity, String.valueOf(mVendor.getId())).build();
                        Intent pinnedShortcutCallbackIntent =
                                mShortcutManager.createShortcutResultIntent(pinShortcutInfo);
                        PendingIntent successCallback = PendingIntent.getBroadcast(mActivity, 0,
                                pinnedShortcutCallbackIntent, 0);

                        mShortcutManager.requestPinShortcut(pinShortcutInfo,
                                successCallback.getIntentSender());

                    }
                } else {
                    getActivity().sendBroadcast(addIntent);
                    GoMobileApp.Toast("Shortcut created successfully");
                }

            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("error", "Getting image");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.e("preparing", "Loading");
            }
        });
    }
}