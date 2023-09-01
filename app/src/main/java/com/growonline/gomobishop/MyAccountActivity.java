package com.growonline.gomobishop;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.TextView;

import com.growonline.gomobishop.asynctask.AsyncTaskGetUserDetails;
import com.growonline.gomobishop.asynctask.AsyncTaskPostCustomerInfo;
import com.growonline.gomobishop.model.InfoModel;

import org.json.JSONException;
import org.json.JSONObject;

public class MyAccountActivity extends BaseActivity implements PopupMenu.OnMenuItemClickListener, View.OnClickListener {

    private String mDobDay, mDobMonth, mDobYear;
    private TextView mTextviewDay, mTextviewMonth, mTextviewYear;
    private PopupMenu mYearPopupMenu, mMonthPopupMenu, mDaysPopupMenu;
    private RadioButton mCheckboxMale, mCheckboxFemale;
    private LinearLayout genderLayout;
    private Button mUpadteButton;
    private LinearLayout loadingLayout;
    private TextView text_Username, text_firstName,
            text_lastName, text_email, text_company,
            text_streetAddress, text_streetAddress2, text_zipPostalCode,
            text_city, text_county, text_country, text_stateProvinceId, text_phone, text_fax, text_signature;
    private InfoModel info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        setToolBarTitle(getString(R.string.title_my_account));
        new AsyncTaskGetUserDetails(MyAccountActivity.this, false).execute();
        IniUI();
    }

    private void IniUI() {
        text_Username = (EditText) findViewById(R.id.text_Username);
        text_firstName = (EditText) findViewById(R.id.text_firstName);
        text_lastName = (EditText) findViewById(R.id.text_lastName);
        text_email = (EditText) findViewById(R.id.text_email);
        text_company = (EditText) findViewById(R.id.text_company);
        text_streetAddress = (EditText) findViewById(R.id.text_streetAddress);
        text_streetAddress2 = (EditText) findViewById(R.id.text_streetAddress2);
        text_zipPostalCode = (EditText) findViewById(R.id.text_zipPostalCode);
        text_city = (EditText) findViewById(R.id.text_city);
        text_county = (EditText) findViewById(R.id.text_county);
        text_country = (EditText) findViewById(R.id.text_country);
        text_stateProvinceId = (EditText) findViewById(R.id.text_stateProvinceId);
        text_phone = (EditText) findViewById(R.id.text_phone);
        text_fax = (EditText) findViewById(R.id.text_fax);
        text_signature = (EditText) findViewById(R.id.text_signature);

        //textViews
        mTextviewDay = (TextView) findViewById(R.id.text_days);
        mTextviewMonth = (TextView) findViewById(R.id.text_month);
        mTextviewYear = (TextView) findViewById(R.id.text_year);

        //popup menu
        mDaysPopupMenu = new PopupMenu(MyAccountActivity.this, mTextviewDay);
        mMonthPopupMenu = new PopupMenu(MyAccountActivity.this, mTextviewMonth);
        mYearPopupMenu = new PopupMenu(MyAccountActivity.this, mTextviewYear);

        //button
        mUpadteButton = (Button) findViewById(R.id.activity_customer_submit_btn);

        loadingLayout = (LinearLayout) findViewById(R.id.review_loading);

        mCheckboxMale = (RadioButton) findViewById(R.id.activity_customer_male);
        mCheckboxFemale = (RadioButton) findViewById(R.id.activity_customer_female);
        genderLayout = (LinearLayout) findViewById(R.id.gender_layout);

        for (int i = 0; i < 31; i++)
            mDaysPopupMenu.getMenu().add(1, R.id.text_days, i + 1, String.valueOf(i + 1));
        for (int i = 0; i < 12; i++)
            mMonthPopupMenu.getMenu().add(1, R.id.text_month, i + 1, String.valueOf(i + 1));
        for (int i = 0; i < 67; i++)
            mYearPopupMenu.getMenu().add(1, R.id.text_year, i + 1, String.valueOf(1950 + i));

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


        mUpadteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (GetValueOfView(text_firstName).isEmpty() && info.getFirstNameRequired()) {
                    GoMobileApp.Toast(R.string.required_first_name);
                    return;
                }

                if (GetValueOfView(text_lastName).isEmpty() && info.getLastNameRequired()) {
                    GoMobileApp.Toast(R.string.required_last_name);
                    return;
                }

                if ((mTextviewDay.getText().toString().trim().length() == 0 ||
                        mTextviewMonth.getText().toString().trim().length() == 0 ||
                        mTextviewYear.getText().toString().trim().length() == 0) && info.getDateOfBirthRequired()) {
                    GoMobileApp.Toast(getString(R.string.required_dob));
                    return;
                }
                if (GetValueOfView(text_email).isEmpty()) {
                    GoMobileApp.Toast(R.string.required_email);
                    return;
                } else {
                    if (!GoMobileApp.isValidEmail(GetValueOfView(text_email))) {
                        GoMobileApp.Toast(R.string.invalid_email);
                        return;
                    }
                }
                if (GetValueOfView(text_company).isEmpty() && info.getCompanyRequired()) {
                    GoMobileApp.Toast(R.string.required_company);
                    return;
                }
                if (GetValueOfView(text_streetAddress).isEmpty() && info.getStreetAddressRequired()) {
                    GoMobileApp.Toast(R.string.required_street_address);
                    return;
                }
                if (GetValueOfView(text_streetAddress2).isEmpty() && info.getStreetAddress2Required()) {
                    GoMobileApp.Toast(R.string.required_street_address_2);
                    return;
                }
                if (GetValueOfView(text_zipPostalCode).isEmpty() && info.getZipPostalCodeRequired()) {
                    GoMobileApp.Toast(R.string.required_zip_code);
                    return;
                }
                if (GetValueOfView(text_city).isEmpty() && info.getCityRequired()) {
                    GoMobileApp.Toast(R.string.required_city);
                    return;
                }
                if (GetValueOfView(text_county).isEmpty() && info.getCountyRequired()) {
                    GoMobileApp.Toast(R.string.required_county);
                    return;
                }
                if (GetValueOfView(text_country).isEmpty() && info.getCountryRequired()) {
                    GoMobileApp.Toast(R.string.required_country);
                    return;
                }
                if (GetValueOfView(text_phone).isEmpty() && info.getPhoneRequired()) {
                    GoMobileApp.Toast(R.string.required_phone);
                    return;
                } else {
                    if (!GoMobileApp.validatePhoneNumber(GetValueOfView(text_phone))) {
                        GoMobileApp.Toast(R.string.invalid_phone);
                        return;
                    }

                }
                if (GetValueOfView(text_fax).isEmpty() && info.getFaxRequired()) {
                    GoMobileApp.Toast(R.string.required_fax);
                    return;
                }

                JSONObject body = new JSONObject();
                try {
                    body.put("Gender", (mCheckboxMale.isChecked()) ? "m" : "f");
                    body.put("FirstName", GetValueOfView(text_firstName));
                    body.put("LastName", GetValueOfView(text_lastName));
                    body.put("DateOfBirthDay", mDobDay);
                    body.put("DateOfBirthMonth", mDobMonth);
                    body.put("DateOfBirthYear", mDobYear);
                    body.put("Phone", GetValueOfView(text_phone));

                    body.put("Email", GetValueOfView(text_email));
                    body.put("Company", GetValueOfView(text_company));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                new AsyncTaskPostCustomerInfo(MyAccountActivity.this, body).execute();
            }
        });


        mTextviewDay.setOnClickListener(this);
        mTextviewMonth.setOnClickListener(this);
        mTextviewYear.setOnClickListener(this);

        mDaysPopupMenu.setOnMenuItemClickListener(this);
        mMonthPopupMenu.setOnMenuItemClickListener(this);
        mYearPopupMenu.setOnMenuItemClickListener(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.text_days:
                mDobDay = item.getTitle().toString();
                mTextviewDay.setText(mDobDay);
                break;

            case R.id.text_month:
                mDobMonth = item.getTitle().toString();
                mTextviewMonth.setText(mDobMonth);
                break;

            case R.id.text_year:
                mDobYear = item.getTitle().toString();
                mTextviewYear.setText(mDobYear);
                break;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.text_days:
                mDaysPopupMenu.show();
                break;

            case R.id.text_year:
                mYearPopupMenu.show();
                break;

            case R.id.text_month:
                mMonthPopupMenu.show();
                break;
        }
    }

    public void updateDetails(InfoModel info) {
        this.info = info;
        loadingLayout.setVisibility(View.GONE);

        if (info.getGenderEnabled()) {
            if (info.getGender().equalsIgnoreCase("f")) {
                mCheckboxFemale.setChecked(true);
            } else {
                mCheckboxMale.setChecked(true);
            }
        } else {
            genderLayout.setVisibility(View.GONE);
        }


        if (info.getCheckUsernameAvailabilityEnabled())
            text_Username.setText(info.getUsername());
        else
            text_Username.setVisibility(View.GONE);

        if (info.getFirstNameEnabled())
            text_firstName.setText(info.getFirstName());
        else
            text_firstName.setVisibility(View.GONE);

        if (info.getLastNameEnabled())
            text_lastName.setText(info.getLastName());
        else
            text_lastName.setVisibility(View.GONE);

        text_email.setText(info.getEmail());

        if (info.getCompanyEnabled())
            text_company.setText(info.getCompany());
        else
            text_company.setVisibility(View.GONE);

        if (info.getStreetAddressEnabled())
            text_streetAddress.setText(info.getStreetAddress());
        else
            text_streetAddress.setVisibility(View.GONE);

        if (info.getStreetAddress2Enabled())
            text_streetAddress2.setText(info.getStreetAddress2());
        else
            text_streetAddress2.setVisibility(View.GONE);

        if (info.getZipPostalCodeEnabled())
            text_zipPostalCode.setText(info.getZipPostalCode());
        else
            text_zipPostalCode.setVisibility(View.GONE);

        if (info.getCityEnabled())
            text_city.setText(info.getCity());
        else
            text_city.setVisibility(View.GONE);

        if (info.getCountyEnabled())
            text_county.setText(info.getCounty());
        else
            text_county.setVisibility(View.GONE);


        if (info.getCountryEnabled())
            text_country.setText(info.getCountryId());
        else
            text_country.setVisibility(View.GONE);

        if (info.getStateProvinceEnabled())
            text_stateProvinceId.setText(info.getStateProvinceId());
        else
            text_stateProvinceId.setVisibility(View.GONE);

        if (info.getPhoneEnabled())
            text_phone.setText(info.getPhone());
        else
            text_phone.setVisibility(View.GONE);

        if (info.getFaxEnabled())
            text_fax.setText(info.getFax());
        else
            text_fax.setVisibility(View.GONE);

        if (info.getSignatureEnabled())
            text_signature.setText(info.getSignature());
        else
            text_signature.setVisibility(View.GONE);

        if (info.getDateOfBirthDay() != null)
            mTextviewDay.setText(String.format("%s", info.getDateOfBirthDay().toString()));

        if (info.getDateOfBirthMonth() != null)
            mTextviewMonth.setText(String.format("%s", info.getDateOfBirthMonth().toString()));

        if (info.getDateOfBirthYear() != null)
            mTextviewYear.setText(String.format("%s", info.getDateOfBirthYear().toString()));

    }

    public String GetValueOfView(TextView view) {
        if (view == null) return "";
        return view.getText().toString();
    }


}