package com.example.shixian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.shixian.widget.myToolbar;

public class SuggestResultActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggest_result);
        mToolbar = findViewById(R.id.suggest_result_toolbar);
        mStatus = findViewById(R.id.suggest_result_status);
        mButton = findViewById(R.id.suggest_result_button);

        init();
    }

    private void init() {

        initToolbar();
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SuggestResultActivity.this.finish();
            }
        });
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SuggestResultActivity.this.finish();
            }
        });
    }

}
