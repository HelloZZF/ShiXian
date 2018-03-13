package com.example.shixian.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.bean.User;
import com.example.shixian.utils.FastBlurUtil;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by admin on 2017/9/2.
 */

public class UserFragment extends BaseFragment {

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
    private User mUser;
    private LinearLayout useritem1;
    private View statusBar;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
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
        statusBar = view.findViewById(R.id.user_StatusbarView);
        mUser = ShiXianApplication.getInstance().getUser();

        useritem1 = (LinearLayout) view.findViewById(R.id.user_item1);
        useritem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "哈哈哈哈哈", Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }


    @Override
    public void init() {

        initUser();
        initItem();
        BlurImage();
        FitsStatusBar(statusBar);
    }


    private void BlurImage() {
        //背景图片虚化
        Resources res = getResources();
        //Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.userbg);
        //用Picsso获取网络图片的Bitmap

        if (!"".equals(mUser.getIcon())){
            Picasso.with(getContext()).load(mUser.getIcon()).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 2);
                    userbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    userbg.setImageBitmap(blurBitmap);
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        }


//        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 3);
//        userbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        userbg.setImageBitmap(blurBitmap);
    }

    public void initUser() {

        if (mUser != null){
            usericon.setImageResource(R.drawable.more);
            if (!"".equals(mUser.getIcon()))
                Picasso.with(getContext()).load(mUser.getIcon()).into(userimage);
            username.setText(mUser.getName());
            userscore.setText(mUser.getScroe());
        }

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
        itemicon1s.setImageResource(R.drawable.more);
        itemicon2s.setImageResource(R.drawable.more);
        itemicon3s.setImageResource(R.drawable.more);
        itemicon4s.setImageResource(R.drawable.more);
        itemicon5s.setImageResource(R.drawable.more);
        itemicon6s.setImageResource(R.drawable.more);
        itemicon7s.setImageResource(R.drawable.more);
        itemicon8s.setImageResource(R.drawable.more);
    }


}
