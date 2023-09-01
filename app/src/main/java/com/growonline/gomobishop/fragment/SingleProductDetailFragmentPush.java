package com.growonline.gomobishop.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.LoginSignUpActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RatingActivity;
import com.growonline.gomobishop.RelatedProductsActivity;
import com.growonline.gomobishop.ShopCartActivity;
import com.growonline.gomobishop.StoreActivity;
import com.growonline.gomobishop.adapter.AdapterAttributeDetail;
import com.growonline.gomobishop.adapter.AssociateProductAdapter;
import com.growonline.gomobishop.adapter.ExpendableRecyclerAdapter;
import com.growonline.gomobishop.adapter.ProductListingAdapter;
import com.growonline.gomobishop.adapter.ProductShowcaseAdapter;
import com.growonline.gomobishop.asynctask.AddToCartAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.BackInStockPost;
import com.growonline.gomobishop.asynctask.GetProductDetailAsyncTask;
import com.growonline.gomobishop.asynctask.NotifyWhenAvailable;
import com.growonline.gomobishop.control.FlipAnimation;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.control.VerticalViewPager;
import com.growonline.gomobishop.control.multisnaprecyclerview.MultiSnapRecyclerView;
import com.growonline.gomobishop.model.AttributeValue;
import com.growonline.gomobishop.model.BackInStock;
import com.growonline.gomobishop.model.BeanProdDetailWithRProd;
import com.growonline.gomobishop.model.BeanProductDetail;
import com.growonline.gomobishop.model.DefaultPictureModel;
import com.growonline.gomobishop.model.ExpendableModel;
import com.growonline.gomobishop.model.Product;
import com.growonline.gomobishop.model.ProductAttribute;
import com.growonline.gomobishop.model.ProductPointer;
import com.growonline.gomobishop.model.ProductSpecification;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.network.NetworkUtils;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.SystemIntents;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

