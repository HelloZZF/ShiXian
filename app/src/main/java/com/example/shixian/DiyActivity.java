package com.example.shixian;

import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_diy_image;
import com.example.shixian.adapter.cell.cell_order_wares;
import com.example.shixian.bean.ShopCart;
import com.example.shixian.utils.FastBlurUtil;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.util.ArrayList;
import java.util.List;

public class DiyActivity extends BaseActivity {

    private View mStatus;
    private myToolbar mToolbar;
    private ScrollView mScrollView;
    private FloatingActionButton mActionButton;
    private RecyclerView mRecyclerView;
    private Button mButton;
    private RVSimpleAdapter mAdapter;
    private EditText mDoText;
    private List<Uri> mImageList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diy);
        mStatus = findViewById(R.id.diy_status);
        mToolbar = findViewById(R.id.diy_toolbar);
        mScrollView = findViewById(R.id.diy_scrollview);
        mActionButton = findViewById(R.id.diy_float);
        mRecyclerView = findViewById(R.id.diy_recycler);
        mButton = findViewById(R.id.diy_share);
        mDoText = findViewById(R.id.do_way);

        init();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();
        initButton();
        BlurImage();
    }

    private void initButton() {

        mActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addImage();
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ("".equals(mDoText.getText().toString()))
                    ToastUtils.show(DiyActivity.this, "分享失败");
                else
                    ToastUtils.show(DiyActivity.this, "分享成功");
                DiyActivity.this.finish();
            }
        });
    }

    private void addImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case 1:
                if (resultCode == RESULT_OK) {

                    try {

                        //获取系统返回照片的URI
                        Uri selectImage = data.getData();
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        //从系统中查询对应URI的照片
//                        Cursor cursor = getContentResolver().query(selectImage, filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        //获取照片路径
//                        String path =cursor.getString(columnIndex);
//                        cursor.close();
//                        Bitmap bitmap = BitmapFactory.decodeFile(path);

                        mImageList.add(selectImage);
                        initRecycleView();

                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                }
                break;
        }
    }

    private void initRecycleView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_diy_image(mImageList), mImageList.size());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(manager);

        }else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(new cell_diy_image(mImageList), mImageList.size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.scrollToPosition(mImageList.size() - 1);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void BlurImage() {

        Resources res = getResources();
        Bitmap scaledBitmap = BitmapFactory.decodeResource(res, R.drawable.diybg);
        Bitmap blurBitmap = FastBlurUtil.toBlur(scaledBitmap, 2, 4);
        Drawable drawable = new BitmapDrawable(blurBitmap);
        mScrollView.setBackground(drawable);
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiyActivity.this.finish();
            }
        });
    }

}
