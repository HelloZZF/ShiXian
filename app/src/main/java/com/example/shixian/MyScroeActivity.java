package com.example.shixian;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.shixian.widget.myToolbar;

public class MyScroeActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_scroe);
        mToolbar = findViewById(R.id.score_toolbar);
        mStatus = findViewById(R.id.score_status);
        mWebView = findViewById(R.id.score_web);
        init();
    }

    private void init() {

        setStatusbar();
        FitsStatusBar(mStatus);
        initWebView();
        initToolbar();
    }

    private void initWebView() {

        mWebView.setWebViewClient(new WebViewClient());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl("https://www.taobao.com/");
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyScroeActivity.this.finish();
            }
        });
    }
}
