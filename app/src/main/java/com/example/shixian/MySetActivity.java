package com.example.shixian;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.shixian.widget.myToolbar;

public class MySetActivity extends BaseActivity {

    private View mStatus;
    private myToolbar mToolbar;
    private RelativeLayout mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_set);
        mStatus = findViewById(R.id.set_status);
        mToolbar = findViewById(R.id.set_toolbar);
        mMessage = findViewById(R.id.my_message);
        init();
    }

    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();

        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MySetActivity.this, MyMessageActivity.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySetActivity.this.finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            setResult(RESULT_OK);
            finish();
        }
    }
}
