package com.example.shixian;

import android.os.Bundle;
import android.view.View;

import com.example.shixian.widget.myToolbar;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class LookUsActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private JZVideoPlayerStandard mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_us);
        mToolbar = findViewById(R.id.lu_toolbar);
        mStatus = findViewById(R.id.lu_status);
        mVideoView = findViewById(R.id.videoplayer);

        init();
    }

    private void init() {

        setStatusbar();
        FitsStatusBar(mStatus);
        initToolbar();
        initVideo();

    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LookUsActivity.this.finish();
            }
        });
    }

    private void initVideo() {

        mVideoView.setUp("http://39.108.177.58/sxapi/video/video"
                , JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "中央厨房实况");
        mVideoView.thumbImageView
                .setImageResource(R.drawable.lookusimage);

    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }
}
