package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.ProductListingAdapter;
import com.growonline.gomobishop.adapter.SearchVendorAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.QuickSearchAsyncTask;
import com.growonline.gomobishop.asynctask.SearchVendorsAsyncTask;
import com.growonline.gomobishop.control.GridSpacingItemDecoration;
import com.growonline.gomobishop.control.ProductListingDecoration;
import com.growonline.gomobishop.control.SpacesItemDecoration;
import com.growonline.gomobishop.model.SearchModel;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    static final float MINIMUM = 25;
    private final int PageSize = 20;
    SearchVendorAdapter searchVendorAdapter;
    SearchVendorAdapter searchCategoryAdapter;
    List<SearchVendorsAsyncTask> searchCalls;
    RecyclerView searchProductRecyclerView, searchVendorRecyclerView, searchCategoryRecyclerView;
    int currentLoadingPage = 0;
    int currentLoadingCategory;
    String userQuery = null;
    private AutoCompleteTextView searchField;
    private TextView vendorSearch;
    private TextView categorySearch;
    private TextView productSearch;
    private ProductListingAdapter adapter;
    private boolean noProductFound = false;
    private int mPageNumber = 1, mTotalPages;
    private ProgressBar loader;
    private View networkError;
    private FrameLayout mImageNoProductFound;
    private int CategoryId = 0;
    private int VendorId = 0;
    private int OrderBy = AppConstant.ProductSortingEnum.Position.getValue();
    private ArrayList<String> tag = new ArrayList<>();
    private ArrayAdapter<String> tagAdapter;
    private int scrollDist = 0;
    private boolean isVisible = true;
    private LinearLayout recycler_holder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_search);
        vendorSearch = (TextView) findViewById(R.id.search_vendor_text);
        categorySearch = (TextView) findViewById(R.id.search_category_text);
        productSearch = (TextView) findViewById(R.id.search_product_text);
        recycler_holder = (LinearLayout) findViewById(R.id.recycler_holder);
        initUi();
    }

    @Override
    public void onBackPressed() {
        closeActivity();
    }

    void initUi() {
        searchCalls = new ArrayList<>();
        mImageNoProductFound = (FrameLayout) findViewById(R.id.img_no_prod_found);
        searchVendorAdapter = new SearchVendorAdapter(this, AppConstant.TYPE_VENDOR);
        searchCategoryAdapter = new SearchVendorAdapter(this, AppConstant.TYPE_CATEGORY);

        loader = (ProgressBar) findViewById(R.id.progress_bar);
        networkError = findViewById(R.id.network_error);
        Button Reload = (Button) findViewById(R.id.reload);

        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkError.setVisibility(View.GONE);
                loadProducts(currentLoadingPage, userQuery);
            }
        });


        searchVendorRecyclerView = (RecyclerView) findViewById(R.id.search_vendor_view);
        searchVendorRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.HORIZONTAL, false));
        searchVendorRecyclerView.addItemDecoration(new SpacesItemDecoration(AppHelper.dpToPx(5, getApplicationContext())));
        searchVendorRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchVendorRecyclerView.setAdapter(searchVendorAdapter);

        searchCategoryRecyclerView = (RecyclerView) findViewById(R.id.search_category_view);
        searchCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.HORIZONTAL, false));
        searchCategoryRecyclerView.addItemDecoration(new SpacesItemDecoration(AppHelper.dpToPx(5, getApplicationContext())));
        searchCategoryRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchCategoryRecyclerView.setAdapter(searchCategoryAdapter);

        searchProductRecyclerView = (RecyclerView) findViewById(R.id.search_product_view);
        searchProductRecyclerView.setLayoutManager(new GridLayoutManager(SearchActivity.this, 2));
        searchProductRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, AppHelper.dpToPx(10, getApplicationContext()), true));
        searchProductRecyclerView.setItemAnimator(new DefaultItemAnimator());
        searchProductRecyclerView.addItemDecoration(new ProductListingDecoration(10));

        ImageView btnBack = (ImageView) findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });

        searchField = (AutoCompleteTextView) findViewById(R.id.txt_search);
        tagAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, tag);
        searchField.setAdapter(tagAdapter);
        new QuickSearchAsyncTask().addOnSearchResultsListener(new AsyncTaskResultListener<ArrayList<String>>() {
            @Override
            public void response(AsyncTaskResult<ArrayList<String>> response) {
                if (!response.hasException()) {
                    tagAdapter.clear();
                    tagAdapter.addAll(response.getResult());

//Force the adapter to filter itself, necessary to show new data.
//Filter based on the current text because api call is asynchronous.
                    tagAdapter.getFilter().filter(searchField.getText(), null);
                }

            }
        });

        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
//Force the adapter to filter itself, necessary to show new data.
//Filter based on the current text because api call is asynchronous.
                tagAdapter.getFilter().filter(searchField.getText(), null);
            }
        });

        searchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                searchField.setText(tagAdapter.getItem(position));
                flushOldSearchCalls();
                adapter = null;
                mPageNumber = 1;
                loadProducts(currentLoadingPage, tagAdapter.getItem(position));
                final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (getCurrentFocus() != null)
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        });

        searchField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                userQuery = textView.getText().toString();
