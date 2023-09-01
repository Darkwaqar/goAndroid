package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.MarketPlaceCategoriesListingAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetFeaturedItemsAsyncTask;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.CategorySimpleModel;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiscoverFragmentDoubleList extends Fragment {


    private Vendor mVendor;
    private GetFeaturedItemsAsyncTask backgroundTask;
    private RecyclerView leftRecycler, rightRecycler;

    private Handler loadingAnimationHandler;
    private Runnable loadingAnimationRunnable;
    private List<Product> products = new ArrayList<>();
    private View networkError;
    private Button reload;

    public static DiscoverFragmentDoubleList newInstance(Vendor vendor) {
        DiscoverFragmentDoubleList fragment = new DiscoverFragmentDoubleList();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    public DiscoverFragmentDoubleList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_discover_fragment_double_list, container, false);
        initUi(v);
        return v;
    }

    void initUi(View view) {
        leftRecycler = (RecyclerView) view.findViewById(R.id.left_recyclerView);
        rightRecycler = (RecyclerView) view.findViewById(R.id.right_recyclerView);
        networkError = view.findViewById(R.id.network_error);
        reload = view.findViewById(R.id.reload);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFeaturedItems();
                networkError.setVisibility(View.GONE);
            }
        });


//        listView.setFriction(2);
        loadFeaturedItems();
    }

    void loadFeaturedItems() {

        ((StoreActivity) getActivity()).setLoadingAnimation(true);

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

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            networkError.setVisibility(View.VISIBLE);
    }

    void bindData(List<Product> items) {
        if (items == null) return;
        List<CategorySimpleModel> categories = new ArrayList<>();
        products.clear();

        for (Product product : items) {
            products.add(product);
            categories.add(new CategorySimpleModel(product.getName(), product.getDefaultPictureModel().getImageUrl(), product.getId()));
        }
        // call split method which return List of array
        final List[] CategoriesLists = splitCategories(categories);
        final List[] ProductLists = splitProducts(products);


        MarketPlaceCategoriesListingAdapter adapter = new MarketPlaceCategoriesListingAdapter(getActivity(), CategoriesLists[0]);
        MarketPlaceCategoriesListingAdapter adapter1 = new MarketPlaceCategoriesListingAdapter(getActivity(), CategoriesLists[1]);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true);
        leftRecycler.setLayoutManager(mLayoutManager);
        rightRecycler.setLayoutManager(mLayoutManager1);
        leftRecycler.setAdapter(adapter);
        rightRecycler.setAdapter(adapter1);

        final RecyclerView.OnScrollListener[] scrollListeners = new RecyclerView.OnScrollListener[2];
        scrollListeners[0] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                rightRecycler.removeOnScrollListener(scrollListeners[1]);
                rightRecycler.scrollBy(dx, -dy);
                rightRecycler.addOnScrollListener(scrollListeners[1]);
            }
        };
        scrollListeners[1] = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                leftRecycler.removeOnScrollListener(scrollListeners[0]);
                leftRecycler.scrollBy(dx, -dy);
                leftRecycler.addOnScrollListener(scrollListeners[0]);
            }
        };
        leftRecycler.addOnScrollListener(scrollListeners[0]);
        rightRecycler.addOnScrollListener(scrollListeners[1]);


        leftRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), leftRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = (Product) ProductLists[0].get(position);
                StoreActivity sActivity = ((StoreActivity) getActivity());
                sActivity.openProductDetailFragment(product.getId(), product.getName(), products);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        rightRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), rightRecycler, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Product product = (Product) ProductLists[1].get(position);
                StoreActivity sActivity = ((StoreActivity) getActivity());
                sActivity.openProductDetailFragment(product.getId(), product.getName(), products);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    // function to split a list into two sublists in Java
    public static List[] splitCategories(List<CategorySimpleModel> list) {

        // find size of the list and put in size
        int size = list.size();

        // create new list and insert valuese which is returne by
        // list.subList() method
        List<CategorySimpleModel> first = new ArrayList<>(list.subList(0, (size) / 2));
        List<CategorySimpleModel> second = new ArrayList<>(list.subList((size) / 2, size));

        // return an List of array
        return new List[]{first, second};
    }

    public static List[] splitProducts(List<Product> list) {

        // find size of the list and put in size
        int size = list.size();

        // create new list and insert valuese which is returne by
        // list.subList() method
        List<Product> first = new ArrayList<>(list.subList(0, (size) / 2));
        List<Product> second = new ArrayList<>(list.subList((size) / 2, size));

        // return an List of array
        return new List[]{first, second};
    }

    @Override
    public void onDestroyView() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        if (loadingAnimationHandler != null && loadingAnimationRunnable != null)
            loadingAnimationHandler.removeCallbacks(loadingAnimationRunnable);

        super.onDestroyView();
    }


}
