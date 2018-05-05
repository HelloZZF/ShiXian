package com.example.shixian;

import android.os.Bundle;
import android.view.View;

import com.example.shixian.widget.myToolbar;

public class MyAboutUsActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_about_us);
        mToolbar = findViewById(R.id.about_toolbar);
        mStatus = findViewById(R.id.about_status);

        init();
    }

    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyAboutUsActivity.this.finish();
            }
        });
    }
}
