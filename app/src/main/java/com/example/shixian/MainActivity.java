package com.example.shixian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.shixian.classify.ClassifyFragment;
import com.example.shixian.homepage.HomePageFragment;
import com.example.shixian.shopcart.ShopCartFragment;
import com.example.shixian.user.UserFragment;
import com.example.shixian.user.UserLoginActivity;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    private MenuItem menuItem;

    private static int whetherLogin = 0;

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom);
        initBottomNavigationView();
        initViewPager();


        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        //从一个活动的Fragment跳转到另一个活动的Fragment
        int id = getIntent().getIntExtra("id",0);
        if (id == 1) {
            viewPager.setCurrentItem(3);
        }

    }

    private void initBottomNavigationView() {

        disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_home_page:
                                viewPager.setCurrentItem(0);
                                break;
                            case R.id.menu_classify:
                                viewPager.setCurrentItem(1);
                                break;
                            case R.id.menu_shop_cart:
                                viewPager.setCurrentItem(2);
                                break;
                            case R.id.menu_user:
                                if (whetherLogin == 0){
                                    whetherLogin = 1;
                                    bottomNavigationView.getMenu().getItem(3).setChecked(true);
                                    Intent intent = new Intent(MainActivity.this, UserLoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    viewPager.setCurrentItem(3);
                                }
                                break;
                        }
                        return false;
                    }
                });

    }

    private void initViewPager() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//                if (menuItem != null) {
//                    menuItem.setChecked(false);
//                } else {
//                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                }
                menuItem = bottomNavigationView.getMenu().getItem(position);
                menuItem.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //禁止ViewPager滑动
//        viewPager.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return true;
//            }
//        });

        setupViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new HomePageFragment());
        adapter.addFragment(new ClassifyFragment());
        adapter.addFragment(new ShopCartFragment());
        adapter.addFragment(new UserFragment());
        viewPager.setAdapter(adapter);
    }

//用反馈设置bottomNaviggation的动画
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
