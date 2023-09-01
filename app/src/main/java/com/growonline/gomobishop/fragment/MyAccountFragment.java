package com.growonline.gomobishop.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.growonline.gomobishop.AddressActivity;
import com.growonline.gomobishop.ChangePasswordActivity;
import com.growonline.gomobishop.ChatActivity;
import com.growonline.gomobishop.GoMobileApp;
import com.growonline.gomobishop.LoginSignUpActivity;
import com.growonline.gomobishop.MyAccountActivity;
import com.growonline.gomobishop.NotificationActivity;
import com.growonline.gomobishop.OrderListingActivity;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.RewardPointsActivity;
import com.growonline.gomobishop.TutorialActivity;
import com.growonline.gomobishop.WebViewActivity;
import com.growonline.gomobishop.WishListDetailsActivity;
import com.growonline.gomobishop.adapter.AdapterMyAccount;
import com.growonline.gomobishop.control.RatingDialog;
import com.growonline.gomobishop.control.RecyclerTouchListener;
import com.growonline.gomobishop.model.MyAccountModel;
import com.growonline.gomobishop.network.NetworkUtils;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.SystemIntents;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyAccountFragment extends Fragment {
    private RecyclerView myAccountRecyclerView;
    private AdapterMyAccount adapter;
    private List<MyAccountModel> myAccountModelList = new ArrayList<>();
    private Context mContext;
    public MyAccountFragment() {
        // Required empty public constructor
    }

    public static MyAccountFragment newInstance() {
        return new MyAccountFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getActivity().getBaseContext();
        myAccountRecyclerView = (RecyclerView) view.findViewById(R.id.my_account_recyclerView);
        myAccountRecyclerView.setLayoutManager(new GridLayoutManager(null, 1, LinearLayoutManager.VERTICAL, false));
        myAccountRecyclerView.setItemAnimator(new DefaultItemAnimator());
        myAccountRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new AdapterMyAccount(getActivity(), myAccountModelList);
        myAccountRecyclerView.setAdapter(adapter);

        myAccountRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), myAccountRecyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                MyAccountModel menu = myAccountModelList.get(position);
                boolean login = GoMobileApp.IsUserLogin();
                switch (menu.getId()) {
                    case AppConstant.LOGIN:
                        startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_MAIN_SCREEN));
                        break;
                    case AppConstant.LOGOUT:
                        AppHelper.Logout();
                        LoadMenu();
                        break;
                    case AppConstant.USER_INFO:
                        if (login)
                            startActivity(new Intent(getActivity(), MyAccountActivity.class));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_MY_ACCOUNT));
                        break;
                    case AppConstant.CHANGE_PASSWORD:
                        if (login)
                            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_CHANGE_PASSWORD));
                        break;
                    case AppConstant.MY_ORDER:
                        if (login)
                            startActivity(new Intent(getActivity(), OrderListingActivity.class));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_ORDER_LIST));
                        break;
                    case AppConstant.WISH_LIST:
                        if (login)
                            startActivity(new Intent(getActivity(), WishListDetailsActivity.class).putExtra(AppConstant.INTENT_WISH_LIST, AppConstant.RETURN_MAIN_SCREEN));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_WISH_LIST));
                        break;
                    case AppConstant.LOYALTY_PROGRAM:
                        startActivity(new Intent(getActivity(), RewardPointsActivity.class));
                        break;
                    case AppConstant.FAQ:
                        Intent activityFaqIntent = new Intent(getActivity(), WebViewActivity.class);
                        activityFaqIntent.putExtra(AppConstant.INTENT_URL, AppConstant.FAQS_URL);
                        activityFaqIntent.putExtra(AppConstant.INTENT_TITLE, AppConstant.FAQ_TITLE);
                        startActivity(activityFaqIntent);
                        break;
                    case AppConstant.HOW_TO_USE:
                        startActivity(new Intent(getActivity(), TutorialActivity.class));
                        break;
                    case AppConstant.ABOUT:
                        Intent about = new Intent(getActivity(), WebViewActivity.class);
                        about.putExtra(AppConstant.INTENT_URL, AppConstant.ABOUT_GOMOBISHOP_URL);
                        about.putExtra(AppConstant.INTENT_TITLE, AppConstant.ABOUT_GOMOBISHOP_TITLE);
                        startActivity(about);
                        break;
                    case AppConstant.CONTACT:
                        break;
                    case AppConstant.RATE:
                        showDialog();
                        break;
                    case AppConstant.FEEDBACK:
                        break;
                    case AppConstant.CREATE_APP:
                        Intent createApp = new Intent(getActivity(), WebViewActivity.class);
                        createApp.putExtra(AppConstant.INTENT_URL, AppConstant.BUILDER_URL);
                        createApp.putExtra(AppConstant.INTENT_TITLE, AppConstant.CREATE_APP_TITLE);
                        startActivity(createApp);
                        break;
                    case AppConstant.NOTIFICATION:
                        if (login)
                            startActivityForResult((new Intent(getActivity(), NotificationActivity.class)), AppConstant.CODE_NOTIFICATION_CENTER);
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_NOTIFICATION));
                        break;
                    case AppConstant.ROAD_MAP:
                        Intent roadMap = new Intent(getActivity(), WebViewActivity.class);
                        roadMap.putExtra(AppConstant.INTENT_URL, AppConstant.ROAD_MAP_URL);
                        roadMap.putExtra(AppConstant.INTENT_TITLE, AppConstant.ROAD_MAP_TITLE);
                        startActivity(roadMap);
                        break;

                    case AppConstant.SHARE_APP:
                        String sharingLink = "https://play.google.com/store/apps/details?id=" + getActivity().getPackageName();
                        SystemIntents.share(getActivity(), sharingLink, null,
                                NetworkUtils.PLAIN_TEXT);
                        break;


                    case AppConstant.MY_ADDRESS:
                        if (login)
                            startActivity(new Intent(getActivity(), AddressActivity.class));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_ADDRESS));
                        break;

                    case AppConstant.CHAT:
                        if (login)
                            startActivity(new Intent(getActivity(), ChatActivity.class));
                        else
                            startActivity(new Intent(getActivity(), LoginSignUpActivity.class).putExtra(AppConstant.RETURN_URI, AppConstant.RETURN_CHAT));
                        break;
                }

            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));
        LoadMenu();
    }

    private void LoadMenu() {
        myAccountModelList.clear();
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.USER_INFO_TITLE), R.drawable.ic_person_black_24dp, AppConstant.USER_INFO));
//        myAccountModelList.add(new MyAccountModel(AppConstant.CHANGE_PASSWORD_TITLE, R.drawable.ic_vpn_key_black_24dp, AppConstant.CHANGE_PASSWORD));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.MY_ORDER_TITLE), R.drawable.ic_shopping_cart_black_24dp, AppConstant.MY_ORDER));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.CHAT_TITLE), R.drawable.baseline_inbox_black_24, AppConstant.CHAT));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.MY_ADDRESS_TITLE), R.drawable.ic_add_location_black_24dp, AppConstant.MY_ADDRESS));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.WISH_LIST_TITLE), R.drawable.ic_favorite_black_24dp, AppConstant.WISH_LIST));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.LOYALTY_PROGRAM_TITLE), R.drawable.ic_loyalty_black_24dp, AppConstant.LOYALTY_PROGRAM));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.HOW_TO_USE_TITLE), R.drawable.ic_view_carousel_black_24dp, AppConstant.HOW_TO_USE));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.ABOUT_GOMOBISHOP_TITLE), R.drawable.ic_supervisor_account_black_24dp, AppConstant.ABOUT));
//        myAccountModelList.add(new MyAccountModel(AppConstant.CONTACT_TITLE, R.drawable.ic_call_black_24dp, AppConstant.CONTACT));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.RATE_TITLE), R.drawable.ic_rate_review_black_24dp, AppConstant.RATE));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.SHARE_APP_TITLE), R.drawable.ic_share_black, AppConstant.SHARE_APP));
//        myAccountModelList.add(new MyAccountModel(AppConstant.FEEDBACK_TITLE, R.drawable.ic_feedback_black_24dp, AppConstant.FEEDBACK));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.CREATE_APP_TITLE), R.drawable.ic_create_black_24dp, AppConstant.CREATE_APP));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.FAQ_TITLE), R.drawable.ic_info_outline_black_24dp, AppConstant.FAQ));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.NOTIFICATION_TITLE), R.drawable.ic_notifications_black_24dp, AppConstant.NOTIFICATION));
        myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.ROAD_MAP_TITLE), R.drawable.ic_add_location_black_24dp, AppConstant.ROAD_MAP));

        if (GoMobileApp.IsUserLogin())
            myAccountModelList.add(new MyAccountModel(mContext.getResources().getString(AppConstant.LOGOUT_TITLE), R.drawable.ic_settings_power_black_24dp, AppConstant.LOGOUT));
        else
            myAccountModelList.add(0, new MyAccountModel(mContext.getResources().getString(AppConstant.LOGIN_TITLE), R.drawable.ic_settings_power_black_24dp, AppConstant.LOGIN));
        adapter.notifyDataSetChanged();
    }

    void restartFragment() {
        MyAccountFragment fragment = (MyAccountFragment) getActivity().getSupportFragmentManager().getFragments().get(5);
        getActivity().getSupportFragmentManager().beginTransaction()
                .detach(fragment)
                .attach(fragment)
                .commit();
    }

    private void showDialog() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(getActivity())
                .threshold(3)
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                    }
                }).build();

        ratingDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        LoadMenu();
    }
}
