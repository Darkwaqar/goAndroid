package com.growonline.gomobishop.util;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.model.SpecificationAttribute;
import com.growonline.gomobishop.model.SpecificationAttributeOption;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Basit on 9/17/2016.
 */
public class AppHelper {

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);
    private static boolean isExceptionDialogShowing = false;

    public static void applyRobotoLightFont(TextView mTextView) {
        mTextView.setTypeface(Typeface.createFromAsset(mTextView.getContext().getAssets(), AppConstant.FONTS_DIR + "Roboto-Light.ttf"));
    }

    public static void applyRobotoRegularFont(TextView mTextView) {
        mTextView.setTypeface(Typeface.createFromAsset(mTextView.getContext().getAssets(), AppConstant.FONTS_DIR + "Roboto-Regular.ttf"));
    }

    public static void applyRobotoBoldFont(TextView mTextView) {
        mTextView.setTypeface(Typeface.createFromAsset(mTextView.getContext().getAssets(), AppConstant.FONTS_DIR + "Roboto-Bold.ttf"));
    }

    public static void applyPlayFairDisplayBoldFont(TextView mTextView) {
        mTextView.setTypeface(Typeface.createFromAsset(mTextView.getContext().getAssets(), AppConstant.FONTS_DIR + "PlayfairDisplay-Bold.ttf"));
    }

    public static void applyPlayFairDisplayRegularFont(TextView mTextView) {
        mTextView.setTypeface(Typeface.createFromAsset(mTextView.getContext().getAssets(), AppConstant.FONTS_DIR + "PlayfairDisplay-Regular.ttf"));
    }

    public static void applyCustomFont(TextView fontTextView, String fontType) {
        fontTextView.setTypeface(Typeface.createFromAsset(fontTextView.getContext().getAssets(), AppConstant.FONTS_DIR + fontType));
    }

    public static void LogEvent(String logstring) {
        if (AppConstant.isLogEnabled) {
            Log.e("Zuni:", logstring);
        }
    }



    /**
     * Converting dp to pixel
     */
    public static int dpToPx(int dp, Context context) {
        Resources r = context.getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }



    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {

            View listItem = listAdapter.getView(i, null, listView);
            Log.e("size:" + listAdapter.getCount(), i + listItem.toString());
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static void showMsgDialog(AppCompatActivity activity,
                                     String title, String msg, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        try {
            if (positiveButton == null) {
                positiveButton = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            }

            new AlertDialog.Builder(activity, R.style.AppTheme_Dialog).setMessage(msg).setCancelable(false)
                    .setPositiveButton(activity.getString(android.R.string.ok), positiveButton).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showException(final AppCompatActivity activity, final String message, final Throwable exception) {
        AlertDialog mErrorDialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
        builder.setCancelable(false);
        builder.setTitle("Alert!");
        builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                isExceptionDialogShowing = false;
            }
        });
        mErrorDialog = builder.create();
        mErrorDialog.show();
    }

    public static void showNetworkError(final AppCompatActivity activity, final String message) {

        if (activity == null) return;
        if (!isExceptionDialogShowing) {
            AlertDialog mErrorDialog = null;

            AlertDialog.Builder builder = new AlertDialog.Builder(activity, R.style.AppTheme_Dialog);
            builder.setCancelable(false);
            builder.setTitle("Network Error !");
            builder.setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    isExceptionDialogShowing = false;
                }
            });
            mErrorDialog = builder.create();
            mErrorDialog.show();
            isExceptionDialogShowing = true;
        }
    }

    public static void emailToDeveloper(AppCompatActivity activity, String message, Throwable exception) {

        String LINE_SEPARATOR = "\n";

        StringWriter stackTrace = new StringWriter();
        exception.printStackTrace(new PrintWriter(stackTrace));

        StringBuilder errorReport = new StringBuilder();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());
        errorReport.append(LINE_SEPARATOR).append(LINE_SEPARATOR);
        errorReport.append("************ MESSAGE ************\n\n");
        errorReport.append(message);

        errorReport.append("\n\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK_INT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "basit.ali@outlook.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CELEBRITY MOBILE APP ERROR REPORT");
        emailIntent.putExtra(Intent.EXTRA_TEXT, errorReport.toString());

        activity.startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    public static void toggleNavigation(DrawerLayout layout, int gravity) {
        layout.closeDrawers();
        if (!layout.isDrawerOpen(gravity))
            layout.openDrawer(gravity);
    }

    public static void setTextViewSelector(TextView textView, String pressesColor) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, new ColorDrawable(Color.parseColor(pressesColor)));
        textView.setBackgroundDrawable(stateListDrawable);
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }


    public static String[] splitStringOnFirst(String str, char c) {

        String[] temp;

        int idx = str.indexOf(c);

        if (idx >= 0) {

            String head = str.substring(0, idx);
            String tail = str.substring(idx + 1);
            temp = new String[]{head, tail};

        } else {
            temp = new String[]{str};
        }

        return temp;
    }

    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }

    public static void showMsgDialog(Activity activity,
                                     String title, String msg, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        try {
            if (positiveButton == null) {
                positiveButton = new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
            }

            new AlertDialog.Builder(activity, R.style.AppTheme_Dialog).setMessage(msg).setCancelable(false)
                    .setPositiveButton(activity.getString(android.R.string.ok), positiveButton).create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String printHash(Activity a) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = a.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = a.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", a.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    public static ArrayList<Integer> GetSelectedSpecificationAttributes(List<SpecificationAttribute> mSpecList) {
        ArrayList<Integer> selectedSpecs = new ArrayList<>();

        for (int i = 0; i < mSpecList.size(); i++) {

            SpecificationAttribute attribute = mSpecList.get(i);
            for (int j = 0; j < attribute.getOptions().size(); j++) {
                SpecificationAttributeOption option = attribute.getOptions().get(j);

                if (option.isSelected())
                    selectedSpecs.add(option.getId());
            }
        }

        return selectedSpecs;
    }

    public static void ResetSpecificationAttributes(List<SpecificationAttribute> mSpecList, List<Integer> specificationOptions) {

        for (int i = 0; i < mSpecList.size(); i++) {

            SpecificationAttribute attribute = mSpecList.get(i);
            for (int j = 0; j < attribute.getOptions().size(); j++) {
                SpecificationAttributeOption option = attribute.getOptions().get(j);

                if (option.isSelected()) {

                    for (int k = 0; k < specificationOptions.size(); k++) {

                        if (option.getId() == specificationOptions.get(k))
                            option.setSelected(false);
                    }
                }
            }
        }
    }

    public static void Logout() {
        GoMobileApp.removeSharedPrefs(AppConstant.IS_USER_LOGGED_IN);
        GoMobileApp.removeSharedPrefs(AppConstant.USER_EMAIL_PREFS_KEY);
        GoMobileApp.removeSharedPrefs(AppConstant.USER_PASSWORD_PREFS_KEY);
        GoMobileApp.removeSharedPrefs(AppConstant.SIGNED_IN_FROM);
        GoMobileApp.removeSharedPrefs(AppConstant.USER_NAME_PREFS_KEY);
        GoMobileApp.removeSharedPrefs(AppConstant.COOKIE_HANDLER);
        GoMobileApp.setShoppingCartItemsCount(0);

        GoMobileApp.Toast("You have successfully signed out");
    }

}
