package com.infoicon.acim.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.infoicon.acim.utils.UtilsMethods;


public class CustomButton extends AppCompatButton {


    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomButton(Context context) {
        super(context);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

}
