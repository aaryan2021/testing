package com.infoicon.acim.donate;


import android.support.annotation.IdRes;

import android.os.Bundle;

import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.CustomScrollView;
import com.infoicon.acim.utils.baseclasses.BaseActivity;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.AppConstants;

import com.infoicon.acim.utils.UtilsMethods;


public class DonateActivity extends BaseActivity implements View.OnClickListener {

    private CustomTextView title,link,subtitle,bodypart1,bodypart2;
    private ImageButton btnBack;
    CustomScrollView  scrollView;
    private static int size=16;
    private ImageView  increase, decrease;
    private ImageView donateAudioBooks,donateDailyLessons,donateTranslationProjects,donateGeneralAdmin,
            donateAudioBookTranslation,donateDailyLessonMailing;
    private RadioGroup radioGroup1,radioGroup2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donate);
        UtilsMethods.checkTablet(DonateActivity.this);
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        title = findViewById(R.id.title);
        title.setText(R.string.donations);
        btnBack= findViewById(R.id.btnBack);
        scrollView=(CustomScrollView)findViewById(R.id.customscrollview) ;
        scrollView.setEnableScrolling(true);
        increase = (ImageView)findViewById(R.id.increase);
        decrease = (ImageView)findViewById(R.id.decrease);
        link=(CustomTextView) findViewById(R.id.link);
        title=(CustomTextView)findViewById(R.id.title);
        subtitle=(CustomTextView)findViewById(R.id.subtitle);
        bodypart1=(CustomTextView)findViewById(R.id.bodypart1);
        bodypart2=(CustomTextView)findViewById(R.id.bodypart2);
        link.setMovementMethod(LinkMovementMethod.getInstance());
        title.setTextSize(size);
        subtitle.setTextSize(size);
        bodypart1.setTextSize(size);
        link.setTextSize(size);
        bodypart2.setTextSize(size);

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                finish();
                break;
            case R.id.increase:
                size=size+1;
                title.setTextSize(size);
                subtitle.setTextSize(size);
                bodypart1.setTextSize(size);
                link.setTextSize(size);
                bodypart2.setTextSize(size);

                break;

            case R.id.decrease:
                size=size-1;
                title.setTextSize(size);
                subtitle.setTextSize(size);
                bodypart1.setTextSize(size);
                link.setTextSize(size);
                bodypart2.setTextSize(size);

                break;

        }
    }


}
