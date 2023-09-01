package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.growonline.gomobishop.adapter.CountryAdapter;
import com.growonline.gomobishop.asynctask.AsyncTaskGetCurrency;
import com.growonline.gomobishop.asynctask.AsyncTaskSetCurrency;
import com.growonline.gomobishop.control.AdapterLinearLayout;
import com.growonline.gomobishop.control.GRadioGroup;
import com.growonline.gomobishop.model.AllAvailableCurrencies;
import com.growonline.gomobishop.model.PaymentMethod;

import java.util.ArrayList;
import java.util.List;

public class CountrySelectorActivity extends AppCompatActivity {
    private AdapterLinearLayout mCountryLayout, mCurrencyLayout;
    private GRadioGroup mRadioCountryUtil, mRadioCurrencyUtil;
    private Button proceedButton;
    private List<PaymentMethod> country = new ArrayList<>();
    private List<PaymentMethod> currency = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selector);
        mCountryLayout = (AdapterLinearLayout) findViewById(R.id.country_layout);
        mCurrencyLayout = (AdapterLinearLayout) findViewById(R.id.currency_layout);
        proceedButton = (Button) findViewById(R.id.proceed_btn);

        populateCountry();
        populateCurrency();

        mCountryLayout.setAdapter(new CountryAdapter(CountrySelectorActivity.this, country));
        mRadioCountryUtil = new GRadioGroup(((CountryAdapter) mCountryLayout.getmAdapter()).getmRadioList());


        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String method = country.get(mRadioCountryUtil.getSelectedRadioButtonPos()).getName();
                int currencySelected = currency.get(mRadioCurrencyUtil.getSelectedRadioButtonPos()).getId();
                SetCurrency(currencySelected);
            }
        });

    }

    public void populateCurrency() {
        new AsyncTaskGetCurrency(CountrySelectorActivity.this, false).execute();
//        currency.add(new PaymentMethod(1, "US($)", 0));
//        currency.add(new PaymentMethod(2, "UK(£)", 0));
//        currency.add(new PaymentMethod(3, "PKR(Rs.)", 0));
    }

    public void updateCurrency(AllAvailableCurrencies mAllAvailableCurrencies) {
        if (mAllAvailableCurrencies == null) return;
        currency = new ArrayList<>();
        for (AllAvailableCurrencies.AvailableCurrency s : mAllAvailableCurrencies.getAvailableCurrencies()) {
            currency.add(new PaymentMethod(s.getId(), s.getName() + " " + s.getCurrencySymbol(), 0));
        }
        mCurrencyLayout.setAdapter(new CountryAdapter(CountrySelectorActivity.this, currency));
        mRadioCurrencyUtil = new GRadioGroup(((CountryAdapter) mCurrencyLayout.getmAdapter()).getmRadioList());
    }

    private void populateCountry() {
        country.add(new PaymentMethod(-1, "United Kingdom", R.drawable.uk));
        country.add(new PaymentMethod(-1, "Pakistan", R.drawable.pk));
    }

    public void SetCurrency(int currencyId) {
        new AsyncTaskSetCurrency(CountrySelectorActivity.this, false, currencyId).execute();
    }

    public void updatedCurrency(int mMessage) {
        GoMobileApp.Toast(mMessage);
        Intent currentIntent = getIntent();
        Intent i = new Intent(CountrySelectorActivity.this, TutorialActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.putExtra("startUpMode", true);

        if (currentIntent.getData() != null) {
            i.setData(currentIntent.getData());
        }
        startActivity(i);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        finish();
    }
}
