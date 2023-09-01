package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.StepsView;
import com.growonline.gomobishop.model.SimpleOrderModel;

import java.util.List;


public class AdapterOrderList extends RecyclerView.Adapter<AdapterOrderList.MyViewHolder> {
    private final String[] labels = {"Pending", "Processing", "Complete", "Cancelled"};
    private BaseActivity mActivity;
    private List<SimpleOrderModel> mOrderListModel;
    private LayoutInflater mLayoutInflator;




    public AdapterOrderList(BaseActivity activity, List<SimpleOrderModel> order) {
        this.mActivity = activity;
        this.mOrderListModel = order;
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AdapterOrderList.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_order_list, parent, false);
        return new AdapterOrderList.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterOrderList.MyViewHolder holder, int position) {
        SimpleOrderModel order = mOrderListModel.get(position);
        holder.orderID.setText(order.getId().toString());
        holder.orderDate.setText(GoMobileApp.ConvertUtcToDate(order.getCreatedOn()));
        holder.orderStatus.setText(order.getOrderStatus());
        holder.orderTotal.setText(order.getOrderTotal());
        GoMobileApp.getmCacheManager().loadImageRectangle(Uri.parse(order.getImage()), holder.aspectRatioImageView, null);

        holder.mStepsView.setCompletedPosition(findValue(order.getOrderStatus()))
                .setLabels(labels)
                .setBarColorIndicator(
                        mActivity.getResources().getColor(R.color.colorPrimaryDark))
                .setProgressColorIndicator(mActivity.getResources().getColor(R.color.colorPrimary))
                .setLabelColorIndicator(mActivity.getResources().getColor(R.color.colorPrimary))
                .drawView();
    }

    @Override
    public int getItemCount() {
        return mOrderListModel.size();
    }

    public int findValue(String name) {
        for (int i = 0; i < labels.length; i++) {
            if (labels[i].equals(name)) {
                return i;
            }
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AspectRatioImageView aspectRatioImageView;
        private TextView orderID;
        private TextView orderDate;
        private TextView orderStatus;
        private TextView orderTotal;
        private TextView orderDetail;
        private StepsView mStepsView;


        public MyViewHolder(View view) {
            super(view);
            aspectRatioImageView = (AspectRatioImageView) view.findViewById(R.id.aspectRatioImageView);
            orderID = (TextView) view.findViewById(R.id.order_id);
            orderDate = (TextView) view.findViewById(R.id.order_date);
            orderStatus = (TextView) view.findViewById(R.id.order_status);
            orderTotal = (TextView) view.findViewById(R.id.order_total);
            orderDetail = (TextView) view.findViewById(R.id.order_detail);
            mStepsView = (StepsView) view.findViewById(R.id.stepsView);

        }
    }

}
