package com.infoicon.acim.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;


/**
 * Created by sumit on 7/11/17.
 */

public class UserGuide extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private CustomTextView title, textViewUserGuide1, textViewUserGuide2, textViewUserGuide3;
    private ImageButton btnBack;
    private ImageView decrease, increase;
    private float size;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_user_guide, container, false);
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
        title.setText(getResources().getString(R.string.user_guide));
        title.setGravity(Gravity.CENTER);
        title.setMaxLines(1);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        textViewUserGuide1 = (CustomTextView) rootView.findViewById(R.id.textViewUserGuide1);
        textViewUserGuide2 = (CustomTextView) rootView.findViewById(R.id.textViewUserGuide2);
        textViewUserGuide3 = (CustomTextView) rootView.findViewById(R.id.textViewUserGuide3);
        decrease = (ImageView) rootView.findViewById(R.id.decrease);
        increase = (ImageView) rootView.findViewById(R.id.increase);
        size = User.getInstance(getActivity()).getSize();
        setTextSize();
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        decrease.setOnClickListener(this);
        increase.setOnClickListener(this);
    }

    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
            case R.id.decrease:
                if (size > AppConstants.MIN_SIZE) {
                    size = size - 1;
                    User.getInstance(getActivity()).setSize(size);
                    setTextSize();
                }
                break;
            case R.id.increase:
                size = size + 1;
                User.getInstance(getActivity()).setSize(size);
                setTextSize();

                break;
        }

    }

    /**
     * method to set text size
     */
    private void setTextSize() {
        textViewUserGuide1.setTextSize(size);
        textViewUserGuide2.setTextSize(size);
        textViewUserGuide3.setTextSize(size);
    }

}

