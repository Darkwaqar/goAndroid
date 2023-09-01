package com.growonline.gomobishop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.control.AspectRatioImageView;
import com.growonline.gomobishop.model.MyAccountModel;

import java.util.List;

/**
 * Created by asifrizvi on 2/26/2019.
 */

public class AdapterMyAccount extends RecyclerView.Adapter<AdapterMyAccount.MyViewHolder> {
    private Activity mActivity;
    private List<MyAccountModel> mMenus;
    private LayoutInflater mLayoutInflator;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private AspectRatioImageView myAccountIcon;
        private TextView myAccountName;

        public MyViewHolder(View view) {
            super(view);
            myAccountIcon = (AspectRatioImageView) view.findViewById(R.id.my_account_icon);
            myAccountName = (TextView) view.findViewById(R.id.my_account_name);

        }
    }

    public AdapterMyAccount(Activity activity, List<MyAccountModel> menus) {
        this.mActivity = activity;
        this.mMenus = menus;
        this.mLayoutInflator = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public AdapterMyAccount.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_account_item, parent, false);
        return new AdapterMyAccount.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(AdapterMyAccount.MyViewHolder holder, int position) {
        MyAccountModel menu = mMenus.get(position);
        holder.myAccountName.setText(menu.getName());
        GoMobileApp.getmCacheManager().loadImage(menu.getIcon(), holder.myAccountIcon, null);
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

}
