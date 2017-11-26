package com.example.shixian;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by admin on 2017/11/13.
 */

public class DetailsFragment extends Fragment{

    FloatingActionButton fab;
    MyScrollView scrollView;
    RelativeLayout relativeLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        scrollView = (MyScrollView) view.findViewById(R.id.details_scroll);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.details_relative);

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


        return view;
    }
}
