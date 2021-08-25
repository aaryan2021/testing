package com.infoicon.acim.utils.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;



import com.infoicon.acim.utils.UtilsMethods;


public class CustomTextView extends AppCompatTextView {


    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

    public CustomTextView(Context context) {
        super(context);
        setTypeface(UtilsMethods.getTypeFaceFutura(context));
    }

}
