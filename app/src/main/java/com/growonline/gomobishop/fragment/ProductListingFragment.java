package com.growonline.gomobishop.fragment;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gordonwong.materialsheetfab.MaterialSheetFab;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.AdapterSpecificationAttribute;
import com.growonline.gomobishop.adapter.AdapterSpecificationOption;
import com.growonline.gomobishop.adapter.ProductListingAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetProductsAsyncTask;
import com.growonline.gomobishop.base.Fab;
import com.growonline.gomobishop.control.Align;
import com.growonline.gomobishop.control.Config;
import com.growonline.gomobishop.control.ProductListingDecoration;
import com.growonline.gomobishop.control.StackLayoutManager;
import com.growonline.gomobishop.model.PagedProducts;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.model.SpecificationAttributeOption;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ProductListingFragment extends Fragment implements View.OnClickListener {

    static final float MINIMUM = 25;

    private static final String ARG_MODE = "paramMode";
    private Vendor mVendor;
    private AppConstant.ProductListFragmentMode mMode;
    private int mCategoryId;
    private int mPageSize = 14;
    private int mPageNumber = 1, mTotalPages;
    private int currentLoadingPage = 0;
    private int scrollDist = 0;
    private ProductListingAdapter adapter;
    private RecyclerView recyclerView, filterRecyclerView, filterOptionRecyclerView;
    private GetProductsAsyncTask backgroundTask;
    private StoreActivity mActivity;
    private Button btnFilter;
    private Button reload;
    private View mDisabled;
    private View networkError;
    private ImageView btn_cancel_filter_dialog;
    private ImageView mPopupIndicator;
    private ProgressBar mProgressBar;
    private FrameLayout mImageNoProductFound;
    private FrameLayout mSearchContainer;
    private RelativeLayout mFilterOptionDialog;
    private AdapterSpecificationAttribute adapterSpecificationAttribute;
    private AdapterSpecificationOption adapterSpecificationOption;
    private boolean isFilterDialogVisible = false;
    private boolean noProductFound = false;
    private boolean isVisible = true;
    private List<SpecificationAttribute> mSpecList = new ArrayList<>();
    private List<Integer> mSortByOptions = new ArrayList<>();
    private List<Integer> mSingleSessionSpecificationOptionIds = new ArrayList<>();
    private LinearLayout filterContainer;
    private String mSearchTerm = null;
    private EditText mSearchProductEditText;
    private MaterialSheetFab materialSheetFab;
    private int OrderBy = AppConstant.ProductSortingEnum.Position.getValue();
    private CardView sheetView;
    private TextView message_no_product_found;


    public ProductListingFragment() {
        // Required empty public constructor
    }

    public static ProductListingFragment newInstance(Vendor vendor, int categoryId, AppConstant.ProductListFragmentMode mode) {
        ProductListingFragment fragment = new ProductListingFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        args.putInt(AppConstant.INTENT_CATEGORY_ID, categoryId);
        args.putSerializable(ARG_MODE, mode);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
            mMode = (AppConstant.ProductListFragmentMode) getArguments().getSerializable(ARG_MODE);
            mCategoryId = getArguments().getInt(AppConstant.INTENT_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.prouct_listing_fragment_layout, null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUi(view);
        setupFab(view);
        loadData();
    }


    void initUi(View view) {
        mActivity = (StoreActivity) getActivity();
        mPopupIndicator = (ImageView) view.findViewById(R.id.popup_indicator);
        btn_cancel_filter_dialog = (ImageView) view.findViewById(R.id.btn_cancel_filter_dialog);
        mSearchContainer = (FrameLayout) view.findViewById(R.id.btn_search_container);
        mImageNoProductFound = (FrameLayout) view.findViewById(R.id.img_no_prod_found);
        filterContainer = (LinearLayout) view.findViewById(R.id.recycler_view_filters_container);
        mFilterOptionDialog = (RelativeLayout) view.findViewById(R.id.dialog_filter_option);
        filterRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_filters);
        filterOptionRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_filter_option);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_products);
        btnFilter = (Button) view.findViewById(R.id.btn_filter);
        mDisabled = view.findViewById(R.id.disableView);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        message_no_product_found = (TextView) view.findViewById(R.id.message_no_product_found);
        mSearchProductEditText = (EditText) view.findViewById(R.id.txt_search);
        networkError = view.findViewById(R.id.network_error);
        reload = view.findViewById(R.id.reload);
        btnFilter.setOnClickListener(this);
        reload.setOnClickListener(this);
        mDisabled.setOnClickListener(this);
        btn_cancel_filter_dialog.setOnClickListener(this);

        recyclerView.addItemDecoration(new ProductListingDecoration(10));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);


                if (!mMode.equals(AppConstant.ProductListFragmentMode.SHOP_THE_LOOK) && newState == RecyclerView.SCROLL_STATE_IDLE) {
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

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isVisible && scrollDist > MINIMUM) {
                    filterContainer.animate().translationY(-filterRecyclerView.getHeight() - 8).setInterpolator(new AccelerateInterpolator(2)).start();
                    mSearchContainer.animate().translationY(-filterRecyclerView.getHeight() - 8).setInterpolator(new AccelerateInterpolator(2)).start();
                    scrollDist = 0;
                    isVisible = false;
                } else if (!isVisible && scrollDist < -MINIMUM) {
                    filterContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                    mSearchContainer.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
                    scrollDist = 0;
                    isVisible = true;
                }

                if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
                    scrollDist += dy;
                }

            }
        });


        mSearchProductEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    mSearchTerm = mSearchProductEditText.getText().toString();
                    adapter = null;
                    loadProducts(1);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(mSearchProductEditText.getWindowToken(), 0);
                    return true;

                }

                return false;
            }

        });
    }

    void loadProducts(int pageNumber) {
        noProductFound = false;
        mImageNoProductFound.setVisibility(View.GONE);

        switch (mMode) {
            case SINGLE_CATEGORY:
                backgroundTask = new GetProductsAsyncTask(mVendor.getId(), mCategoryId, pageNumber, mPageSize, OrderBy,
                        AppHelper.GetSelectedSpecificationAttributes(mSpecList));
                break;
            case SHOP_THE_LOOK:
                backgroundTask = new GetProductsAsyncTask(mVendor.getId(), pageNumber, mPageSize, OrderBy,
                        AppHelper.GetSelectedSpecificationAttributes(mSpecList), mMode);
                break;
            case SEARCH_PRODUCT:
                backgroundTask = new GetProductsAsyncTask(mSearchTerm, mVendor.getId(), pageNumber, mPageSize, OrderBy,
                        AppHelper.GetSelectedSpecificationAttributes(mSpecList), mMode);
                mSearchContainer.setVisibility(View.VISIBLE);
                mSearchProductEditText.requestFocus();
                break;
            default:
                backgroundTask = new GetProductsAsyncTask(mVendor.getId(), pageNumber, mPageSize, OrderBy,
                        AppHelper.GetSelectedSpecificationAttributes(mSpecList), mMode);
                break;
        }

        backgroundTask.addOnResultListener(new AsyncTaskResultListener<PagedProducts>() {
            @Override
            public void response(final AsyncTaskResult<PagedProducts> response) {
                if (!response.hasException()) {

                    mPageNumber = response.getResult().getPageNumber();
                    mTotalPages = response.getResult().getTotalPages();

                    if (adapter == null) {
                        adapter = new ProductListingAdapter(response.getResult().getProducts(), mVendor.getThemeColor(), (AppCompatActivity) getActivity(), mVendor);
                        if (mMode.equals(AppConstant.ProductListFragmentMode.SHOP_THE_LOOK)) {
                            Config config = new Config();
                            config.secondaryScale = 1f;
                            config.scaleRatio = 1f;
                            config.maxStackCount = 4;
                            config.initialStackCount = response.getResult().getProducts().size() - 1;
                            config.space = 20;
                            config.parallex = 1.5f;
                            config.align = Align.TOP;
                            recyclerView.setLayoutManager(new StackLayoutManager(config));
                            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
                            params.width = (int) (GoMobileApp.getScreenWidth() / 1.2);
                            recyclerView.setLayoutParams(params);

                        } else {
                            recyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
                        }
                        recyclerView.setAdapter(adapter);

                        if (response.getResult().getProducts().size() <= 0)
                            noProductFound();

                    } else {
                        adapter.addItems(response.getResult().getProducts());
                        adapter.notifyDataSetChanged();
                    }


                    if (response.getResult().getSpecList() != null && response.getResult().getSpecList().size() == 0 || mMode.equals(AppConstant.ProductListFragmentMode.SHOP_THE_LOOK)) {
                        filterRecyclerView.setVisibility(View.GONE);
                        if (mMode != AppConstant.ProductListFragmentMode.SEARCH_PRODUCT)
                            recyclerView.setPadding(0, 8, 0, 0);
                    } else {
                        setUpFilters(response.getResult().getSpecList());
                    }


                } else {
                    if (response.getException() instanceof NetworkErrorException) {
                        networkError.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        filterContainer.setVisibility(View.GONE);
                    } else
                        launchException(response.getException().getMessage(), response.getException(), true);
                }

                currentLoadingPage = 0;
                endProgress();
            }
        });
        backgroundTask.execute();
        startProgress();
    }

    @Override
    public void onResume() {
        super.onResume();

        mProgressBar.setVisibility(View.GONE);
        mActivity.setToolBarTitle("STORE");
        if (recyclerView.getAdapter() == null && adapter != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        }

        if (filterRecyclerView.getAdapter() == null && adapterSpecificationAttribute != null) {
            filterRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.HORIZONTAL, false));
            filterRecyclerView.setAdapter(adapterSpecificationAttribute);
        }
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    @Override
    public void onDestroyView() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        super.onDestroyView();
    }

    private void loadData() {
        if (adapter == null)
            loadProducts(mPageNumber);
    }

    public void loadDataRestartPaging() {
        if (!isFilterDialogVisible) {
            adapter = null;
            mPageNumber = 1;
            loadProducts(mPageNumber);
        }
    }

    private void setUpFilters(List<SpecificationAttribute> specList) {

        if (mSpecList != null && mSpecList.size() > 0) return;
        if (adapter != null && adapter.getItemCount() <= 0) return;
        mSpecList = specList;
        filterRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.HORIZONTAL, false));
        adapterSpecificationAttribute = new AdapterSpecificationAttribute((AppCompatActivity) getActivity(), this, mSpecList);
        filterRecyclerView.setAdapter(adapterSpecificationAttribute);
    }

    public void showFilterOption(int specificationAttributeId, float x, float y) {

        List<SpecificationAttributeOption> tempList = new ArrayList<>();
        for (int i = 0; i < mSpecList.size(); i++) {
            SpecificationAttribute s = mSpecList.get(i);
            if (s.getId() == specificationAttributeId) {
                tempList = s.getOptions();
                break;
            }
        }

        filterOptionRecyclerView.setLayoutManager(new GridLayoutManager(null, 2, LinearLayoutManager.VERTICAL, false));
        adapterSpecificationOption = new AdapterSpecificationOption((AppCompatActivity) getActivity(),
                tempList, this);
        filterOptionRecyclerView.setAdapter(adapterSpecificationOption);
        mFilterOptionDialog.setVisibility(View.VISIBLE);
        ShowFilterDialog();
        mPopupIndicator.setVisibility(View.VISIBLE);
        mPopupIndicator.setX(x + GoMobileApp.convertPixelsToDp(y / 2));
        mDisabled.setVisibility(View.VISIBLE);

        if (!isFilterDialogVisible) {
            btn_cancel_filter_dialog.setVisibility(View.GONE);
            Animation btnFilterCancelAnim = AnimationUtils.loadAnimation(mActivity, R.anim.btn_filter_cancel_anim_start);
            btn_cancel_filter_dialog.startAnimation(btnFilterCancelAnim);
            mSingleSessionSpecificationOptionIds.clear();
        }

        isFilterDialogVisible = true;
    }

    public void notifyDataSetChanged() {
        adapterSpecificationAttribute.notifyDataSetChanged();
        adapterSpecificationOption.notifyDataSetChanged();
    }

    public void notifySelectedOptionId(int optionId) {
        mSingleSessionSpecificationOptionIds.add(optionId);
        //todo uncomment Last line to real time
//        loadDataRestartPaging();
    }

    private void noProductFound() {
        noProductFound = true;
        mImageNoProductFound.setVisibility(View.VISIBLE);
        if (mMode == AppConstant.ProductListFragmentMode.SEARCH_PRODUCT)
            message_no_product_found.setText("We can't seems to find any product that matches your search");
        else
            message_no_product_found.setText(R.string.no_product_found);

    }

    private void closeButtonCancelFilterDialog() {
        Animation btnFilterCancelAnimEnd = AnimationUtils.loadAnimation(mActivity, R.anim.btn_filter_cancel_anim_end);
        btn_cancel_filter_dialog.startAnimation(btnFilterCancelAnimEnd);
        btnFilterCancelAnimEnd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                btn_cancel_filter_dialog.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

        });
        Animation animSlideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animSlideUp.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                filterContainer.getBackground().setAlpha(0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mFilterOptionDialog.setVisibility(View.GONE);
                filterContainer.getBackground().setAlpha(255);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFilterOptionDialog.startAnimation(animSlideUp);
    }

    private void ShowFilterDialog() {
        Animation animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animSlideDown.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                filterContainer.getBackground().setAlpha(0);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                filterContainer.getBackground().setAlpha(255);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mFilterOptionDialog.startAnimation(animSlideDown);
    }

    private void startProgress() {
        mDisabled.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void endProgress() {
        mProgressBar.setVisibility(View.GONE);
        mDisabled.setVisibility(View.GONE);
    }

    /**
     * Sets up the Floating action button.
     */
    private void setupFab(View view) {

        Fab fab = (Fab) view.findViewById(R.id.fab);
        sheetView = (CardView) view.findViewById(R.id.fab_sheet);
        View overlay = view.findViewById(R.id.overlay);
        int sheetColor = view.getResources().getColor(R.color.colorAccent);
        int fabColor = getResources().getColor(R.color.colorPrimary);

        // Create material sheet FAB
        materialSheetFab = new MaterialSheetFab<>(fab, sheetView, overlay, sheetColor, fabColor);
        mSortByOptions.add(R.id.fab_sheet_item_Position);
        mSortByOptions.add(R.id.fab_sheet_item_NameAsc);
        mSortByOptions.add(R.id.fab_sheet_item_NameDesc);
        mSortByOptions.add(R.id.fab_sheet_item_PriceAsc);
        mSortByOptions.add(R.id.fab_sheet_item_PriceDesc);
        mSortByOptions.add(R.id.fab_sheet_item_CreatedOn);

        for (int sort : mSortByOptions) {
            view.findViewById(sort).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab_sheet_item_Position:
                OrderBy = AppConstant.ProductSortingEnum.Position.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_Position);
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_NameAsc:
                OrderBy = AppConstant.ProductSortingEnum.NameAsc.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_NameAsc);
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_NameDesc:
                OrderBy = AppConstant.ProductSortingEnum.NameDesc.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_NameDesc);
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_PriceAsc:
                OrderBy = AppConstant.ProductSortingEnum.PriceAsc.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_PriceAsc);
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_PriceDesc:
                OrderBy = AppConstant.ProductSortingEnum.PriceDesc.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_PriceDesc);
                materialSheetFab.hideSheet();
                break;
            case R.id.fab_sheet_item_CreatedOn:
                OrderBy = AppConstant.ProductSortingEnum.CreatedOn.getValue();
                loadDataRestartPaging();
                selectOption(R.id.fab_sheet_item_CreatedOn);
                materialSheetFab.hideSheet();
                break;
            case R.id.btn_filter:
                isFilterDialogVisible = false;
                mDisabled.setVisibility(View.GONE);
                mPopupIndicator.setVisibility(View.GONE);
                loadDataRestartPaging();
                closeButtonCancelFilterDialog();
                break;
            case R.id.btn_cancel_filter_dialog:
                isFilterDialogVisible = false;
                mDisabled.setVisibility(View.GONE);
                mPopupIndicator.setVisibility(View.GONE);
                AppHelper.ResetSpecificationAttributes(mSpecList, mSingleSessionSpecificationOptionIds);
                notifyDataSetChanged();
                closeButtonCancelFilterDialog();
                break;
            case R.id.reload:
                loadData();
                networkError.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                filterContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.disableView:
                if (adapterSpecificationOption != null)
                    btn_cancel_filter_dialog.performClick();
                break;

        }

    }

    private void selectOption(int id) {
        ViewGroup viewGroup = ((ViewGroup) sheetView.getChildAt(0));
        for (int j = 0; j < viewGroup.getChildCount(); j++) {
            if (viewGroup.getChildAt(j).getId() == id)
                ((TextView) viewGroup.findViewById(viewGroup.getChildAt(j).getId())).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
            else
                ((TextView) viewGroup.findViewById(viewGroup.getChildAt(j).getId())).setBackgroundColor(getResources().getColor(R.color.colorAccent));
        }
    }

}
