package com.example.shixian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shixian.bean.ShopCart;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.widget.myToolbar;

import java.util.List;

public class PayResultActivity extends BaseActivity {

    private myToolbar mToolbar;
    private TextView mPhoneText;
    private TextView mAddressText;
    private TextView mTotalText;
    private TextView mTimeText;
    private TextView mStatusText;
    private TextView mWareText;
    private Button mButton;
    private ImageView mStatusImage;

    private String mAddress;
    private String mTotal;
    private String mTime;
    private String mDate;
    private String mPhone;
    private int mStatus;
    private int isBuy = 0;
    private Wares mWares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        mToolbar = findViewById(R.id.toolbar);
        mPhoneText = findViewById(R.id.phone_text);
        mAddressText = findViewById(R.id.address_text);
        mTotalText = findViewById(R.id.total);
        mTimeText = findViewById(R.id.time_text);
        mStatusText = findViewById(R.id.status_text);
        mWareText = findViewById(R.id.ware_text);
        mButton = findViewById(R.id.back_home);
        mStatusImage = findViewById(R.id.status_image);

        Intent intent = getIntent();
        if (intent != null) {

            mAddress = intent.getStringExtra("address");
            mTotal = intent.getStringExtra("total");
            mTime = intent.getStringExtra("time");
            mDate = intent.getStringExtra("date");
            mPhone = intent.getStringExtra("phone");
            mStatus = intent.getIntExtra("status", 0);

            isBuy = intent.getIntExtra("buy", 0) == 0 ? 0:1 ;
            if (intent.getSerializableExtra("ware") != null) {
                mWares = (Wares) intent.getSerializableExtra("ware");
            }
        }


        initToolbar();
        initOrderList();
        initBackButton();
    }

    private void initToolbar() {

        setStatusbar();
        mToolbar.setTitle("订单详情");
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initOrderList() {

        mPhoneText.setText(mPhone);
        mTotalText.setText(mTotal);
        mPhoneText.setText(mPhone);
        mAddressText.setText(mAddress);
        mTimeText.setText(mDate + " " + mTime);
        mWareText.setText(CreateWareList());

        if (mStatus == 1) {
            mStatusText.setText("支付成功");
            mStatusImage.setImageResource(R.drawable.success);
        }else if (mStatus == -1) {
            mStatusText.setText("支付失败");
            mStatusImage.setImageResource(R.drawable.defeat);
        }else if (mStatus == -2) {
            mStatusText.setText("支付取消");
            mStatusImage.setImageResource(R.drawable.defeat);
        }

    }

    private String CreateWareList() {

        String wareList = "";

        if (isBuy == 1 && mWares != null) {

            wareList += mWares.getName() + "x" + "1";
        }else {

            CartProvider provider = new CartProvider(this);
            List<ShopCart> list = provider.getAll();
            for (ShopCart cart : list) {
                wareList += cart.getName() + "   x " + cart.getCount() + "\n";
            }
        }



        return wareList;
    }

    private void initBackButton() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayResultActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
