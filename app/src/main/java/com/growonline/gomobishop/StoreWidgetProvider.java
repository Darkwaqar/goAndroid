package com.growonline.gomobishop;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.google.gson.Gson;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.CornerTransformer;
import com.squareup.picasso.Picasso;


public class StoreWidgetProvider extends AppWidgetProvider {
    Vendor mVendor;

    public static void updateWidget(StoreWidgetConfigActivity context,
                                    AppWidgetManager appWidgetManager,
                                    int mAppWidgetId, Vendor mVendor) {
        RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_store);

        GoMobileApp.addToSharedPrefs(mAppWidgetId + "", new Gson().toJson(mVendor, Vendor.class));

        if (mVendor != null) {
            Picasso picasso = Picasso.with(context);

            picasso.load(mVendor.getLogoUrl())
                    .transform(new CornerTransformer(22, 1))
                    .into(updateViews, R.id.widget_imgview, new int[]{mAppWidgetId});

            updateViews.setTextViewText(R.id.WidgetLabel, mVendor.getName());

            Intent intent = new Intent(context, SplashScreen.class);
            intent.setData(Uri.parse("gfh://widget?id=" + mVendor.getId()));
            intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
            updateViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
        }
        appWidgetManager.updateAppWidget(mAppWidgetId, updateViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (int i = 0; i < appWidgetIds.length; i++) {
            int appWidgetId = appWidgetIds[i];
            RemoteViews updateViews = new RemoteViews(context.getPackageName(), R.layout.widget_store);

            String json = GoMobileApp.getmAppPreferences().getString(appWidgetId + "", null);

            if (json != null) {
                mVendor = new Gson().fromJson(json, Vendor.class);
                if (mVendor != null) {
                    Picasso picasso = Picasso.with(context);

                    picasso.load(mVendor.getLogoUrl())
                            .transform(new CornerTransformer(22, 1))
                            .into(updateViews, R.id.widget_imgview, new int[]{appWidgetId});

                    updateViews.setTextViewText(R.id.WidgetLabel, mVendor.getName());

                    Intent intent = new Intent(context, SplashScreen.class);
                    intent.setData(Uri.parse("gfh://widget?id=" + mVendor.getId()));
                    intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
                    updateViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
                }
            }
            appWidgetManager.updateAppWidget(appWidgetId, updateViews);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