import static androidx.core.app.FrameMetricsAggregator.ANIMATION_DURATION;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SingleProductDetailFragmentPush#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleProductDetailFragmentPush extends Fragment implements ViewPager.OnPageChangeListener {
    public static final String TAG_DIALOG_BOX_SIZE_GUIDE = "sizeGuideDialogBox";
    private final int[] index = {0};
    int[] limitBottom = new int[2];
    private int mProductId;
    private String mProductPrice, mSizeGuideHtml, mProductName;
    private Vendor mVendor;
    private FragmentActivity mActivity;
    private boolean isProductLoaded = false;
    private VerticalViewPager imagesViewPager;
    private LinearLayout indicatorLayout;
    private BeanProductDetail mDetailProductBean;
    private GetProductDetailAsyncTask backgroundTask;
    private Boolean mGroupedProduct = false;
    private ViewGroup mFooterLayout;
    private ViewGroup mRelatedProductLayout;
    private ArrayList<Product> mRelatedProducts;
    private ArrayList<ProductPointer> mProductPointers;
    private ViewGroup mRelatedProductVisibleLayout;
    private boolean lastPageChange;
    private GridView mSizeGridView;
    private ArrayList<AttributeValue> mSelectedAttributeValues;
    private AttributeValue mSelectedAttributeValue;
    private TextView txtAddToCart;
    private ArrayList<ProductAttribute> mSelectedProductAttributes;
    private ProductAttribute mSelectedProductAttribute;
    private TextView lblPrice, lbl_price_strike_through;
    private View mDisableView;
    private TextView mInfoTextView;
    private View mLineLayout;
    private ImageView mCallImageView;
    private ImageView bottomSheetIndicator;
    private Button mNext;
    private TextView mBtnSizeGuide, mShare, mWhatapp, mAddtoWishlistTextView;
    private ImageView pointer;
    private LinearLayout mProductRating;
    private TextView mRatingStar;
    private TextView mRatingTotal;
    private FontTextView mSelectFilterTitle;
    private List<ProductAttribute> ProductAttributes;
    private int ProductAttributeSize;
    private List<ExpendableModel> expendableData = new ArrayList<>();
    private ExpendableRecyclerAdapter expendableRecyclerAdapter;
    private BottomSheetDialog dialog;
    private RecyclerView mProductDetailRecycler;
    private View mBottomSheet;
    private Boolean mShowSizeGuide = false;
    private EditText mExtraInformation;

    private NotifyWhenAvailable notifyWhenAvailable;

    private EditText recipientName;
    private EditText recipientEmail;
    private EditText fromName;
    private EditText fromEmail;
    private EditText fromMessage;

    private ViewPager shopTheLookViewPager;
    private ProductListingAdapter adapter;
    private RelativeLayout shopTheLookLayout;
    private RelativeLayout imageLayout;
    private ImageView mCloseButton;
    private MultiSnapRecyclerView shopTheLookRecycler;
    private ArrayList<Product> shopTheLookProducts = new ArrayList<>();

    private boolean scrollStarted, checkDirection;
    private TextView productName;
    private RelativeLayout attr_layout;
    private LinearLayout thumbImages;
    private List<DefaultPictureModel> productImages = new ArrayList<>();
    private List<View> viewList = new ArrayList<>();
    private List<BeanProductDetail> mShopTheLookProducts = new ArrayList<>();
    private GifImageView zoomImage;
    private AdapterAttributeDetail adapterAttribute;

    private AssociateProductAdapter mAssociateProductAdaptor;

    public SingleProductDetailFragmentPush() {
    }

    public static SingleProductDetailFragmentPush newInstance(int productId, Vendor vendor) {
        SingleProductDetailFragmentPush fragment = new SingleProductDetailFragmentPush();
        Bundle args = new Bundle();
        args.putInt(AppConstant.INTENT_PRODUCT_ID, productId);
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProductId = getArguments().getInt(AppConstant.INTENT_PRODUCT_ID);
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
            mActivity = getActivity();
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_single_product_detail_push, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUi(view);

    }

    private void updateRelatedProducts() {
        final ArrayList<Product> bean = mRelatedProducts;
        mRelatedProductLayout.removeAllViews();
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < bean.size(); i++) {
            View view = inflater.inflate(R.layout.fragment_detail_related_product, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.detail_related_product_imgview);
            view.setTag(i);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tag = (Integer) view.getTag();
                    Intent i = new Intent(getActivity(), RelatedProductsActivity.class);
                    i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                    i.putParcelableArrayListExtra(AppConstant.INTENT_PRODUCT, bean);
                    i.putExtra(AppConstant.INTENT_PRODUCT_ID, mRelatedProducts.get(tag).getId());
                    i.putExtra(AppConstant.INTENT_TITLE, mRelatedProducts.get(0).getName());
                    startActivity(i);
                    getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);

                }
            });
            GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(bean.get(i).getDefaultPictureModel().getImageUrl()), imageView, null);
            mRelatedProductLayout.addView(view);
        }
    }

    private void updateShopTheLook(List<BeanProductDetail> associatedProducts) {
        if (associatedProducts == null) return;

        mShopTheLookProducts.clear();
        mShopTheLookProducts.addAll(associatedProducts);
        mAssociateProductAdaptor.notifyDataSetChanged();
    }

    void initUi(View view) {
        txtAddToCart = view.findViewById(R.id.txt_addtocart);
        mCallImageView = (ImageView) view.findViewById(R.id.call_to_order);
        mCloseButton = (ImageView) view.findViewById(R.id.btn_close);
        bottomSheetIndicator = (ImageView) view.findViewById(R.id.bottom_sheet_indicator);
        Animation pulse = AnimationUtils.loadAnimation(getActivity(), R.anim.pulse);
        bottomSheetIndicator.startAnimation(pulse);
        pointer = (ImageView) view.findViewById(R.id.pointer);
        mInfoTextView = (TextView) view.findViewById(R.id.txt_detail);
        mDisableView = view.findViewById(R.id.disable_view);
        indicatorLayout = (LinearLayout) view.findViewById(R.id.indicator_layout);
        imagesViewPager = (VerticalViewPager) view.findViewById(R.id.view_pager_img_prd);
        mLineLayout = view.findViewById(R.id.line_layout);
        mFooterLayout = (ViewGroup) view.findViewById(R.id.footer_normal_new);
        mRelatedProductVisibleLayout = (ViewGroup) view.findViewById(R.id.footer_related_products);
        mRelatedProductLayout = (ViewGroup) view.findViewById(R.id.detail_parent_bottom_related_layout);
        mSizeGridView = (GridView) view.findViewById(R.id.size_gridview);
        mProductRating = (LinearLayout) view.findViewById(R.id.product_rating_stars);
        mRatingStar = (TextView) view.findViewById(R.id.rating_stars);
        mRatingTotal = (TextView) view.findViewById(R.id.rating_total);
        mSelectFilterTitle = (FontTextView) view.findViewById(R.id.select_filter_title);
        mExtraInformation = (EditText) view.findViewById(R.id.extra_information);
        lblPrice = (TextView) view.findViewById(R.id.txt_price);
        lbl_price_strike_through = (TextView) view.findViewById(R.id.txt_price_strike_through);
        shopTheLookViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        shopTheLookLayout = (RelativeLayout) view.findViewById(R.id.shopTheLookLayout);
        shopTheLookLayout.getLayoutParams().width = GoMobileApp.getScreenWidth() / 100 * 60;
        imageLayout = (RelativeLayout) view.findViewById(R.id.imageLayout);
        shopTheLookRecycler = (MultiSnapRecyclerView) view.findViewById(R.id.shopTheLookRecycler);
        attr_layout = (RelativeLayout) view.findViewById(R.id.attr_layout);
        thumbImages = (LinearLayout) view.findViewById(R.id.image_thumb);
//        zoomImage = (GifImageView) view.findViewById(R.id.zoom);
//
//        String zoomTime=GoMobileApp.getStringPrefs(AppConstant.ZOOM_KEY);
//        int times = Integer.parseInt(!zoomTime.equals("")?zoomTime:String.valueOf(0));
//        if (times < 10) {
//            zoomImage.setVisibility(View.GONE);
//            GoMobileApp.addToSharedPrefs(AppConstant.ZOOM_KEY,String.valueOf(++times));
//
//        }
//        else {
//            GoMobileApp.addToSharedPrefs(AppConstant.ZOOM_KEY,String.valueOf(0));
//            new Handler()
//            .postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    zoomImage.setVisibility(View.GONE);
//                }
//            }, 3000);
//        }
//        adapter = new ProductListingAdapter(shopTheLookProducts, "#000000", (AppCompatActivity) getActivity(), null);
//        shopTheLookRecycler.setLayoutManager(new GridLayoutManager(null, 1, LinearLayoutManager.VERTICAL, false));
//        shopTheLookRecycler.setAdapter(adapter);

        mAssociateProductAdaptor = new AssociateProductAdapter(getActivity(), null, mShopTheLookProducts);
        shopTheLookRecycler.setLayoutManager(new LinearLayoutManager(null, LinearLayoutManager.VERTICAL, false));
        shopTheLookRecycler.setAdapter(mAssociateProductAdaptor);

//        shopTheLookRecycler.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), shopTheLookRecycler, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, int position) {
//                Product product = shopTheLookProducts.get(position);
//                Intent i = new Intent(getActivity(), ActivityRelatedProducts.class);
//                i.putExtra("paramStore", mVendor);
//                i.putParcelableArrayListExtra(AppConstant.INTENT_PRODUCT, shopTheLookProducts);
//                i.putExtra(AppConstant.INTENT_PRODUCT, product.getId());
//                i.putExtra("title", product.getName());
//                startActivity(i);
//                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//            }
//        }));

        mExtraInformation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0)
                    mNext.setText("Next");
                else
                    mNext.setText("Skip");
            }
        });

        mExtraInformation.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    mNext.performClick();
                    return true;
                }
                return false;
            }
        });

