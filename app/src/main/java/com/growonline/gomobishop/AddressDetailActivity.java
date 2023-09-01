package com.growonline.gomobishop;

import android.content.Intent;
import android.os.Bundle;

import com.growonline.gomobishop.fragment.SingleAddressFragment;
import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;

public class AddressDetailActivity extends BaseActivity {

    private Address address;
    private ArrayList<AvalibleCountry> avalibleCountry;

    private SingleAddressFragment fragShippingAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_detail);
        setToolBarTitle(getString(R.string.title_address_details));

        address = getIntent().getParcelableExtra(AppConstant.INTENT_ADDRESS);
        avalibleCountry = getIntent().getParcelableArrayListExtra(AppConstant.INTENT_COUNTRY);
        fragShippingAddress = SingleAddressFragment.newInstance(address,
                AppConstant.AddressType.SHIPPING, avalibleCountry);
        getSupportFragmentManager().beginTransaction().replace(R.id.address_fragment, fragShippingAddress).commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent();
        setResult(AppConstant.CODE_ADDRESS, i);
        finish();
    }
}
