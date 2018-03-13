package com.example.shixian;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.shixian.adapter.ViewPagerAdapter;
import com.example.shixian.fragment.ClassifyFragment;
import com.example.shixian.fragment.HomePageFragment;
import com.example.shixian.fragment.ShopCartFragment;
import com.example.shixian.fragment.UserFragment;

import java.lang.reflect.Field;
import java.util.List;

public class MainActivity extends BaseActivity {

    private ViewPager viewPager;

    private MenuItem menuItem;

    private BottomNavigationView bottomNavigationView;

    private ShopCartFragment mShopCartFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bottom);
        initViewPager();
        initBottomNavigationView();
        setStatusbar();

        //从一个活动的Fragment跳转到另一个活动的Fragment
        int id = getIntent().getIntExtra("VPid",0);
        if (id == 3) {
            viewPager.setCurrentItem(3);
        }else if (id == 2){
            viewPager.setCurrentItem(2);
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
                                if (ShiXianApplication.getInstance().getUser() == null){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("VPid", 2);
                                    startActivity(intent, true);
                                }else{
                                    if (mShopCartFragment != null){
                                        viewPager.setCurrentItem(2);
                                        mShopCartFragment.refData();
                                    }
                                }
                                break;
                            case R.id.menu_user:
                                if (ShiXianApplication.getInstance().getUser() == null){
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("VPid", 3);
                                    startActivity(intent, true);
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
        mShopCartFragment = new ShopCartFragment();
        adapter.addFragment(mShopCartFragment);
        adapter.addFragment(new UserFragment());

        viewPager.setAdapter(adapter);
    }

//用反射设置bottomNaviggation的动画
    @SuppressLint("RestrictedApi")
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
