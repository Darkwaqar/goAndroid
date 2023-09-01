package com.growonline.gomobishop;

import android.accounts.NetworkErrorException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.adapter.AddressAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.GetAddressesAsyncTask;
import com.growonline.gomobishop.asynctask.GetAvailableCountriesAndStates;
import com.growonline.gomobishop.control.GridSpacingItemDecoration;
import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class AddressActivity extends BaseActivity {

    private RecyclerView recyclerView;
    private Button addAddressButton;
    private LinearLayout loadingLayout;
    private AddressAdapter addressAdapter;
    private List<Address> mAddressList = new ArrayList<>();
    private FrameLayout noItemFound;
    private GetAddressesAsyncTask backgroundTask;
    private GetAvailableCountriesAndStates getAvailableCountriesAndStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setToolBarTitle(getString(R.string.title_address));

        loadingLayout = (LinearLayout) findViewById(R.id.review_loading);
        addAddressButton = (Button) findViewById(R.id.address_button);
        noItemFound = (FrameLayout) findViewById(R.id.no_item_found);
        recyclerView = (RecyclerView) findViewById(R.id.address_RecyclerView);
        addressAdapter = new AddressAdapter(AddressActivity.this, mAddressList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, AppHelper.dpToPx(10, AddressActivity.this), true));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(addressAdapter);
        addAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAvailableCountriesAndStates = new GetAvailableCountriesAndStates(new AsyncTaskResultListener<ArrayList<AvalibleCountry>>() {
                    @Override
                    public void response(AsyncTaskResult<ArrayList<AvalibleCountry>> response) {
                        Intent intent = new Intent(AddressActivity.this, AddressDetailActivity.class);
                        intent.putExtra(AppConstant.INTENT_VENDOR, mVendor);
                        intent.putExtra(AppConstant.INTENT_COUNTRY, response.getResult());
                        startActivityForResult(intent, AppConstant.CODE_ADDRESS);
                    }
                });
                getAvailableCountriesAndStates.execute();

            }
        });
        LoadAddress();
    }

    public void LoadAddress() {
        backgroundTask = new GetAddressesAsyncTask();
        backgroundTask.addOnResultsListener(new AsyncTaskResultListener<List<Address>>() {
            @Override
            public void response(AsyncTaskResult<List<Address>> response) {
                if (!response.hasException()) {
                    loadingLayout.setVisibility(View.GONE);
                    if (mAddressList == null) {
                        noItemFound.setVisibility(View.VISIBLE);
                        return;
                    }
                    mAddressList.clear();
                    mAddressList.addAll(response.getResult());
                    if (mAddressList.size() == 0)
                        noItemFound.setVisibility(View.VISIBLE);
                    else
                        noItemFound.setVisibility(View.GONE);
                    addressAdapter.notifyDataSetChanged();
                } else {
                    if (response.getException() instanceof NetworkErrorException)
                        launchException(response.getException().getMessage(), response.getException(), false);
                    else
                        launchException(response.getException().getMessage(), response.getException(), true);

                }
            }
        });
        backgroundTask.execute();
    }

    @Override
    protected void onDestroy() {
        if (backgroundTask != null && !backgroundTask.isCancelled())
            backgroundTask.cancel(true);
        super.onDestroy();
    }

    void launchException(String message, Throwable exception, boolean actionButtons) {
        if (actionButtons)
            AppHelper.showException(AddressActivity.this, message, exception);
        else
            AppHelper.showNetworkError(AddressActivity.this, message);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case AppConstant.CODE_ADDRESS:
                LoadAddress();
                break;
            default:
                LoadAddress();
                break;
        }
    }
}
