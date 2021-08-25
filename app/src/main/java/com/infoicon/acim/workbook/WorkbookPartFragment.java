package com.infoicon.acim.workbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.adapter.ExpandableListAdapter;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.customviews.CustomTextViewBold;
import com.infoicon.acim.utils.customviews.NonScrollExpandableListView;
import com.infoicon.acim.introduction.FragmentIntroduction;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID;



/**
 * Created by sumit on 16/9/17.
 */

public class WorkbookPartFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private LinearLayout layout_workbook;
    private List<GetWorkbookLessonResponse.DataBean.DatachildBean> dataChildBeanListOne, dataChildBeanListTwo;
    private NonScrollExpandableListView expendableListViewPartOne, expendableListViewPartTwo;
    private ExpandableListAdapter expandableListAdapterPartOne, expandableListAdapterPartTwo;
    private LinearLayout retry;
    private ImageButton btnBack;
    private CustomTextViewBold textViewPartOneIntro, textViewPartTwoIntro, textViewEpilogue;

    private HashMap<String, List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean>> listDataChild, listDataChild1;
    private List<String> listDataHeader, listDataHeader1;
    private int lastExpandedPosition = -1;
    private int lastExpandedPosition1 = -1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConstants.PART_NUM=1;
        AppConstants.CLICKED_POS=0;
        callWorkbookLessonsWebservice();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_new_workbook, container, false);
        rootView.setOnClickListener(null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpExpendableListView();
        setUpBackGround();
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.e("onStart","onStart");

       if (dataChildBeanListOne != null && dataChildBeanListTwo != null) {
            layout_workbook.setVisibility(View.VISIBLE);
            retry.setVisibility(View.GONE);
            expandableListAdapterPartOne = new ExpandableListAdapter(WorkbookPartFragment.this,getActivity(),
                    listDataHeader, listDataChild,1);
            expendableListViewPartOne.setAdapter(expandableListAdapterPartOne);

            expandableListAdapterPartTwo = new ExpandableListAdapter(WorkbookPartFragment.this,
                    getActivity(), listDataHeader1, listDataChild1,2);
            expendableListViewPartTwo.setAdapter(expandableListAdapterPartTwo);

           expendableListViewPartOne.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

               @Override
               public void onGroupExpand(int groupPosition) {
                   if (lastExpandedPosition != -1
                           && groupPosition != lastExpandedPosition) {
                       expendableListViewPartOne.collapseGroup(lastExpandedPosition);
                   }
                   lastExpandedPosition = groupPosition;
               }
           });
           expendableListViewPartTwo.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

               @Override
               public void onGroupExpand(int groupPosition) {
                   if (lastExpandedPosition1 != -1
                           && groupPosition != lastExpandedPosition1) {
                       expendableListViewPartTwo.collapseGroup(lastExpandedPosition1);
                   }
                   lastExpandedPosition1 = groupPosition;
               }
           });
           if(AppConstants.PART_NUM==1){
               expendableListViewPartOne.expandGroup(AppConstants.CLICKED_POS_GROUP);
               expendableListViewPartOne.setSelectedGroup(AppConstants.CLICKED_POS_GROUP);
            }else if(AppConstants.PART_NUM==2){
               expendableListViewPartTwo.expandGroup(AppConstants.CLICKED_POS_GROUP);
               expendableListViewPartTwo.setSelectedGroup(AppConstants.CLICKED_POS_GROUP);
               expendableListViewPartTwo.setSelectionFromTop(AppConstants.CLICKED_POS_GROUP,0);
               expendableListViewPartTwo.smoothScrollToPosition(AppConstants.CLICKED_POS_GROUP);

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("onResume","onResume");

    }

    @Override
    public void initViews() {
        title = getActivity().findViewById(R.id.title);
        title.setText(R.string.workbook_title);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        layout_workbook = getActivity().findViewById(R.id.layout_workbook);
        layout_workbook.setVisibility(View.INVISIBLE);
        retry = rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        btnBack = getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);
        textViewPartOneIntro = rootView.findViewById(R.id.textViewPartOneIntro);
        textViewPartTwoIntro = rootView.findViewById(R.id.textViewPartTwoIntro);
        textViewEpilogue = rootView.findViewById(R.id.textViewEpilogue);
    }

    @Override
    public void initListeners() {
        retry.setOnClickListener(this);
        textViewPartOneIntro.setOnClickListener(this);
        textViewPartTwoIntro.setOnClickListener(this);
        textViewEpilogue.setOnClickListener(this);
    }

    private void setUpExpendableListView() {
        expendableListViewPartOne = rootView.findViewById(R.id.expendableListViewPartOne);
        expendableListViewPartTwo = rootView.findViewById(R.id.expendableListViewPartTwo);
    }

    private void setUpBackGround() {
        RelativeLayout main = getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.retry:
                callWorkbookLessonsWebservice();
                break;
            case R.id.textViewPartOneIntro:
                callIntroductionFragment("Part 1 | Introduction", AppConstants.WORKBOOK_PART1_INTRO);
                break;
            case R.id.textViewPartTwoIntro:
                callIntroductionFragment("Part 2 | Introduction", AppConstants.WORKBOOK_PART2_INTRO);
                break;
            case R.id.textViewEpilogue:
                callIntroductionFragment("Part 2 | Epilogue", AppConstants.WORKBOOK_EPILOGUE);
                break;
        }
    }


    /*method to open introduction fragment
    * */
    private void callIntroductionFragment(String title, String id) {
        FragmentIntroduction fragmentIntroduction = new FragmentIntroduction();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE, title);
        bundle.putString(Keys.TOPIC_ID, id);
        bundle.putString("flag","1");
        fragmentIntroduction.setArguments(bundle);
        UtilsMethods.replaceFragment(getActivity(), fragmentIntroduction);
    }

    private void callWorkbookLessonsWebservice() {



        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.TYPE, Keys.WORKBOOK);

        final retrofit.Call<GetWorkbookLessonResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getWorkbookLessonResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call,true, new Callback<GetWorkbookLessonResponse>() {

            @Override
            public void onResponse(Response<GetWorkbookLessonResponse> response, Retrofit retrofit) {


                GetWorkbookLessonResponse getWorkbookLessonResponse = response.body();
                if (getWorkbookLessonResponse.getSuccess().equals("true")) {
                    if (!getWorkbookLessonResponse.getData().isEmpty()) {
                        layout_workbook.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.GONE);
                        dataChildBeanListOne = getWorkbookLessonResponse.getData().get(0).getDatachild();
                        dataChildBeanListTwo = getWorkbookLessonResponse.getData().get(1).getDatachild();

                       listDataHeader = new ArrayList<>();
                        listDataChild = new HashMap<>();

                        for (int i = 0; i < dataChildBeanListOne.size(); i++) {

                            listDataHeader.add(dataChildBeanListOne.get(i).getLesson_name());
                            List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> child_list = dataChildBeanListOne.get(i).getLesson_topic();
                            listDataChild.put(dataChildBeanListOne.get(i).getLesson_name(), child_list);
                        }


                        listDataHeader1 = new ArrayList<>();
                        listDataChild1 = new HashMap<>();
                        for (int i = 0; i < dataChildBeanListTwo.size(); i++) {
                            listDataHeader1.add(dataChildBeanListTwo.get(i).getLesson_name());

                            List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> child_list = dataChildBeanListTwo.get(i).getLesson_topic();
                            listDataChild1.put(listDataHeader1.get(i), child_list);
                        }


                        expandableListAdapterPartOne = new ExpandableListAdapter(
                                WorkbookPartFragment.this,getActivity(),
                                listDataHeader, listDataChild,1);
                        expendableListViewPartOne.setAdapter(expandableListAdapterPartOne);
                        expandableListAdapterPartTwo = new ExpandableListAdapter(
                                WorkbookPartFragment.this,getActivity(),
                                listDataHeader1, listDataChild1,2);
                        expendableListViewPartTwo.setAdapter(expandableListAdapterPartTwo);

                        expendableListViewPartOne.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                                if (lastExpandedPosition != -1
                                        && groupPosition != lastExpandedPosition) {
                                    expendableListViewPartOne.collapseGroup(lastExpandedPosition);
                                }
                                lastExpandedPosition = groupPosition;
                            }
                        });
                        expendableListViewPartTwo.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                            @Override
                            public void onGroupExpand(int groupPosition) {
                                if (lastExpandedPosition1 != -1
                                        && groupPosition != lastExpandedPosition1) {
                                    expendableListViewPartTwo.collapseGroup(lastExpandedPosition1);
                                }
                                lastExpandedPosition1 = groupPosition;
                            }
                        });

                    } else {
                        UtilsMethods.getToast(getActivity(),"No data found.");
                       // UtilsMethods.showToast(getActivity(), "No data found.");
                        layout_workbook.setVisibility(View.VISIBLE);
                        retry.setVisibility(View.GONE);

                    }

                } else {
                    UtilsMethods.getToast(getActivity(),getWorkbookLessonResponse.getMsg());
                   // UtilsMethods.showToast(getActivity(), getWorkbookLessonResponse.getMsg());
                    layout_workbook.setVisibility(View.VISIBLE);
                    retry.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Throwable t) {
                layout_workbook.setVisibility(View.INVISIBLE);
                retry.setVisibility(View.VISIBLE);
                Log.e("Error", "Error-" + t.getMessage());

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 147) {
            if (resultCode == Activity.RESULT_OK) {
                if(data.getIntExtra(Keys.PART_NUMBER, -1)==1) {
                    dataChildBeanListOne.get(data.getIntExtra(Keys.GROUP_POSITION, -1))
                            .setIsBookMarked(true);
                    HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                    hashMap.put(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID,  dataChildBeanListOne.
                            get(data.getIntExtra(Keys.GROUP_POSITION, -1)).getLesson_name());
                    User.getInstance(getContext()).saveBookmarkData(hashMap);

                }
                else {
                    dataChildBeanListTwo.get(data.getIntExtra(Keys.GROUP_POSITION, -1))
                            .setIsBookMarked(true);
                    HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                    hashMap.put(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID,  dataChildBeanListTwo.
                            get(data.getIntExtra(Keys.GROUP_POSITION, -1)).getLesson_name());
                    User.getInstance(getContext()).saveBookmarkData(hashMap);

                }
                expandableListAdapterPartTwo.notifyDataSetChanged();
                expandableListAdapterPartOne.notifyDataSetChanged();
            }
        }
    }

}
