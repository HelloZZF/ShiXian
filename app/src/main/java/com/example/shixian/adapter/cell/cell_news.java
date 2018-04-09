package com.example.shixian.adapter.cell;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.shixian.NewsActivity;
import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/20.
 */

public class cell_news extends RVBaseCell<Object> {

    public static final int Type = 44;
    private Context mContext;
    private List<News> mNews;

    public cell_news(List<News> news) {
        super(news);
        mNews = news;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_news_layout, parent, false);
        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        News news = mNews.get(position);
        Picasso.with(mContext)
                .load(news.getImageurl()).into(holder.getImageView(R.id.news_image));
        holder.getTextView(R.id.news_title).setText(news.getTitle());
        holder.getTextView(R.id.news_content).setText(news.getHead());

    }
}
