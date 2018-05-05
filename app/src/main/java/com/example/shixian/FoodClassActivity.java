package com.example.shixian;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shixian.utils.FastBlurUtil;
import com.example.shixian.utils.ToastUtils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;

public class FoodClassActivity extends Activity {

    private Button mOkButton;
    private Button mScanButton;
    private EditText mIdEditText;
    private LinearLayout mLinearLayout;
    private int REQUEST_CODE_SCAN = 111;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_class);
        mOkButton = findViewById(R.id.fc_ok);
        mScanButton = findViewById(R.id.fc_scanning);
        mIdEditText = findViewById(R.id.fc_id);
        mLinearLayout = findViewById(R.id.fc_linear);

        initScanning();
        init();
        //setBackgroundBlur();


    }

    private void init() {

        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String text = mIdEditText.getText().toString();
                if (!"".equals(text)) {

                    Intent intent = new Intent(FoodClassActivity.this, DiyWareActivity.class);
                    intent.putExtra("id", text);

                    try {
                        Integer.parseInt(text);
                        startActivity(intent);
                        FoodClassActivity.this.finish();
                    } catch (Exception e) {
                        ToastUtils.show(FoodClassActivity.this, "请输入数字");
                    }

                } else {
                    ToastUtils.show(FoodClassActivity.this, "请输入菜品编号");
                }
            }
        });

        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(FoodClassActivity.this, CaptureActivity.class);
                //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });

    }

    private void initScanning() {

        /*ZxingConfig是配置类  可以设置是否显示底部布局，闪光灯，相册，是否播放提示音  震动等动能
         * 也可以不传这个参数
         * 不传的话  默认都为默认不震动  其他都为true
         * */
        ZxingConfig config = new ZxingConfig();
        config.setShowbottomLayout(true);//底部布局（包括闪光灯和相册）
//        config.setPlayBeep(true);//是否播放提示音
//        config.setShake(true);//是否震动
        config.setShowAlbum(true);//是否显示相册
        config.setShowFlashLight(true);//是否显示闪光灯
    }

    @Override
    public void finish() {
        super.finish();
        //关闭Activity退出时的动画效果
        overridePendingTransition(0, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                if (!"".equals(content)) {

                    Intent intent = new Intent(FoodClassActivity.this, DiyWareActivity.class);
                    intent.putExtra("id", content);

                    try {
                        Integer.parseInt(content);
                        startActivity(intent);
                        FoodClassActivity.this.finish();
                    } catch (Exception e) {
                        ToastUtils.show(FoodClassActivity.this, "请输入数字");
                    }

                } else {
                    ToastUtils.show(FoodClassActivity.this, "请输入菜品编号");
                }
                //result.setText("扫描结果为：" + content);
            }
        }
    }
}
