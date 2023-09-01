package com.growonline.gomobishop.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.model.Notification;

import java.util.List;

/**
 * Created by asifrizvi on 4/11/2019.
 */

public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.MyViewHolder> {
    private List<Notification> offerList;


    public AdapterNotification(List<Notification> offerList) {
        this.offerList = offerList;
    }

    @Override
    public AdapterNotification.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_row, parent, false);
        return new AdapterNotification.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterNotification.MyViewHolder holder, int position) {
        Notification notification = offerList.get(position);
        holder.notificationTitle.setText(notification.getTitle());
        holder.notificationMessage.setText(notification.getMessage());
        if (notification.getDirectIconLink() != null)
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(notification.getDirectIconLink()), holder.vendorLogo, null);
        else
            holder.vendorLogo.setVisibility(View.GONE);

        if (notification.getDirectImageLink() != null)
            GoMobileApp.getmCacheManager().loadImageRectangle(Uri.parse(notification.getDirectImageLink()), holder.notificationImage, null);
        else
            holder.notificationImage.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        //        private AspectRatioImageView aspectRatioImageView;
        private LinearLayout offerListMain;
        private FontTextView notificationTitle;
        private ImageView vendorLogo;
        private FontTextView notificationMessage;
        private AspectRatioImageView notificationImage;


        public MyViewHolder(View view) {
            super(view);
            offerListMain = (LinearLayout) view.findViewById(R.id.offer_list_main);
            notificationTitle = (FontTextView) view.findViewById(R.id.notification_title);
            vendorLogo = (ImageView) view.findViewById(R.id.notification_vendor_logo);
            notificationMessage = (FontTextView) view.findViewById(R.id.notification_message);
            notificationImage = (AspectRatioImageView) view.findViewById(R.id.notification_image);
        }
    }

}