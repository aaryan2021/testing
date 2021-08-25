package com.infoicon.acim.home;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.GetIntroductionResponse;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sumit on 18/8/17.
 */

public class AboutUsFragment extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private CustomTextView title,textViewAboutOrg;
    private ImageButton btnBack;
    private LinearLayout retry,bottom_view;
    private GetIntroductionResponse getIntroductionResponse;
    private ImageView  decrease,increase;
    private float size;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        callAboutOrganizationWebService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_about_us,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
    }

    @Override
    public void initViews() {
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.about_org);
        title.setGravity(Gravity.CENTER);
        title.setMaxLines(1);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        textViewAboutOrg=(CustomTextView)rootView.findViewById(R.id.textViewAboutOrg);
        textViewAboutOrg.setVisibility(View.GONE);
        retry=(LinearLayout)rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        bottom_view=(LinearLayout)rootView.findViewById(R.id.bottom_view);
        bottom_view.setVisibility(View.GONE);
        decrease=(ImageView)rootView.findViewById(R.id.decrease);
        increase=(ImageView)rootView.findViewById(R.id.increase);
        size = User.getInstance(getActivity()).getSize();
        textViewAboutOrg.setTextSize(size);
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        retry.setOnClickListener(this);
        decrease.setOnClickListener(this);
        increase.setOnClickListener(this);
    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onStart() {
        super.onStart();
        if(getIntroductionResponse!=null){
            if(getIntroductionResponse.getData()!=null){
                if (Build.VERSION.SDK_INT >= 24) {
                    textViewAboutOrg.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription(),Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textViewAboutOrg.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription()));
                }

            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
            case R.id.retry:
                callAboutOrganizationWebService();
                break;
            case R.id.decrease:
                if (size > AppConstants.MIN_SIZE) {
                    size = size - 1;
                    User.getInstance(getActivity()).setSize(size);
                    textViewAboutOrg.setTextSize(size);
                }
                break;
            case R.id.increase:
                size = size + 1;
                User.getInstance(getActivity()).setSize(size);
                textViewAboutOrg.setTextSize(size);

                break;
        }

    }


    /*web service call to get organisation data*/
    private void callAboutOrganizationWebService(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty(Keys.TYPE,Keys.TEXT_INTRO);
        jsonObject.addProperty("key","text_about");

        final retrofit.Call<GetIntroductionResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getIntroductionResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call,true,
                new Callback<GetIntroductionResponse>() {

            @Override
            public void onResponse(Response<GetIntroductionResponse> response,
                                   Retrofit retrofit)
            {
                retry.setVisibility(View.GONE);
                textViewAboutOrg.setVisibility(View.VISIBLE);
                bottom_view.setVisibility(View.VISIBLE);
                getIntroductionResponse     =response.body();
                if(getIntroductionResponse.getSuccess().equals("true"))
                {
                    if (Build.VERSION.SDK_INT >= 24) {
                        textViewAboutOrg.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription(),Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textViewAboutOrg.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription()));

                    }
                }
                else
                    {
                    UtilsMethods.showToast(getActivity(),getIntroductionResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t)
            {
                //UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                textViewAboutOrg.setVisibility(View.GONE);
                bottom_view.setVisibility(View.GONE);
            }
        });
    }

}
