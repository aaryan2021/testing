package com.infoicon.acim.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.workbook.WorkBookLessonFragment;
import com.infoicon.acim.workbook.WorkbookLessonDescFragment;

import java.util.ArrayList;

/**
 * Created by sumit on 2/9/17.
 */

public class WorkBookLessonAdapter extends RecyclerView.Adapter<WorkBookLessonAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    ArrayList<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> lessonTopicBeen;
    private RecyclerViewClickListener itemListener;
    WorkBookLessonFragment workBookLessonFragment;
    public WorkBookLessonAdapter(WorkBookLessonFragment workBookLessonFragment,FragmentActivity activity, ArrayList<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> lessonTopicBeen){
        this.activity=activity;
        this.lessonTopicBeen=lessonTopicBeen;
        this.workBookLessonFragment=workBookLessonFragment;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
       ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        viewHolder.name.setText(lessonTopicBeen.get(position).getName());
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                WorkbookLessonDescFragment workbookLessonDescFragment=new WorkbookLessonDescFragment();
                Bundle bundle=new Bundle();
                workbookLessonDescFragment.setTargetFragment(workbookLessonDescFragment,147);

                bundle.putString(Keys.TITLE,lessonTopicBeen.get(position).getName());
                bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) lessonTopicBeen);
                bundle.putInt(Keys.POSITION,position);
                workbookLessonDescFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, workbookLessonDescFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lessonTopicBeen.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(CustomTextView) itemView.findViewById(R.id.textViewChapterNo);

        }
    }


}
