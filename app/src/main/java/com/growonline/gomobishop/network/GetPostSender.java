package com.growonline.gomobishop.network;

import android.util.Log;

import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;


public class GetPostSender extends NetworkUtils {
    private int ConnectionTimeout = 20000;

    public String sendGet(String url, boolean isSaveCookie) {
        String response = null;

        HttpURLConnection httpURLConnection;
        try {
            httpURLConnection = getUrlConnection(url, HTTP_GET, PLAIN_TEXT, "");
//            httpURLConnection.setConnectTimeout(ConnectionTimeout);
//            httpURLConnection.setReadTimeout(ConnectionTimeout);
            Log.e("url:", "" + url);
            httpURLConnection.connect();
            response = getResponse(httpURLConnection, isSaveCookie);
            Log.e("response:", "" + response);

            return response;
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            //return e.getMessage();
        } finally {
            return response;
        }
    }

    public String sendPostJSON(String url, String params, boolean isSaveCookie) {
        String response = null;
        HttpURLConnection httpURLConnection;
        try {
            if (AppConstant.isLogEnabled) {
                AppHelper.LogEvent("URL:" + url);
                AppHelper.LogEvent("Request Params:" + params);
            }

            httpURLConnection = getUrlConnection(url, HTTP_POST, APPLICATION_JSON, "");
            httpURLConnection.setConnectTimeout(ConnectionTimeout);
            httpURLConnection.setReadTimeout(ConnectionTimeout);
            httpURLConnection.connect();

            PrintWriter writer = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(), "UTF-8"),
                    true);
            writer.write(params);
            writer.flush();
            response = getResponse(httpURLConnection, isSaveCookie);
            if (AppConstant.isLogEnabled) {
                AppHelper.LogEvent("Response:" + response);
            }
            return response;
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            return "false";
        } catch (Exception e) {
            e.printStackTrace();
            //return e.getMessage();
        } finally {
            return response;
        }
    }

}
