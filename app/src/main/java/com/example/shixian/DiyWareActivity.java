package com.example.shixian;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_diy_ware;
import com.example.shixian.bean.DiyWare;
import com.example.shixian.bean.DiyWareMsg;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.FastBlurUtil;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.util.List;

import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Call;
import okhttp3.Response;

public class DiyWareActivity extends BaseActivity {

    private myToolbar mToolbar;
    private TextView mContent, mingredients, mburden;
    private ImageView mImageView, mImgBackg;
    private RecyclerView mRecyclerView;
    private RVSimpleAdapter mAdapter;
    private View mStatus;
    private List<DiyWare> mDiyWareList;
    private ScrollView mScrollView;
    private Target target;
    private int WareId = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy_ware);
        mToolbar = findViewById(R.id.diy_ware_toolbar);
        mStatus = findViewById(R.id.diy_ware_status);
        mContent = findViewById(R.id.diy_ware_content);
        mRecyclerView = findViewById(R.id.diy_ware_recycler);
        mImageView = findViewById(R.id.diy_ware_img);
        mImgBackg = findViewById(R.id.diy_ware_imgbg);
        mScrollView = findViewById(R.id.diy_ware_scroll);
        mingredients = findViewById(R.id.ingredients);
        mburden = findViewById(R.id.burden);

        if (getIntent() != null) {

            try {

                WareId = Integer.parseInt(getIntent().getStringExtra("id"));
            }catch (Exception e) {

                this.finish();
            }

        }

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        initToolbar();
        setStatusbar();
        getTarget();
        initData();
        FitsStatusBar(mStatus);
    }

    private void getTarget() {
        target = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Bitmap blurBitmap = FastBlurUtil.toBlur(bitmap, 2, 2);
                mImgBackg.setScaleType(ImageView.ScaleType.CENTER_CROP);
                mImgBackg.setImageBitmap(blurBitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        };
    }

    private void initImgBg() {

        if (!"".equals(mDiyWareList.get(0).getAlbums().get(0))){
            Picasso.with(this).load(mDiyWareList.get(0).getAlbums().get(0)).into(target);
        }

    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyWareActivity.this.finish();
            }
        });

        mToolbar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showShare();
            }
        });
    }

    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        oks.disableSSOWhenAuthorize();
        oks.setTitle(mDiyWareList.get(0).getTitle());
        oks.setText(mDiyWareList.get(0).getIngredients());
        oks.setImageUrl(mDiyWareList.get(0).getImtro());

        // 启动分享GUI
        oks.show(this);
    }

    private void initRecyclerView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_diy_ware(mDiyWareList), mDiyWareList.get(0).getSteps().size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setNestedScrollingEnabled(false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initData() {

        if(WareId != 0)
            SimpleHttpClient.newBuilder()
                    .get()
                    .url(Contants.API.DIY_WARE_GET)
                    .addParams("id",WareId)
                    .build()
                    .enqueue(new SimpleCallBack<DiyWareMsg>() {
                        @Override
                        public void onSuccess(Response response, DiyWareMsg diyWareMsg) {

                                if ("206".equals(diyWareMsg.getResultcode())){

                                    DiyWareActivity.this.finish();
                                    ToastUtils.show(DiyWareActivity.this, "菜品编号不存在");
                                }else if ("200".equals(diyWareMsg.getResultcode())) {

                                    mDiyWareList = diyWareMsg.getResult().getData();
                                    initRecyclerView();
                                    initImgBg();
                                    mingredients.setText("食材: "+mDiyWareList.get(0).getIngredients());
                                    mburden.setText("调料: " + mDiyWareList.get(0).getBurden());
                                    mToolbar.setTitle(mDiyWareList.get(0).getTitle());
                                }

                        }

                        @Override
                        public void onError(int code, Exception e) {

                        }
                    });
    }
}
