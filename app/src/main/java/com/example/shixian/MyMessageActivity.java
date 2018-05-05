package com.example.shixian;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.shixian.bean.BaseMsg;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import okhttp3.Response;

public class MyMessageActivity extends BaseActivity {

    private View mStatus;
    private myToolbar mToolbar;
    private Button mButton;
    private EditText mNameText, mPhoneText, mEmailText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_message);
        mStatus = findViewById(R.id.message_status);
        mToolbar = findViewById(R.id.message_toolbar);
        mButton = findViewById(R.id.message_button);
        mNameText = findViewById(R.id.message_name_text);
        mPhoneText = findViewById(R.id.message_phone_text);
        mEmailText = findViewById(R.id.message_email_text);

        init();
    }

    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();
        setButton();
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyMessageActivity.this.finish();
            }
        });
    }

    private void setButton() {

        mButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if (!mEmailText.getText().toString().equals("") &&
                        !mPhoneText.getText().toString().equals("") &&
                        !mNameText.getText().toString().equals("")) {

                    updateData(mNameText.getText().toString());

                }else {
                    ToastUtils.show(MyMessageActivity.this, "请填写完整信息");
                }
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void updateData(String username) {

        SimpleHttpClient.newBuilder()
                .get()
                .url(Contants.API.USER_UPDATE)
                .addParams("user_id", ShiXianApplication.getInstance().getUser().getId())
                .addParams("user_name", username)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg baseMsg) {

                        if (baseMsg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {
                            setResult(RESULT_OK);
                            ToastUtils.show(MyMessageActivity.this, "更新成功");
                            MyMessageActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {

                    }
                });
    }
}
