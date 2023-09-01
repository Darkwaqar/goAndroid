package com.growonline.gomobishop;

/**
 * Created by asifrizvi on 1/22/2019.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.growonline.gomobishop.asynctask.AddDeviceToServer;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.NotificationUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private AddDeviceToServer backgroundTask;
    private NotificationUtils notificationUtils;


    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.e(TAG, "From: " + remoteMessage.getData().toString());
        Log.e(TAG, "From: " + remoteMessage.getNotification().getBody());
        Log.e(TAG, "From: " + remoteMessage.getNotification().getTitle());

        if (remoteMessage == null)
            return;

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Notification Body: " + remoteMessage.getNotification().getBody());
            handleNotification(remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());

            try {
                handleDataMessage(remoteMessage);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }
    // [END receive_message
    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(token);
        addReferrerToSharedPreferences(getApplicationContext(), token);

    }
    // [END on_new_token]


    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        JSONObject params = new JSONObject();
        try {
            params.put("DeviceId", token);
            params.put("Package", BuildConfig.APPLICATION_ID);
            params.put("DeviceOS", "1");
            params.put("Brand", Build.BRAND);
            params.put("OSVersion", Build.VERSION.SDK);
            params.put("Carrier", Build.MANUFACTURER);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        backgroundTask = new AddDeviceToServer(params);
        backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    Log.d(TAG, "Device Add");
                } else {
                    Log.d(TAG, "Device failed");
                }
            }
        });
        backgroundTask.execute();

    }

//    /**
//     * Create and show a simple notification containing the received FCM message.
//     *
//     * @param messageBody FCM message body received.
//     */
//    private void sendNotification(String title,String messageBody) {
//        Intent intent = new Intent(this, MainActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
//                PendingIntent.FLAG_ONE_SHOT);
//
//        String channelId = getString(R.string.default_notification_channel_id);
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder notificationBuilder =
//                new NotificationCompat.Builder(this, channelId)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle(title)
//                        .setContentText(messageBody)
//                        .setStyle(new Notification.BigPictureStyle()
//                                .bigPicture(aBigBitmap))
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        // Since android Oreo notification channel is needed.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(channelId,
//                    "Channel human readable title",
//                    NotificationManager.IMPORTANCE_DEFAULT);
//            notificationManager.createNotificationChannel(channel);
//        }
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
//    }

    private void handleNotification(String message) {
        // app is in foreground, broadcast the push message
        Intent pushNotification = new Intent(AppConstant.PUSH_NOTIFICATION);
        pushNotification.putExtra("message", message);
        LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);

        // play notification sound
        NotificationUtils notificationUtils = new NotificationUtils(getApplicationContext());
        notificationUtils.playNotificationSound();

    }


    private void handleDataMessage(RemoteMessage remoteMessage) {

        try {
            JSONObject json = new JSONObject(remoteMessage.getData());
            int FcmActionType = json.getInt("fcmtype");
            int fcmApplicationType = json.getInt("fcmApplicationType");
            Intent resultIntent;
            String title = remoteMessage.getNotification().getTitle();
            String message = remoteMessage.getNotification().getBody();
            String icon = remoteMessage.getNotification().getIcon();
            if (FcmActionType == AppConstant.SIMPLE_NOTIFICATION) {
                resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
                resultIntent.putExtra("message", message);
                showNotificationMessage(getApplicationContext(), title, message, icon, resultIntent, null);
            } else if (FcmActionType == AppConstant.IMAGE_NOTIFICATION) {
                Integer action = Integer.valueOf(remoteMessage.getNotification().getClickAction());
                String imageUrl = "";
                String button_action = "";
                switch (action) {
                    case AppConstant.CLOSE_APP:
                        break;
                    case AppConstant.OPEN_APP:
                        break;
                    case AppConstant.OPEN_ANY_URL:
                        break;
                    case AppConstant.OPEN_VENDOR_APP:
                        imageUrl = json.getString("image");
                        button_action = json.getString("button_action");
                        resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
                        resultIntent.setData(Uri.parse(button_action));
                        showNotificationMessage(getApplicationContext(), title, message, icon, resultIntent, imageUrl);
                        break;
                    case AppConstant.OPEN_PRODUCT:
                        imageUrl = json.getString("image");
                        button_action = json.getString("button_action");
                        resultIntent = new Intent(getApplicationContext(), SplashScreen.class);
                        resultIntent.setData(Uri.parse(button_action));
                        showNotificationMessage(getApplicationContext(), title, message, icon, resultIntent, imageUrl);
                        break;
                }


            } else if (FcmActionType == AppConstant.WEB_NOTIFICATION) {

            } else if (FcmActionType == AppConstant.NEWS_NOTIFICATION) {

            }
        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }

    /**
     * Showing notification with text only
     */
    private void showNotificationMessage(Context context, String title, String message, String icon, Intent intent, String imageUrl) {
        notificationUtils = new NotificationUtils(context);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationUtils.showNotificationMessage(title, message, icon, intent, imageUrl);
    }

    void addReferrerToSharedPreferences(Context context, String Token) {

        if (Token != null) {
            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            editor.putString(AppConstant.KEY_TOKEN_ID, Token);
            editor.commit();
        }
    }

}