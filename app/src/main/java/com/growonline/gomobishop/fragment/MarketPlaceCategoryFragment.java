package com.growonline.gomobishop.fragment;

import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.MainActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.adapter.MarketPlaceCategoriesListingAdapter;
import com.growonline.gomobishop.adapter.ProductListingAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetAllMarketPlaceCategories;
import com.growonline.gomobishop.asynctask.GetProductsAsyncTask;
import com.growonline.gomobishop.control.GridSpacingItemDecoration;
import com.growonline.gomobishop.control.ProductListingDecoration;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.model.PagedProducts;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;
import static com.growonline.gomobishop.util.AppConstant.ProductListFragmentMode.MARKET_PRODUCT;

public class MarketPlaceCategoryFragment extends Fragment {

    public static final String TAG =
            MarketPlaceCategoryFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "categoryId";
    private static final String ARG_PARAM_TYPE = "type";
    private final int mPageSize = 10;
    int currentLoadingPage = 0;
    int currentLoadingCategory;
    private boolean noProductFound = false;
    private int mPageNumber = 1, mTotalPages;
    private int mParam1;
    private List<CategorySimpleModel> mCategoryList = new ArrayList<>();
    private GetAllMarketPlaceCategories backgroundTask;
    private View networkError;
    private RecyclerView recyclerView;
    private FrameLayout mImageNoProductFound;
    private ProductListingAdapter adapter;
    private MarketPlaceCategoriesListingAdapter marketPlaceCategoriesListingAdapter;
    private GetProductsAsyncTask getProductsBackgroundTask;
    private List<SpecificationAttribute> mSpecList = new ArrayList<>();
    private MainActivity mainActivity;

    private int type;
    private int OrderBy = AppConstant.ProductSortingEnum.Position.getValue();

    public MarketPlaceCategoryFragment() {
        // Required empty public constructor
    }

    public static MarketPlaceCategoryFragment newInstance(int param1, int type) {
        MarketPlaceCategoryFragment fragment = new MarketPlaceCategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1, 0);
            type = getArguments().getInt(ARG_PARAM_TYPE, 0);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_market_place_category, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mainActivity = (MainActivity) getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mImageNoProductFound = (FrameLayout) view.findViewById(R.id.img_no_prod_found);
        networkError = view.findViewById(R.id.network_error);
        Button Reload = (Button) view.findViewById(R.id.reload);

        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkError.setVisibility(View.GONE);
                loadAllCategories(mParam1);
            }
        });

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.dpToPx(10, getApplicationContext()), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        if (type == AppConstant.TYPE_CATEGORY) {
            recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
                @Override
                public void onClick(View view, int position) {
                    CategorySimpleModel item = mCategoryList.get(position);
                    if (item.getVendorId() < 0) return;
                    if (getActivity() instanceof MainActivity) {
                        MainActivity mainActivity = (MainActivity) getActivity();
                        mainActivity.ShowBottomSheet();
                        if (item.getHasSubCategory()) {
                            mainActivity.ReplaceMarketplaceCategory(item.getCategoryId(), AppConstant.TYPE_CATEGORY);
                        } else {
                            mainActivity.ReplaceMarketplaceCategory(item.getCategoryId(), AppConstant.TYPE_PRODUCT);
                        }

                    }
                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));
        }
        recyclerView.addItemDecoration(new ProductListingDecoration(10));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (newState == RecyclerView.SCROLL_STATE_IDLE && type == AppConstant.TYPE_PRODUCT) {
                    if (!noProductFound &&
                            ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                        if (mPageNumber == mTotalPages) {
                            GoMobileApp.Toast("no more items available to load");
                        } else {
                            if (currentLoadingPage != mPageNumber + 1) {
                                currentLoadingPage = mPageNumber + 1;
                                loadProducts(currentLoadingPage);
                            }
                        }
                    }
                }
            }

//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                if (isVisible && scrollDist > MINIMUM) {
//                    filterContainer.animate().translationY(-filterRecyclerView.getHeight() - 8).setInterpolator(new AccelerateInterpolator(2)).start();
//                    scrollDist = 0;
//                    isVisible = false;
//                } else if (!isVisible && scrollDist < -MINIMUM) {
//                    filterContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                    scrollDist = 0;
//                    isVisible = true;
//                }
//
//                if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
//                    scrollDist += dy;
//                }
//
//            }
        });
        loadData();
    }

    private void loadData() {
        if (marketPlaceCategoriesListingAdapter == null)
            if (type == AppConstant.TYPE_CATEGORY)
                loadAllCategories(mParam1);
            else
                loadDataRestartPaging(mParam1);

    }

    void loadAllCategories(int categoryId) {
        mainActivity.setLoading(true);
        backgroundTask = new GetAllMarketPlaceCategories(categoryId);
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<List<CategorySimpleModel>>() {
            @Override
            public void response(AsyncTaskResult<List<CategorySimpleModel>> response) {
                mainActivity.setLoading(false);
                if (!response.hasException()) {
                    mCategoryList.clear();
                    mCategoryList.addAll(response.getResult());
                    if (mCategoryList.size() < 8) {
                        int totalEmptyBox = 8 - mCategoryList.size();
                        for (int i = 0; i < totalEmptyBox; i++) {
                            mCategoryList.add(new CategorySimpleModel("", "", -1));
                        }
                    }
                    marketPlaceCategoriesListingAdapter = new MarketPlaceCategoriesListingAdapter(getActivity(), mCategoryList, 1);
                    recyclerView.setAdapter(marketPlaceCategoriesListingAdapter);
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

    void launchException(String message, Throwable exception, boolean actionButtons) {
        networkError.setVisibility(View.VISIBLE);
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    void loadProducts(int pageNumber) {
        noProductFound = false;
        mImageNoProductFound.setVisibility(View.GONE);

        getProductsBackgroundTask = new GetProductsAsyncTask(currentLoadingCategory, pageNumber,
                mPageSize, OrderBy, AppHelper.GetSelectedSpecificationAttributes(mSpecList), MARKET_PRODUCT);

        getProductsBackgroundTask.addOnResultListener(new AsyncTaskResultListener<PagedProducts>()

        {
            @Override
            public void response(final AsyncTaskResult<PagedProducts> response) {
                if (!response.hasException()) {
                    mPageNumber = response.getResult().getPageNumber();
                    mTotalPages = response.getResult().getTotalPages();
                    if (adapter == null) {
                        adapter = new ProductListingAdapter(response.getResult().getProducts(), "#000000", (AppCompatActivity) getActivity(), null);
                        recyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(adapter);

                        if (response.getResult().getProducts().size() <= 0)
                            noProductFound();

                    } else {
                        adapter.addItems(response.getResult().getProducts());
                        adapter.notifyDataSetChanged();
                    }

                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }

                currentLoadingPage = 0;
                mainActivity.setLoading(false);
            }
        });
        getProductsBackgroundTask.execute();
        mainActivity.setLoading(true);
    }

    private void noProductFound() {
        noProductFound = true;
        mImageNoProductFound.setVisibility(View.VISIBLE);
    }


    public void loadDataRestartPaging(Integer categoryId) {
        adapter = null;
        mPageNumber = 1;
        currentLoadingCategory = categoryId;
        loadProducts(mPageNumber);
    }


    @Override
    public void onResume() {
        super.onResume();
        if (recyclerView.getAdapter() == null && marketPlaceCategoriesListingAdapter != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(marketPlaceCategoriesListingAdapter);
        }
    }
}
