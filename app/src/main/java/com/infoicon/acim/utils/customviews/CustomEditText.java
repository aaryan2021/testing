package com.infoicon.acim.utils.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;

import android.util.AttributeSet;

import com.infoicon.acim.utils.UtilsMethods;


public class CustomEditText extends AppCompatEditText {


    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomEditText(Context context) {
        super(context);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

}
