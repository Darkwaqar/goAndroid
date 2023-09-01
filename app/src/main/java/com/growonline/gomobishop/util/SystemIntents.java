package com.growonline.gomobishop.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.Html;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.network.NetworkUtils;

import java.util.List;

public class SystemIntents {
    public static final int SYSTEM_INTENT_GALLERY_REQUEST_CODE = 1122;

    public static void pickImageIntent(Activity fragment) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        fragment.startActivityForResult(galleryIntent, SYSTEM_INTENT_GALLERY_REQUEST_CODE);
    }


    /**
     * @param mActivity context
     * @param snippet   phone number on which call have to make
     * @throws java.lang.SecuirtyException: needs the permission in the manifest : <uses-permission android:name="android.permission.CALL_PHONE"/>
     *                                      <p>
     *                                      public static void makeCall(Activity mActivity, String snippet)
     *                                      {
     *                                      Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + snippet));
     *                                      mActivity.startActivity(intent);
     *                                      }
     */

    public static void settingLocations(Activity activity, Integer requestCode) {
        Intent gpsOptionsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        if (requestCode != null)
            activity.startActivityForResult(gpsOptionsIntent, requestCode);
        else
            activity.startActivity(gpsOptionsIntent);
    }

    public static void whatsAppSharing(Activity activity, String text) {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.setType("text/plain");

        System.out.println(" == text == " + text);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);

        /** Event for post share on whatsapp */
        PackageManager pm = activity.getApplicationContext().getPackageManager();
        final List<ResolveInfo> matches = pm.queryIntentActivities(sendIntent, 0);
        boolean temWhatsApp = false;
        String packageName = "com.whatsapp";
        for (final ResolveInfo info : matches) {
            if (info.activityInfo.packageName.startsWith(packageName)) {
                final ComponentName name = new ComponentName(info.activityInfo.applicationInfo.packageName, info.activityInfo.name);

                sendIntent.setPackage(info.activityInfo.packageName);
                sendIntent.addCategory(Intent.CATEGORY_LAUNCHER);
                sendIntent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NEW_TASK);
                sendIntent.setComponent(name);
                temWhatsApp = true;
                break;
            }
        }
        if (temWhatsApp) {
            activity.startActivity(sendIntent);
        } else {
            GoMobileApp.Toast("Your device doesn't have whatsApp.");
        }
    }

    public static void sendMsg(Activity activity, String text) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"));
        intent.putExtra("sms_body", text);
        activity.startActivity(intent);
    }

    public static void share(Activity activity, String txt, String imageurl, String contentType) {
        Intent intent = new Intent(Intent.ACTION_SEND);

        intent.setType(contentType);
        if (contentType.equalsIgnoreCase(NetworkUtils.TEXT_HTML)) {
            intent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(txt));
        } else {
            intent.putExtra(Intent.EXTRA_TEXT, txt);
        }
        activity.startActivity(Intent.createChooser(intent, "Share link using"));
    }

    public static void requestUrlIntent(Activity mActivity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        mActivity.startActivity(i);
    }

    public static void onlinePdfViewer(Activity mActivity, String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse("https://docs.google.com/gview?url=" + url + "&embedded=true"));
        mActivity.startActivity(i);
    }
}
