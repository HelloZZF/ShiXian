package com.example.shixian;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_news;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.News;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class NewsActivity extends BaseActivity {

    private View statusBar;
    private myToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private MaterialRefreshLayout mRefreshLayout;
    private RVSimpleAdapter mAdapter;
    private List<News> mNews = new ArrayList<>();
    private int pagesize = 6;
    private int curpage = 1;
    private int type = 0;
    private int flag = 1;
    private int BeforeNewsSize = 0;

    private  static final int STATE_NORMAL = 0;
    private  static final int STATE_REFREH = 1;
    private  static final int STATE_MORE = 2;
    private  int state = STATE_NORMAL;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        statusBar = findViewById(R.id.news_StatusbarView);
        mToolbar = findViewById(R.id.news_toolbar);
        mRecyclerView = findViewById(R.id.news_recyclerview);
        mRefreshLayout = findViewById(R.id.news_refresh);

        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        setStatusbar();
        FitsStatusBar(statusBar);
        initToolbar();
        initNews();
        initRefresh();
    }

    private void initToolbar() {

        type = getIntent().getIntExtra("type", 0);
        if (type == 1) {
            mToolbar.setTitle("我为你搭");
        }else if (type == 2) {
            mToolbar.setTitle("潮流资讯");
        }
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsActivity.this.finish();
            }
        });
    }

    private void initRefresh() {

        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Refresh();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                LoadMore();
                if (curpage > 10 && flag == 1){
                    flag = 0;
                    Toast.makeText(NewsActivity.this, "我是有底线的", Toast.LENGTH_SHORT).show();
                }
                mRefreshLayout.finishRefreshLoadMore();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void Refresh(){

        curpage = 1;
        state = STATE_REFREH;
        mNews.clear();
        initNews();

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void LoadMore(){

        curpage++;
        BeforeNewsSize = mNews.size();
        state = STATE_MORE;
        initNews();
    }

    private void ShowNews() {

        switch (state){

            case STATE_NORMAL:

                if (mAdapter == null) {

                    mAdapter = new RVSimpleAdapter();
                    mAdapter.OnlyOneItem(new cell_news(mNews), mNews.size());
                    mAdapter.setOnItemClickListener(new RVBaseAdapter.OnItemClickListener() {
                        @Override
                        public void OnItemClick(View view, int position) {
                            News news = mNews.get(position);
                            Intent intent = new Intent(NewsActivity.this, NewsDetailsActivity.class);
                            intent.putExtra("news", news);
                            startActivity(intent);
                        }
                    });
                    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                    mRecyclerView.setAdapter(mAdapter);
                }
                break;

            case STATE_REFREH:

                mAdapter.clear();
                mAdapter.OnlyOneItem(new cell_news(mNews), mNews.size());
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:

                List<cell_news> cells = new ArrayList<>();
                for (int i = 0; i < mNews.size()-BeforeNewsSize; i++){

                    cells.add(new cell_news(mNews));
                }
                if (cells != null && cells.size() > 0)
                    mAdapter.addAll(BeforeNewsSize, cells);
                mRefreshLayout.finishRefreshLoadMore();
                break;

            default:
                break;
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initNews() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.NEWS_GET)
                .addParams("pagesize", pagesize)
                .addParams("curpage", curpage)
                .addParams("type", type)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<News>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<News> news) {

                        int size = news.getData().size();
                        List<News> newsList = news.getData();
                        if (size != 0) {

                            for (int i = 0; i < newsList.size(); i++) {
                                mNews.add(newsList.get(i));
                            }
                            ShowNews();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }
}
