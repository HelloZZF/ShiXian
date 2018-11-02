package com.example.shixian.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shixian.Contants;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.User;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.CountTimerView;
import com.example.shixian.utils.ToastUtils;
import com.mob.MobSDK;

import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;
import okhttp3.Response;

/**
 * Created by admin on 2017/11/5.
 */

public class RegisterFragment extends BaseFragment {

    private static final String DEFAULT_COUNTRY_CODE = "86";

    private EditText mPhone;
    private EditText mPassword;
    private EditText mVerify;
    private Button verifyButton;
    private Button registerButton;
    private CountTimerView countTimerView;

    @Override
    public void init() {

        getVerify();
        Register();
    }

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        mPhone = view.findViewById(R.id.register_phone);
        mPassword = view.findViewById(R.id.register_password);
        mVerify = view.findViewById(R.id.verification_code);
        verifyButton = view.findViewById(R.id.verification_button);
        registerButton = view.findViewById(R.id.register_button);

        // 通过代码注册你的AppKey和AppSecret
        MobSDK.init(getContext(), "2451f719bc5c6", "fdc7201f8821fc30e50158294d3b5d1d");


        return view;
    }

    private void getVerify() {

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //trim去掉String前后的空格，replace用""代替\\s*
                String phone = mPhone.getText().toString().trim().replace("\\s*", "");
                checkPhoneNum(phone);

                countTimerView = new CountTimerView(verifyButton, R.string.smssdk_resend_identify_code);
                countTimerView.start();
                sendCode(DEFAULT_COUNTRY_CODE, phone);
            }
        });

    }

    private void Register() {

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verifyCode = mVerify.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String phone = mPhone.getText().toString().trim();
                if (TextUtils.isEmpty(verifyCode)) {
                    ToastUtils.show(getContext(), "请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    ToastUtils.show(getContext(), "请输入密码");
                    return;
                }
                submitCode(DEFAULT_COUNTRY_CODE, phone, password, verifyCode);
            }
        });

    }

    private void checkPhoneNum(String phone) {

        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show(getContext(), "请输入手机号");
            return;
        }

        if (phone.length() != 11) {
            ToastUtils.show(getContext(), "手机号位数不对");
            return;
        }

        //判断输入手机号格式
        String rule = "^1(3|5|7|8|4)\\d{9}";
        Pattern p = Pattern.compile(rule);
        Matcher m = p.matcher(phone);

        if (!m.matches()) {
            ToastUtils.show(getContext(), "您输入的手机号码格式不正确");
            return;
        }

    }

    // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
    public void sendCode(String country, String phone) {
        // 注册一个事件回调，用于处理发送验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            public void afterEvent(final int event, final int result, final Object data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                                ToastUtils.show(getContext(), "验证码已发送");
                            }
                        } else {
                            // TODO 处理错误的结果
                            // 根据服务器返回的网络错误，给toast提示
                            try {
                                ((Throwable) data).printStackTrace();
                                Throwable throwable = (Throwable) data;

                                JSONObject object = new JSONObject(
                                        throwable.getMessage());
                                String des = object.optString("detail");
                                if (!TextUtils.isEmpty(des)) {
                                    ToastUtils.show(getContext(), des);
                                    return;
                                }
                            } catch (Exception e) {
                                SMSLog.getInstance().w(e);
                            }
                        }
                    }
                });


            }
        });
        // 触发操作
        SMSSDK.getVerificationCode(country, phone);
    }

    // 提交验证码，其中的code表示验证码，如“1357”
    public void submitCode(String country, final String phone, final String password, String code) {
        // 注册一个事件回调，用于处理提交验证码操作的结果
        SMSSDK.registerEventHandler(new EventHandler() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            public void afterEvent(final int event, final int result, final Object data) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证成功的结果
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                doRegister(phone, password);
                            }
                        } else {
                            // TODO 处理错误的结果
                            // 根据服务器返回的网络错误，给toast提示
                            try {
                                ((Throwable) data).printStackTrace();
                                Throwable throwable = (Throwable) data;

                                JSONObject object = new JSONObject(
                                        throwable.getMessage());
                                String des = object.optString("detail");
                                if (!TextUtils.isEmpty(des)) {
                                    ToastUtils.show(getContext(), des);
                                    return;
                                }
                            } catch (Exception e) {
                                SMSLog.getInstance().w(e);
                            }
                        }
                    }
                });


            }
        });
        // 触发操作
        SMSSDK.submitVerificationCode(country, phone, code);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void doRegister(String phone, String password) {

        SimpleHttpClient.newBuilder().post()
                .url(Contants.API.USER_REGISTERED)
                .addParams("phone", phone)
                .addParams("password", password)
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<User>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<User> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            ShiXianApplication application = ShiXianApplication.getInstance();
                            application.putUser(msg.getData().get(0));

                            if (application.getIntent() == null) {
                                getActivity().setResult(Activity.RESULT_OK);
                                getActivity().finish();
                            } else {
                                application.jumpToTargetActivity(getActivity());
                            }
                            ToastUtils.show(getContext(), "注册成功,已自动登录");
                        }

                        getActivity().finish();
                    }

                    @Override
                    public void onError(int code, Exception e) {

                        Toast.makeText(getContext(), "注册失败", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //用完回调要注销掉，否则可能会出现内存泄露
        SMSSDK.unregisterAllEventHandler();
    }
}