//                try {
//                    userQuery = URLEncoder.encode(userQuery, "utf-8");
//                } catch (UnsupportedEncodingException ignored) {
//
//                }
                if (userQuery.length() > 1) {
                    flushOldSearchCalls();
                    adapter = null;
                    mPageNumber = 1;
                    loadProducts(currentLoadingPage, userQuery);

                } else {
                    userQuery = null;
                    flushOldSearchCalls();
                    adapter = null;
                    mPageNumber = 1;
                    loadProducts(currentLoadingPage, userQuery);
                }

                return false;
            }
        });

        searchProductRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && adapter != null) {
                    if (!noProductFound &&
                            ((GridLayoutManager) recyclerView.getLayoutManager()).findLastCompletelyVisibleItemPosition() == adapter.getItemCount() - 1) {
                        if (mPageNumber == mTotalPages) {
                            GoMobileApp.Toast("no more items available to load");
                        } else {
                            if (currentLoadingPage != mPageNumber + 1) {
                                currentLoadingPage = mPageNumber + 1;
                                loadProducts(currentLoadingPage, userQuery);
                            }
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isVisible && scrollDist > MINIMUM) {
                    recycler_holder.animate().translationY(-recycler_holder.getHeight() - 8).setInterpolator(new AccelerateInterpolator(2)).start();
                    scrollDist = 0;
                    isVisible = false;
                } else if (!isVisible && scrollDist < -MINIMUM) {
                    recycler_holder.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                    scrollDist = 0;
                    isVisible = true;
                }

                if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
                    scrollDist += dy;
                }

            }
        });

        loadProducts(currentLoadingPage, userQuery);

    }

    void flushOldSearchCalls() {
        if (searchCalls.size() > 0) {
            for (int i = 0; i < searchCalls.size(); i++) {
                searchCalls.get(i).cancel(true);
            }
        }
    }

    public void LoadCategory(int Id) {
        if (CategoryId == Id)
            CategoryId = 0;
        else
            CategoryId = Id;


        adapter = null;
        mPageNumber = 1;
        loadProducts(currentLoadingPage, userQuery);
    }

    public void LoadVendor(int Id) {
        if (VendorId == Id)
            VendorId = 0;
        else
            VendorId = Id;

        CategoryId = 0;
        adapter = null;
        mPageNumber = 1;
        loadProducts(currentLoadingPage, userQuery);
    }

    void closeActivity() {
        finish();
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void noProductFound() {
        noProductFound = true;
        mImageNoProductFound.setVisibility(View.VISIBLE);
        searchProductRecyclerView.setVisibility(View.GONE);
    }

    void loadProducts(int PageNumber, String SearchTerm) {
        noProductFound = false;
        mImageNoProductFound.setVisibility(View.GONE);

        SearchVendorsAsyncTask searchingTask = new SearchVendorsAsyncTask(SearchTerm, VendorId, CategoryId, OrderBy, PageNumber, PageSize);
        searchingTask.addOnSearchResultsListener(new AsyncTaskResultListener<SearchModel>() {
            @Override
            public void response(AsyncTaskResult<SearchModel> response) {
                if (!response.hasException()) {
                    if (response.getResult().getAvailableVendors().size() > 0) {
                        vendorSearch.setVisibility(View.VISIBLE);
                        searchVendorRecyclerView.setVisibility(View.VISIBLE);
                        vendorSearch.setText(String.format("%s in Shop ", userQuery == null ? "" : userQuery).toUpperCase());
                        searchVendorAdapter.setVendorList(response.getResult().getAvailableVendors());
                        searchVendorAdapter.notifyDataSetChanged();
                    } else {
                        vendorSearch.setVisibility(View.GONE);
                        searchVendorRecyclerView.setVisibility(View.GONE);
                    }
                    if (response.getResult().getAvailableCategories().size() > 0) {
                        categorySearch.setVisibility(View.VISIBLE);
                        searchCategoryRecyclerView.setVisibility(View.VISIBLE);
                        categorySearch.setText(String.format("%s in Categories ", userQuery == null ? "" : userQuery).toUpperCase());
                        searchCategoryAdapter.setVendorList(response.getResult().getAvailableCategories());
                        searchCategoryAdapter.notifyDataSetChanged();
                    } else {
                        searchCategoryRecyclerView.setVisibility(View.GONE);
                        categorySearch.setVisibility(View.GONE);
                    }

                    if (response.getResult().getProducts().size() > 0) {
                        searchProductRecyclerView.setVisibility(View.VISIBLE);
                        productSearch.setVisibility(View.VISIBLE);
                        productSearch.setText(String.format("%s in product ", userQuery == null ? "" : userQuery).toUpperCase());
                        mPageNumber = response.getResult().getPagingFilteringContext().getPageNumber();
                        mTotalPages = response.getResult().getPagingFilteringContext().getTotalPages();
                        if (adapter == null) {
                            adapter = new ProductListingAdapter(response.getResult().getProducts(), "#000000", (AppCompatActivity) SearchActivity.this, null);
                            searchProductRecyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
                            searchProductRecyclerView.setAdapter(adapter);
                        } else {
                            adapter.addItems(response.getResult().getProducts());
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        noProductFound();
                    }

                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        AppHelper.showNetworkError(SearchActivity.this, response.getException().getMessage());
                    else
                        AppHelper.showException(SearchActivity.this, response.getException().getMessage(), response.getException());
                }

                currentLoadingPage = 0;
                endProgress();

            }
        });

        searchCalls.add(searchingTask);
        startProgress();

    }

    private void startProgress() {
        loader.setVisibility(View.VISIBLE);
    }

    private void endProgress() {
        loader.setVisibility(View.GONE);
    }

}
