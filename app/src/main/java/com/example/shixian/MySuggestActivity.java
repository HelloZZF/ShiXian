package com.example.shixian;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shixian.utils.FastBlurUtil;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;

public class MySuggestActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private Button mButton;
    private LinearLayout mBackground;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_suggest);
        mToolbar = findViewById(R.id.suggest_toolbar);
        mStatus = findViewById(R.id.suggest_status);
        mBackground = findViewById(R.id.suggest_bg);
        mButton = findViewById(R.id.suggest_button);
        mEditText = findViewById(R.id.suggest_content);

        init();
    }

    private void init() {

        initToolbar();
        setStatusbar();
        FitsStatusBar(mStatus);
        BlurImage();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mEditText.getText().toString().equals("")) {

                    mEditText.setText("");
                    Intent intent = new Intent(MySuggestActivity.this, SuggestResultActivity.class);
                    startActivity(intent);
                }else {

                    ToastUtils.show(MySuggestActivity.this, "请输入内容");
                }
            }
        });


    }

    private void BlurImage() {
        //背景图片虚化
        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.suggestbg);
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 2, 4);
        Drawable drawable = new BitmapDrawable(blurBitmap);
        mBackground.setBackground(drawable);

    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MySuggestActivity.this.finish();
            }
        });
    }
}
