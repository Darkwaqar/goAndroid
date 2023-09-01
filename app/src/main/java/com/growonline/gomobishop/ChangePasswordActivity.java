package com.growonline.gomobishop;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.growonline.gomobishop.asynctask.AsyncTaskChangePassword;

public class ChangePasswordActivity extends BaseActivity {


    EditText mold_psdEditText, mNew_passwordEditText, mConfirm_passwordEditText;
    Button mChangePassword_btn;
    String mOld_psdString, mNew_psdString, mConfirm_psdString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_change_password);
        setToolBarTitle(getString(R.string.title_change_password));
        initUi();
        mChangePassword_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOld_psdString = GoMobileApp.getText(mold_psdEditText);
                mNew_psdString = GoMobileApp.getText(mNew_passwordEditText);
                mConfirm_psdString = GoMobileApp.getText(mConfirm_passwordEditText);

                if (mOld_psdString.isEmpty()) {
                    GoMobileApp.Toast(R.string.required_old_password);
                    return;
                }

                if (mNew_psdString.isEmpty()) {
                    GoMobileApp.Toast(R.string.required_new_password);
                    return;
                }

                if (!mConfirm_psdString.equals(mNew_psdString)) {
                    GoMobileApp.Toast(R.string.error_match_password);
                    return;
                }
                new AsyncTaskChangePassword(ChangePasswordActivity.this, mOld_psdString, mNew_psdString).execute();

            }
        });
    }

    private void initUi() {
        mold_psdEditText = (EditText) findViewById(R.id.editText_old);
        mNew_passwordEditText = (EditText) findViewById(R.id.editText_new);
        mConfirm_passwordEditText = (EditText) findViewById(R.id.editText__confirm);
        mChangePassword_btn = (Button) findViewById(R.id.changepsd_btn);
    }
}
