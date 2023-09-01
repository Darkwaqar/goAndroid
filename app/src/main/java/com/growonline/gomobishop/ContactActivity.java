package com.growonline.gomobishop;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.asynctask.ContactUsAsyncTask;
import com.growonline.gomobishop.control.AndroidBug5497Workaround;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.fragment.FollowDialog;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;
import com.growonline.gomobishop.util.UnCaughtExceptionHandlerCelebrityApp;

import java.util.HashMap;

public class ContactActivity extends BaseActivity {
    private EditText mQueryEditText;
    private EditText mEmailEditText;
    private EditText mNameEditText;
    private EditText mNumberEditText;
    private EditText mEventEditText;

    private String Email;
    private String Query;
    private String Name;
    private String Number;
    private String Event;

    private Vendor mVendor;
    private boolean showFollowLink = false, showContact = false;
    private FontTextView btnContact;
    private LinearLayout mainContentFrame;
    private BottomSheetBehavior behavior;
    private View mBottomSheetShadow;

    private LinearLayout appointmentLayout;

    private TextInputEditText appointmentName;
    private TextInputEditText appointmentEmail;
    private TextInputEditText appointmentPhone;
    private TextInputEditText appointmentEvent;
    private TextInputEditText appointmentQuery;

    private ImageView appointmentImage;
    private ImageView mImageCover;
    private ImageView facebook;
    private ImageView linkedin;
    private ImageView twitter;
    private ImageView instagram;
    private ImageView youtube;
    private ImageView whatsapp;
    private Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new UnCaughtExceptionHandlerCelebrityApp(this));
        setContentView(R.layout.activity_contact);
        init();
    }

    void init() {

        Intent intent = getIntent();
        mVendor = intent.getParcelableExtra(AppConstant.INTENT_VENDOR);
        showFollowLink = intent.getBooleanExtra(AppConstant.INTENT_SHOW_FOLLOW_LINK, false);
        showContact = intent.getBooleanExtra(AppConstant.INTENT_SHOW_CONTACT, false);

        setToolBarTitle(getString(R.string.title_contact_us));

        mainContentFrame = (LinearLayout) findViewById(R.id.content_frame);
        mImageCover = (ImageView) findViewById(R.id.contactImage);

        GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(mVendor.getCoverPictureURL()), mImageCover, null);

        if (mVendor.getContactDescription() != null && mVendor.getContactDescription().trim().length() > 0) {
            TextView txt_desc = (TextView) findViewById(R.id.txt_contact_desc);
            txt_desc.setText(mVendor.getContactDescription());
        }

        mBottomSheetShadow = findViewById(R.id.drp_shadow);
        btnContact = (FontTextView) findViewById(R.id.btn_contact);

        if (showFollowLink) {
            DialogFragment dialogFollow = FollowDialog.newInstance(mVendor);
            dialogFollow.show(getSupportFragmentManager(), "followDialogBox");
        }


        mEmailEditText = (EditText) findViewById(R.id.activity_contact_email);
        mQueryEditText = (EditText) findViewById(R.id.activity_contact_query);
        mNameEditText = (EditText) findViewById(R.id.activity_contact_name);
        mNumberEditText = (EditText) findViewById(R.id.activity_contact_number);
        mEventEditText = (EditText) findViewById(R.id.activity_contact_event);

        appointmentLayout = (LinearLayout) findViewById(R.id.appointment_layout);
        appointmentImage = (ImageView) findViewById(R.id.appointment_image);
        appointmentName = (TextInputEditText) findViewById(R.id.appointment_name);
        appointmentEmail = (TextInputEditText) findViewById(R.id.appointment_email);
        appointmentPhone = (TextInputEditText) findViewById(R.id.appointment_phone);
        appointmentEvent = (TextInputEditText) findViewById(R.id.appointment_event);
        appointmentQuery = (TextInputEditText) findViewById(R.id.appointment_query);
        submit = (Button) findViewById(R.id.appointment_submit);


        LinearLayout bottomSheet = (LinearLayout) findViewById(R.id.bottom_sheet_form);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                mBottomSheetShadow.setAlpha(Math.min(slideOffset, 0.6f));
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        Button btnSubmit = (Button) findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendFeedback();
            }
        });

        if (showContact) {
            mNameEditText.setVisibility(View.VISIBLE);
            mNumberEditText.setVisibility(View.VISIBLE);
            mEventEditText.setVisibility(View.VISIBLE);
            btnContact.setText(AppConstant.APPOINTMENT_TITLE);
            setToolBarTitle(getBaseContext().getResources().getString(AppConstant.APPOINTMENT_TITLE));
            mainContentFrame.setVisibility(View.GONE);
            bottomSheet.setVisibility(View.GONE);
            if (behavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
            AndroidBug5497Workaround.assistActivity(ContactActivity.this);

            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendFeedback();
                }
            });
        } else {
            mainContentFrame.setVisibility(View.VISIBLE);
            bottomSheet.setVisibility(View.VISIBLE);
            appointmentLayout.setVisibility(View.GONE);
        }

        GoMobileApp.getmCacheManager().loadImageWithCenterCrop(Uri.parse(mVendor.getAppointmentPictureUrl()), appointmentImage, null);

        facebook = (ImageView) findViewById(R.id.facebook);
        linkedin = (ImageView) findViewById(R.id.linkedin);
        twitter = (ImageView) findViewById(R.id.twitter);
        instagram = (ImageView) findViewById(R.id.instagram);
        youtube = (ImageView) findViewById(R.id.youtube);
        whatsapp = (ImageView) findViewById(R.id.whatsapp);


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

    boolean validateInfo() {
        if (showContact) {
            Name = GoMobileApp.getText(appointmentName);
            Email = GoMobileApp.getText(appointmentEmail);
            Number = GoMobileApp.getText(appointmentPhone);
            Event = GoMobileApp.getText(appointmentEvent);
            Query = GoMobileApp.getText(appointmentQuery);

            if (Name.length() == 0) {
                appointmentName.requestFocus();
                GoMobileApp.Toast(R.string.required_name);
                return false;
            }

            if (Email.length() == 0) {
                appointmentEmail.requestFocus();
                GoMobileApp.Toast(R.string.required_email);
                return false;
            }
            //if(Email) email format
            if (!GoMobileApp.isValidEmail(Email)) {
                appointmentEmail.requestFocus();
                GoMobileApp.Toast(R.string.invalid_email);
                return false;
            }

            if (Number.length() == 0) {
                appointmentPhone.requestFocus();
                GoMobileApp.Toast(R.string.required_phone);
                return false;
            }
            if (Event.length() == 0) {
                appointmentEvent.requestFocus();
                GoMobileApp.Toast(R.string.required_event);
                return false;
            }

            if (Query.length() == 0) {
                appointmentQuery.requestFocus();
                GoMobileApp.Toast(R.string.empty_query);
                return false;
            }
        } else {

            Email = GoMobileApp.getText(mEmailEditText);
            Query = GoMobileApp.getText(mQueryEditText);
            Name = GoMobileApp.getText(mNameEditText);
            Number = GoMobileApp.getText(mNumberEditText);
            Event = GoMobileApp.getText(mEventEditText);

            if (Email.length() == 0) {
                mEmailEditText.requestFocus();
                GoMobileApp.Toast(R.string.empty_emailField);
                return false;
            }
            //if(Email) email format
            if (!GoMobileApp.isValidEmail(Email)) {
                mEmailEditText.requestFocus();
                GoMobileApp.Toast(R.string.invalid_email);
                return false;
            }

            if (showContact) {
                if (Name.length() == 0) {
                    mNameEditText.requestFocus();
                    GoMobileApp.Toast(R.string.required_name);
                    return false;
                }

                if (Number.length() == 0) {
                    mNumberEditText.requestFocus();
                    GoMobileApp.Toast(R.string.required_phone);
                    return false;
                }
                if (Event.length() == 0) {
                    mEventEditText.requestFocus();
                    GoMobileApp.Toast(R.string.required_event);
                    return false;
                }
            }

            if (Query.length() == 0) {
                mQueryEditText.requestFocus();
                GoMobileApp.Toast(R.string.empty_query);
                return false;
            }
        }
        return true;
    }

    void launchException(String message, Throwable exception) {
        AppHelper.showException(this, message, exception);
    }

    void sendFeedback() {
        if (validateInfo()) {
            HashMap<String, String> jsonObject = new HashMap<>();
            jsonObject.put("email", Email);
            jsonObject.put("enquiry", Query);
            jsonObject.put("vendorId", mVendor.getId().toString());

            if (showContact) {
                jsonObject.put("FullName", Name);
                jsonObject.put("PhoneNumber", Number);
                jsonObject.put("subject", Event);
            }
            ContactUsAsyncTask backgroundTask = new ContactUsAsyncTask(jsonObject);
            backgroundTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
                @Override
                public void response(AsyncTaskResult<Boolean> response) {
                    if (!response.hasException()) {
                        if (showContact) GoMobileApp.Toast("We Will get back as soon as possible");
                        else GoMobileApp.Toast("Thank you For Your Feedback");
                        finish();
                    } else {
                        launchException(response.getException().getMessage(), response.getException());
                    }
                }
            });
            backgroundTask.execute();

        }
    }

    @Override
    public void onBackPressed() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            super.onBackPressed();
        }

    }

    void openLink(String url) {
        Intent returnAndExchangePolicy = new Intent(ContactActivity.this, WebViewActivity.class);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_VENDOR, mVendor);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_TITLE, AppConstant.FOLLOW_TITLE);
        returnAndExchangePolicy.putExtra(AppConstant.INTENT_URL, url);
        startActivity(returnAndExchangePolicy);
    }


}


