package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.CartDetailItemsAdapter;
import com.growonline.gomobishop.asynctask.ApplyCouponCodeAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetCartDetailAsyncTask;
import com.growonline.gomobishop.asynctask.RemoveCouponCodeAsyncTask;
import com.growonline.gomobishop.asynctask.UpdateCartQuantityAsyncTask;
import com.growonline.gomobishop.control.FontEditText;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.fragment.OrderTotal;
import com.growonline.gomobishop.model.BeanGetAllCarts;
import com.growonline.gomobishop.model.DiscountBox;
import com.growonline.gomobishop.model.ShoppingCartItem;
import com.growonline.gomobishop.model.Total;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.List;


public class ShopCartActivity extends BaseActivity {

    Button btnDiscount;
    ImageView removeDiscount;
    private FontEditText mCouponTextView;
    private Button mCancelButton, mRedeemButton;
    private CartDetailItemsAdapter adapter;
    private boolean isEmptyCart = true;
    private GetCartDetailAsyncTask backgroundTask;
    private UpdateCartQuantityAsyncTask updateCartQuantityAsyncTask;
    private Handler delayHandler;
    private Runnable delayRunnable;
    private RelativeLayout btnCheckout;
    private AlertDialog mDialog;
    private FrameLayout noProductLayout;
    private LinearLayout activity_checkout_below_layout;
    private RecyclerView itemsView;
    private List<ShoppingCartItem> cartProducts;
    private RelativeLayout rootLayout;
    private OrderTotal fragOrderTotal;
    private FontTextView lbl_products;
    private LinearLayout discount_layout, reward_layout, delivery_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_shop_cart);
        overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);
        setStoreTitle("Shopping Cart");
        DisableSecondTitleRow();
        initUi();

        Intent i = getIntent();
        String redirectUri = i.getStringExtra("redirect");
        if (redirectUri != null && !redirectUri.isEmpty()) {
            if (redirectUri.equalsIgnoreCase("AddressActivity")) {
                loadShoppingCart();
                redirectToAddressActivity();
            }
        }
