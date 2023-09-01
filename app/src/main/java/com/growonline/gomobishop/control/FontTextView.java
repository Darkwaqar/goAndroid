package com.growonline.gomobishop.control;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.growonline.gomobishop.R;
import com.growonline.gomobishop.util.AppHelper;

public class FontTextView extends TextView {
    private String fontType = "1";

    public FontTextView(Context context) {
        this(context, null);
        if (isInEditMode())
            return;
    }

    public FontTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        if (isInEditMode())
            return;
    }

    public FontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (isInEditMode())
            return;

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FontTextView);

        if (ta.getBoolean(R.styleable.FontTextView_underline, false))
            setPaintFlags(getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        if (ta != null) {
            String fontType = ta.getString(R.styleable.FontTextView_fontType);

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