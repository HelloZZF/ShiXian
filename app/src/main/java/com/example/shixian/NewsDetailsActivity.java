package com.example.shixian;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shixian.bean.News;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

public class NewsDetailsActivity extends BaseActivity {

    private TextView mTextView;
    private Toolbar mToolbar;
    private ImageView mImageView;
    private News mNews;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        mTextView = findViewById(R.id.news_content_text);
        mToolbar = findViewById(R.id.toolbar);
        mImageView = findViewById(R.id.news_image_view);

        Serializable serializable = getIntent().getSerializableExtra("news");
        if (serializable == null){
            finish();
        }else{
            mNews = (News) serializable;
        }
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void init() {

        setStatusbar();
        mTextView.setText(mNews.getContent());
        Picasso.with(this).load(mNews.getImageurl()).into(mImageView);
        mToolbar.setTitle(mNews.getTitle());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailsActivity.this.finish();
            }
        });
    }
}
