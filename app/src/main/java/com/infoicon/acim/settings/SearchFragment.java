package com.infoicon.acim.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextView;

/**
 * Created by sumit on 15/12/17.
 */

public class SearchFragment extends Fragment implements View.OnClickListener {


    private View rootView;
    private WebView webView;
    private CustomTextView title;
    private ImageButton btnBack;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView=inflater.inflate(R.layout.fragment_search,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();

    }

    private void initViews(){
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.search_title);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        webView = (WebView)rootView.findViewById(R.id.webView);
        webView.loadUrl(AppConstants.SEARCH_URL);
    }

    public void initListeners() {
        btnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnBack:
                UtilsMethods.hideSoftKeyboard(getActivity());
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
        }
    }
}
