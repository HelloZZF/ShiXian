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
    private User mUser;
    private LinearLayout useritem1;
    private View statusBar;
    private Target mTarget;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userimage = view.findViewById(R.id.UserImage);
        userbg =  view.findViewById(R.id.User_bg);
        usericon = view.findViewById(R.id.UserIcon);
        username =  view.findViewById(R.id.UserName);
        userscore = view.findViewById(R.id.UserScore);
        statusBar = view.findViewById(R.id.user_StatusbarView);
        mUser = ShiXianApplication.getInstance().getUser();

        return view;
    }


    @Override
    public void init() {

        getTarget();
        initUser();
        BlurImage();
        FitsStatusBar(statusBar);
    }

    private void getTarget() {

        mTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 2, 8);
                userbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                userbg.setImageBitmap(blurBitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }


    private void BlurImage() {
        //背景图片虚化
        //Resources res = getResources();
        //Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.userbg);
        //用Picsso获取网络图片的Bitmap

        if (mUser != null){
            Picasso.with(getContext()).load(mUser.getIcon()).into(mTarget);
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
            userscore.setText("积分: " + mUser.getScroe());
        }

    }


}
