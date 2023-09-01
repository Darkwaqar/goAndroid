package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RelatedProductsActivity;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.CartDetailItemsAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetCartDetailAsyncTask;
import com.growonline.gomobishop.asynctask.UpdateCartQuantityAsyncTask;
import com.growonline.gomobishop.model.BeanGetAllCarts;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class MiniShoppingCartFragment extends Fragment {

    private Vendor vendor;
    private static final Integer heightInDp = 440;
    private TextView lblTotalItem, lblSubTotal;
    private RecyclerView rview;
    private GetCartDetailAsyncTask backgroundTask;
    private FrameLayout pBar;
    private boolean isShown = false, isEmptyCart = false;
    private Button btnCheckout;
    private FrameLayout noProductFound;
    private List<ShoppingCartItem> allCartItems = new ArrayList<>();
    private Integer initProductId = null;
    private CartDetailItemsAdapter adapter;
    private UpdateCartQuantityAsyncTask updateCartQuantityAsyncTask;


    public MiniShoppingCartFragment() {
        // Required empty public constructor
    }

    public static MiniShoppingCartFragment newInstance(Vendor vendor) {
        MiniShoppingCartFragment fragment = new MiniShoppingCartFragment();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            vendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_mini_shopping_cart, container, false);
        initUi(v);

        return v;
    }

    void initUi(View v) {

        lblTotalItem = (TextView) v.findViewById(R.id.lbl_total_item);
        lblSubTotal = (TextView) v.findViewById(R.id.lbl_total_amount);

        rview = (RecyclerView) v.findViewById(R.id.rv_cart_item);
        pBar = (FrameLayout) v.findViewById(R.id.pbar);

        btnCheckout = (Button) v.findViewById(R.id.btn_proceed);
        noProductFound = (FrameLayout) v.findViewById(R.id.empty_cart_layout);

        checkoutProcess();

        Button btnContinue = (Button) v.findViewById(R.id.btn_continue);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hide();
            }
        });

        v.setTranslationY(GoMobileApp.convertDpToPixel(heightInDp) * -1);
    }

    void checkoutProcess() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hide();
                Intent i = new Intent(getContext(), ShopCartActivity.class);
                i.putExtra(AppConstant.INTENT_VENDOR, vendor);
                startActivity(i);

            }
        });
    }

    public void Show() {

        loadShoppingCart();

        if (!isShown) {
            showAnimation();
            isShown = true;
        }
    }

    public void Show(int productId) {

        initProductId = productId;
        loadShoppingCart();

        if (!isShown) {
            showAnimation();
            isShown = true;
        }
    }

    void showAnimation() {

        View v = getView();
        ObjectAnimator slideIn = ObjectAnimator.ofFloat(v, "translationY", GoMobileApp.convertDpToPixel(heightInDp) * -1, 0f);
        slideIn.setInterpolator(new AccelerateDecelerateInterpolator());
        slideIn.setDuration(300);
        slideIn.start();
    }

    public void Hide() {
        View v = getView();

        ObjectAnimator slideOut = ObjectAnimator.ofFloat(v, "translationY", 0f, GoMobileApp.convertDpToPixel(heightInDp) * -1);
        slideOut.setInterpolator(new AccelerateDecelerateInterpolator());
        slideOut.setDuration(300);
        slideOut.start();

        isShown = false;

        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).HideMiniShopCart();
    }

    public void refreshData(int productId) {
        initProductId = productId;
        loadShoppingCart();
    }

    public void refreshData() {
        loadShoppingCart();
    }

    private void loadShoppingCart() {
        backgroundTask = new GetCartDetailAsyncTask();

        backgroundTask.addOnResultsListener(new AsyncTaskResultListener<BeanGetAllCarts>() {
            @Override
            public void response(AsyncTaskResult<BeanGetAllCarts> response) {

                if (!response.hasException()) {
                    if (response.getResult().getItems().size() > 0) {
                        noProductFound.setVisibility(View.GONE);
                        rview.setVisibility(View.VISIBLE);
                        allCartItems = response.getResult().getItems();
                        lblTotalItem.setText("total " + GoMobileApp.getShoppingCartItemsCount() + " item(s)");
                        bindData(allCartItems);
                        lblSubTotal.setText(response.getResult().getOrderTotals().getOrderTotal());

                    } else {
                        bindData(null);
                        noProductFound.setVisibility(View.VISIBLE);
                        rview.setVisibility(View.GONE);
                        lblSubTotal.setText("-");
                        lblTotalItem.setText(R.string.total_zero_items);
                        GoMobileApp.Toast(R.string.empty_cart_message);
                    }
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        showException(response.getException().getMessage(), response.getException(), false);
                    else
                        showException(response.getException().getMessage(), response.getException());
                }

                pBar.setVisibility(View.GONE);
            }
        });

        pBar.setVisibility(View.VISIBLE);
        backgroundTask.execute();
    }


    void showException(String message, Throwable exception) {
        AppHelper.showException((RelatedProductsActivity) getContext(), message, exception);
    }

    void showException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((StoreActivity) getContext(), message, exception);
        else
            AppHelper.showNetworkError((StoreActivity) getContext(), message);
    }


    @Override
    public void onDestroyView() {

        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        if (updateCartQuantityAsyncTask != null && !updateCartQuantityAsyncTask.isCancelled())
            updateCartQuantityAsyncTask.cancel(true);

        super.onDestroyView();
    }

    private void bindData(List<ShoppingCartItem> cartProducts) {

        if (cartProducts != null) {
            rview.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));

            adapter = new CartDetailItemsAdapter(
                    (AppCompatActivity) getActivity(), cartProducts, new ArrayList<VendorItem>()
                    , new CartDetailItemsAdapter.ShopCartListener() {
                @Override
                public void onQuantityAdded(int pid, int cartItemId, int quantity) {
                    updateCartItemQuantity(pid, cartItemId, quantity + 1);
                }

                @Override
                public void onQuantityRemoved(int pid, int cartItemId, int tempQty) {
                    //setLoadingAnimation(true);
                    updateCartItemQuantity(pid, cartItemId, tempQty - 1);
                }

                @Override
                public void onItemDeleted(int pid, int cartItemId, int quantity) {
                    updateCartItemQuantity(pid, cartItemId, 0);
                }
            });

            rview.setAdapter(adapter);
        } else {
            rview.setAdapter(null);
        }
    }

    public void updateCartItemQuantity(int prdId, int cartItemId, int qty) {
        updateCartQuantityAsyncTask = new UpdateCartQuantityAsyncTask(prdId, cartItemId, qty);
        updateCartQuantityAsyncTask.addOnResultListener(new AsyncTaskResultListener<BeanGetAllCarts>() {
            @Override
            public void response(AsyncTaskResult<BeanGetAllCarts> response) {

                if (!response.hasException()) {
                    isEmptyCart = response.getResult().getItems().size() <= 0;
                    refreshData();
                } else {

                    if (response.getException() instanceof NetworkErrorException)
                        showException(response.getException().getMessage(), response.getException(), false);
                    else
                        GoMobileApp.Toast(response.getException().getMessage());
                }
            }
        });

        updateCartQuantityAsyncTask.execute();
    }


}