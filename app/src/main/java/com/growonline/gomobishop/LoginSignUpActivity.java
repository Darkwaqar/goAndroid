package com.growonline.gomobishop;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.growonline.gomobishop.asynctask.AsyncTaskLogin;
import com.growonline.gomobishop.asynctask.AsyncTaskRecoverPassword;
import com.growonline.gomobishop.asynctask.AsyncTaskRegisterUser;
import com.growonline.gomobishop.asynctask.AsyncTaskResult;
import com.growonline.gomobishop.asynctask.AsyncTaskResultListener;
import com.growonline.gomobishop.control.AndroidBug5497Workaround;
import com.growonline.gomobishop.control.FontTextView;
import com.growonline.gomobishop.fragment.DialogWebViewFragment;
import com.growonline.gomobishop.model.Vendor;
import com.growonline.gomobishop.util.AppConstant;
import com.growonline.gomobishop.util.AppHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginSignUpActivity extends AppCompatActivity implements FacebookCallback<LoginResult>, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {
    public static final String TRANSLATION_Y = "translationY";
    public static final String TAG_DIALOG_BOX_TERM_AND_CONDITION = "TermAndCondition";
    private static final int RC_SIGN_IN = 1;
    public Vendor mVendor;
    AsyncTaskRecoverPassword recoverPassTask;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private int returnUri;
    private int productId;
    private View mSigninLayout, mFacebookTextview, mRegisterLayout, mLoginBackImageView,
            mMainFrameLayout, mRegEditTextLayout, mSignInEditTextLayout, mOptionLayout,
            mForgotPassLayout;
    private TextView mGoogleTextview, mLoginTextView, mRegisterTextView, mSendFogotPassSendButton;
    private ImageView mRegBackbutton, mTitleImageView, mForgotPassBackArrow;
    private String mEmail;
    private String mPassword;
    private EditText mLoginEmailEditText;
    private EditText mForgotPassEmailEditText;
    private EditText mLoginPasswordEditText;
    private EditText mRegEmailEditText;
    private EditText mRegPasswordEditText;
    private EditText mRegCPasswordEditText;
    private EditText mRegNameEditText;
    private FontTextView mBtnForgotPassword;
    private String mName;
    private String mCPassword;
    private float mOptionTop;
    private Button mActivity_loginsign_register_textview, mloginsign_signin_textview, mLoginsign_facebook_view;
    private TextView mTermsTextView;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
    }

    private void intiUi() {
        mBtnForgotPassword = (FontTextView) findViewById(R.id.btn_forgot_password);
        mOptionLayout = findViewById(R.id.activity_loginsign_mainlayout);
        mSignInEditTextLayout = findViewById(R.id.activity_login_edtlayout);
        mLoginBackImageView = findViewById(R.id.login_back_arrow);
        mMainFrameLayout = findViewById(R.id.login_framelayout);
        mRegEditTextLayout = findViewById(R.id.activity_reg_edtlayout);
        mTitleImageView = (ImageView) findViewById(R.id.activity_loginsign_title_imageview);
        mFacebookTextview = findViewById(R.id.activity_loginsign_facebook_layout);
        mGoogleTextview = (TextView) findViewById(R.id.activity_loginsign_google_textview);
        mSigninLayout = findViewById(R.id.activity_loginsign_signin_layout);
        mRegisterLayout = findViewById(R.id.activity_loginsign_register_layout);
        mRegBackbutton = (ImageView) findViewById(R.id.reg_back_arrow);
        mForgotPassLayout = findViewById(R.id.activity_forgot_pass_layout);

        mLoginTextView = (TextView) findViewById(R.id.login_btn);
        mRegisterTextView = (TextView) findViewById(R.id.reg_btn);

        mLoginEmailEditText = (EditText) findViewById(R.id.login_email);
        mForgotPassEmailEditText = (EditText) findViewById(R.id.forgot_pass_email);
        mLoginPasswordEditText = (EditText) findViewById(R.id.login_password);
        mRegEmailEditText = (EditText) findViewById(R.id.reg_email);
        mRegPasswordEditText = (EditText) findViewById(R.id.reg_password);
        mRegCPasswordEditText = (EditText) findViewById(R.id.reg_conf_password);
        mRegNameEditText = (EditText) findViewById(R.id.reg_name);
        mSendFogotPassSendButton = (TextView) findViewById(R.id.send_forgot_pass_request);
        mloginsign_signin_textview = (Button) findViewById(R.id.activity_loginsign_signin_textview);
        mActivity_loginsign_register_textview = (Button) findViewById(R.id.activity_loginsign_register_textview);
        mLoginsign_facebook_view = (Button) findViewById(R.id.activity_loginsign_facebook_view);
        mForgotPassBackArrow = (ImageView) findViewById(R.id.forgot_pass_back_arrow);
        mTermsTextView = (TextView) findViewById(R.id.terms);


        mRegisterTextView.setOnClickListener(this);
        mLoginTextView.setOnClickListener(this);
        mSigninLayout.setOnClickListener(this);
        mRegisterLayout.setOnClickListener(this);
        mGoogleTextview.setOnClickListener(this);
        mFacebookTextview.setOnClickListener(this);
        mLoginBackImageView.setOnClickListener(this);
        mRegBackbutton.setOnClickListener(this);
        mBtnForgotPassword.setOnClickListener(this);
        mSendFogotPassSendButton.setOnClickListener(this);
        mForgotPassBackArrow.setOnClickListener(this);
        mActivity_loginsign_register_textview.setOnClickListener(this);
        mLoginsign_facebook_view.setOnClickListener(this);
        mloginsign_signin_textview.setOnClickListener(this);
        mTermsTextView.setOnClickListener(this);

        mOptionTop = mOptionLayout.getTranslationY();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }

        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void login(String email, String password) {
        new AsyncTaskLogin(LoginSignUpActivity.this, email, password, mVendor, returnUri, productId).execute();
    }

    private void register(String email, String pass, String name, String type, int returnUri, int productId) {
        new AsyncTaskRegisterUser(LoginSignUpActivity.this, email, pass, name, type, returnUri, productId).execute();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        AndroidBug5497Workaround.assistActivity(this);

        returnUri = getIntent().getIntExtra(AppConstant.RETURN_URI, 0);
        productId = getIntent().getIntExtra(AppConstant.INTENT_PRODUCT_ID, 0);
        mVendor = getIntent().getParcelableExtra(AppConstant.INTENT_VENDOR);

        intiUi();

        mCallbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mCallbackManager, this);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        if (mVendor != null)
            GoMobileApp.getmCacheManager().loadImage(Uri.parse(mVendor.getLogoUrl()), mTitleImageView);
    }

    void setUpPrivacyPolicyDialog() {
        DialogFragment sizeGuideDialog = DialogWebViewFragment.newInstance(AppConstant.PRIVACY_POLICY_PAGE_URL, "Term and Condition", true);
        sizeGuideDialog.show(getSupportFragmentManager(), TAG_DIALOG_BOX_TERM_AND_CONDITION);
    }

    private boolean validateForgotPassContent() {
        String fEmail = mForgotPassEmailEditText.getText().toString();

        if (fEmail.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_email);
            return false;
        } else {
            if (!GoMobileApp.isValidEmail(mEmail)) {
                GoMobileApp.Toast(R.string.invalid_email);
                return false;
            }
        }

        return true;
    }

    private boolean validateRegContent() {
        mEmail = mRegEmailEditText.getText().toString();
        mPassword = mRegPasswordEditText.getText().toString();
        mName = mRegNameEditText.getText().toString();
        mCPassword = mRegCPasswordEditText.getText().toString();

        if (mEmail.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_email);
            return false;
        } else {
            if (!GoMobileApp.isValidEmail(mEmail)) {
                GoMobileApp.Toast(R.string.invalid_email);
                return false;
            }
        }

        if (mPassword.equalsIgnoreCase("")) {
            AppHelper.showMsgDialog(LoginSignUpActivity.this, getString(R.string.app_name), getString(R.string.invalid_password), null, null);
            return false;
        }

        if (mPassword.length() < 6) {
            GoMobileApp.Toast(R.string.required_password_length);
            return false;
        }

        if (mName.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_name);
            return false;
        }

        if (mCPassword.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_confirm_password);
            return false;
        }
        if (!mCPassword.equalsIgnoreCase(mPassword)) {
            GoMobileApp.Toast(R.string.error_match_password);
            return false;
        }


        return true;
    }

    private boolean validateContent() {
        mEmail = GoMobileApp.getText(mLoginEmailEditText);
        mPassword = GoMobileApp.getText(mLoginPasswordEditText);

        if (mEmail.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_email);
            return false;
        } else {
            if (!GoMobileApp.isValidEmail(mEmail)) {
                GoMobileApp.Toast(R.string.invalid_email);
                return false;
            }
        }

        if (mPassword.equalsIgnoreCase("")) {
            GoMobileApp.Toast(R.string.required_password);
            return false;
        }
        return true;
    }

    private void openLayout(final View v1, final View v2) {
        ObjectAnimator slideDown = ObjectAnimator.ofFloat(v1, TRANSLATION_Y, v1.getTop(), v1.getBottom());
        slideDown.setDuration(500);
        slideDown.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                v1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                v1.setVisibility(View.VISIBLE);
            }
        });
        slideDown.start();


        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v2, View.ALPHA, 0, 1);
        fadeIn.setDuration(500);
        fadeIn.setStartDelay(500);
        fadeIn.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                v2.setVisibility(View.VISIBLE);
                v2.setAlpha(0);
            }
        });
        fadeIn.start();


    }

    private void closeLayout(final View v1, final View v2) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v1, View.ALPHA, 1, 0);
        fadeOut.setDuration(500);
        fadeOut.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationStart(animation);
                v1.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                v1.setVisibility(View.VISIBLE);
            }
        });
        fadeOut.start();


        ObjectAnimator slideUp = ObjectAnimator.ofFloat(v2, TRANSLATION_Y, v2.getBottom(), Math.round(mOptionTop));
        slideUp.setDuration(500);
        slideUp.setStartDelay(500);
        slideUp.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                v2.setVisibility(View.VISIBLE);
            }
        });
        slideUp.start();
    }

    private void fbSignIn() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
    }

    //region Callbacks
    //!--------------------- Facebook Callbacks ---------------------!//

    private void googleSignIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     * Called when the dialog completes without error.
     * <p>
     * Note: This will be called instead of {@link #onCancel()} if any of the following conditions
     * are true.
     * <ul>
     * <li>
     * </li>
     * <li>
     * The logged in Facebook user has not authorized the app that has initiated the dialog.
     * </li>
     * </ul>
     *
     * @param loginResult Result from the dialog
     */
    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        // Application code
                        if (response.getJSONObject() != null) {
                            JSONObject object2 = response.getJSONObject();
                            try {
                                String email;
                                try {
                                    email = object2.getString("email");
                                } catch (Exception e) {
                                    email = object2.getString("first_name") + "@growonlinepk.com";
                                }
                                register(email, object2.getString("id"), object2.getString("first_name"), AppConstant.FACEBOOK, returnUri, productId);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e("FACEBOOK ERROR:", response.getError().getErrorMessage());
                            response.getError().getException().printStackTrace();
                            GoMobileApp.Toast(response.getError().getErrorMessage());
                        }
                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onCancel() {
        GoMobileApp.Toast("Cancelled By User");

    }

    /**
     * Called when the dialog finishes with an error.
     *
     * @param error The error that occurred
     */
    @Override
    public void onError(FacebookException error) {
        if (error instanceof FacebookAuthorizationException) {
            if (AccessToken.getCurrentAccessToken() != null) {
                LoginManager.getInstance().logOut();
            }
        }
        AppHelper.LogEvent("FacebookException:" + error.getMessage());
        GoMobileApp.Toast(error.getMessage());
    }

    //!--------------------- Google Plus Callbacks ---------------------!//
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        AppHelper.LogEvent("GoogleException:" + connectionResult.getErrorMessage());
        GoMobileApp.Toast(connectionResult.getErrorMessage());

    }
    //endregion

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            GoogleSignInAccount acct = result.getSignInAccount();
            if (acct != null) {
                register(acct.getEmail(), acct.getId(), acct.getGivenName(), AppConstant.G_PLUS, returnUri, productId);
            }
//            register(acct.getDisplayName().replace(" ", "") + "@growonlinepk.com", acct.getId(), acct.getGivenName(), AppConstant.G_PLUS, returnUri);

        } else {
            GoMobileApp.Toast(result.getStatus().toString());
            Log.e("Google SignIn Error", "handleSignInResult: " + result.getStatus().toString());
        }
    }

    void sendForgotPasswordRequest(String email) {
        recoverPassTask = new AsyncTaskRecoverPassword(email);
        recoverPassTask.addOnResultListener(new AsyncTaskResultListener<Boolean>() {
            @Override
            public void response(AsyncTaskResult<Boolean> response) {
                if (!response.hasException()) {
                    if (response.getResult()) {
                        AppHelper.showMsgDialog(LoginSignUpActivity.this,
                                getString(R.string.app_name),
                                getString(R.string.successEmailMessage) +
                                        getString(R.string.successMessageEmailSend) +
                                        "PLEASE CHECK YOUR JUNK MAIL TO ENSURE YOU RECEIVE IT",
                                null,
                                null);
                    } else {
                        AppHelper.showMsgDialog(LoginSignUpActivity.this,
                                getString(R.string.app_name),
                                getString(R.string.errorMessageEmailNotFound),
                                null,
                                null);
                    }
                } else {
                    AppHelper.showMsgDialog(LoginSignUpActivity.this,
                            getString(R.string.app_name),
                            getString(R.string.invalid_email),
                            null,
                            null);
                }
            }
        });
        recoverPassTask.execute();
    }

    @Override
    public void onBackPressed() {
        if (mOptionLayout.getVisibility() != View.VISIBLE) {
            if (mSignInEditTextLayout.getVisibility() == View.VISIBLE) {
                closeLayout(mSignInEditTextLayout, mOptionLayout);
                return;
            } else if (mRegEditTextLayout.getVisibility() == View.VISIBLE) {
                closeLayout(mRegEditTextLayout, mOptionLayout);
                return;
            } else if (mForgotPassLayout.getVisibility() == View.VISIBLE) {
                closeLayout(mForgotPassLayout, mSignInEditTextLayout);
                return;
            }
        }
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
        hideSoftKeyboard(LoginSignUpActivity.this);
        switch (view.getId()) {
            case R.id.activity_loginsign_facebook_view:
            case R.id.activity_loginsign_facebook_layout:
                fbSignIn();
                break;

            case R.id.activity_loginsign_google_textview:
                googleSignIn();
                break;

            case R.id.activity_loginsign_signin_textview:
            case R.id.activity_loginsign_signin_layout:
                openLayout(mOptionLayout, mSignInEditTextLayout);
                break;

            case R.id.login_back_arrow:
                closeLayout(mSignInEditTextLayout, mOptionLayout);
                break;

            case R.id.activity_loginsign_register_textview:
            case R.id.activity_loginsign_register_layout:
                openLayout(mOptionLayout, mRegEditTextLayout);
                break;

            case R.id.reg_back_arrow:
                closeLayout(mRegEditTextLayout, mOptionLayout);
                break;

            case R.id.login_btn:
                if (validateContent())
                    login(mEmail, mPassword);
                break;

            case R.id.reg_btn:
                if (validateRegContent())
                    register(mEmail, mPassword, mName, AppConstant.MANUAL_SIGN_UP, returnUri, productId);
                break;
            case R.id.btn_forgot_password:
                openLayout(mSignInEditTextLayout, mForgotPassLayout);
                break;
            case R.id.send_forgot_pass_request:
                if (validateForgotPassContent())
                    sendForgotPasswordRequest(mForgotPassEmailEditText.getText().toString());
                break;
            case R.id.forgot_pass_back_arrow:
                closeLayout(mForgotPassLayout, mSignInEditTextLayout);
                break;

            case R.id.terms:
                setUpPrivacyPolicyDialog();
                break;
        }
    }
}