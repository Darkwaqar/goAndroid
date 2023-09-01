package com.growonline.gomobishop;

import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

public class CrashActivity extends AppCompatActivity {

    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_crash);

        Intent i = getIntent();
        error = i.getStringExtra("error");
        Log.e("error", error);

        TextView btnGoBack = (TextView) findViewById(R.id.btn_goBack);
        btnGoBack.setPaintFlags(btnGoBack.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Intent startApp = new Intent(CrashActivity.this, MainActivity.class);
                    startApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(startApp);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                    finish();
                } catch (Exception ex) {
                    GoMobileApp.Toast("Please restart app");
                }
            }
        });
    }


    void reportError() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + "developer.digitrends@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "CELEBRITY MOBILE APP UN-HANDLED ERROR REPORT");
        emailIntent.putExtra(Intent.EXTRA_TEXT, error);

        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    @Override
    public void onBackPressed() {
        Intent startApp = new Intent(CrashActivity.this, MainActivity.class);
        startApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(startApp);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
