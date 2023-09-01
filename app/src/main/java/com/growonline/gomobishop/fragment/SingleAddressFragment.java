package com.growonline.gomobishop.fragment;


import android.accounts.NetworkErrorException;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.growonline.gomobishop.AddressDetailActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.OnePageCheckoutActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.asynctask.AddAddressAsyncTask;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.model.State;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import java.util.ArrayList;
import java.util.List;

public class SingleAddressFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final Integer PHONE_NUM_LENGTH = 11;

    private Activity mActivity;
    private OnePageCheckoutActivity onePageCheckoutActivity;
    private Address mAddress;
    private AppConstant.AddressType mAddressType;
    private List<AvalibleCountry> mAvailableCountries;

    private EditText mNameEditText, mSecNameEditText, mEmailEditText, mPhoneNumberEditText,
            mCityEditText, mAddressEditText;
    private TextView mCountryText, mStateText;
    private Button mBtnSave;
    private PopupMenu mCountryMenu, mStateMenu;
    private int mSelectedCountryId, mSelectedStateId;
    private AddAddressAsyncTask addAddressAsyncTask;
    private RelativeLayout country_dropdown, state_dropdown;
    private EditText mPostalCode;
    private AddressDetailActivity addressDetailActivity;
    private boolean proceedToCheckout = false;
    private boolean addressChanged;


    public SingleAddressFragment() {
    }

    public static SingleAddressFragment newInstance(Address address, AppConstant.AddressType addressType, ArrayList<AvalibleCountry> availableCountries) {
        SingleAddressFragment fragment = new SingleAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, address);
        args.putSerializable(ARG_PARAM2, addressType);
        args.putParcelableArrayList(ARG_PARAM3, availableCountries);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAddress = getArguments().getParcelable(ARG_PARAM1);
            mAddressType = (AppConstant.AddressType) getArguments().getSerializable(ARG_PARAM2);
            mAvailableCountries = getArguments().getParcelableArrayList(ARG_PARAM3);
            mActivity = getActivity();
            if (mActivity instanceof OnePageCheckoutActivity)
                onePageCheckoutActivity = (OnePageCheckoutActivity) mActivity;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.checkout_address, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    void init(View view) {

        mNameEditText = (EditText) view.findViewById(R.id.address_first_name);
        mSecNameEditText = (EditText) view.findViewById(R.id.address_sec_name);
        mEmailEditText = (EditText) view.findViewById(R.id.address_email);
        mPhoneNumberEditText = (EditText) view.findViewById(R.id.address_ph);
        mCityEditText = (EditText) view.findViewById(R.id.address_city);
        mAddressEditText = (EditText) view.findViewById(R.id.address_address);
        mPostalCode = (EditText) view.findViewById(R.id.address_postal_code);
        mCountryText = (TextView) view.findViewById(R.id.country_txtview);
        mStateText = (TextView) view.findViewById(R.id.state_country_txtview);
        mBtnSave = (Button) view.findViewById(R.id.address_button);
        country_dropdown = (RelativeLayout) view.findViewById(R.id.country_dropdown);
        state_dropdown = (RelativeLayout) view.findViewById(R.id.state_dropdown);


        if (mAvailableCountries != null) {

            prepareCountryMenuItem();
            mStateMenu = new PopupMenu(mActivity, state_dropdown);

            mCountryMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mSelectedCountryId = item.getItemId();
                    mCountryText.setText(item.getTitle());
                    mStateText.setText("");
                    prepareStateMenuItem(item.getItemId());
                    return false;
                }
            });

            mStateMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    mSelectedStateId = item.getItemId();
                    mStateText.setText(item.getTitle());
                    return false;
                }
            });

            country_dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCountryMenu.show();
                }
            });

            state_dropdown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mStateMenu.show();
                }
            });
        }

        if (mActivity instanceof OnePageCheckoutActivity) {
            mBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValid()) {
                        onePageCheckoutActivity.setLoadingAnimation(true);
                        addAddressAsyncTask = new AddAddressAsyncTask(mAddress, mAddressType);
                        addAddressAsyncTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                            @Override
                            public void response(AsyncTaskResult<Boolean> response) {
                                onePageCheckoutActivity.setLoadingAnimation(false);
                                if (!response.hasException()) {
                                    String addressTypeString = mAddressType == AppConstant.AddressType.SHIPPING ? "Shipping" : "Billing";
                                    GoMobileApp.Toast(addressTypeString + " location saved successfully");
                                    setAddressChanged(false);
                                    if (proceedToCheckout)//no need to press checkout button again
                                        onePageCheckoutActivity.ProceedToCheckout();
                                    // person has to click checkout button again to progrees
                                    onePageCheckoutActivity.loadCheckoutDetails();
                                    proceedToCheckout = false;
                                } else {
                                    if (response.getException() instanceof NetworkErrorException)
                                        onePageCheckoutActivity.showException(response.getException().getMessage(), response.getException(), true);
                                    else
                                        GoMobileApp.Toast(response.getException().getMessage());
                                }
                            }
                        });
                        addAddressAsyncTask.execute();
                    }
                }
            });
        } else if (mActivity instanceof AddressDetailActivity) {
            mBtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (isValid()) {
                        addressDetailActivity = (AddressDetailActivity) mActivity;
                        addressDetailActivity.setLoadingAnimation(true);
                        addAddressAsyncTask = new AddAddressAsyncTask(mAddress, mAddressType);
                        addAddressAsyncTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                            @Override
                            public void response(AsyncTaskResult<Boolean> response) {
                                addressDetailActivity.setLoadingAnimation(false);
                                if (!response.hasException()) {
                                    String addressTypeString = mAddressType == AppConstant.AddressType.SHIPPING ? "Shipping" : "Billing";
                                    GoMobileApp.Toast(addressTypeString + " location saved successfully");
                                    addressDetailActivity.onBackPressed();
                                } else {
                                    if (response.getException() instanceof NetworkErrorException)
                                        addressDetailActivity.showException(response.getException().getMessage(), response.getException(), true);
                                    else
                                        GoMobileApp.Toast(response.getException().getMessage());
                                }
                            }
                        });
                        addAddressAsyncTask.execute();
                    }
                }
            });
        } else {
            mNameEditText.setEnabled(false);
            mSecNameEditText.setEnabled(false);
            mEmailEditText.setEnabled(false);
            mPhoneNumberEditText.setEnabled(false);
            mCityEditText.setEnabled(false);
            mAddressEditText.setEnabled(false);
            mCountryText.setEnabled(false);
            mStateText.setEnabled(false);
            mBtnSave.setVisibility(View.GONE);
            country_dropdown.setClickable(false);
            state_dropdown.setClickable(false);
            mPostalCode.setEnabled(false);
        }

        if (mAddress != null) {
            bindData();
            mBtnSave.setText(R.string.update);
        } else {
            mAddress = new Address();
            //set defaults
            AvalibleCountry defaultCountry = mAvailableCountries.get(0);
            if (defaultCountry != null) {
                mSelectedCountryId = defaultCountry.getCountryId();
                mCountryText.setText(defaultCountry.getName());
                State defaultState = defaultCountry.getStates().get(0);
                if (defaultState != null) {
                    mSelectedStateId = defaultState.getProvinceId();
                    mStateText.setText(defaultState.getName());
                } else {
                    mSelectedStateId = 0;
                    mStateText.setText("");
                }
            }
            prepareStateMenuItem(mSelectedCountryId);
            String mSignedInEmail = GoMobileApp.getStringPrefs(AppConstant.USER_EMAIL_PREFS_KEY);
            String mUsername = GoMobileApp.getStringPrefs(AppConstant.USER_NAME_PREFS_KEY);
            mEmailEditText.setText(mSignedInEmail);
            mNameEditText.setText(mUsername);
        }


        TextWatcher watcher = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                setAddressChanged(true);
            }
        };
        mNameEditText.addTextChangedListener(watcher);
        mSecNameEditText.addTextChangedListener(watcher);
        mEmailEditText.addTextChangedListener(watcher);
        mPhoneNumberEditText.addTextChangedListener(watcher);
        mCityEditText.addTextChangedListener(watcher);
        mAddressEditText.addTextChangedListener(watcher);
        mCountryText.addTextChangedListener(watcher);
        mStateText.addTextChangedListener(watcher);
    }

    void bindData() {
        mSelectedCountryId = mAddress.getCountryId();
        mNameEditText.setText(mAddress.getFirstName());
        mSecNameEditText.setText(mAddress.getLastName());
        mEmailEditText.setText(mAddress.getEmail());
        mPhoneNumberEditText.setText(mAddress.getPhoneNumber());
        mCityEditText.setText(mAddress.getCity());
        mAddressEditText.setText(mAddress.getAddress1());
        mPostalCode.setText(mAddress.getZipPostalCode());
        if (mAddress.getStateProvinceId() != null)
            mSelectedStateId = mAddress.getStateProvinceId();
        else
            mAddress.setStateProvinceId(0);

        prepareStateMenuItem(mSelectedCountryId);

        if (mActivity instanceof OnePageCheckoutActivity) {
            mCountryText.setText(GetCountryNameByID(mAddress.getCountryId()));
            mStateText.setText(GetStateNameByID(mAddress.getCountryId(), mAddress.getStateProvinceId()));
        } else {
            mCountryText.setText(mAddress.getCountryName());
            mStateText.setText(mAddress.getStateProvinceName());
        }


    }

    void prepareCountryMenuItem() {
        mCountryMenu = new PopupMenu(mActivity, country_dropdown);
        for (int i = 0; i < mAvailableCountries.size(); i++) {
            int group = 1;
            int itemId = mAvailableCountries.get(i).getCountryId();
            String title = mAvailableCountries.get(i).getName();
            mCountryMenu.getMenu().add(group, itemId, i, title);
        }
    }

    void prepareStateMenuItem(int countryId) {
        mStateMenu.getMenu().clear();
        for (AvalibleCountry availableCountry :
                mAvailableCountries) {
            if (availableCountry.getCountryId() == countryId) {
                if (availableCountry.getStates().size() < 1) {
                    State state = new State();
                    state.setName("No States Available");
                    state.setProvinceId(0);
                    availableCountry.getStates().add(state);
                }
                for (State state : availableCountry.getStates()) {
                    int itemId = state.getProvinceId();
                    String title = state.getName();
                    mStateMenu.getMenu().add(1, itemId, 0, title);
                }

            }
        }
    }

    String GetCountryNameByID(int countryId) {
        for (AvalibleCountry availableCountry :
                mAvailableCountries) {
            if (availableCountry.getCountryId() == countryId) {
                return availableCountry.getName();
            }
        }
        return "";
    }

    String GetStateNameByID(int countryId, Integer stateId) {
        for (AvalibleCountry availableCountry :
                mAvailableCountries) {
            if (availableCountry.getCountryId() == countryId) {
                for (State state : availableCountry.getStates()) {
                    if (state.getProvinceId().equals(stateId))
                        return state.getName();
                }
            }
        }
        return "";
    }

    public boolean isValid() {

        try {

            if (GoMobileApp.isNullOrEmpty(mNameEditText)) {
                GoMobileApp.Toast(R.string.required_first_name);
                ScrollToView(mNameEditText);
                return false;
            } else {
                mAddress.setFirstName(mNameEditText.getText().toString());
            }
            if (GoMobileApp.isNullOrEmpty(mSecNameEditText)) {
                GoMobileApp.Toast(R.string.required_last_name);
                ScrollToView(mSecNameEditText);
                return false;
            } else {
                mAddress.setLastName(mSecNameEditText.getText().toString());
            }
            if (GoMobileApp.isNullOrEmpty(mEmailEditText)) {
                GoMobileApp.Toast(R.string.required_email);
                ScrollToView(mEmailEditText);
                return false;
            } else if (!GoMobileApp.isValidEmail(mEmailEditText.getText().toString())) {
                GoMobileApp.Toast(R.string.invalid_email);
                onePageCheckoutActivity.ScrollToView(mEmailEditText);
                return false;
            }
            mAddress.setEmail(mEmailEditText.getText().toString());

            if (GoMobileApp.isNullOrEmpty(mPhoneNumberEditText)) {
                GoMobileApp.Toast(R.string.required_phone);
                ScrollToView(mPhoneNumberEditText);
                return false;
            } else if (!GoMobileApp.validatePhoneNumber(mPhoneNumberEditText.getText().toString())) {
                GoMobileApp.Toast(R.string.invalid_phone);
                ScrollToView(mPhoneNumberEditText);
                return false;
            } else if (isCod()) {
                if (mPhoneNumberEditText.getText().toString().length() != PHONE_NUM_LENGTH) {
                    AppHelper.showMsgDialog(mActivity, getString(R.string.app_name),
                            "Please provide " + PHONE_NUM_LENGTH.toString() + " digit Phone Number.",
                            null, null);
                    ScrollToView(mPhoneNumberEditText);
                    return false;
                } else {
                    mAddress.setPhoneNumber(mPhoneNumberEditText.getText().toString());
                }
            } else if (!isCod()) {
                if (mPhoneNumberEditText.getText().toString().length() < PHONE_NUM_LENGTH) {
                    AppHelper.showMsgDialog(mActivity, getString(R.string.app_name),
                            "Please provide " + PHONE_NUM_LENGTH.toString() + " digit Phone Number.",
                            null, null);
                    ScrollToView(mPhoneNumberEditText);
                    return false;
                } else {
                    mAddress.setPhoneNumber(mPhoneNumberEditText.getText().toString());
                }
            }
            if (GoMobileApp.isNullOrEmpty(mCountryText)) {
                GoMobileApp.Toast(R.string.required_county);
                ScrollToView(mCountryText);
                return false;
            } else {
                mAddress.setCountryName(mCountryText.getText().toString());
                mAddress.setCountryId(mSelectedCountryId);
            }
            if (GoMobileApp.isNullOrEmpty(mStateText)) {
                GoMobileApp.Toast(R.string.required_state);
                ScrollToView(mStateText);
                return false;
            } else {
                mAddress.setStateProvinceName(mStateText.getText().toString());
                mAddress.setStateProvinceId(mSelectedStateId);
            }
            if (GoMobileApp.isNullOrEmpty(mCityEditText)) {
                GoMobileApp.Toast(R.string.required_city);
                ScrollToView(mCityEditText);
                return false;
            } else {
                mAddress.setCity(mCityEditText.getText().toString());
            }

            if (GoMobileApp.isNullOrEmpty(mPostalCode)) {
                GoMobileApp.Toast(R.string.required_zip_code);
                ScrollToView(mPostalCode);
                return false;
            } else {
                mAddress.setZipPostalCode(mPostalCode.getText().toString());
            }

            if (GoMobileApp.isNullOrEmpty(mAddressEditText)) {
                GoMobileApp.Toast(R.string.required_street_address);
                ScrollToView(mAddressEditText);
                return false;
            } else {
                mAddress.setAddress1(mAddressEditText.getText().toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean isCod() {
        return mSelectedCountryId == 57;
    }

    private void ScrollToView(View view) {
        onePageCheckoutActivity.ScrollToView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (addAddressAsyncTask != null && !addAddressAsyncTask.isCancelled())
            addAddressAsyncTask.cancel(true);
    }


    public void UpdateAddress() {
        proceedToCheckout = true;
        mBtnSave.performClick();
    }

    public boolean hasAddressChanged() {
        return addressChanged;
    }

    public void setAddressChanged(boolean addressChanged) {
        this.addressChanged = addressChanged;
    }

}