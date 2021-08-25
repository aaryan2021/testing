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
import com.infoicon.acim.bean.getBookmarkResponse;
import com.infoicon.acim.settings.BookMark;
import com.infoicon.acim.text.TextChapterDescFragment;
import com.infoicon.acim.text.TextChapterFragment;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

public class BookmarkAdapter extends RecyclerView.Adapter<BookmarkAdapter.ViewHolder> {

    private BookmarkAdapter.ViewHolder viewHolder;
    private FragmentActivity activity;
    private List<getBookmarkResponse.BookmarkData.chapter_array>chapter_arrays=new ArrayList<>();
    private List<getBookmarkResponse.BookmarkData> lessonTopicBeen;
    private RecyclerViewClickListener itemListener;


    public BookmarkAdapter(FragmentActivity activity, List<getBookmarkResponse.BookmarkData> lessonTopicBeen) {
        this.activity = activity;
        this.lessonTopicBeen = lessonTopicBeen;
    }

    public void setListener(RecyclerViewClickListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public BookmarkAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        BookmarkAdapter.ViewHolder viewHolder = new BookmarkAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BookmarkAdapter.ViewHolder holder, final int position) {
        viewHolder = holder;
        viewHolder.name.setText(lessonTopicBeen.get(position).getName());
        viewHolder.id.setVisibility(View.VISIBLE);
        viewHolder.id.setText(lessonTopicBeen.get(position).getChapter_id());
        if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER_CONTENT_DESCRIPTION) != null){
            if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER_CONTENT_DESCRIPTION).equalsIgnoreCase(lessonTopicBeen.get(position).getChapter_id())) {
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
                chapter_arrays=lessonTopicBeen.get(position).chapter_arrays;
                TextChapterDescFragment textChapterDescFragment = new TextChapterDescFragment();
                Bundle bundle = new Bundle();
                bundle.putString("FLAG","1");
                bundle.putString(Keys.TITLE, lessonTopicBeen.get(position).getName());
                bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) lessonTopicBeen);
                bundle.putParcelableArrayList(Keys.Chapter_ARRAY, (ArrayList<? extends Parcelable>)chapter_arrays);
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
        private CustomTextView name,id;
        private ImageView bookmark_icon;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.textViewChapterNo);
            id=itemView.findViewById(R.id.textViewChapterid);
            bookmark_icon = itemView.findViewById(R.id.bookmark_icon);


        }
    }
}
