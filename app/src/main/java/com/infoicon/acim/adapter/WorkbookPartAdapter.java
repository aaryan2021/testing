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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumit on 16/9/17.
 */

public class WorkbookPartAdapter extends RecyclerView.Adapter<WorkbookPartAdapter.ViewHolder>{

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private List<GetWorkbookLessonResponse.DataBean.DatachildBean> dataChildBeanList;
    private RecyclerViewClickListener itemListener;

    public WorkbookPartAdapter(FragmentActivity activity, List<GetWorkbookLessonResponse.DataBean.DatachildBean> dataChildBeanList){
        this.activity=activity;
        this.dataChildBeanList=dataChildBeanList;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_workbook_child, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
          ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;

        final String name = dataChildBeanList.get(position).getLesson_name();

        viewHolder.textViewLesson.setText(name);


        viewHolder.textViewLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkBookLessonFragment workBookLessonFragment=new WorkBookLessonFragment();
                Bundle bundle=new Bundle();
                bundle.putString(Keys.TITLE,name);
                bundle.putParcelableArrayList("array", (ArrayList<? extends Parcelable>) dataChildBeanList.get(position).getLesson_topic());

                workBookLessonFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, workBookLessonFragment);
                //   Toast.makeText(activity, "Clicked on position id"+dataChildBeanList.get(position).getLesson_name(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataChildBeanList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView textViewLesson;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewLesson=(CustomTextView) itemView.findViewById(R.id.textViewLesson);


        }
    }
}
