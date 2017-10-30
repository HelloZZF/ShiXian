package com.example.shixian.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.shixian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class HomePageFragment extends Fragment {

    SearchView mSearchView;

    private List<item1> myItem1 = new ArrayList<>();

    private List<item5> myItem5 = new ArrayList<>();

    Activity activity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = getActivity();
//        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_home_page, container, false);

        initMyItem1();
        initMyItem5();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.home_item);
        LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
        recyclerView.setLayoutManager(layoutManager);
        HomeAdapter homeAdapter = new HomeAdapter(myItem1, myItem5, activity);
        recyclerView.setAdapter(homeAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar();
    }

    public Toolbar initToolbar() {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) activity;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.home_toolbar);
        toolbar.inflateMenu(R.menu.home_toolbar);
        toolbar.setNavigationIcon(R.drawable.classify);
//        mAppCompatActivity.setSupportActionBar(toolbar);
//        ActionBar actionBar = mAppCompatActivity.getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.classify);
//            actionBar.setDisplayShowTitleEnabled(false);
//        }
        return toolbar;
    }

//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.home_toolbar, menu);
//        MenuItem searchItem = menu.findItem(R.id.home_search);
//        mSearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
//        mSearchView.setIconifiedByDefault(false);
//        mSearchView.setQueryHint("输入菜品或用户");
//        mSearchView.setMaxWidth(680);
//    }

    private void initMyItem1() {
        item1 Item1 = new item1("全职爸爸","混炒肉丝",R.drawable.item1_1,R.drawable.item1_4);
        item1 Item2 = new item1("Holle Word","秘制炒蛋",R.drawable.item1_2,R.drawable.item1_5);
        item1 Item3 = new item1("宝宝","养生鱼汤",R.drawable.item1_3,R.drawable.item1_6);
        myItem1.add(Item1);
        myItem1.add(Item2);
        myItem1.add(Item3);
    }

    private void initMyItem5() {
        item5 Item1 = new item5(R.drawable.item5_1,"蒜炒五花肉","21¥");
        item5 Item2 = new item5(R.drawable.item5_2,"笋干鸭胗","27¥");
        item5 Item3 = new item5(R.drawable.item5_3,"干菇牛肉","25¥");
        item5 Item4 = new item5(R.drawable.item5_4,"清蒸黄金丝","19¥");
        item5 Item5 = new item5(R.drawable.item5_5,"栗子鸡肉","23¥");
        item5 Item6 = new item5(R.drawable.item5_6,"麻辣滑鸡","30¥");
        item5 Item7 = new item5(R.drawable.item5_7,"红烧鸡块","26¥");
        item5 Item8 = new item5(R.drawable.item5_8,"培根包菜","18¥");
        myItem5.add(Item1);
        myItem5.add(Item2);
        myItem5.add(Item3);
        myItem5.add(Item4);
        myItem5.add(Item5);
        myItem5.add(Item6);
        myItem5.add(Item7);
        myItem5.add(Item8);
    }

}
