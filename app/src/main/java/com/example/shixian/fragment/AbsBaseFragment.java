package com.example.shixian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.shixian.R;
import com.example.shixian.adapter.base.Cell;
import com.example.shixian.adapter.base.RVSimpleAdapter;

import java.util.List;

/**
 * Created by zhouwei on 17/2/3.
 */

public abstract class AbsBaseFragment<T> extends Fragment {

    public static final String TAG = "AbsBaseFragment";
    protected RecyclerView mRecyclerView;
    protected RVSimpleAdapter mBaseAdapter;
    private FrameLayout mToolbarContainer;
    protected MaterialRefreshLayout mRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.rv_base_fragment_layout,null);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.base_refresh_layout);
        mToolbarContainer = (FrameLayout) view.findViewById(R.id.toolbar_container);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.base_fragment_rv);
        mRecyclerView.setLayoutManager(initLayoutManger());
        mBaseAdapter = initAdapter();
        mRecyclerView.setAdapter(mBaseAdapter);

        mRefreshLayout.setWaveColor(0xfff1f1f1);
        mRefreshLayout.setIsOverLay(false);
        mRefreshLayout.setWaveShow(true);
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                onPullRefresh();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                onLoadMore();
            }
        });

        View toolbarView = addToolbar();
        if(toolbarView!=null && mToolbarContainer!=null
                ){
            mToolbarContainer.addView(toolbarView);
        }
        onRecyclerViewInitialized();

    }


    /**
     * 子类可以自己指定Adapter,如果不指定默认RVSimpleAdapter
     * @return
     */
    protected RVSimpleAdapter initAdapter(){
        return new RVSimpleAdapter();
    }

    /**
     * 子类自己指定RecyclerView的LayoutManager,如果不指定，默认为LinearLayoutManager,VERTICAL 方向
     * @return
     */
    protected RecyclerView.LayoutManager initLayoutManger(){
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        return manager;
    }

    /**
     * 添加TitleBar
     * @param
     */
    public View addToolbar(){
      //如果需要Toolbar,子类返回Toolbar View
      return null;
    }

    /**
     *RecyclerView 初始化完毕，可以在这个方法里绑定数据
     */
    public abstract void onRecyclerViewInitialized();

    /**
     * 下拉刷新
     */
    public abstract void onPullRefresh();

    /**
     * 上拉加载更多
     */
    public abstract void onLoadMore();

    /**
     *  根据实体生成对应的Cell
     * @param list  实体列表
     * @return cell列表
     */
    protected abstract List<Cell> getCells(List<T> list);

}
