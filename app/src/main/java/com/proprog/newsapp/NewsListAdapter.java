package com.proprog.newsapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by mohamedAHMED on 2017-11-16.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ItemViewHolder> {

    private ArrayList<News> newses;
    private Context context;

    public NewsListAdapter(Context context, ArrayList<News> newses) {
        this.newses = newses;
        this.context = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(
                LayoutInflater.from(context)
                        .inflate(R.layout.news_list_item, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        News news = newses.get(position);
        String title = news.getWebTitle();
        String section = news.getSectionName();
        String date = news.getWebPublicationDate();
        String author = news.getAuthorName();
        final String url = news.getWebUrl();

        holder.newsTitleTV.setText(title);
        holder.newsSectionTV.setText(section);
        holder.newsDateTV.setText(date);
        if (TextUtils.isEmpty(author)) {
            holder.newsAuthorTV.setVisibility(View.GONE);
        } else {
            holder.newsAuthorTV.setTypeface(Typeface.createFromAsset(context.getApplicationContext().getAssets(),"fonts/dancing_script_regular.ttf"));
            holder.newsAuthorTV.setVisibility(View.VISIBLE);
        }
        holder.newsAuthorTV.setText(author);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("News", url);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.newses.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_title_tv)
        TextView newsTitleTV;
        @BindView(R.id.news_section_tv)
        TextView newsSectionTV;
        @BindView(R.id.news_date_tv)
        TextView newsDateTV;
        @BindView(R.id.news_author_tv)
        TextView newsAuthorTV;
        private View view;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.view = itemView;
        }

    }
}
