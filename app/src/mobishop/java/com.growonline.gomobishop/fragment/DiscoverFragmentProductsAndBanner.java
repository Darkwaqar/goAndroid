package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.BannerAdapter;
import com.growonline.gomobishop.adapter.SimpleRecyclerViewAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetFeaturedItemsAsyncTask;
import com.growonline.gomobishop.asynctask.GetProductsAsyncTask;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.PaddingItemDecoration;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.Banner;
import com.growonline.gomobishop.model.Category;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.model.PagedProducts;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator2;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragmentProductsAndBanner extends Fragment {

    private static final String ARG_VENDOR = "ARG_VENDOR";
    int OrderBy = AppConstant.ProductSortingEnum.Position.getValue();
    Handler mHandler;
    Runnable mRunnable;
    int currentPosition = 0;
    private RecyclerView bannerRecyclerView;
    private RecyclerView discoverCategoryRecycler;
    private Vendor mVendor;
    private GetFeaturedItemsAsyncTask backgroundTask;
    private List<CategorySimpleModel> categories = new ArrayList<>();
    private List<CategorySimpleModel> categoriesProduct = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Banner> banners = new ArrayList<>();
    private AspectRatioImageView secondBanner;
    private AspectRatioImageView thirdBanner;
    private AspectRatioImageView forthBanner;
    private com.growonline.gomobishop.asynctask.GetProductsAsyncTask GetProductsAsyncTask;
    private int mPageSize = 14;
    private int pageNumber = 1, mTotalPages;
    private List<SpecificationAttribute> mSpecList = new ArrayList<>();
    private CircleIndicator2 indicator;
    private CircleIndicator2 indicator2;


    private SimpleRecyclerViewAdapter categoryRecyclerViewAdapter;

    public static Fragment newInstance(Vendor mVendor) {
        DiscoverFragmentProductsAndBanner fragment = new DiscoverFragmentProductsAndBanner();
        Bundle args = new Bundle();
        args.putParcelable(ARG_VENDOR, mVendor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(ARG_VENDOR);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discover_products_banner, container, false);
        InitUi(v);
        GetBanners();
        return v;
    }

    private void GetBanners() {
        if (mVendor.getBanners() != null) {
            BannerAdapter bannerAdapter = new BannerAdapter(mVendor.getBanners());
            RecyclerView.LayoutManager mLayoutManager =
                    new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            bannerRecyclerView.setLayoutManager(mLayoutManager);
            bannerRecyclerView.setAdapter(bannerAdapter);
            PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
            pagerSnapHelper.attachToRecyclerView(bannerRecyclerView);
            indicator.attachToRecyclerView(bannerRecyclerView, pagerSnapHelper);
            banners = mVendor.getBanners();


            mHandler = new Handler();
            mRunnable = new Runnable() {
                @Override
                public void run() {
                    currentPosition++;
                    if (currentPosition > mVendor.getBanners().size())
                        currentPosition = 0;
//                    bannerRecyclerView.scrollToPosition(currentPosition);
                    bannerRecyclerView.smoothScrollToPosition(currentPosition);
                    mHandler.postDelayed(this, 3000);
                }
            };
            mHandler.postDelayed(mRunnable, 3000);
        }
    }

    private void callApi() {
        backgroundTask = new GetFeaturedItemsAsyncTask(mVendor.getId());
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<List<Product>>() {
            @Override
            public void response(final AsyncTaskResult<List<Product>> response) {
                if (!response.hasException()) {
                    bindData(response.getResult());
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }
            }
        });
        backgroundTask.execute();


    }

    private void bindData(List<Product> items) {
        if (items == null) return;
        categories.clear();
        categoriesProduct.clear();
        products.clear();

// TODO: 9/3/2019 fix feature items
        for (Category featuredItem : mVendor.getCategories()) {
            categories.add(new CategorySimpleModel(featuredItem.getName(), featuredItem.getPictureModel().getImageUrl(), featuredItem.getId()));
        }
        for (Product product : items) {
            categoriesProduct.add(new CategorySimpleModel(product.getName(), product.getDefaultPictureModel().getImageUrl(), product.getId()));
            products.add(product);
        }


        categoryRecyclerViewAdapter.notifyDataSetChanged();

    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    private void InitUi(View view) {

        bannerRecyclerView = (RecyclerView) view.findViewById(R.id.banner_RecyclerView);
        discoverCategoryRecycler = (RecyclerView) view.findViewById(R.id.discover_category_recycler);
        secondBanner = (AspectRatioImageView) view.findViewById(R.id.second_banner);
        thirdBanner = (AspectRatioImageView) view.findViewById(R.id.third_banner);
        forthBanner = (AspectRatioImageView) view.findViewById(R.id.forth_banner);


        indicator = view.findViewById(R.id.indicator);
//        indicator2 = view.findViewById(R.id.indicator2);

        categoryRecyclerViewAdapter = new SimpleRecyclerViewAdapter(getActivity(), categories, 0);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        discoverCategoryRecycler.setLayoutManager(mLayoutManager);
        discoverCategoryRecycler.addItemDecoration(new PaddingItemDecoration(60));
        discoverCategoryRecycler.setAdapter(categoryRecyclerViewAdapter);


        GoMobileApp.getmCacheManager().loadImage(Uri.parse(AppConstant.SHOP_THE_LOOK_IMAGE_URL), secondBanner);
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(AppConstant.THIRD_BANNER_IMAGE_URL), thirdBanner);
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(AppConstant.FOURTH_BANNER_IMAGE_URL), forthBanner);
        GoMobileApp.getmCacheManager().loadImage(Uri.parse(AppConstant.FOURTH_BANNER_IMAGE_URL), forthBanner);

        secondBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopTheLook();
            }
        });

        thirdBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreActivity sActivity = ((StoreActivity) getActivity());
                sActivity.setStoreTab(2003);

            }
        });
        forthBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StoreActivity sActivity = ((StoreActivity) getActivity());
                sActivity.setTabFromFragment(AppConstant.MainTabs.ABOUT);
            }
        });


        discoverCategoryRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), discoverCategoryRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                StoreActivity sActivity = ((StoreActivity) getActivity());
                sActivity.setStoreTab(categories.get(position).getVendorId());
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        bannerRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), bannerRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Banner currentBanner = mVendor.getBanners().get(position);
                if (currentBanner.getTitle() != null) {
                    if (currentBanner.getTitle().toLowerCase().equals("cid")) {
                        StoreActivity sActivity = ((StoreActivity) getActivity());
                        try {
                            int cid = Integer.parseInt(currentBanner.getDescription());
                            sActivity.setStoreTab(cid);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    if (currentBanner.getTitle().toLowerCase().equals("pid")) {
                        StoreActivity sActivity = ((StoreActivity) getActivity());
                        try {
                            int pid = Integer.parseInt(currentBanner.getDescription());
                            sActivity.openProductDetailFragment(pid, null, new ArrayList<Product>());
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        for (Category featuredItem : mVendor.getCategories()) {
            categories.add(new CategorySimpleModel(featuredItem.getName(), featuredItem.getPictureModel().getImageUrl(), featuredItem.getId()));
        }

        categoryRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void openShopTheLook() {
        final StoreActivity sActivity = ((StoreActivity) getActivity());
        GetProductsAsyncTask = new GetProductsAsyncTask(mVendor.getId(), pageNumber, mPageSize, OrderBy,
                AppHelper.GetSelectedSpecificationAttributes(mSpecList), AppConstant.ProductListFragmentMode.SHOP_THE_LOOK);
        GetProductsAsyncTask.addOnResultListener(new AsyncTaskResultListener<PagedProducts>() {
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
        GetProductsAsyncTask.execute();
        sActivity.setLoadingAnimation(true);
    }


}
