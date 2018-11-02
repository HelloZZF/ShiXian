package com.example.shixian.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import com.example.shixian.MainActivity;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.UserLoginActivity;
import com.example.shixian.widget.MyScrollView;
import com.example.shixian.R;
import com.example.shixian.widget.myToolbar;

/**
 * Created by admin on 2017/11/13.
 */

public class DetailsFragment extends BaseFragment{

    private FloatingActionButton fab;
    private MyScrollView scrollView;
    private RelativeLayout relativeLayout;
    private myToolbar mToolbar;


    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_details, container, false);
        fab = view.findViewById(R.id.fab);
        scrollView = view.findViewById(R.id.details_scroll);
        relativeLayout = view.findViewById(R.id.details_relative);
        mToolbar = getActivity().findViewById(R.id.commodity_details_toolbar);

        return view;
    }

    @Override
    public void init() {
        setFab();
    }


    private void setFab() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = relativeLayout.getHeight() - scrollView.getChildAt(0).getHeight();
                scrollView.scrollTo(0, y);
            }
        });

        scrollView.setScrollViewListener(new MyScrollView.ScrollViewListener() {
            @Override
            public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
                if (y == -relativeLayout.getHeight() + scrollView.getChildAt(0).getHeight())
                    fab.setVisibility(View.VISIBLE);
                else
                    fab.setVisibility(View.GONE);
            }
        });
    }
}
