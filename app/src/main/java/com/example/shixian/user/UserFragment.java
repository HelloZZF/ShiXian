package com.example.shixian.user;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shixian.R;
import com.example.shixian.util.FastBlurUtil;


/**
 * Created by admin on 2017/9/2.
 */

public class UserFragment extends Fragment {

    Activity activity;
    private ImageView userimage;
    private ImageView userbg;
    private TextView username;
    private TextView userscore;
    private ImageView usericon;
    private ImageView itemicon1;
    private ImageView itemicon1s;
    private ImageView itemicon2;
    private ImageView itemicon2s;
    private ImageView itemicon3;
    private ImageView itemicon3s;
    private ImageView itemicon4;
    private ImageView itemicon4s;
    private ImageView itemicon5;
    private ImageView itemicon5s;
    private ImageView itemicon6;
    private ImageView itemicon6s;
    private ImageView itemicon7;
    private ImageView itemicon7s;
    private ImageView itemicon8;
    private ImageView itemicon8s;
    private LinearLayout useritem1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        activity = getActivity();
        userimage = (ImageView) view.findViewById(R.id.UserImage);
        userbg = (ImageView) view.findViewById(R.id.User_bg);
        usericon = (ImageView) view.findViewById(R.id.UserIcon);
        username = (TextView) view.findViewById(R.id.UserName);
        userscore = (TextView) view.findViewById(R.id.UserScore);
        itemicon1 = (ImageView) view.findViewById(R.id.item_icon1);
        itemicon1s = (ImageView) view.findViewById(R.id.item_icon1s);
        itemicon2 = (ImageView) view.findViewById(R.id.item_icon2);
        itemicon2s = (ImageView) view.findViewById(R.id.item_icon2s);
        itemicon3 = (ImageView) view.findViewById(R.id.item_icon3);
        itemicon3s = (ImageView) view.findViewById(R.id.item_icon3s);
        itemicon4 = (ImageView) view.findViewById(R.id.item_icon4);
        itemicon4s = (ImageView) view.findViewById(R.id.item_icon4s);
        itemicon5 = (ImageView) view.findViewById(R.id.item_icon5);
        itemicon5s = (ImageView) view.findViewById(R.id.item_icon5s);
        itemicon6 = (ImageView) view.findViewById(R.id.item_icon6);
        itemicon6s = (ImageView) view.findViewById(R.id.item_icon6s);
        itemicon7 = (ImageView) view.findViewById(R.id.item_icon7);
        itemicon7s = (ImageView) view.findViewById(R.id.item_icon7s);
        itemicon8 = (ImageView) view.findViewById(R.id.item_icon8);
        itemicon8s = (ImageView) view.findViewById(R.id.item_icon8s);
        initUser();
        initItem();
        useritem1 = (LinearLayout) view.findViewById(R.id.user_item1);
        useritem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "哈哈哈哈哈", Toast.LENGTH_SHORT).show();
            }
        });
    //背景图片虚化
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.userimage);
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 8);
        userbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
        userbg.setImageBitmap(blurBitmap);

        return view;
    }

    public void initUser() {
        usericon.setImageResource(R.drawable.go);
        userimage.setImageResource(R.drawable.userimage);
        username.setText("过完冬的叶");
        userscore.setText("28");
    }

    public void initItem() {
        itemicon1.setImageResource(R.drawable.itemicon1);
        itemicon2.setImageResource(R.drawable.itemicon2);
        itemicon3.setImageResource(R.drawable.itemicon3);
        itemicon4.setImageResource(R.drawable.itemicon4);
        itemicon5.setImageResource(R.drawable.itemicon5);
        itemicon6.setImageResource(R.drawable.itemicon6);
        itemicon7.setImageResource(R.drawable.itemicon7);
        itemicon8.setImageResource(R.drawable.itemicon8);
        itemicon1s.setImageResource(R.drawable.go);
        itemicon2s.setImageResource(R.drawable.go);
        itemicon3s.setImageResource(R.drawable.go);
        itemicon4s.setImageResource(R.drawable.go);
        itemicon5s.setImageResource(R.drawable.go);
        itemicon6s.setImageResource(R.drawable.go);
        itemicon7s.setImageResource(R.drawable.go);
        itemicon8s.setImageResource(R.drawable.go);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initToolbar("用户中心");
    }

    public Toolbar initToolbar(CharSequence title) {
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) activity;
        Toolbar toolbar = (Toolbar) mAppCompatActivity.findViewById(R.id.user_toolbar);
        toolbar.setTitle(title);
        toolbar.setNavigationIcon(R.drawable.classify);
        return toolbar;
    }
}
