package com.infoicon.acim.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.text.TextChapterDescFragment;
import com.infoicon.acim.text.TextChapterFragment;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

/**
 * Created by sumit on 5/9/17.
 */

public class TextChapterAdapter extends RecyclerView.Adapter<TextChapterAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private List<GetChapterApiResponse.DataBean.ChaptersBean.TopicsBean> lessonTopicBeen;
    private RecyclerViewClickListener itemListener;
    private TextChapterFragment textChapterFragment;

    public TextChapterAdapter(FragmentActivity activity, TextChapterFragment textChapterFragment, List<GetChapterApiResponse.DataBean.ChaptersBean.TopicsBean> lessonTopicBeen) {
        this.activity = activity;
        this.textChapterFragment = textChapterFragment;
        this.lessonTopicBeen = lessonTopicBeen;
    }

    public void setListener(RecyclerViewClickListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public TextChapterAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER_CONTENT_DESCRIPTION) != null){
            if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER_CONTENT_DESCRIPTION).equalsIgnoreCase(lessonTopicBeen.get(position).getId())) {
                viewHolder.bookmark_icon.setVisibility(View.VISIBLE);
                viewHolder.bookmark_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bookmark_icon));
            } else {
                viewHolder.bookmark_icon.setVisibility(View.GONE);
                viewHolder.bookmark_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bookmark_icon_white));
            }
        }

        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //itemListener.recyclerViewListClicked(v,position);
                TextChapterDescFragment textChapterDescFragment = new TextChapterDescFragment();
                textChapterDescFragment.setTargetFragment(textChapterFragment, 145);
                Bundle bundle = new Bundle();
                bundle.putString("FLAG","0");
                bundle.putString(Keys.TITLE, lessonTopicBeen.get(position).getName());
                bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) lessonTopicBeen);
                bundle.putInt(Keys.POSITION, position);

                textChapterDescFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, textChapterDescFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lessonTopicBeen.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView name;
        private ImageView bookmark_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewChapterNo);
            bookmark_icon = itemView.findViewById(R.id.bookmark_icon);


        }
    }
}
