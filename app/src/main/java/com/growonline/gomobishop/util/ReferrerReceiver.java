package com.growonline.gomobishop.util;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.growonline.gomobishop.R;

import static android.content.Context.NOTIFICATION_SERVICE;


public class ReferrerReceiver extends BroadcastReceiver {

    private final String referrer_key = "referrer";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null && intent.getExtras() != null) {

            String pair_separater = "_";
            String key_val_separater = ":";

            Bundle extras = intent.getExtras();
            if (extras != null && extras.containsKey(referrer_key)) {

                try {
                    int vendorId = 0, productId = 0;

                    String param = extras.getString(referrer_key).trim();
                    Log.i("receiver", "onReceive: vendor id  " + param);
                    if (param.contains(referrer_key))
                        param = param.replace(referrer_key + "=", "");

                    if (param.length() > 0) {

                        //more than one parameter
                        if (param.contains(pair_separater)) {

                            String[] splitParams = param.split(pair_separater);
                            for (String splitParam : splitParams) {
                                String[] splitKeyValue = splitParam.split(key_val_separater);

                                if (splitKeyValue[0].equalsIgnoreCase("sid")) {
                                    vendorId = Integer.parseInt(splitKeyValue[1]);
                                    Log.i("receiver", "onReceive: vendor id  " + vendorId);

                                } else if (splitKeyValue[0].equalsIgnoreCase("pid")) {

                                    productId = Integer.parseInt(splitKeyValue[1]);
                                    Log.i("receiver", "onReceive: product id " + productId);
                                }
                            }

                        } else {
                            String[] splitKeyValue = param.split(key_val_separater);
                            vendorId = Integer.parseInt(splitKeyValue[1]);
                            Log.i("receiver", "onReceive: vendor id  " + vendorId);
                        }

                        addReferrerToSharedPreferences(context, vendorId, productId);

                    }

                    showNotification(context, "Store loaded " + param);

                } catch (Exception ex) {
                    //in-case number format exception initial store will not download
                    showNotification(context, "Error installing" + ex.getMessage());
                }
            }
        }
    }


    void showNotification(Context context, String content) {
        NotificationCompat.Builder n = new NotificationCompat.Builder(context);
        n.setSmallIcon(R.mipmap.ic_launcher)
                .setContentText(context.getResources().getString(R.string.app_name))
                .setContentText(content);

        NotificationManager nm = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        //Intent i = new Intent(context,MainActivity.class);
        //PendingIntent ac = PendingIntent.getActivity(context, 0, i, 0);
        //n.setContentIntent(ac);
        nm.notify(12222, n.build());
    }

    void addReferrerToSharedPreferences(Context context, int vendorId, int productId) {

        if (vendorId > 0) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putInt(AppConstant.KEY_PLAY_STORE_VENDOR_ID, vendorId);
            if (productId > 0) {
                editor.putInt(AppConstant.KEY_PLAY_STORE_PRODUCT_ID, productId);
            }
            editor.commit();
        }
    }

}