//        else {
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    loadShoppingCart();
//                }
//            }, 200);
//        }
    }

    void initUi() {
        itemsView = (RecyclerView) findViewById(R.id.recycler_view_cart_items);
        btnCheckout = (RelativeLayout) findViewById(R.id.btn_checkout);
        mCouponTextView = (FontEditText) findViewById(R.id.txt_coupon_code);
        noProductLayout = (FrameLayout) findViewById(R.id.empty_cart_layout);
        activity_checkout_below_layout = (LinearLayout) findViewById(R.id.activity_checkout_below_layout);
        removeDiscount = (ImageView) findViewById(R.id.remove_discount);
        discount_layout = (LinearLayout) findViewById(R.id.discount_layout);
        reward_layout = (LinearLayout) findViewById(R.id.reward_layout);
        delivery_layout = (LinearLayout) findViewById(R.id.delivery_layout);
        btnDiscount = (Button) findViewById(R.id.btn_discountCoupon);
        lbl_products = (FontTextView) findViewById(R.id.lbl_total_product);
        btnDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mCouponTextView.getText().toString().trim().length() > 0) {
                    applyCouponCode(mCouponTextView.getText().toString());
                } else {
                    showDialog("Error", getResources().getString(R.string.invalid_coupon_code), false);
                }
            }
        });

        removeDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCouponTextView.getText().toString().trim().length() > 0) {
                    RemoveCouponCode(mCouponTextView.getText().toString());
                }
            }
        });

        checkoutProcess();
    }

    private void RemoveCouponCode(String code) {
        RemoveCouponCodeAsyncTask backgroundTask = new RemoveCouponCodeAsyncTask(code);

        backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    showDialog("Success Removed", "Coupon-code Removed successfully", true);
                    mCouponTextView.setText("");
                    mCouponTextView.setEnabled(true);
                    removeDiscount.setVisibility(View.GONE);
                    btnDiscount.setVisibility(View.VISIBLE);

                } else {
                    showDialog("Error", response.getException().getMessage(), false);
                }
            }
        });
        backgroundTask.execute();

    }

    void loadShoppingCart() {
        setLoadingAnimation(true);
        backgroundTask = new GetCartDetailAsyncTask();

        backgroundTask.addOnResultsListener(new AsyncTaskResultListener<BeanGetAllCarts>() {
            @Override
            public void response(AsyncTaskResult<BeanGetAllCarts> response) {

                if (!response.hasException()) {
                    if (response.getResult().getItems().size() > 0) {
                        isEmptyCart = false;
                        bindData(response.getResult().getItems(), response.getResult().getVendorItems());
                    } else {
                        noProductLayout.setVisibility(View.VISIBLE);
                        activity_checkout_below_layout.setVisibility(View.GONE);
                        GoMobileApp.Toast(R.string.empty_cart_message);
                    }
                    bindTotals(response.getResult().getOrderTotals());
                    BindDiscount(response.getResult().getDiscountBox());
                    refreshShopCartBadge();
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        showException(response.getException().getMessage(), response.getException(), false);
                    else
                        showException(response.getException().getMessage(), response.getException());
                }

                delayHandler = new Handler();
                delayRunnable = new Runnable() {
                    @Override
                    public void run() {
                        setLoadingAnimation(false);
                    }
                };

                delayHandler.postDelayed(delayRunnable, 1000);
            }
        });
        backgroundTask.execute();
    }

    private void bindData(final List<ShoppingCartItem> cartProducts, List<VendorItem> vendorItems) {
        this.cartProducts = cartProducts;
        itemsView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        adapter = new CartDetailItemsAdapter(
                ShopCartActivity.this,
                cartProducts, vendorItems
                , new CartDetailItemsAdapter.ShopCartListener() {
            @Override
            public void onQuantityAdded(int pid, int cartItemId, int quantity) {
                updateCartItemQuantity(pid, cartItemId, quantity + 1);

            }

            @Override
            public void onQuantityRemoved(int pid, int cartItemId, int tempQty) {
                setLoadingAnimation(true);
                updateCartItemQuantity(pid, cartItemId, tempQty - 1);
            }

            @Override
            public void onItemDeleted(int pid, int cartItemId, int quantity) {
                updateCartItemQuantity(pid, cartItemId, 0);
            }
        });

        itemsView.setAdapter(adapter);
        refreshShopCartBadge();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    void checkoutProcess() {
        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmptyCart) {
                    GoMobileApp.Toast(R.string.empty_cart_message);
                } else {
                    if (GoMobileApp.IsUserLogin()) {
                        redirectToAddressActivity();
                    } else {
                        Intent i = new Intent(ShopCartActivity.this, LoginSignUpActivity.class);
                        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                        i.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_SHIPPING);
                        startActivity(i);
                    }
                }
            }
        });

    }

    public void refreshData() {
        adapter.notifyDataSetChanged();
    }

    void redirectToAddressActivity() {
        Intent i = new Intent(ShopCartActivity.this, OnePageCheckoutActivity.class);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        startActivity(i);
    }

    void applyCouponCode(String code) {

        ApplyCouponCodeAsyncTask backgroundTask = new ApplyCouponCodeAsyncTask(code);

        backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    showDialog("Success Applied", "Coupon-code applied successfully", true);
                } else {
                    showDialog("Error", response.getException().getMessage(), false);
                }
            }
        });
        backgroundTask.execute();
    }

    @Override
    protected void onDestroy() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        if (updateCartQuantityAsyncTask != null && !updateCartQuantityAsyncTask.isCancelled())
            updateCartQuantityAsyncTask.cancel(true);

        if (delayHandler != null && delayRunnable != null)
            delayHandler.removeCallbacks(delayRunnable);

        super.onDestroy();
    }

    public void updateCartItemQuantity(int prdId, int cartItemId, int qty) {
        setLoadingAnimation(true);

        updateCartQuantityAsyncTask = new UpdateCartQuantityAsyncTask(prdId, cartItemId, qty);
        updateCartQuantityAsyncTask.addOnResultListener(new AsyncTaskResultListener<BeanGetAllCarts>() {
            @Override
            public void response(AsyncTaskResult<BeanGetAllCarts> response) {

                if (!response.hasException()) {
                    setLoadingAnimation(false);

                    if (response.getResult().getItems().size() > 0) {
                        isEmptyCart = false;
                    } else {
                        isEmptyCart = true;
                        noProductLayout.setVisibility(View.VISIBLE);
                    }

                    adapter.setData(response.getResult().getItems(), response.getResult().getVendorItems());
                    refreshData();
                    bindTotals(response.getResult().getOrderTotals());
                    BindDiscount(response.getResult().getDiscountBox());
                    refreshShopCartBadge();
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

    private void BindDiscount(DiscountBox discountBox) {
        if (discountBox.getAppliedDiscountsWithCodes().size() != 0) {
            mCouponTextView.setText(discountBox.getAppliedDiscountsWithCodes().get(0).getCouponCode());
            mCouponTextView.setEnabled(false);
            btnDiscount.setVisibility(View.GONE);
            removeDiscount.setVisibility(View.VISIBLE);
        }

    }

    private void bindTotals(Total orderTotal) {

        fragOrderTotal = OrderTotal.newInstance(orderTotal);
        getSupportFragmentManager().beginTransaction().replace(R.id.total_fragment, fragOrderTotal).commit();
        lbl_products.setText(String.format(getString(R.string.product_total), GoMobileApp.getShoppingCartItemsCount()));
    }

    void showDialog(String title, String message, final boolean reload) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AppTheme_Dialog);
        builder.setCancelable(false);
        builder.setTitle(title);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mDialog.dismiss();
                        if (reload)
                            loadShoppingCart();
                    }
                });
        mDialog = builder.create();

        mDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadShoppingCart();
    }

}