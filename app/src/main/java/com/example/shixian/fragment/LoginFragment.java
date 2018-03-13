package com.example.shixian.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shixian.Contants;
import com.example.shixian.MainActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.User;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;

import okhttp3.Response;

/**
 * Created by admin on 2017/11/5.
 */

public class LoginFragment extends BaseFragment {

    private Button Loginbutton;
    private EditText mPhone;
    private EditText mPassword;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Loginbutton = (Button) view.findViewById(R.id.login_button);
        mPhone = view.findViewById(R.id.login_phone);
        mPassword = view.findViewById(R.id.login_password);

        return view;
    }

    @Override
    public void init() {

        login();
    }

    private void login() {

        Loginbutton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {

                String phone = mPhone.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                if (TextUtils.isEmpty(phone)){

                    Toast.makeText(getContext(), "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)){

                    Toast.makeText(getContext(), "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }

                SimpleHttpClient.newBuilder().post()
                        .url(Contants.API.USER_LOGIN)
                        .addParams("phone", phone)
                        .addParams("password", password)
                        .build()
                        .enqueue(new SimpleCallBack<BaseMsg<User>>() {
                            @Override
                            public void onSuccess(Response response, BaseMsg<User> msg) {

                                if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS){

                                    ShiXianApplication application = ShiXianApplication.getInstance();
                                    application.putUser(msg.getData().get(0));

                                    if (application.getIntent() == null){
                                        getActivity().setResult(Activity.RESULT_OK);
                                        getActivity().finish();
                                    }else{
                                        application.jumpToTargetActivity(getActivity());
                                    }

                                }else{

                                    Toast.makeText(getContext(), "请检查用户名和密码", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onError(int code, Exception e) {

                                Toast.makeText(getContext(), "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        });



            }
        });




    }
}
