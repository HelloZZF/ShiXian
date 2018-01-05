package com.example.shixian;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.shixian.bean.Category;
import com.example.shixian.bean.Home;
import com.example.shixian.bean.Page;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.BaseCallBack;
import com.example.shixian.http.OkHttpManager;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by admin on 2018/1/3.
 */

public class TestActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Button mButton = findViewById(R.id.button);

        Log.d("TestActivity", "11111");

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.URL)
                .build()
                .enqueue(new SimpleCallBack<List<Category>>() {
                    @Override
                    public void onSuccess(Response response, List<Category> categories) {
                        Log.d("msg111",categories.get(0).getName() );
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.URL)
                .build()
                .enqueue(new SimpleCallBack<List<Category>>() {
                    @Override
                    public void onSuccess(Response response, List<Category> categories) {
                        Log.d("msg222",categories.get(1).getName() );
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });

    }

}
