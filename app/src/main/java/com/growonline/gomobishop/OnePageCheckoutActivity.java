package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.growonline.gomobishop.adapter.CartDetailItemsAdapter;
import com.growonline.gomobishop.adapter.CheckoutLocationMethodAdapter;
import com.growonline.gomobishop.adapter.CheckoutPaymentAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetCheckDetails;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.CancelRedirectionAsyncTask;
import com.growonline.gomobishop.asynctask.ConfirmOrderAsyncTask;
import com.growonline.gomobishop.asynctask.GetPaymentInfo;
import com.growonline.gomobishop.asynctask.UpdateCartQuantityAsyncTask;
import com.growonline.gomobishop.asynctask.UseRewardPointAsyncTask;
import com.growonline.gomobishop.control.AdapterLinearLayout;
import com.growonline.gomobishop.control.GRadioGroup;
import com.growonline.gomobishop.fragment.DialogWebViewFragment;
import com.growonline.gomobishop.fragment.OrderTotal;
import com.growonline.gomobishop.fragment.SingleAddressFragment;
import com.growonline.gomobishop.model.BeanGetAllCarts;
import com.growonline.gomobishop.model.CheckoutModel;
import com.growonline.gomobishop.model.OrderConfirmModel;
import com.growonline.gomobishop.model.PaymentInfoModel;
import com.growonline.gomobishop.model.VendorItem;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

