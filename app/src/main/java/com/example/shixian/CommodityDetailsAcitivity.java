package com.example.shixian;


import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.shixian.adapter.ViewPagerAdapter;
import com.example.shixian.fragment.CommodityFragment;
import com.example.shixian.fragment.DetailsFragment;
import com.example.shixian.fragment.EvaluationFragment;


public class CommodityDetailsAcitivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commodity_details);

        tabLayout = (TabLayout) findViewById(R.id.commdetails_tablayout);
        viewPager = (ViewPager) findViewById(R.id.commdetails_viewpager);

        initTabLayout();
        initViewPager();

        if(Build.VERSION.SDK_INT>=19){
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


    }


    private void initViewPager() {

//        用inflatr得到的布局文件是重新渲染的文件，这里用fragment的getview得到的是这个碎片的view，不过报空指针。。。
//        CommodityFragment commodityFragment = new CommodityFragment();
//        View v = commodityFragment.getView();
//        ScrollView scrollView = (ScrollView) v.findViewById(R.id.commodity_scroll);
//        scrollView.setBackgroundColor(0xffc2c2c2);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CommodityFragment());
        adapter.addFragment(new DetailsFragment());
        adapter.addFragment(new EvaluationFragment());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initTabLayout() {

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
