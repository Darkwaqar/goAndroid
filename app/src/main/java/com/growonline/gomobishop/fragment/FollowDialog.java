package com.growonline.gomobishop.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.WebViewActivity;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;

public class FollowDialog extends DialogFragment {


    private Vendor mVendor;
    private ImageView facebook;
    private ImageView linkedin;
    private ImageView twitter;
    private ImageView instagram;
    private ImageView youtube;
    private ImageView whatsapp;

    public FollowDialog() {

    }

    public static FollowDialog newInstance(Vendor vendor) {
        FollowDialog fragment = new FollowDialog();
        Bundle args = new Bundle();
        args.putParcelable(AppConstant.INTENT_VENDOR, vendor);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mVendor = getArguments().getParcelable(AppConstant.INTENT_VENDOR);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_follow_dialog, container, false);

        initUi(v);
        return v;
    }

    void initUi(View view) {
        facebook = (ImageView) view.findViewById(R.id.facebook);
        linkedin = (ImageView) view.findViewById(R.id.linkedin);
        twitter = (ImageView) view.findViewById(R.id.twitter);
        instagram = (ImageView) view.findViewById(R.id.instagram);
        youtube = (ImageView) view.findViewById(R.id.youtube);
        whatsapp = (ImageView) view.findViewById(R.id.whatsapp);


        if (mVendor.getSocialLinks().getFaceboolWebURL() != null)
            facebook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getFaceboolWebURL());
                }
            });
        else
            facebook.setAlpha(0.1f);


        if (mVendor.getSocialLinks().getLinkedWebURL() != null)
            linkedin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getLinkedWebURL());
                }
            });
        else
            linkedin.setAlpha(0.1f);

        if (mVendor.getSocialLinks().getTwitterWebURL() != null)
            twitter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getTwitterWebURL());
                }
            });
        else
            twitter.setAlpha(0.1f);


        if (mVendor.getSocialLinks().getInstagramWebURL() != null)
            instagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getInstagramWebURL());
                }
            });
        else
            instagram.setAlpha(0.1f);


        if (mVendor.getSocialLinks().getYoutubeWebURL() != null)
            youtube.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getYoutubeWebURL());
                }
            });
        else
            youtube.setAlpha(0.1f);


        if (mVendor.getSocialLinks().getWhatsappMobile() != null)
            whatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openLink(mVendor.getSocialLinks().getWhatsappMobile());
                }
            });
        else
            whatsapp.setAlpha(0.1f);
    }

    void openLink(String url) {
        Intent returnAndExchangePolicy = new Intent(getActivity(), WebViewActivity.class);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_TITLE, AppConstant.FOLLOW_TITLE);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_URL, url);
        startActivity(returnAndExchangePolicy);
    }

}
