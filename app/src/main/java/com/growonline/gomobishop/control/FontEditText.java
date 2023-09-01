package com.growonline.gomobishop.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.google.android.material.textfield.TextInputEditText;
import com.growonline.gomobishop.R;
import com.growonline.gomobishop.util.AppHelper;

public class FontEditText extends TextInputEditText {
    private String fontType = "1";

    public FontEditText(Context context) {
        super(context);
        if (isInEditMode()) {
        }
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (isInEditMode())
            return;
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontEditText);

        if (ta != null) {
            String fontType = ta.getString(R.styleable.FontEditText_fontType);

            if (fontType == null) fontType = "1";

            switch (fontType) {

                case "1":
                    AppHelper.applyPlayFairDisplayRegularFont(this);
                    break;

                case "2":
                    AppHelper.applyPlayFairDisplayBoldFont(this);
                    break;

                case "3":
                    AppHelper.applyRobotoLightFont(this);
                    break;

                case "4":
                    AppHelper.applyRobotoLightFont(this);
                    break;

                case "5":
                    AppHelper.applyRobotoRegularFont(this);
                    break;

                case "6":
                    AppHelper.applyRobotoBoldFont(this);
                    break;

                default:
                    AppHelper.applyCustomFont(this, fontType);
                    break;



            }
        }
    }

    public void setFontType(String fontType) {
        this.fontType = fontType;
        switch (fontType) {

            case "1":
                AppHelper.applyPlayFairDisplayRegularFont(this);
                break;

            case "2":
                AppHelper.applyPlayFairDisplayBoldFont(this);
                break;

            case "3":
                AppHelper.applyRobotoLightFont(this);
                break;

            case "4":
                AppHelper.applyRobotoLightFont(this);
                break;

            case "5":
                AppHelper.applyRobotoRegularFont(this);
                break;

            case "6":
                AppHelper.applyRobotoBoldFont(this);
                break;

            default:
                AppHelper.applyCustomFont(this, fontType);
                break;
        }
    }
}