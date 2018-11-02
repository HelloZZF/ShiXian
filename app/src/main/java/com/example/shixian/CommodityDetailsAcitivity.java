package com.example.shixian;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.annotation.StyleableRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.shixian.adapter.ViewPagerAdapter;
import com.example.shixian.bean.Wares;
import com.example.shixian.fragment.CommodityFragment;
import com.example.shixian.fragment.DetailsFragment;
import com.example.shixian.fragment.EvaluationFragment;
import com.example.shixian.widget.myToolbar;
import com.mob.MobSDK;

import java.io.Serializable;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;


public class CommodityDetailsAcitivity extends BaseActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private myToolbar mToolbar;
    private Wares mWares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ShiXianApplication.getInstance().getUser() == null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("VPid", 0);
            ShiXianApplication.getInstance().putIntent(intent);
            Intent LoginIntent = new Intent(this, UserLoginActivity.class);
            CommodityDetailsAcitivity.this.finish();
            startActivity(LoginIntent);
        }
        setContentView(R.layout.activity_commodity_details);

        //MobSDK.init(this, "2451f719bc5c6", "fdc7201f8821fc30e50158294d3b5d1d");
        tabLayout = findViewById(R.id.commdetails_tablayout);
        viewPager = findViewById(R.id.commdetails_viewpager);
        mToolbar = findViewById(R.id.commodity_details_toolbar);

        Serializable serializable = getIntent().getSerializableExtra("ware");
        if (serializable == null){
            finish();
        }else{
            mWares = (Wares) serializable;
        }

        init();

//        if(Build.VERSION.SDK_INT>=19){
//            //设置status为半透明
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }


    }

    private void init() {

        setStatusbar();
        initTabLayout();
        initViewPager();
        initToolbar();
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommodityDetailsAcitivity.this.finish();
            }
        });

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(mWares.getName());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        //oks.setTitleUrl("http://www.baidu.com");
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWares.getName());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        //oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(mWares.getImageurl());
        // url仅在微信（包括好友和朋友圈）中使用
        //oks.setUrl("http://www.baidu.com");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        //oks.setComment("mWares.getName()");
        // site是分享此内容的网站名称，仅在QQ空间使用
        //oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        //oks.setSiteUrl("http://www.baidu.com");

        // 启动分享GUI
        oks.show(this);
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
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {

                    mToolbar.setBackgroundColor(0x73000000);
                    getWindow().setStatusBarColor(Color.TRANSPARENT);

                }else if (tab.getPosition() == 1) {

                    mToolbar.showTabLayout();
                    mToolbar.setBackgroundColor(0x73000000);
                    getWindow().setStatusBarColor(Color.TRANSPARENT);
                }
                else if (tab.getPosition() == 2) {

                    mToolbar.setBackgroundColor(Color.WHITE);
                    //mToolbar.getTabLayout().setTabTextColors(0xffC2C2C2, 0xff05d712);
                    mToolbar.showTabLayout();
                    getWindow().setStatusBarColor(0x59000000);

                }

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