//        attr_layout.setOnTouchListener(new View.OnTouchListener() {
//            private float mDy;
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                if (action == MotionEvent.ACTION_DOWN) {
//                    mDy = attr_layout.getY() - event.getRawY();
//                } else if (action == MotionEvent.ACTION_MOVE) {
//                    if (event.getRawY() + mDy < limitBottom[1]/2 && event.getRawY() + mDy > 1)
//                        attr_layout.setY((int) (event.getRawY() + mDy));
//                    Log.e("value of y", String.valueOf(event.getRawY() + mDy));
//                }
//                return true;
//            }
//        });

        InitBottomSheet();

        if (mVendor.getMobileAppSetting().getCallToOrderEnabled()) {
            mCallImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
                    alertDialogBuilder.setMessage("Your are about to call " + mVendor.getName()).setPositiveButton("Call", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + (mVendor.getContactNumber() != null ? mVendor.getContactNumber() : AppConstant.ORDER_PHONE_NUMBER)));
                                startActivity(callIntent);
                            } catch (Exception activityException) {
                                Log.e(getString(R.string.callingPhoneNumber), getString(R.string.callfailed), activityException);
                            }
                        }
                    }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
                }
            });
        } else {
            mCallImageView.setVisibility(View.GONE);
        }

        mInfoTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isProductLoaded) {
                    InitBottomSheet();
                    dialog.show();
                } else {
                    GoMobileApp.Toast(R.string.please_wait_message);
                }
            }
        });

        mFooterLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mInfoTextView.performClick();
            }
        });


        mNext = (Button) view.findViewById(R.id.next_btn);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboardFrom(getActivity(), view);
                if (mSelectedAttributeValue != null || mSelectedProductAttribute != null) {
                    mSelectedAttributeValues.add(mSelectedAttributeValue);
                    mSelectedProductAttributes.add(mSelectedProductAttribute);
                } else {
                    if (ProductAttributes.get(index[0]).getAttributeControlType().equals(AppConstant.TEXT_INPUT) || ProductAttributes.get(index[0]).getAttributeControlType().equals(AppConstant.TEXT_MULTI_LINE_INPUT)) {
                        mSelectedAttributeValues.add(new AttributeValue(mExtraInformation.getText().toString(), -1));
                        mSelectedProductAttributes.add(ProductAttributes.get(index[0]));
                    } else if (ProductAttributes.get(index[0]).getAttributeControlType().equals(AppConstant.CHECKBOX)) {
                        StringBuilder result = new StringBuilder();
                        for (AttributeValue attributeValue : adapterAttribute.getSelectedList()) {
                            result.append(attributeValue.getId());
                            result.append(",");
                        }
                        mSelectedAttributeValues.add(new AttributeValue(result.length() > 0 ? result.substring(0, result.length() - 1) : "", -1));
                        mSelectedProductAttributes.add(ProductAttributes.get(index[0]));
                    }
                }

                index[0]++;
                if (ProductAttributes.size() > index[0]) {
                    addValueToGirdView(index[0]);
                } else
                    txtAddToCart.performClick();


            }
        });


        mDisableView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        mCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideShopTheLook();
            }
        });

        mProductRating.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                startActivity(new Intent(getActivity(), RatingActivity.class).putExtra("Type", AppConstant.PRODUCT_REVIEW).putExtra("Id", mProductId).putExtra(AppConstant.INTENT_VENDOR, mVendor));
                return true;
            }
        });

        loadProductDetail();
        mSelectedAttributeValues = new ArrayList<>();
        mSelectedProductAttributes = new ArrayList<>();

        txtAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtAddToCart.getLocationOnScreen(limitBottom);
                txtAddToCart.setClickable(false);
                if (isProductLoaded) {
                    pointer.setX(v.getX() + v.getWidth() / 2);
                    try {
                        if (!mGroupedProduct) {
                            if (validateSelectedContent()) {
                                String defaultPrefix = "product_attribute_";
                                JSONObject attributeArray = new JSONObject();

                                for (int i = 0; i < mSelectedAttributeValues.size(); i++) {
                                    if (mSelectedAttributeValues.get(i).getId() > 0)
                                        attributeArray.put(defaultPrefix + mSelectedProductAttributes.get(i).getId(), mSelectedAttributeValues.get(i).getId());
                                    else {
                                        attributeArray.put(defaultPrefix + mSelectedProductAttributes.get(i).getId(), mSelectedAttributeValues.get(i).getName());
                                    }
                                }

                                //Simple product case
                                AddToCartAsyncTask backgroundTask = new AddToCartAsyncTask(mProductId, 1, 1,
                                        mProductPrice, attributeArray);
                                backgroundTask.execute();
                                backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                                    @Override
                                    public void response(AsyncTaskResult<Boolean> response) {
                                        if (!response.hasException()) {
                                            GoMobileApp.Toast("Added to Cart");
                                            if (mActivity instanceof StoreActivity)
                                                ((StoreActivity) mActivity).refreshShopCartBadge();
                                            else
                                                ((RelatedProductsActivity) mActivity).showMiniShopCart(mProductId);
                                            resetAllValues();
                                        } else {
                                            launchException(response.getException().getMessage(), response.getException(), true);
                                            resetAllValues();
                                        }
                                    }
                                });
                            }
                        } else {

                            ShowBottomSheetShopTheLook(0);
                        }
                    } catch (Exception ex) {
                        Log.e("onShopClick", "onClick: ", ex);
                        resetAllValues();
                    }
                } else {
                    GoMobileApp.Toast(R.string.please_wait_message);

                }
                txtAddToCart.setClickable(true);
            }
        });
    }

    private void InitBottomSheet() {
        dialog = new BottomSheetDialog(getActivity());
        mBottomSheet = getLayoutInflater().inflate(R.layout.product_detail_bottom_sheet, null);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(mBottomSheet);

        mProductDetailRecycler = (RecyclerView) dialog.findViewById(R.id.product_detail_recycler);
        bottomSheetIndicator = (ImageView) dialog.findViewById(R.id.bottom_sheet_indicator);
        mAddtoWishlistTextView = (TextView) dialog.findViewById(R.id.wishlist);
        mShare = (TextView) dialog.findViewById(R.id.share);
        mBtnSizeGuide = (TextView) dialog.findViewById(R.id.btn_size_guide);
        mWhatapp = (TextView) dialog.findViewById(R.id.whatsapp);
        productName = (TextView) dialog.findViewById(R.id.Bottom_sheet_product_name);
        productName.setText(mProductName);
        mProductDetailRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mProductDetailRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        expendableRecyclerAdapter = new ExpendableRecyclerAdapter(expendableData);
        mProductDetailRecycler.setAdapter(expendableRecyclerAdapter);


        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isProductLoaded) {
                    String sharingLink = AppConstant.GET_SHARE_PRODUCT
                            + mVendor.getId()
                            + "&pid="
                            + mProductId;

                    SystemIntents.share(getActivity(), sharingLink, null,
                            NetworkUtils.PLAIN_TEXT);
                } else {
                    GoMobileApp.Toast(R.string.please_wait_message);
                }
            }
        });

        mBtnSizeGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String url = AppConstant.BASE_DOMAIN + "/t-popup/" + mSizeGuideHtml;
                DialogFragment sizeGuideDialog = DialogWebViewFragment.newInstance(url, "Size Guide", true);
                sizeGuideDialog.show(mActivity.getSupportFragmentManager(), TAG_DIALOG_BOX_SIZE_GUIDE);
            }
        });

        if (mShowSizeGuide) {
            mBtnSizeGuide.setVisibility(View.VISIBLE);
        }

        mAddtoWishlistTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProductToWishList();
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
                BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
                behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                    @Override
                    public void onStateChanged(@NonNull View bottomSheet, int newState) {
                        if (newState == BottomSheetBehavior.STATE_HIDDEN)
                            dialog.dismiss();
                    }

                    @Override
                    public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                        float rotation = slideOffset * 180;
                        if (rotation > 0)
                            bottomSheetIndicator.setRotation(rotation);
                    }
                });
            }
        });

        if (mVendor.getSocialLinks().getWhatsappMobile() != null)
            mWhatapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Uri uri = Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s",
                            mVendor.getSocialLinks().getWhatsappMobile().replace(" ", "").replaceAll("[^0-9]", ""), "Hi"));
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(sendIntent);
                }
            });
        else
            mWhatapp.setVisibility(View.GONE);
    }

    private void resetAllValues() {
        mSelectedAttributeValues.clear();
        mSelectedAttributeValue = null;
        mSelectedProductAttributes.clear();
        if (mLineLayout.getVisibility() == View.VISIBLE) {
            manageVisibility();
        }
        index[0] = 0;
        addValueToGirdView(index[0]);
        mExtraInformation.setText("");
        hideKeyboardFrom(getActivity(), mNext);
    }

    public void showNextButton(Boolean show) {
        mNext.setText(show ? "Next" : "Skip");
        mNext.setVisibility(show ? View.VISIBLE : View.GONE);
    }

    private void addValueToGirdView(int i) {
        mSelectedAttributeValue = null;
        mSelectedProductAttribute = null;

        if (ProductAttributes.size() > 0) {
            ProductAttribute productAttributes = ProductAttributes.get(i);
            mSelectFilterTitle.setText(productAttributes.getName());
            if (productAttributes.getAttributeControlType().equals(AppConstant.TEXT_INPUT) || productAttributes.getAttributeControlType().equals(AppConstant.TEXT_MULTI_LINE_INPUT)) {
                mSizeGridView.setVisibility(View.GONE);
                mExtraInformation.setVisibility(View.VISIBLE);
                mExtraInformation.requestFocus();
                if (i > 0) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(mExtraInformation, InputMethodManager.SHOW_IMPLICIT);
                }
                if (productAttributes.getDefaultValue() != null) {
                    mExtraInformation.setText(productAttributes.getDefaultValue());
                }
                mNext.setText("Skip");
                mNext.setVisibility(View.VISIBLE);

            } else {
                mSizeGridView.setVisibility(View.VISIBLE);
                mExtraInformation.setVisibility(View.GONE);
                if (productAttributes.getIsRequired())
                    mNext.setVisibility(View.GONE);
                else
                    mNext.setVisibility(View.VISIBLE);
            }

            adapterAttribute = new AdapterAttributeDetail(getActivity(), productAttributes, SingleProductDetailFragmentPush.this);
            mSizeGridView.setAdapter(adapterAttribute);
        }
    }

    private void addProductToWishList() {
        try {

            String defaultPrefix = "product_attribute_";
            JSONObject attributeArray = new JSONObject();


            if (ProductAttributeSize > 0) {
                for (int i = 0; i < ProductAttributeSize; i++) {
                    if (ProductAttributes.get(i).getValues().size() > 0)
                        attributeArray.put(defaultPrefix + ProductAttributes.get(i).getId(), ProductAttributes.get(i).getValues().get(0).getId());
                }
            }

            AddToCartAsyncTask a = new AddToCartAsyncTask(mDetailProductBean.getId(), 1,
                    2, mDetailProductBean.getProductPrice().getPrice(), attributeArray);

            if (mActivity instanceof StoreActivity) {
                ((StoreActivity) getActivity()).setLoadingAnimation(true);
            }

            a.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                @Override
                public void response(AsyncTaskResult<Boolean> response) {

                    if (mActivity instanceof StoreActivity) {
                        ((StoreActivity) getActivity()).setLoadingAnimation(false);
                    }

                    if (!response.hasException()) {
                        GoMobileApp.Toast(R.string.productAddedToWishlist);
                    } else {
                        GoMobileApp.Toast(response.getException().getMessage());
                    }
                }
            });
            a.execute();
        } catch (Exception ex) {
            GoMobileApp.Toast(ex.getMessage());
        }
    }

    private boolean validateSelectedContent() {
        if (ProductAttributeSize < mSelectedAttributeValues.size()) {
            mSelectedAttributeValues.clear();
            mSelectedProductAttributes.clear();
        }

        if (index[0] == ProductAttributeSize) return true;
        if (ProductAttributeSize == mSelectedAttributeValues.size()) {
            return true;
        } else {
            manageVisibility();
            return false;
        }

    }

    private void manageVisibility() {
        if (mLineLayout.getVisibility() == View.VISIBLE) {
            mLineLayout.setVisibility(View.GONE);
        } else {
            mLineLayout.setVisibility(View.VISIBLE);
        }
    }

    private void updateAttrs(List<ProductAttribute> attributes, BeanProductDetail model) {

        ProductAttributes = null;
        ProductAttributeSize = 0;
        ProductAttributes = model.getProductAttributes();
        ProductAttributeSize = model.getProductAttributes().size();

        if (attributes.size() > 0) {
            addValueToGirdView(index[0]);
        }
    }

    void loadProductDetail() {
        backgroundTask = new GetProductDetailAsyncTask(mProductId);
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<BeanProdDetailWithRProd>() {
            @Override
            public void response(AsyncTaskResult<BeanProdDetailWithRProd> response) {
                isProductLoaded = true;
                if (!response.hasException()) {
                    BeanProdDetailWithRProd beans = response.getResult();
                    if (beans == null) return;
                    mDetailProductBean = beans.getPageDetailModel();
                    mRelatedProducts = beans.getRelatedProducts();
                    mProductPointers = beans.getProductPointers();
                    bindData(mDetailProductBean, mProductPointers);
                } else {
                    launchException(response.getException().getMessage(), response.getException());
                }
            }
        });
        backgroundTask.execute();
    }

    void bindData(final BeanProductDetail model, ArrayList<ProductPointer> mProductPointers) {
        if (model == null)
            return;

        if (mActivity instanceof BaseActivity) {
            if (!((BaseActivity) mActivity).IsSecondRowVisible())
                ((BaseActivity) mActivity).setToolBarTitle(model.getName());
        }
        if (mRelatedProducts != null && mRelatedProducts.size() > 0) {
            updateRelatedProducts();
        }

        updateShopTheLook(mDetailProductBean.getAssociatedProducts());


        mProductPrice = model.getProductPrice().getPrice();
        mProductName = model.getName();
        if (model.getPictureModels() != null && model.getPictureModels().size() > 0) {
            productImages.clear();
            productImages.addAll(model.getPictureModels());
            setUpProductImagesViewPager(model.getPictureModels(), mProductPointers);
            AddThumbs();
        }
        updateAttrs(model.getProductAttributes(), model);


        if (model.getProductType().equals(AppConstant.GROUPED_PRODUCT_TYPE)) {
            txtAddToCart.setText(R.string.shopTheLook);
            showOrCloseRelatedProductsDropdown(null, mFooterLayout, 0);
            mGroupedProduct = true;
            if (!mVendor.getMobileAppSetting().getShopTheLookEnabled()) {
                txtAddToCart.setEnabled(false);
                txtAddToCart.setBackgroundColor(Color.DKGRAY);
            }
            mAddtoWishlistTextView.setVisibility(View.GONE);


        } else {
            mAddtoWishlistTextView.setVisibility(View.VISIBLE);
        }

        if (model.getProductPrice().getCallForPrice()) {
            txtAddToCart.setText(R.string.call_for_price);
            txtAddToCart.setAllCaps(true);
            txtAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCallImageView.performClick();
                }
            });

        }

        if (model.getDisplayBackInStockSubscription()) {
            txtAddToCart.setText(R.string.back_in_stock);
            txtAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SubscribePopup(mProductId);
                }
            });
        }

        if (model.getGiftCard().getIsGiftCard()) {
            txtAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    View bottomSheet = getLayoutInflater().inflate(R.layout.gift_card, null);
                    dialog.setContentView(bottomSheet);
                    dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    InitBottomSheetGiftCart();
                    dialog.show();


                    bottomSheet.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    bottomSheet.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            validateGiftCart(dialog);
                        }
                    });
                }
            });

        }

        ///price
        if (model.getProductPrice() != null && model.getProductPrice().getPriceWithDiscount() != "0") {
            if (model.getProductPrice().getCallForPrice())
                lblPrice.setText(R.string.call_for_price);
            else
                lblPrice.setText(model.getProductPrice().getPrice());
            lbl_price_strike_through.setVisibility(View.VISIBLE);
            if (model.getProductPrice().getOldPrice() != null && !model.getProductPrice().getOldPrice().equals("null")) {
                lbl_price_strike_through.setText(model.getProductPrice().getOldPrice());
            } else if (model.getProductPrice().getPriceWithDiscount() != null && !model.getProductPrice().getPriceWithDiscount().equals("null")) {
                lbl_price_strike_through.setText(model.getProductPrice().getPrice());
                lblPrice.setText(model.getProductPrice().getPriceWithDiscount());
            } else {
                lbl_price_strike_through.setVisibility(View.GONE);
            }


            lbl_price_strike_through.setTextColor(Color.GRAY);
            lbl_price_strike_through.setPaintFlags(lbl_price_strike_through.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        } else {
            lblPrice.setText(model.getProductPrice().getPrice());
        }

        if (model.getProductReviewOverview() != null) {
            mRatingStar.setText(String.valueOf(model.getProductReviewOverview().getRatingSum()));
            mRatingTotal.setText(String.valueOf(model.getProductReviewOverview().getTotalReviews()));
        }

        expendableData.add(new ExpendableModel("Description", model.getShortDescription(), true));
        expendableData.add(new ExpendableModel("Component", model.getFullDescription(), true));
        expendableData.add(new ExpendableModel("SKU", model.getSku(), true));
//        expendableData.add(new ExpendableModel("Dispatch Time", model.getDeliveryDate(), false));

        for (ProductSpecification productSpecification : model.getProductSpecifications()) {
            if (productSpecification.getSpecificationAttributeName().toLowerCase().equals("size guide")) {
                mSizeGuideHtml = productSpecification.getValueRaw();
                mShowSizeGuide = true;
                continue;
            }
            expendableData.add(new ExpendableModel(productSpecification.getSpecificationAttributeName(), productSpecification.getValueRaw(), false));
        }

        expendableRecyclerAdapter.notifyDataSetChanged();

    }

    public void ShowBottomSheetShopTheLook(final Integer taggedProductId) {
        if (!IsShopTheLookVisible()) {
            ObjectAnimator transAnimation = ObjectAnimator.ofFloat(shopTheLookLayout, "translationX", 0, -1 * shopTheLookLayout.getWidth());
            transAnimation.setDuration(500);//set duration
            transAnimation.start();
            ObjectAnimator transAnimation1 = ObjectAnimator.ofFloat(imageLayout, "translationX", 0, -1 * shopTheLookLayout.getWidth());
            transAnimation1.setDuration(500);//set duration
            transAnimation1.start();
            transAnimation1.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
//                    shopTheLookRecycler.getLayoutManager().scrollToPosition(findProductInShopTheLook(taggedProductId));
                    shopTheLookRecycler.smoothScrollToPosition(findProductInShopTheLook(taggedProductId));
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }

        shopTheLookRecycler.smoothScrollToPosition(findProductInShopTheLook(taggedProductId));

    }

    private void InitBottomSheetGiftCart() {
        recipientName = (EditText) dialog.findViewById(R.id.txt_recipient_name);
        recipientEmail = (EditText) dialog.findViewById(R.id.txt_recipient_email);
        fromName = (EditText) dialog.findViewById(R.id.txt_from_name);
        fromEmail = (EditText) dialog.findViewById(R.id.txt_your_email);
        fromMessage = (EditText) dialog.findViewById(R.id.txt_message);


        String mSignedInEmail = GoMobileApp.getStringPrefs(AppConstant.USER_EMAIL_PREFS_KEY);
        String mUsername = GoMobileApp.getStringPrefs(AppConstant.USER_NAME_PREFS_KEY);
        fromEmail.setText(mSignedInEmail);
        fromName.setText(mUsername);
    }

    private void validateGiftCart(BottomSheetDialog dialog) {
        String errorMessage = "";
        if (recipientName.getText().toString().isEmpty()) {
            errorMessage = "Please provide Recipient's name";
            GoMobileApp.showMsgDialog(getActivity(), "Error", errorMessage, null, null);
            return;
        } else if (recipientEmail.getText().toString().isEmpty()) {
            errorMessage = "Please provide Recipient's email";
            GoMobileApp.showMsgDialog(getActivity(), "Error", errorMessage, null, null);
            return;
        } else if (fromName.getText().toString().isEmpty()) {
            errorMessage = "Please provide Your name";
            GoMobileApp.showMsgDialog(getActivity(), "Error", errorMessage, null, null);
            return;
        } else if (fromEmail.getText().toString().isEmpty()) {
            errorMessage = "Please provide Your email";
            GoMobileApp.showMsgDialog(getActivity(), "Error", errorMessage, null, null);
            return;
        } else if (fromMessage.getText().toString().isEmpty()) {
            errorMessage = "Please provide Message";
            GoMobileApp.showMsgDialog(getActivity(), "Error", errorMessage, null, null);
            return;
        }
        addGiftCardToCart(recipientName.getText().toString(), recipientEmail.getText().toString(),
                fromName.getText().toString(), fromEmail.getText().toString(), fromMessage.getText().toString(), dialog);
    }

    private void addGiftCardToCart(String recipientName, String recipientEmail, String fromName, String fromEmail, String fromMessage, final Dialog dialog) {
        JSONObject attributeArray = new JSONObject();
        try {

            String attrPrefix = "giftcard_" + mProductId + ".";
            attributeArray.put(attrPrefix + "SenderName", fromName);
            attributeArray.put(attrPrefix + "SenderEmail", fromEmail);
            attributeArray.put(attrPrefix + "Message", fromMessage);
            attributeArray.put(attrPrefix + "RecipientName", recipientName);
            attributeArray.put(attrPrefix + "RecipientEmail", recipientEmail);
        } catch (Exception e) {
            e.printStackTrace();
        }
        AddToCartAsyncTask backgroundTask = new AddToCartAsyncTask(mProductId, 1, 1,
                mProductPrice, attributeArray);
        backgroundTask.execute();
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    txtAddToCart.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(getActivity(), ShopCartActivity.class);
                            i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                            getActivity().startActivity(i);
                        }
                    });
                    dialog.dismiss();
                    GoMobileApp.Toast("Added to Cart");
                    if (mActivity instanceof StoreActivity)
                        ((StoreActivity) mActivity).refreshShopCartBadge();
                    else
                        ((RelatedProductsActivity) mActivity).showMiniShopCart(mProductId);
                    resetAllValues();
                } else {
                    launchException(response.getException().getMessage(), response.getException(), true);
                    resetAllValues();
                }
            }
        });
    }

    void setUpProductImagesViewPager(List<DefaultPictureModel> images, ArrayList<ProductPointer> mProductPointers) {

        ProductShowcaseAdapter adapter = new ProductShowcaseAdapter(getContext(),
                images,
                mProductPointers,
                mDetailProductBean.getName(),
                mVendor,
                this);
        imagesViewPager.setAdapter(adapter);
        createIndicators(0);

        imagesViewPager.setOnPageChangeListener(this);
    }

    private void createIndicators(int selected) {
        indicatorLayout.removeAllViews();
        List<DefaultPictureModel> list = mDetailProductBean.getPictureModels();

        boolean mhasproduct = mRelatedProducts != null && mRelatedProducts.size() > 0;

        int j = list.size();

        if (mhasproduct) j++;

        for (int i = 0; i < j; i++) {
            ImageView imageView = new ImageView(getActivity());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            imageView.setLayoutParams(layoutParams);
            imageView.setPadding(0, 0, 0, 10);

            if (i == selected) {
                if (selected != list.size())
                    imageView.setImageResource(R.drawable.selected_dot_viewpager);
                else
                    imageView.setImageResource(R.drawable.selected_square_viewpager);
            } else if (i == list.size())
                imageView.setImageResource(R.drawable.square_viewpager);
            else
                imageView.setImageResource(R.drawable.dot_viewpager);


            indicatorLayout.addView(imageView);
        }
    }


    @Override
    public void onDestroyView() {
        if (!backgroundTask.isCancelled())
            backgroundTask.cancel(true);

        super.onDestroyView();
    }

    private void showOrCloseRelatedProductsDropdown(final View showView, final View hideView, final Integer taggedProductId) {
        final Animation bottomTopAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.bottom_to_top);
        bottomTopAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (taggedProductId > 0)
                    shopTheLookViewPager.setCurrentItem(findProductInShopTheLook(taggedProductId), true);
            }

            @Override
            public void onAnimationStart(Animation animation) {
                if (showView != null)
                    showView.setVisibility(View.VISIBLE);
            }
        });

        Animation topToBottomAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.top_to_bottom);
        topToBottomAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (hideView != null)
                    hideView.setVisibility(View.GONE);

                if (showView != null)
                    showView.startAnimation(bottomTopAnimation);
            }
        });

        if (hideView != null)
            hideView.startAnimation(topToBottomAnimation);
        else if (showView != null)
            showView.startAnimation(bottomTopAnimation);

    }

    /**
     * This method will be invoked when the current page is scrolled, either as part
     * of a programmatically initiated smooth scroll or a user initiated touch scroll.
     *
     * @param position             Position index of the first page currently being displayed.
     *                             Page position+1 will be visible if positionOffset is nonzero.
     * @param positionOffset       AttributeValue from [0, 1) indicating the offset from the page at position.
     * @param positionOffsetPixels AttributeValue in pixels indicating the offset from position.
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        if (checkDirection) {
            int lastIdx = imagesViewPager.getAdapter().getCount() - 1;
            if (mRelatedProducts != null && mRelatedProducts.size() > 0) {

                if (lastPageChange && position == lastIdx) {
                    lastPageChange = false;
                    createIndicators(mDetailProductBean.getPictureModels().size());
                    HideInfoAndAttributes();

                    if (mRelatedProductVisibleLayout.getVisibility() == View.VISIBLE)
                        if (mGroupedProduct)
                            showOrCloseRelatedProductsDropdown(null, mRelatedProductVisibleLayout, 0);
                        else
                            showOrCloseRelatedProductsDropdown(mFooterLayout, mRelatedProductVisibleLayout, 0);
                    else {
                        if (mGroupedProduct)
                            showOrCloseRelatedProductsDropdown(mRelatedProductVisibleLayout, null, 0);
                        else
                            showOrCloseRelatedProductsDropdown(mRelatedProductVisibleLayout, mFooterLayout, 0);
                    }

                } else {
                    if (mRelatedProductVisibleLayout.getVisibility() == View.VISIBLE)
                        if (mGroupedProduct)
                            showOrCloseRelatedProductsDropdown(null, mRelatedProductVisibleLayout, 0);
                        else
                            showOrCloseRelatedProductsDropdown(mFooterLayout, mRelatedProductVisibleLayout, 0);

                }
                checkDirection = false;
            }
        }

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) showMenuContent();
        else {
            if (viewList.get(0).getVisibility() == View.VISIBLE)
                hideMenuContent();
        }
        createIndicators(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        int lastIdx = imagesViewPager.getAdapter().getCount() - 1;

        int curItem = imagesViewPager.getCurrentItem();
        lastPageChange = curItem == lastIdx && state == 1;

        if (!scrollStarted && state == ViewPager.SCROLL_STATE_DRAGGING) {
            scrollStarted = true;
            checkDirection = true;
        } else {
            scrollStarted = false;
        }

    }

    public void HideShopTheLook() {

        if (-1 * imageLayout.getX() == shopTheLookLayout.getWidth()) {
            ObjectAnimator transAnimation = ObjectAnimator.ofFloat(shopTheLookLayout, "translationX", -1 * shopTheLookLayout.getWidth(), 0);
            transAnimation.setDuration(500);//set duration
            transAnimation.start();


            ObjectAnimator transAnimation1 = ObjectAnimator.ofFloat(imageLayout, "translationX", imageLayout.getX(), 0);
            transAnimation1.setDuration(500);//set duration
            transAnimation1.start();
        }


        if (mRelatedProductVisibleLayout.getVisibility() == View.VISIBLE)
            showOrCloseRelatedProductsDropdown(null, mRelatedProductVisibleLayout, 0);
    }

    public boolean IsShopTheLookVisible() {
        return -1 * imageLayout.getX() == shopTheLookLayout.getWidth();
    }

    public void HideInfoAndAttributes() {
        if (mRelatedProductVisibleLayout.getVisibility() == View.VISIBLE)
            if (mGroupedProduct)
                showOrCloseRelatedProductsDropdown(null, mRelatedProductVisibleLayout, 0);
            else
                showOrCloseRelatedProductsDropdown(mFooterLayout, mRelatedProductVisibleLayout, 0);

        mLineLayout.setVisibility(View.GONE);
        resetAllValues();
    }

    void SubscribePopup(final int ProductId) {
        final JSONObject params = new JSONObject();
        try {
            params.put("ProductId", ProductId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        notifyWhenAvailable = new NotifyWhenAvailable(params);
        notifyWhenAvailable.addOnResultListener(new AsyncTaskResultListener<BackInStock>() {
            @Override
            public void response(AsyncTaskResult<BackInStock> response) {
                if (!response.hasException()) {
                    if (response.getResult().getAlreadySubscribed()) {
                        GoMobileApp.Toast("Already Subscribed");
                    } else {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity(), R.style.AppTheme_Dialog);
                        alertDialogBuilder.setMessage("Notify via email when product back in stock").setPositiveButton(R.string.Notify_Me, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (GoMobileApp.IsUserLogin()) {
                                    new BackInStockPost(params).execute();
                                    GoMobileApp.Toast("Added to Notification");
                                } else {
                                    Intent login = new Intent(getActivity(), LoginSignUpActivity.class);
                                    login.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                                    login.putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_PRODUCT);
                                    login.putExtra(AppConstant.INTENT_PRODUCT_ID, ProductId);
                                    startActivity(login);
                                }
                            }
                        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }


                        }).show();
                    }

                }
            }
        });
        notifyWhenAvailable.execute();
    }

    void launchException(String message, Throwable exception) {
        AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException((AppCompatActivity) getActivity(), message, exception);
        else
            AppHelper.showNetworkError((AppCompatActivity) getActivity(), message);
    }

    public int findProductInShopTheLook(Integer taggedProductId) {
        int i = 0;
        for (BeanProductDetail product : mShopTheLookProducts) {
            if (product.getId().equals(taggedProductId)) {
                return i;
            }
            i++;
        }
        return 0;
    }

    public void onAttrbutemarked(AttributeValue attributeValue, ProductAttribute productAttribute) {
        mSelectedAttributeValue = attributeValue;
        mSelectedProductAttribute = productAttribute;
        mNext.performClick();
    }

    public void AddThumbs() {
        double size = productImages.size();
        for (int i = 0; i < size; i++) {
            View viewMenu = getActivity().getLayoutInflater().inflate(R.layout.image_thumbnail, null);
            final int finalI = i;
            viewMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagesViewPager.setCurrentItem(finalI, true);
                }
            });
            GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(productImages.get(i).getFullSizeImageUrl()), ((ImageView) viewMenu.findViewById(R.id.menu_item_image)), null);
            viewList.add(viewMenu);
            thumbImages.addView(viewMenu);
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }

                }
            }, (long) delay);
        }

    }

    public void showMenuContent() {
        double size = productImages.size();
        for (int i = 0; i < size; i++) {
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateView((int) position);
                    }

                }
            }, (long) delay);
        }
    }

    private void hideMenuContent() {
        double size = viewList.size();
        for (int i = viewList.size(); i >= 0; i--) {
            final double position = i;
            final double delay = 3 * ANIMATION_DURATION * (position / size);
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if (position < viewList.size()) {
                        animateHideView((int) position);
                    }
                }
            }, (long) delay);
        }

    }

    private void animateView(final int position) {
        final View view = viewList.get(position);
        view.setVisibility(View.VISIBLE);
        FlipAnimation rotation =
                new FlipAnimation(90, 0, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }

    private void animateHideView(final int position) {
        final View view = viewList.get(position);
        FlipAnimation rotation =
                new FlipAnimation(0, 90, 0.0f, view.getHeight() / 2.0f);
        rotation.setDuration(ANIMATION_DURATION);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());
        rotation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

                view.clearAnimation();
                view.setVisibility(View.INVISIBLE);
                if (position == viewList.size() - 1) {
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        view.startAnimation(rotation);
    }
}