package com.example.shixian.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.shixian.AllOrderActivity;
import com.example.shixian.Contants;
import com.example.shixian.MyAboutUsActivity;
import com.example.shixian.MyAddressActivity;
import com.example.shixian.MyScroeActivity;
import com.example.shixian.MySetActivity;
import com.example.shixian.MySuggestActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.UserLoginActivity;
import com.example.shixian.UserUniversalActivity;
import com.example.shixian.WaitReceiptActivity;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.User;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.utils.FastBlurUtil;
import com.example.shixian.utils.ToastUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import okhttp3.Response;

import static android.app.Activity.RESULT_OK;


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
    private RelativeLayout mChangeUser;
    private View statusBar;
    private Target mTarget;
    private CartProvider mCartProvider;
    private CardView mEvaluationCardView, mAddressCardView, mWaitreceiptCarView, mOrderCarView, mScoreView;
    private CardView mSuggest, mAboutUs, mSet, mRecord;
    private LinearLayout mCouponLayout, mCollectLayout, mDiyLayout;

    @Override
    public void init() {

        getTarget();
        initUser();
        BlurImage();
        FitsStatusBar(statusBar);
        ChangeUser();
        ToGoActivity();
    }

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_user, container, false);
        userimage = view.findViewById(R.id.UserImage);
        userbg = view.findViewById(R.id.User_bg);
        usericon = view.findViewById(R.id.UserIcon);
        username = view.findViewById(R.id.UserName);
        userscore = view.findViewById(R.id.UserScore);
        statusBar = view.findViewById(R.id.user_StatusbarView);
        mChangeUser = view.findViewById(R.id.user_relative);
        mEvaluationCardView = view.findViewById(R.id.my_evaluation);
        mCouponLayout = view.findViewById(R.id.user_coupon);
        mCollectLayout = view.findViewById(R.id.user_collect);
        mDiyLayout = view.findViewById(R.id.user_mydiy);
        mWaitreceiptCarView = view.findViewById(R.id.my_wait_receipt);
        mOrderCarView = view.findViewById(R.id.my_order);
        mAddressCardView = view.findViewById(R.id.my_address);
        mScoreView = view.findViewById(R.id.my_score);
        mSuggest = view.findViewById(R.id.my_suggest);
        mAboutUs = view.findViewById(R.id.my_about_us);
        mSet = view.findViewById(R.id.my_set);
        mRecord = view.findViewById(R.id.my_record);

        return view;
    }

    private void ToGoActivity() {

        mEvaluationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), UserUniversalActivity.class);
                intent.putExtra("UNIVERSAL","MY_EVALUATION");
                startActivity(intent);
            }
        });

        mCouponLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), UserUniversalActivity.class);
                intent.putExtra("UNIVERSAL","MY_COUPON");
                startActivity(intent);
            }
        });

        mCollectLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), UserUniversalActivity.class);
                intent.putExtra("UNIVERSAL","MY_COLLECT");
                startActivity(intent);
        }
        });

        mDiyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), UserUniversalActivity.class);
                intent.putExtra("UNIVERSAL","MY_DIY");
                startActivity(intent);

            }
        });

        mWaitreceiptCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), WaitReceiptActivity.class);
                intent.putExtra("UNIVERSAL","MY_WAIT_RECEIPT");
                startActivity(intent);

            }
        });

        mAddressCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MyAddressActivity.class);
                startActivity(intent);

            }
        });

        mOrderCarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), AllOrderActivity.class);
                startActivity(intent);

            }
        });

        mScoreView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MyScroeActivity.class);
                startActivity(intent);

            }
        });

        mSuggest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MySuggestActivity.class);
                startActivity(intent);
            }
        });

        mAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MyAboutUsActivity.class);
                startActivity(intent);
            }
        });

        mSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), MySetActivity.class);
                startActivityForResult(intent, 2);
            }
        });

        mRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ToastUtils.show(getContext(),"暂无浏览记录");
            }
        });
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

    public void initUser() {

        mUser = ShiXianApplication.getInstance().getUser();
        if (mUser != null) {
            usericon.setImageResource(R.drawable.more);
            if (!"".equals(mUser.getIcon()))
                Picasso.with(getContext()).load(mUser.getIcon()).into(userimage);
            username.setText(mUser.getName());
            userscore.setText("积分: " + mUser.getScroe());
        }

    }

    private void BlurImage() {
        //背景图片虚化
        //Resources res = getResources();
        //Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.userbg);
        //用Picsso获取网络图片的Bitmap
        mUser = ShiXianApplication.getInstance().getUser();
        if (mUser != null) {
            Picasso.with(getContext()).load(mUser.getIcon()).into(mTarget);
        }


//        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 3);
//        userbg.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        userbg.setImageBitmap(blurBitmap);
    }

    private void ChangeUser() {

        mChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserLoginActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mCartProvider = new CartProvider(getContext());
        if (requestCode == 1 && resultCode == RESULT_OK) {

            initUser();
            BlurImage();
            mCartProvider.clear();
        }else if (requestCode == 2 && resultCode == RESULT_OK) {

            initUserData();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initUserData() {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.USER_GET)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<User>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<User> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            ShiXianApplication.getInstance().cleanUser();
                            ShiXianApplication.getInstance().putUser(msg.getData().get(0));
                            initUser();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }
}
