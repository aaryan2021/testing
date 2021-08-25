package com.infoicon.acim.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextViewBold;
import com.infoicon.acim.workbook.WorkbookLessonDescFragment;
import com.infoicon.acim.workbook.WorkbookPartFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER;
import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID;



/**
 * Created by sumit on 31/1/17.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private FragmentActivity activity;
    WorkbookPartFragment workbookPartFragment;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean>> _listDataChild;
    private int part_no;
    public ExpandableListAdapter(WorkbookPartFragment workbookPartFragment,FragmentActivity activity, List<String> listDataHeader,
                                 HashMap<String, List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean>> listChildData, int part_no) {
        this.activity = activity;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.workbookPartFragment=workbookPartFragment;
        this.part_no=part_no;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean childDataBean= (GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean) getChild(groupPosition, childPosition);
       final    List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> lessonTopicBeen =   _listDataChild.get(_listDataHeader.get(groupPosition));
        ChildViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder=new ChildViewHolder();

                    LayoutInflater infalInflater = (LayoutInflater) this.activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_workbook_child, null);
            viewHolder.textViewLessonTitle=(CustomTextViewBold) convertView.findViewById(R.id.textViewLessonTitle);
            viewHolder.imageView_bookmark=(ImageView) convertView.findViewById(R.id.imageView_bookmark);
            viewHolder.textViewLesson = (CustomTextView) convertView
                    .findViewById(R.id.textViewLesson);
            viewHolder.lesson_layout=(LinearLayout)convertView.findViewById(R.id.lesson_layout);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ChildViewHolder) convertView.getTag();
        }

        String lesson_title=childDataBean.getChapter_title();
        if(lesson_title.equals("Review I")|lesson_title.equals("Review II")|lesson_title.equals("Review III")
                |lesson_title.equals("Review IV")|lesson_title.equals("Review V")|lesson_title.equals("Review VI")|lesson_title.equals("")){
            viewHolder.textViewLessonTitle.setVisibility(View.GONE);
        }else{
            viewHolder.textViewLessonTitle.setVisibility(View.VISIBLE);
        }
        viewHolder.textViewLessonTitle.setText(lesson_title);


        if (User.getInstance(activity).getBookmarkData().get(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID) != null)
        {
            if (User.getInstance(activity).getBookmarkData().get(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID).
                    equalsIgnoreCase(childDataBean.getId())) {
                viewHolder.imageView_bookmark.setVisibility(View.VISIBLE);
                viewHolder.imageView_bookmark.setImageDrawable(activity.getResources()
                        .getDrawable(R.drawable.bookmark_icon));
            } else {
                viewHolder.imageView_bookmark.setVisibility(View.GONE);
                viewHolder.imageView_bookmark.setImageDrawable(activity.getResources()
                        .getDrawable(R.drawable.bookmark_icon_white));
            }
        }

        viewHolder.textViewLesson.setText(childDataBean.getName());
        viewHolder.lesson_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstants.PART_NUM=part_no;
                AppConstants.CLICKED_POS=childPosition;
                AppConstants.CLICKED_POS_GROUP=groupPosition;
                WorkbookLessonDescFragment workbookLessonDescFragment=new WorkbookLessonDescFragment();
                Bundle bundle=new Bundle();

                workbookLessonDescFragment.setTargetFragment(workbookPartFragment,147);

                bundle.putString(Keys.TITLE,lessonTopicBeen.get(childPosition).getChapter_title());
                bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) lessonTopicBeen);
                bundle.putInt(Keys.POSITION,childPosition);
                bundle.putInt(Keys.PART_NUMBER,part_no);
                bundle.putInt(Keys.GROUP_POSITION,groupPosition);
                workbookLessonDescFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, workbookLessonDescFragment);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        HeaderViewHolder viewHolder = null;
        String headerTitle = (String) getGroup(groupPosition);
     //   Log.e("headerTitle",headerTitle);
        if (convertView == null) {
            viewHolder=new HeaderViewHolder();
            LayoutInflater infalInflater = (LayoutInflater) this.activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_workbook_header, null);

            viewHolder.textViewHeading = (CustomTextViewBold) convertView
                    .findViewById(R.id.textViewHeading);
            viewHolder.group_indicator=(ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.imageView_bookmark=(ImageView)convertView.findViewById(R.id.imageView_bookmark);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (HeaderViewHolder) convertView.getTag();
        }

        viewHolder.textViewHeading.setText(headerTitle);


        if (User.getInstance(activity).getBookmarkData().get(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID) != null) {
            if (User.getInstance(activity).getBookmarkData().get(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID).
                    equalsIgnoreCase(headerTitle)) {
                viewHolder.imageView_bookmark.setVisibility(View.VISIBLE);
                viewHolder.imageView_bookmark.setImageDrawable(activity.getResources()
                        .getDrawable(R.drawable.bookmark_icon));
            } else {
                viewHolder.imageView_bookmark.setVisibility(View.GONE);
                viewHolder.imageView_bookmark.setImageDrawable(activity.getResources()
                        .getDrawable(R.drawable.bookmark_icon_white));
            }
        }

        if (isExpanded) {
            viewHolder.group_indicator.setImageResource(R.drawable.up_arrow);
        } else {
            viewHolder.group_indicator.setImageResource(R.drawable.down_arrow);

        }
       /* ExpandableListView eLV = (ExpandableListView) parent;
        eLV.expandGroup(groupPosition);
*/

        return convertView;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    public static class HeaderViewHolder{
        CustomTextViewBold textViewHeading;
        ImageView group_indicator;
        ImageView imageView_bookmark;

    }

    public static class ChildViewHolder{
        CustomTextViewBold  textViewLessonTitle;
        CustomTextView      textViewLesson;
        LinearLayout lesson_layout;
        ImageView imageView_bookmark;
    }
}
