package com.example.shixian;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.shixian.adapter.ViewPagerAdapter;
import com.example.shixian.fragment.LoginFragment;
import com.example.shixian.fragment.RegisterFragment;
import com.example.shixian.utils.FastBlurUtil;

/**
 * Created by admin on 2017/11/5.
 */

public class UserLoginActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView loginbg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        viewPager = findViewById(R.id.user_viewpager);
        tabLayout = findViewById(R.id.user_tablayout);
        loginbg = findViewById(R.id.login_bg);

        initViewPager();
        initTabLayout();
        BlurImage();
        FitsStatusBar();

        // 状态栏半透明
//        if(Build.VERSION.SDK_INT>=19){
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        }
        //状态栏透明
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void BlurImage() {

        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.loginbg);
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 2, 8);
        loginbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        loginbg.setImageBitmap(blurBitmap);
    }

    private void FitsStatusBar() {

        int statusbarHeight = 0;

        //通过反射获取状态栏高度
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusbarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        //设置占位View的高度
        View statusBar = findViewById(R.id.user_StatusbarView);
        ViewGroup.LayoutParams layoutParams = statusBar.getLayoutParams();
        layoutParams.height = statusbarHeight;

    }

    private void initViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LoginFragment());
        adapter.addFragment(new RegisterFragment());
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
