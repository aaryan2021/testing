package com.infoicon.acim.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.SectionParameters;
import com.infoicon.acim.utils.StatelessSection;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.workbook.WorkBookLessonFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sumit on 2/9/17.
 */

public class WorkbookLessonListAdapter extends StatelessSection {

    private String title;
    private List<GetWorkbookLessonResponse.DataBean.DatachildBean> dataChildBeanList;
    private FragmentActivity activity;

    public WorkbookLessonListAdapter(FragmentActivity activity,String title, List<GetWorkbookLessonResponse.DataBean.DatachildBean> dataChildBeanList) {
        super(new SectionParameters.Builder(R.layout.list_item_workbook_child)
                .headerResourceId(R.layout.list_item_workbook_header)
                .build());
        this.activity=activity;
        this.title = title;
        this.dataChildBeanList = dataChildBeanList;
    }


    @Override
    public int getContentItemsTotal() {
        return dataChildBeanList.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        final String name = dataChildBeanList.get(position).getLesson_name();

        itemHolder.textViewLesson.setText(name);


        itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                             WorkBookLessonFragment workBookLessonFragment=new WorkBookLessonFragment();
                Bundle bundle=new Bundle();
                bundle.putString(Keys.TITLE,name);
                bundle.putParcelableArrayList("array", (ArrayList<? extends Parcelable>) dataChildBeanList.get(position).getLesson_topic());
               ;
                workBookLessonFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, workBookLessonFragment);
             //   Toast.makeText(activity, "Clicked on position id"+dataChildBeanList.get(position).getLesson_name(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.textViewHeading.setText(title);
    }


    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final CustomTextView textViewHeading;

        HeaderViewHolder(View view) {
            super(view);

            textViewHeading = (CustomTextView) view.findViewById(R.id.textViewHeading);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final CustomTextView textViewLesson;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            textViewLesson = (CustomTextView) view.findViewById(R.id.textViewLesson);
        }

}
}