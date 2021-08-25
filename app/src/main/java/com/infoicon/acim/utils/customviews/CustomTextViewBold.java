package com.infoicon.acim.utils.customviews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.infoicon.acim.utils.UtilsMethods;

/**
 * Created by sumit on 9/11/17.
 */

public class CustomTextViewBold extends AppCompatTextView {


    public CustomTextViewBold(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(UtilsMethods.getTypeFaceFutura(context), Typeface.BOLD);
    }

    public CustomTextViewBold(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(UtilsMethods.getTypeFaceFutura(context), Typeface.BOLD);
    }

    public CustomTextViewBold(Context context) {
        super(context);
        setTypeface(UtilsMethods.getTypeFaceFutura(context), Typeface.BOLD);
    }

}
