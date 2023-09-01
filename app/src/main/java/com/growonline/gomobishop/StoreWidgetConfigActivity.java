package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.AdapterStoreSelection;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.FetchAllVendorsFromDbAsyncTask;
import com.growonline.gomobishop.control.SimpleDividerItemDecoration;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppHelper;

import java.util.List;

/**
 * Created by Basit on 5/13/2017.
 */

public class StoreWidgetConfigActivity extends AppCompatActivity implements AdapterStoreSelection.WidgetConfigListener {

    public static Vendor mVendor;
    private int mAppWidgetId;
    private RecyclerView mRecyclerView;
    private ImageView mBackgroundImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_store);
        setResult(RESULT_CANCELED);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_featured_products);

        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.HORIZONTAL_LIST));
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this, SimpleDividerItemDecoration.VERTICAL_LIST));

        loadStoresFromDb();
    }

    private void loadStoresFromDb() {
        FetchAllVendorsFromDbAsyncTask backgroundTask = new FetchAllVendorsFromDbAsyncTask(this);
        backgroundTask.addOnVendorsLoadedListener(new AsyncTaskResultListener<List<Vendor>>() {
            @Override
            public void response(AsyncTaskResult<List<Vendor>> response) {

                if (!response.hasException()) {
                    List<Vendor> tempList = response.getResult();
                    mRecyclerView.setAdapter(new AdapterStoreSelection(StoreWidgetConfigActivity.this, tempList, StoreWidgetConfigActivity.this));
                    mRecyclerView.setLayoutManager(new GridLayoutManager(StoreWidgetConfigActivity.this, 2));
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException().getCause());
                    else
                        launchException(response.getException().getMessage(), response.getException().getCause());
                }
            }
        });
        backgroundTask.execute();
    }

    void launchException(String message, Throwable exception) {
        AppHelper.showException(this, message, exception);
    }


    @Override
    public void onItemSelected(int pos, Vendor v) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        StoreWidgetProvider.updateWidget(this, appWidgetManager, mAppWidgetId, v);

        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }
}