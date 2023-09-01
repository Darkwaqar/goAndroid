package com.growonline.gomobishop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.AddressDetailActivity;
import com.growonline.gomobishop.BaseActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.DeleteAddressAsyncTask;
import com.growonline.gomobishop.asynctask.GetAvailableCountriesAndStates;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.control.SwipeRevealLayout;
import com.growonline.gomobishop.control.ViewBinderHelper;
import com.growonline.gomobishop.model.Address;
import com.growonline.gomobishop.model.AvalibleCountry;
import com.growonline.gomobishop.util.AppConstant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asifrizvi on 6/15/2019.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    private final ViewBinderHelper binderHelper = new ViewBinderHelper();
    private BaseActivity mActivity;
    private List<Address> Addresses;
    private LayoutInflater mLayoutInflator;
    private GetAvailableCountriesAndStates getAvailableCountriesAndStates;

    public AddressAdapter(BaseActivity activity, List<Address> order) {
        this.mActivity = activity;
        this.Addresses = order;
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // uncomment if you want to open only one row at a time
        binderHelper.setOpenOnlyOne(true);
    }

    @Override
    public AddressAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.address_item, parent, false);
        return new AddressAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AddressAdapter.MyViewHolder holder, int position) {
        Address address = Addresses.get(position);
        holder.addressName.setText(address.getFirstName());
        holder.addressEmail.setText(address.getEmail());
        holder.addressPhone.setText(address.getPhoneNumber());
        holder.addressAddress1.setText(address.getAddress1());
        holder.addressCityState.setText(address.getStateProvinceName());
        holder.addressCountry.setText(address.getCountryName());
    }

    @Override
    public int getItemCount() {
        return Addresses.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private SwipeRevealLayout swipeLayout;
        private TextView addressEdit;
        private TextView addressDelete;
        private TextView addressName;
        private FontTextView addressEmail;
        private TextView addressPhone;
        private TextView addressAddress1;
        private TextView addressCityState;
        private TextView addressCountry;

        public MyViewHolder(View view) {
            super(view);
            swipeLayout = (SwipeRevealLayout) view.findViewById(R.id.swipe_layout);
            addressEdit = (TextView) view.findViewById(R.id.Address_Edit);
            addressDelete = (TextView) view.findViewById(R.id.Address_Delete);
            addressName = (TextView) view.findViewById(R.id.address_name);
            addressEmail = (FontTextView) view.findViewById(R.id.address_email);
            addressPhone = (TextView) view.findViewById(R.id.address_phone);
            addressAddress1 = (TextView) view.findViewById(R.id.address_address1);
            addressCityState = (TextView) view.findViewById(R.id.address_city_state);
            addressCountry = (TextView) view.findViewById(R.id.address_country);
            addressEdit.setOnClickListener(this);
            addressDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.Address_Edit: {
                    getAvailableCountriesAndStates = new GetAvailableCountriesAndStates(new AsyncTaskResultListener<ArrayList<AvalibleCountry>>() {
                        @Override
                        public void response(AsyncTaskResult<ArrayList<AvalibleCountry>> response) {
                            Intent intent = new Intent(mActivity, AddressDetailActivity.class);
                            intent.putExtra(AppConstant.INTENT_VENDOR, ((BaseActivity) mActivity).mVendor);
                            intent.putExtra(AppConstant.INTENT_COUNTRY, response.getResult());
                            intent.putExtra(AppConstant.INTENT_ADDRESS, Addresses.get(getAdapterPosition()));
                            mActivity.startActivityForResult(intent, AppConstant.CODE_ADDRESS);
                        }
                    });
                    getAvailableCountriesAndStates.execute();
                }
                break;
                case R.id.Address_Delete: {
                    new DeleteAddressAsyncTask(mActivity, Addresses.get(getAdapterPosition()).getId()).execute();
                }
                break;
            }
        }
    }

}