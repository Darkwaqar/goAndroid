package com.growonline.gomobishop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RelatedProductsActivity;
import com.growonline.gomobishop.adapter.RatingDetailAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetPendingReviews;
import com.growonline.gomobishop.asynctask.GetProductReviews;
import com.growonline.gomobishop.asynctask.GetVendorReviews;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.PendingReview;
import com.growonline.gomobishop.model.ProductReview;
import com.growonline.gomobishop.model.Review;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.model.VendorReview;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {

    private static final String ARG_TYPE = "Type";

    private int mType;
    private Vendor mVendor;
    private RecyclerView reviewRecyclerView;
    private GetProductReviews getProductReviews;
    private GetVendorReviews getVendorReviews;
    private GetPendingReviews getPendingReviews;
    private View networkError;

    private RatingDetailAdapter ratingDetailAdapter;

    private List<Review> reviews = new ArrayList<>();
    private FrameLayout noReviewLayout;
    private TextView noReviewText;

    private List<ProductReview> productReviews = new ArrayList<>();
    private List<VendorReview> vendorReviews = new ArrayList<>();
    private List<PendingReview> pendingReviews = new ArrayList<>();


    public ReviewFragment() {
        // Required empty public constructor
    }

    public static ReviewFragment newInstance(int Type, Vendor mVendor) {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, Type);
        args.putParcelable(AppConstant.INTENT_VENDOR, mVendor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mType = getArguments().getInt(ARG_TYPE, 0);
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        Init(view);
        return view;
    }

    private void Init(View view) {
        networkError = view.findViewById(R.id.network_error);
        Button Reload = (Button) view.findViewById(R.id.reload);
        Reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                networkError.setVisibility(View.GONE);
                LoadData();
            }
        });
        noReviewLayout = (FrameLayout) view.findViewById(R.id.no_review_layout);
        noReviewText = (TextView) view.findViewById(R.id.no_review_text);
        reviewRecyclerView = (RecyclerView) view.findViewById(R.id.review_recyclerView);
        ratingDetailAdapter = new RatingDetailAdapter(getActivity(), productReviews, vendorReviews, pendingReviews, mType);
        RecyclerView.LayoutManager mLayoutManager1 =
                new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        reviewRecyclerView.setItemAnimator(new DefaultItemAnimator());
        reviewRecyclerView.setLayoutManager(mLayoutManager1);
        reviewRecyclerView.setAdapter(ratingDetailAdapter);


        reviewRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), reviewRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent i = new Intent(getActivity(), RelatedProductsActivity.class);
                if (mType == AppConstant.TYPE_PRODUCT) {
                    i.putExtra(AppConstant.INTENT_PRODUCT_ID, productReviews.get(position).getProductId());
                } else if (mType == AppConstant.TYPE_VENDOR) {
                    i.putExtra(AppConstant.INTENT_PRODUCT_ID, vendorReviews.get(position).getProductId());
                } else if (mType == AppConstant.TYPE_PENDING) {
                    i.putExtra(AppConstant.INTENT_PRODUCT_ID, pendingReviews.get(position).getProductId());
                }
                i.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoadData();
    }

    private void LoadData() {
        if (mType == AppConstant.TYPE_PRODUCT) {
            getProductReviews = new GetProductReviews();
            getProductReviews.addOnResultListener(new AsyncTaskResultListener<List<ProductReview>>() {
                @Override
                public void response(AsyncTaskResult<List<ProductReview>> response) {
                    if (!response.hasException()) {
                        productReviews.clear();
                        productReviews.addAll(response.getResult());
                        if (productReviews.size() == 0) {
                            noReviewLayout.setVisibility(View.VISIBLE);
                            noReviewText.setText(R.string.no_product_review);
                        }
                        ratingDetailAdapter.notifyDataSetChanged();
                    } else {
                        networkError.setVisibility(View.VISIBLE);
                    }
                }
            });
            getProductReviews.execute();
        } else if (mType == AppConstant.TYPE_VENDOR) {
            getVendorReviews = new GetVendorReviews();
            getVendorReviews.addOnResultListener(new AsyncTaskResultListener<List<VendorReview>>() {
                @Override
                public void response(AsyncTaskResult<List<VendorReview>> response) {
                    if (!response.hasException()) {
                        vendorReviews.clear();
                        vendorReviews.addAll(response.getResult());
                        if (vendorReviews.size() == 0) {
                            noReviewLayout.setVisibility(View.VISIBLE);
                            noReviewText.setText(R.string.no_vendor_review);
                        }
                        ratingDetailAdapter.notifyDataSetChanged();
                    } else {
                        networkError.setVisibility(View.VISIBLE);
                    }
                }
            });
            getVendorReviews.execute();
        } else {
            getPendingReviews = new GetPendingReviews();
            getPendingReviews.addOnResultListener(new AsyncTaskResultListener<List<List<PendingReview>>>() {
                @Override
                public void response(AsyncTaskResult<List<List<PendingReview>>> response) {
                    if (!response.hasException()) {
                        pendingReviews.clear();
                        for (List<PendingReview> pendingReview : response.getResult()) {
                            pendingReviews.addAll(pendingReview);
                        }
//                        pendingReviews.addAll((List<PendingReview>) response.getResult().get(0));
                        if (pendingReviews.size() == 0) {
                            noReviewLayout.setVisibility(View.VISIBLE);
                            noReviewText.setText(R.string.no_product_found);
                        }
                        ratingDetailAdapter.notifyDataSetChanged();
                    } else {
                        networkError.setVisibility(View.VISIBLE);
                    }
                }
            });
            getPendingReviews.execute();
        }
    }
}
