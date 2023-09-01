package com.growonline.gomobishop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.growonline.gomobishop.control.AVLoadingIndicatorView;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.fragment.MiniShoppingCartFragment;
import com.growonline.gomobishop.fragment.NotificationBadge;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

public class BaseActivity extends AppCompatActivity {

    private final int durationMinMaxAnimation = 300;
    public Vendor mVendor;
    private View mActivityLayout;
    private CoordinatorLayout mBaseCoordinatorLayout;
    private FrameLayout mFrameLayout;
    private RelativeLayout mBaseLayoutContainer, mBtnShopCart;
    private LinearLayout blackOut, mSecondTitleRow;
    private FontTextView toolBarTitle, storeTitle;
    private AVLoadingIndicatorView loader;
    private Snackbar snackbar;
    private AlertDialog mErrorDialog;
    private ImageView mBackButtonImageView;
    private NotificationBadge badge;
    private ImageView mArrowMinimize;
    private ImageView mLeftImageView;
    private ImageView mBackButton;
    private FrameLayout dialog;
    private MiniShoppingCartFragment mMiniShopCart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = this.getIntent();
        mVendor = i.getParcelableExtra(AppConstant.INTENT_VENDOR);

        if (mActivityLayout == null) {
            mActivityLayout = getLayoutInflater().inflate(R.layout.activity_base, null);
            mFrameLayout = (FrameLayout) mActivityLayout.findViewById(R.id.body);
            mBaseLayoutContainer = (RelativeLayout) mActivityLayout.findViewById(R.id.activity_base);
            loader = (AVLoadingIndicatorView) mActivityLayout.findViewById(R.id.loader_base);
            blackOut = (LinearLayout) mActivityLayout.findViewById(R.id.blackOut_base);
            mSecondTitleRow = (LinearLayout) mActivityLayout.findViewById(R.id.toolbar_second_row);
            toolBarTitle = (FontTextView) mActivityLayout.findViewById(R.id.title_activity);
            storeTitle = (FontTextView) mActivityLayout.findViewById(R.id.title_store);
            mBtnShopCart = (RelativeLayout) mActivityLayout.findViewById(R.id.btn_shopCart);
            mBaseCoordinatorLayout = (CoordinatorLayout) mActivityLayout.findViewById(R.id.cordinator_layout_base);
            mArrowMinimize = (ImageView) mActivityLayout.findViewById(R.id.icon_minimize);
            mBackButton = (ImageView) mActivityLayout.findViewById(R.id.btn_back);
            dialog = (FrameLayout) mActivityLayout.findViewById(R.id.overlay_dialogbox_container);

        }

        InitUiBase();
    }

    @Override
    public void setContentView(int layoutResID) {

        if (mActivityLayout == null) {
            mActivityLayout = getLayoutInflater().inflate(R.layout.activity_base, null);
            mFrameLayout = (FrameLayout) mActivityLayout.findViewById(R.id.body);
            mBaseLayoutContainer = (RelativeLayout) mActivityLayout.findViewById(R.id.activity_base);
            loader = (AVLoadingIndicatorView) mActivityLayout.findViewById(R.id.loader_base);
            blackOut = (LinearLayout) mActivityLayout.findViewById(R.id.blackOut_base);
            toolBarTitle = (FontTextView) mActivityLayout.findViewById(R.id.title_activity);
            storeTitle = (FontTextView) mActivityLayout.findViewById(R.id.title_store);
            mBaseCoordinatorLayout = (CoordinatorLayout) mActivityLayout.findViewById(R.id.cordinator_layout_base);
        }

        setContentView(mActivityLayout);
        getLayoutInflater().inflate(layoutResID, mFrameLayout, true);
    }

    void InitUiBase() {
        badge = (NotificationBadge) mActivityLayout.findViewById(R.id.badge);

        LinearLayout mToolBar = (LinearLayout) mActivityLayout.findViewById(R.id.app_bar);
        if (mVendor == null)
            mToolBar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        else {
            mToolBar.setBackgroundColor(Color.parseColor(mVendor.getThemeColor()));
            storeTitle.setText(mVendor.getName());
        }

        toolBarTitle.setText("");

//        storeTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mVendor != null)
//                    minimizeScreen();
//                else
//                    onBackPressed();
//            }
//        });
        mBackButtonImageView = (ImageView) mActivityLayout.findViewById(R.id.btn_back);

        mBackButtonImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mBtnShopCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BaseActivity.this, ShopCartActivity.class);
                i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                startActivity(i);
            }
        });

        refreshShopCartBadge();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mMiniShopCart = MiniShoppingCartFragment.newInstance(mVendor);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.overlay_dialogbox_container, mMiniShopCart);
                ft.commit();
            }
        }, 300);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }


    public void setToolBarTitle(String title) {
        if (title != null)
            if (mVendor != null && BuildConfig.market) {
                toolBarTitle.setText(title.toUpperCase());
                mSecondTitleRow.setVisibility(View.VISIBLE);
            } else {
                DisableSecondTitleRow();
                setStoreTitle(title);
            }

    }

    public void setStoreTitle(String title) {
        if (title != null)
            storeTitle.setText(title.toUpperCase());
    }

    public void setBackButtonClickListener(View.OnClickListener clickListener) {
        mBackButtonImageView.setOnClickListener(clickListener);
    }

    public void setLoadingAnimation(boolean show) {

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


    public void showSnackBar(String message) {
        snackbar = Snackbar.make(mBaseCoordinatorLayout, message, Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void showSnackBarLong(String message) {
        snackbar = Snackbar.make(mBaseCoordinatorLayout, message, Snackbar.LENGTH_LONG);
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void showSnackBarOK(String message) {
        snackbar = Snackbar.make(mBaseCoordinatorLayout, message,
                Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                    }
                });

        snackbar.setActionTextColor(Color.LTGRAY);

        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(com.google.android.material.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public void showException(String message, Throwable exception) {
        AppHelper.showException(this, message, exception);
    }

    public void showException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(this, message, exception);
        else
            AppHelper.showNetworkError(this, message);
    }

    public void DisableSecondTitleRow() {
        mSecondTitleRow.setVisibility(View.GONE);
    }

    public void refreshShopCartBadge() {
        badge.setNumber(GoMobileApp.getShoppingCartItemsCount());
    }

    public void hideShopCartBadge() {
        mBackButton.setVisibility(View.GONE);
        mBtnShopCart.setVisibility(View.GONE);
    }

    public boolean IsSecondRowVisible() {
        return mSecondTitleRow.getVisibility() == View.VISIBLE;
    }

    public void showMiniShopCart(int productId) {
        refreshShopCartBadge();
        dialog.setVisibility(View.VISIBLE);
        blackOut.setVisibility(View.VISIBLE);
        mMiniShopCart.Show(productId);
        blackOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMiniShopCart.Hide();
                HideMiniShopCart();
            }
        });
    }

    public void HideMiniShopCart() {
        blackOut.setVisibility(View.GONE);
    }

}
