package com.growonline.gomobishop.network;

import android.content.SharedPreferences;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;


public class NetworkUtils {

    public final static String PLAIN_TEXT = "text/plain";
    public static final String TEXT_HTML = "text/html";
    private static final String COOKIES_HEADER = "Set-Cookie";
    public final String UTF8 = "UTF-8";
    public final String HTTP_GET = "GET";
    public final String HTTP_POST = "POST";
    public final String CHANGE_LINE = "\r\n";
    public final String END_REQUEST = "--";
    public final String CONTENT_DISPOSITION = "Content-Disposition: ";
    public final String CONTENT_TYPE = "Content-Type: ";
    public final String CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding: ";
    public final String CHARSET = " charset=";
    public final String FILE_NAME = " filename=";
    public final String NAME = "name=";
    public final String FORM_DATA = "form-data; ";
    public final String BINARY = "binary";
    public final String VIDEO_MP4 = "video/mp4";
    public final String IMAGE_JPEG = "image/jpeg";
    public final String APPLICATION_MULTIPART = "multipart/form-data";
    public final String APPLICATION_JSON = "application/json";


    public String mBoundary = END_REQUEST + END_REQUEST + "WebKitFormBoundaryflDIl9j7fMbC5CDw";

    public NetworkUtils() {
    }

    public HttpURLConnection getUrlConnection(String URL, String httpMethod,
                                              String contentType, String boundary) throws Exception {
        java.net.URL url = new URL(URL);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        if (httpMethod.equalsIgnoreCase(HTTP_POST)) {
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
        }
        urlConnection.setRequestMethod(httpMethod);

        if (contentType.equalsIgnoreCase(APPLICATION_MULTIPART)) {
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");
            urlConnection.setRequestProperty("Cache-Control", "max-age=0");
        } else {
            urlConnection.setRequestProperty("Content-Type"
                    , contentType);
        }

        //Zuni Header
        String cookieString = GoMobileApp.getStringPrefs(AppConstant.COOKIE_HANDLER);
        urlConnection.setRequestProperty("Cookie", cookieString);

        if (!cookieString.equalsIgnoreCase(""))
            AppHelper.LogEvent("Cookie:" + cookieString);

        return urlConnection;
    }

    String getResponse(HttpURLConnection urlConnection, boolean isSaveCookie) throws Exception {
        String response = "";
        if (!GoMobileApp.IsUserLogin() || !GoMobileApp.IsCookieAvailable()) {
            AppHelper.LogEvent("saving cookies...");
            Map<String, List<String>> headerFields = urlConnection.getHeaderFields();
            List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

            if (cookiesHeader != null && isSaveCookie) {
                SharedPreferences.Editor editor = GoMobileApp.getmAppPrefEditor();
                String cookies = "";

                for (String cookie : cookiesHeader)
                    cookies = cookies + cookie + ";";

                editor.putString(AppConstant.COOKIE_HANDLER, cookies);
                AppHelper.LogEvent(cookies);
                editor.commit();
            } else {
                AppHelper.LogEvent("saving cookies cancelled...");
            }
        }

        int status = urlConnection.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response = response + line;
            }
            reader.close();
            urlConnection.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
        return response;
    }

}
