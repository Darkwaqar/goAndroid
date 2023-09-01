package com.growonline.gomobishop;

import android.animation.Animator;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.AssociateProductAdapter;
import com.growonline.gomobishop.fragment.MiniShoppingCartFragment;
import com.growonline.gomobishop.model.BeanProductDetail;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;


public class ShopTheLookDialog extends DialogFragment {
    private static final String ARG_PARAM1 = "dialog_products";
    private static final String ARG_PARAM2 = "dialog_main_product_id";

    private TextView mShowError, mHeaderTitle;
    private ImageView mBtnClose;
    private List<BeanProductDetail> mProducts = new ArrayList<>();
    private int mMainProductId;
    private RecyclerView mRecyclerView;
    private AssociateProductAdapter mAssociateProductAdaptor;
    private View layoutRoot, mDisabler;
    private MiniShoppingCartFragment mMiniShopCart;


    public ShopTheLookDialog() {

    }

    public static ShopTheLookDialog newInstance(List<BeanProductDetail> associateProducts, int mainProductId) {
        ShopTheLookDialog fragment = new ShopTheLookDialog();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, new ArrayList<>(associateProducts));
        args.putInt(ARG_PARAM2, mainProductId);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);

        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(ARG_PARAM1);
            mMainProductId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layoutRoot = inflater.inflate(R.layout.shopthelook_dialog, container);

        layoutRoot.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    v.removeOnLayoutChangeListener(this);

                    int cx = GoMobileApp.getScreenWidth();
                    int cy = GoMobileApp.getScreenHeight();

                    float finalRadius = (float) Math.sqrt(cx * cx + cy * cy);

                    Animator anim = ViewAnimationUtils.createCircularReveal(layoutRoot, cx, cy, 0, finalRadius);
                    anim.setDuration(350);
                    anim.start();
                }
            }
        });


        initUi(layoutRoot);
        return layoutRoot;
    }

    private void CircularUnreveal() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            int cx = GoMobileApp.getScreenWidth();
            int cy = GoMobileApp.getScreenHeight();

            float finalRadius = (float) Math.sqrt(cx * cx + cy * cy);

            Animator anim = ViewAnimationUtils.createCircularReveal(layoutRoot, cx, cy, finalRadius, 0);
            anim.setDuration(350);
            anim.start();

            anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    getDialog().dismiss();
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        } else {
            getDialog().dismiss();
        }
    }

    private void initUi(View v) {
        mHeaderTitle = (TextView) v.findViewById(R.id.associate_title);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.dialogbox_recycler_view);
        mShowError = (TextView) v.findViewById(R.id.errorshow_textview);
        mBtnClose = (ImageView) v.findViewById(R.id.btn_close);
        mDisabler = v.findViewById(R.id.disabler);

        //empty click listener
        mDisabler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        mBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CircularUnreveal();
            }
        });
        AppHelper.applyPlayFairDisplayBoldFont(mHeaderTitle);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mProducts != null) {
                    mAssociateProductAdaptor = new AssociateProductAdapter(getActivity(), ShopTheLookDialog.this, mProducts);
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
                    mRecyclerView.setAdapter(mAssociateProductAdaptor);

                    Vendor vendor = (((RelatedProductsActivity) getActivity()).getmVendor());
                    mMiniShopCart = MiniShoppingCartFragment.newInstance(vendor);
                    FragmentTransaction ft = getChildFragmentManager().beginTransaction();
                    ft.replace(R.id.overlay_dialogbox_container, mMiniShopCart);
                    ft.commit();
                }
            }
        }, 300);
    }

    public void showMiniShopCart(int productId) {
        FrameLayout container = (FrameLayout) getView().findViewById(R.id.overlay_dialogbox_container);
        container.setVisibility(View.VISIBLE);

        mMiniShopCart.Show(productId);
    }


}
