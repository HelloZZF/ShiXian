package com.example.shixian.classify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shixian.R;

/**
 * Created by admin on 2017/9/2.
 */

public class ClassifyFragment extends Fragment {

    private String toolsList[];
    private TextView toolsTextViews[];
    private View views[];
    private ScrollView scrollView;
    private int scrollViewWidth = 0, scrollViewMiddle = 0;
    private ViewPager goods_pager;
    private int currentItem = 0;
    private ShopAdapter shopAdapter;
    private View view;
    private AppCompatActivity mAppCompatActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        view = inflater.inflate(R.layout.fragment_classify, container, false);
        scrollView = (ScrollView) view.findViewById(R.id.classify_scrollview);
        shopAdapter = new ShopAdapter(getChildFragmentManager());
        mAppCompatActivity = (AppCompatActivity) getActivity();

        toolsList = new String[]{"推荐","销量排行","每周新品","荤菜","素菜","荤素搭配","汤类","家常菜","儿童专区","水产海鲜","点心速食","当季蔬菜","当地特色"};
        LinearLayout toolsLayout = (LinearLayout) view.findViewById(R.id.tools);
        toolsTextViews = new TextView[toolsList.length];
        views = new View[toolsList.length];
        for (int i = 0; i < toolsList.length; i++) {
            View fcview = inflater.inflate(R.layout.classify_tools_layout, container, false);
            fcview.setId(i);
            fcview.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    goods_pager.setCurrentItem(v.getId());
                }
            });
            TextView textView = (TextView) fcview.findViewById(R.id.text);
            textView.setText(toolsList[i]);
            toolsLayout.addView(fcview);
            toolsTextViews[i] = textView;
            views[i] = fcview;
        }

        changeTextColor(2);

        goods_pager = (ViewPager) view.findViewById(R.id.goods_pager);
        goods_pager.setAdapter(shopAdapter);
        goods_pager.setOnPageChangeListener(onPageChangeListener);

        return view;
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener(){
        @Override
        public void onPageSelected(int position) {
            if (goods_pager.getCurrentItem() != position) {
                goods_pager.setCurrentItem(position);
            }
            if (currentItem != position) {
                changeTextColor(position);
                changeTextLocation(position);
            }
            currentItem = position;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    private void changeTextColor(int id) {
        for (int i = 0; i < toolsTextViews.length; i++) {
            if (i != id) {
                toolsTextViews[i].setBackgroundResource(android.R.color.white);
                toolsTextViews[i].setTextColor(0xff000000);
            }
        }
        toolsTextViews[id].setBackgroundColor(0xfff1f5f6);
        toolsTextViews[id].setTextColor(0xffff5d5e);
    }

    private void changeTextLocation(int clickPosition) {

        int x = (views[clickPosition].getTop() - getScrollViewMiddle() + (getViewheight(views[clickPosition]) / 2));
        scrollView.smoothScrollTo(0, x);
    }

    private int getScrollViewMiddle() {
        if (scrollViewMiddle == 0)
            scrollViewMiddle = getScrollViewheight() / 2;
        return scrollViewMiddle;
    }

    private int getScrollViewheight() {
        if (scrollViewWidth == 0)
            scrollViewWidth = scrollView.getBottom() - scrollView.getTop();
        return scrollViewWidth;
    }

    private int getViewheight(View view) {
        return view.getBottom() - view.getTop();
    }

    private class ShopAdapter extends FragmentPagerAdapter {

        public ShopAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new ClassifyVpFragment();
            return fragment;
        }

        @Override
        public int getCount() {
            return toolsList.length;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar("");
    }

    public Toolbar initToolbar(CharSequence title) {
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.classify_toolbar);
        toolbar.setTitle(title);
        toolbar.inflateMenu(R.menu.classify_toolbar);
        toolbar.setNavigationIcon(R.drawable.classify);
        return toolbar;
    }

}
