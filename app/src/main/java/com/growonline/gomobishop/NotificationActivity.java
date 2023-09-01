package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.AdapterNotification;
import com.growonline.gomobishop.asynctask.GetNotificationsAsyncTask;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.Notification;
import com.growonline.gomobishop.model.Offer;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

public class NotificationActivity extends BaseActivity {

    private RecyclerView notificationRecyclerView;
    private AdapterNotification adapterNotification;
    private List<Notification> offerList = new ArrayList<>();
    private LinearLayout loadingLayout;
    private FrameLayout noItemFound;
    private GetNotificationsAsyncTask backgroundTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        setToolBarTitle(getString(R.string.title_notification));

        notificationRecyclerView = (RecyclerView) findViewById(R.id.notification_RecyclerView);
        noItemFound = (FrameLayout) findViewById(R.id.no_item_found);
        loadingLayout = (LinearLayout) findViewById(R.id.review_loading);
        adapterNotification = new AdapterNotification(offerList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        notificationRecyclerView.setLayoutManager(mLayoutManager);
        notificationRecyclerView.setAdapter(adapterNotification);
        notificationRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), notificationRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Notification notification = offerList.get(position);
                Intent i;
                Offer offer;
                switch (notification.getFcmActionType()) {
                    case AppConstant.CLOSE_APP:
                        break;
                    case AppConstant.OPEN_APP:
                        break;
                    case AppConstant.OPEN_ANY_URL:
                        break;
                    case AppConstant.OPEN_MARKET:
                        break;
                    case AppConstant.OPEN_VENDOR_APP:
                        i = new Intent();
                        offer = new Offer();
                        offer.setVendorId(notification.getVendorId());
                        offer.setProductId(0);
                        offer.setVendorName(notification.getVendorName());
                        offer.setImage(notification.getVendorImage());
                        i.putExtra(AppConstant.INTENT_NOTIFICATION, offer);
                        setResult(AppConstant.CODE_NOTIFICATION_CENTER, i);
                        finish();
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        break;
                    case AppConstant.OPEN_PRODUCT:
                        i = new Intent();
                        offer = new Offer();
                        offer.setVendorId(notification.getVendorId());
                        offer.setProductId(Integer.valueOf(notification.getExtra()));
                        offer.setVendorName(notification.getVendorName());
                        offer.setImage(notification.getVendorImage());
                        i.putExtra(AppConstant.INTENT_NOTIFICATION, offer);
                        setResult(AppConstant.CODE_NOTIFICATION_CENTER, i);
                        finish();
                        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
                        break;
                }
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        new GetNotificationsAsyncTask(NotificationActivity.this, false).execute();
    }


    public void LoadOffers(List<Notification> offerList) {
        loadingLayout.setVisibility(View.GONE);
        if (offerList == null) {
            noItemFound.setVisibility(View.VISIBLE);
            return;
        }
        this.offerList.clear();
        this.offerList.addAll(offerList);
        if (offerList.size() == 0)
            noItemFound.setVisibility(View.VISIBLE);
        else
            noItemFound.setVisibility(View.GONE);
        adapterNotification.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);
        super.onDestroy();
    }
}