public class OnePageCheckoutActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG_DIALOG_BOX_TERM_AND_CONDITION = "TermAndCondition";
    public CheckoutModel mCheckoutDetails;
    private RecyclerView mShopCartRecyclerView;
    private AdapterLinearLayout mLocationMethod, mPaymentLayout;
    private TextView mTermsTextView;
    private CheckBox mSameAsShippingCheckbox;
    private AsyncTaskGetCheckDetails getCheckoutDetails;
    private View mShippingVisibleLayout,
            mBillingVisibleLayout;
    private GRadioGroup mRadioLMethodUtil, mRadioPaymentUtil;
    private TextView mProcessOrder;
    private SingleAddressFragment fragShippingAddress, fragBillingAddress;
    private OrderTotal fragOrderTotal;
    private CheckBox chkRewardPoint;
    private NestedScrollView mainScroll;
    private LinearLayout addCompleteBillingLayout;
    // stripe
    private String paymentIntentClientSecret;
    private Stripe stripe;
    private String PaymentIntentId;
    private CardInputWidget cardInputWidget;
    private LinearLayout mCreditCardLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_one_page_checkout);
        setStoreTitle(getString(R.string.title_checkout));
        DisableSecondTitleRow();
        InitUI();
        loadCheckoutDetails();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private void InitUI() {
        mainScroll = (NestedScrollView) findViewById(R.id.opc_scroll);
        addCompleteBillingLayout = (LinearLayout) findViewById(R.id.add_complete_billing_layout);

        mShopCartRecyclerView = (RecyclerView) findViewById(R.id.shop_cart_recyclerView);
        mLocationMethod = (AdapterLinearLayout) findViewById(R.id.shipping_method_layout);
        mPaymentLayout = (AdapterLinearLayout) findViewById(R.id.payment_method_layout);
        mSameAsShippingCheckbox = (CheckBox) findViewById(R.id.add_billing_checkbox);
        chkRewardPoint = (CheckBox) findViewById(R.id.chk_reward_point);
        mShippingVisibleLayout = findViewById(R.id.shipping_visible_layout);
        mBillingVisibleLayout = findViewById(R.id.billing_visible_layout);
        mTermsTextView = (TextView) findViewById(R.id.terms);
        mProcessOrder = (TextView) findViewById(R.id.confirm_btn);
        cardInputWidget = findViewById(R.id.cardInputWidget);
        mCreditCardLayout = (LinearLayout) findViewById(R.id.credit_card_layout);
        mProcessOrder.setOnClickListener(this);
        mTermsTextView.setOnClickListener(this);

        LinearLayoutManager lm = new LinearLayoutManager(OnePageCheckoutActivity.this);
        mShopCartRecyclerView.setLayoutManager(lm);

        applyTextViewStyling();

        if (mVendor != null)
            if (!mVendor.getMobileAppSetting().getLoyalityEnabled()) {
                chkRewardPoint.setChecked(false);
                chkRewardPoint.setVisibility(View.GONE);
            }
        mSameAsShippingCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mBillingVisibleLayout.setVisibility(View.GONE);
                    fragBillingAddress = SingleAddressFragment.newInstance(mCheckoutDetails.getShippingLocation(),
                            AppConstant.AddressType.BILLING, mCheckoutDetails.getAvalibleCountries());
                    getSupportFragmentManager().beginTransaction().replace(R.id.billing_fragment, fragBillingAddress).commit();
                    ScrollToView(addCompleteBillingLayout);
                } else {
                    mBillingVisibleLayout.setVisibility(View.VISIBLE);
                    fragBillingAddress = SingleAddressFragment.newInstance(null,
                            AppConstant.AddressType.BILLING, mCheckoutDetails.getAvalibleCountries());
                    getSupportFragmentManager().beginTransaction().replace(R.id.billing_fragment, fragBillingAddress).commit();
                }
            }
        });

    }

    private void bindData() {

        if (mCheckoutDetails.getShoppingCartItems() != null) {

            if (mCheckoutDetails.getShoppingCartItems().size() > 1) {
                mShopCartRecyclerView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.checkout_recycler_height);
                mShopCartRecyclerView.requestLayout();
            } else {
                mShopCartRecyclerView.getLayoutParams().height = mShopCartRecyclerView.getLayoutParams().WRAP_CONTENT;
                mShopCartRecyclerView.requestLayout();
            }
            mShopCartRecyclerView.setAdapter(new CartDetailItemsAdapter(OnePageCheckoutActivity.this, mCheckoutDetails.getShoppingCartItems(), new ArrayList<VendorItem>(), new CartDetailItemsAdapter.ShopCartListener() {
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
            }));
        }

        if (mCheckoutDetails.getPaymentMethods() != null) {
            mPaymentLayout.setAdapter(new CheckoutPaymentAdapter(OnePageCheckoutActivity.this, mCheckoutDetails.getPaymentMethods().getPaymentMethods()));
            mRadioPaymentUtil = new GRadioGroup(((CheckoutPaymentAdapter) mPaymentLayout.getmAdapter()).getmRadioList());
        }
        if (mCheckoutDetails.getShippingMethod() != null) {
            mLocationMethod.setAdapter(new CheckoutLocationMethodAdapter(OnePageCheckoutActivity.this, mCheckoutDetails.getShippingMethod().getShippingMethods()));
            mRadioLMethodUtil = new GRadioGroup(((CheckoutLocationMethodAdapter) mLocationMethod.getmAdapter()).getmRadioList());
        }

        fragShippingAddress = SingleAddressFragment.newInstance(mCheckoutDetails.getShippingLocation(),
                AppConstant.AddressType.SHIPPING, mCheckoutDetails.getAvalibleCountries());
        getSupportFragmentManager().beginTransaction().replace(R.id.shipping_fragment, fragShippingAddress).commit();

        fragOrderTotal = OrderTotal.newInstance(mCheckoutDetails.getTotal());
        getSupportFragmentManager().beginTransaction().replace(R.id.total_fragment, fragOrderTotal).commit();


        mShippingVisibleLayout.setVisibility(View.VISIBLE);

        if (mCheckoutDetails.getPaymentMethods().getRewardPointsAmount() != null && mCheckoutDetails.getPaymentMethods().getRewardPointsBalance() > 0) {
            chkRewardPoint.setText((getResources().getString(R.string.use_my_reward_points) + ", Amount " + mCheckoutDetails.getPaymentMethods().getRewardPointsAmount() + " (" + mCheckoutDetails.getPaymentMethods().getRewardPointsBalance() + ") available"));
            chkRewardPoint.setChecked(mCheckoutDetails.getTotal().getRedeemedRewardPointsAmount() != null);
            chkRewardPoint.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                    UseRewardPoint(checked);
                }
            });
        } else {
            chkRewardPoint.setVisibility(View.GONE);
        }
    }

    public void updateCartItemQuantity(int prdId, int cartItemId, int qty) {
        setLoadingAnimation(true);

        UpdateCartQuantityAsyncTask updateCartQuantityAsyncTask = new UpdateCartQuantityAsyncTask(prdId, cartItemId, qty);
        updateCartQuantityAsyncTask.addOnResultListener(new AsyncTaskResultListener<BeanGetAllCarts>() {
            @Override
            public void response(AsyncTaskResult<BeanGetAllCarts> response) {

                if (!response.hasException()) {
                    setLoadingAnimation(false);

                    if (response.getResult().getItems().size() > 0) {
                        /*isEmptyCart = false;*/
                    } else {
                        finish();
                        return;
                    }
                    loadCheckoutDetails();

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


    private void applyTextViewStyling() {
        mTermsTextView.setPaintFlags(ContextCompat.getColor(OnePageCheckoutActivity.this, R.color.mid_blue));
        mTermsTextView.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        AppHelper.applyCustomFont(mSameAsShippingCheckbox, "Lato-Light.ttf");
    }

    public void loadCheckoutDetails() {
        setLoadingAnimation(true);
        getCheckoutDetails = new AsyncTaskGetCheckDetails();
        getCheckoutDetails.addOnResultListener(new AsyncTaskResultListener<CheckoutModel>() {
            @Override
            public void response(AsyncTaskResult<CheckoutModel> response) {
                setLoadingAnimation(false);
                if (!response.hasException()) {
                    if (response.getResult() != null) {
                        mCheckoutDetails = response.getResult();
                        bindData();
                    } else {
                        GoMobileApp.Toast(R.string.empty_cart_message);
                    }
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        showException(response.getException().getMessage(), response.getException(), false);
                    else
                        showException(response.getException().getMessage(), response.getException(), false);
                }
            }
        });
        getCheckoutDetails.execute();
    }

    public void OnPaymentOptionSelect(String paymentMethodSystemName) {
        if (paymentMethodSystemName.equals("Payments.Stripe") && stripe == null) {
            LoadDataFromServerStripe();
        } else if (paymentMethodSystemName.equals("Payments.Stripe")) {
            mCreditCardLayout.setVisibility(View.VISIBLE);
        } else {
            mCreditCardLayout.setVisibility(View.GONE);
        }

    }

    public void LoadDataFromServerStripe() {
        GetPaymentInfo getPaymentInfo = new GetPaymentInfo();
        getPaymentInfo.addOnResultListener(new AsyncTaskResultListener<PaymentInfoModel>() {
            @Override
            public void response(AsyncTaskResult<PaymentInfoModel> response) {
                if (response != null && response.getResult() != null) {
                    // Configure the SDK with your Stripe publishable key so it can make requests to Stripe
                    stripe = new Stripe(
                            getApplicationContext(),
                            Objects.requireNonNull(response.getResult().getPublishableKey()));
                    paymentIntentClientSecret = response.getResult().getClientSecret();
                }
            }
        });
        getPaymentInfo.execute();
        mCreditCardLayout.setVisibility(View.VISIBLE);
    }

    public void UseRewardPoint(Boolean UserRewardPoint) {
        UseRewardPointAsyncTask useRewardPointAsyncTask = new UseRewardPointAsyncTask(UserRewardPoint);
        useRewardPointAsyncTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (response != null && response.getResult() != null) {
                    loadCheckoutDetails();
                }
            }
        });
        useRewardPointAsyncTask.execute();
    }

    public void showException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(this, message, exception);
        else
            AppHelper.showNetworkError(this, message);
    }

    @Override
    public void onClick(View view) {

        if (mTermsTextView == view) {
            setUpPrivacyPolicyDialog();
            return;
        }

        if (view == mProcessOrder) {
            if (fragShippingAddress.hasAddressChanged()) {
                fragShippingAddress.UpdateAddress();
                return;
            }

            if (mBillingVisibleLayout.getVisibility() == View.VISIBLE || (fragBillingAddress != null)) {
                if (fragBillingAddress.hasAddressChanged()) {
                    fragBillingAddress.UpdateAddress();
                    return;
                }
            }

            String method = mCheckoutDetails.getShippingMethod().getShippingMethods().get(mRadioLMethodUtil.getSelectedRadioButtonPos()).getName();
            String systemName = mCheckoutDetails.getShippingMethod().getShippingMethods().get(mRadioLMethodUtil.getSelectedRadioButtonPos()).getShippingRateComputationMethodSystemName();
            String mPaymentMethodSystemName = mCheckoutDetails.getPaymentMethods().getPaymentMethods().get(mRadioPaymentUtil.getSelectedRadioButtonPos()).getPaymentMethodSystemName();
            String mPaymentMethodName = mCheckoutDetails.getPaymentMethods().getPaymentMethods().get(mRadioPaymentUtil.getSelectedRadioButtonPos()).getName();


            if (mBillingVisibleLayout.getVisibility() == View.VISIBLE ? !fragBillingAddress.isCod() : !fragShippingAddress.isCod() && mPaymentMethodSystemName.equals("Payments.CashOnDelivery")) {
                GoMobileApp.Toast(R.string.availableInPakistan);
                return;
            }
            if (mPaymentMethodSystemName.equals("Payments.Stripe") && PaymentIntentId == null) {
                PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
                if (params != null) {
                    ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
                            .createWithPaymentMethodCreateParams(params, paymentIntentClientSecret);
                    setLoadingAnimation(true);
                    stripe.confirmPayment(this, confirmParams);

                }
                return;
            }

            ConfirmOrderAsyncTask a = new ConfirmOrderAsyncTask(PaymentIntentId, method, systemName, mPaymentMethodSystemName, chkRewardPoint.isChecked());

            final String finalMPaymentMethodName = mPaymentMethodName;
            mProcessOrder.setEnabled(false);
            a.addOnResultListener(new AsyncTaskResultListener<OrderConfirmModel>() {
                @Override
                public void response(AsyncTaskResult<OrderConfirmModel> response) {
                    if (response == null) return;
                    if (!response.hasException()) {
                        GoMobileApp.setShoppingCartItemsCount(0);

                        AppHelper.LogEvent(finalMPaymentMethodName);
                        if (response.getResult().getPaymentMethodType() == 15) {
                            redirectToRedirectedWebsite(response.getResult().getRedirectUrl(), finalMPaymentMethodName);
                        } else {
                            thankYouActivity();
                        }
                    } else {
                        if (response.getException() instanceof NetworkErrorException)
                            showException(response.getException().getMessage(), response.getException(), false);
                        else
                            GoMobileApp.Toast(response.getException().getMessage());
                    }
                    mProcessOrder.setEnabled(true);
                }
            });

            a.execute();
        }
        //endregion
    }

    void redirectToEasyPayPayment(int orderId) {
        Intent i = new Intent(OnePageCheckoutActivity.this, BrowserActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        i.putExtra("url", AppConstant.EasyPayRedirectUrl + orderId);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void redirectToRedirectedWebsite(String url, String finalMPaymentMethodName) {
        Intent i = new Intent(OnePageCheckoutActivity.this, WebViewActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra(AppConstant.INTENT_URL, url.replace("\\u0026", "&"));
        i.putExtra(AppConstant.INTENT_TITLE, finalMPaymentMethodName);
        startActivityForResult(i, AppConstant.CODE_ONE_PAGE_CHECKOUT);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void thankYouActivity() {
        Intent i = new Intent(OnePageCheckoutActivity.this, ThankYouActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    void setUpPrivacyPolicyDialog() {
        DialogFragment sizeGuideDialog = DialogWebViewFragment.newInstance(AppConstant.PRIVACY_POLICY_PAGE_URL, getString(R.string.term_and_condition), true);
        sizeGuideDialog.show(getSupportFragmentManager(), TAG_DIALOG_BOX_TERM_AND_CONDITION);
    }

    @Override
    protected void onDestroy() {

        if (getCheckoutDetails != null && !getCheckoutDetails.isCancelled())
            getCheckoutDetails.cancel(true);

        super.onDestroy();
    }

    public void ScrollToView(final View view) {
//        ObjectAnimator.ofInt(mainScroll, "scrollY", view.getTop()).setDuration(500).start();
        view.getParent().requestChildFocus(view, view);
        view.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        setLoadingAnimation(false);
        switch (resultCode) {
            case AppConstant.CODE_STRIPE_PAYMENT:
                // Handle the result of stripe.confirmPayment
                stripe.onPaymentResult(requestCode, data, new PaymentResultCallback(this));
                break;
            case AppConstant.CODE_ONE_PAGE_CHECKOUT:
                thankYouActivity();
                break;
            case AppConstant.CODE_REDIRECTION_CANCEL:
            default:
                new CancelRedirectionAsyncTask().execute();
                loadCheckoutDetails();
                break;
        }
    }


    public void ProceedToCheckout() {
        mProcessOrder.performClick();
    }

    private static final class PaymentResultCallback
            implements ApiResultCallback<PaymentIntentResult> {
        @NonNull
        private final WeakReference<OnePageCheckoutActivity> activityRef;

        PaymentResultCallback(@NonNull OnePageCheckoutActivity activity) {
            activityRef = new WeakReference<>(activity);
        }

        @Override
        public void onSuccess(@NonNull PaymentIntentResult result) {
            final OnePageCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
            PaymentIntent paymentIntent = result.getIntent();
            PaymentIntent.Status status = paymentIntent.getStatus();
            if (status == PaymentIntent.Status.Succeeded) {
                // Payment completed successfully
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                activity.PaymentIntentId = paymentIntent.getId();
                activity.ProceedToCheckout();

            } else if (status == PaymentIntent.Status.RequiresPaymentMethod) {
                // Payment failed – allow retrying using a different payment method
                GoMobileApp.Toast("Payment failed" + paymentIntent.getLastPaymentError().getMessage());
            }
        }

        @Override
        public void onError(@NonNull Exception e) {
            final OnePageCheckoutActivity activity = activityRef.get();
            if (activity == null) {
                return;
            }
        }
    }


}