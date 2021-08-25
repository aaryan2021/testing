package com.infoicon.acim.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.donate.DonateActivity;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.utils.HomeMenuItemClickListener;
import com.infoicon.acim.utils.UtilsMethods;

/**
 * Created by sumit on 16/8/17.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener{


    private View rootView;
    ImageView imglogo;
    private CustomTextView title;
    private CustomButton btnText,btnWorkbook,btnManual,btnDonation,btnNotes,btnAboutOrg;
    private ImageButton btnBack;
     private HomeMenuItemClickListener homeMenuItemClickListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


       rootView=inflater.inflate(R.layout.fragment_home,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        if(getResources().getBoolean(R.bool.isTablet)){
            main.setBackgroundResource(R.drawable.main_bg_tab);
        }else{
            main.setBackgroundResource(R.drawable.main_bg);
        }

        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void initViews() {
        homeMenuItemClickListener=(HomeMenuItemClickListener)getActivity();
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText("HOME");
        btnText=(CustomButton)rootView.findViewById(R.id.btnText);
        imglogo=(ImageView)rootView.findViewById(R.id.homologo);
        btnWorkbook=(CustomButton)rootView.findViewById(R.id.btnWorkbook);
        btnManual=(CustomButton)rootView.findViewById(R.id.btnManual);
        btnDonation=(CustomButton)rootView.findViewById(R.id.btnDonation);
        btnNotes= (CustomButton) rootView.findViewById(R.id.btnNotes);
        btnAboutOrg=(CustomButton)rootView.findViewById(R.id.btnAboutOrg);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.GONE);
    }

    @Override
    public void initListeners() {
        btnText.setOnClickListener(this);
        btnWorkbook.setOnClickListener(this);
        btnManual.setOnClickListener(this);
        btnDonation.setOnClickListener(this);
        btnNotes.setOnClickListener(this);
        btnAboutOrg.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btnText:

                homeMenuItemClickListener.onHomeMenuItemClick(1);

                break;

            case R.id.btnWorkbook:

                homeMenuItemClickListener.onHomeMenuItemClick(2);

                break;
            case R.id.btnManual:

                homeMenuItemClickListener.onHomeMenuItemClick(3);

                break;

            case R.id.btnDonation:

                Intent intent=new Intent(getActivity(),DonateActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                break;
            case R.id.btnNotes:

                UtilsMethods.replaceFragment(getActivity(),new FragmentNotes());
                imglogo.setVisibility(View.GONE);
                break;
            case R.id.btnAboutOrg:

                UtilsMethods.replaceFragment(getActivity(),new AboutUsFragment());

                break;
        }
    }


 /*   @Override
    public void onPause() {
        super.onPause();
        imglogo.setVisibility(View.GONE);
    }*/
}
