package com.example.shixian;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.squareup.picasso.Picasso;

import okhttp3.Response;

/**
 * Created by admin on 2018/1/3.
 */

public class TestActivity extends AppCompatActivity{

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button mButton = findViewById(R.id.button);
        final ImageView mImage = findViewById(R.id.image);
        final TextView mText = findViewById(R.id.text);
        final Handler mHandler = new Handler();

        SimpleHttpClient.newBuilder()
                .get()
                .addParams("category",1)
                .addParams("curpage", 1)
                .addParams("pagesize", 3)
                .url(Contants.API.WARE_GET)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Wares>>() {

                    @Override
                    public void onSuccess(Response response, final BaseMsg<Wares> Msg) {

                        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
                            Log.d("tttt", "Main Thread");
                        } else {
                            Log.d("tttt", "Not Main Thread");
                        }

                        Log.d("msg","1111");
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {

                                mText.setText(Msg.getData().get(0).getImageurl());
                                Picasso.with(TestActivity.this)
                                        .load(Msg.getData().get(0).getImageurl())
                                        .into(mImage);
                            }
                        });
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

//        SimpleHttpClient.newBuilder()
//                .post()
//                .url("http://169.254.29.249/sxapi/order/add.php")
//                .addParams("user_id", 9)
//                .addParams("ware_id", 2)
//                .addParams("ispaid", 1)
//                .addParams("time", "2222")
//                .build()
//                .enqueue(new SimpleCallBack<BaseMsg>() {
//                    @Override
//                    public void onSuccess(Response response, final BaseMsg msg) {
//
//                        mHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mText.setText(msg.getReason());
//                            }
//                        });
//                    }
//
//                    @Override
//                    public void onError(int code, Exception e) {
//
//                    }
//                });

    }

}
