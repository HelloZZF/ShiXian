package com.example.shixian;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_order_wares;
import com.example.shixian.adapter.layoutmanager.FullyLinearLayoutManager;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.bean.ShopCart;
import com.example.shixian.bean.User;
import com.example.shixian.bean.Wares;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;
import com.pingplusplus.android.Pingpp;

import org.apache.commons.codec.binary.StringUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import okhttp3.Address;
import okhttp3.Response;

public class CreateOrderActivity extends BaseActivity implements View.OnClickListener{

    /**
     * 微信支付渠道
     */
    private static final String CHANNEL_WECHAT = "wx";
    /**
     * 支付支付渠道
     */
    private static final String CHANNEL_ALIPAY = "alipay";

    private View statusBar;
    private myToolbar mToolbar;
    private TextView mPhone;
    private TextView mAddress;
    private RecyclerView mRecyclerView;
    private RelativeLayout mAliPayLayout;
    private RelativeLayout mWeChatLayout;
    private RadioButton mAliPayButton;
    private RadioButton mWeChatButton;
    private TextView mTotal;
    private Button mOrderButton;
    private TextView mDateText;
    private TextView mTimeText;
    private String total;
    private int isBuy = 0;
    private Wares mWare = null;
    //获取日期格式器对象
    private DateFormat format =  DateFormat.getDateTimeInstance();
    private Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private String payChannel = CHANNEL_ALIPAY;
    private HashMap<String,RadioButton> channels = new HashMap<>(3);


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        statusBar = findViewById(R.id.order_StatusbarView);
        mToolbar = findViewById(R.id.order_toolbar);
        mPhone = findViewById(R.id.phone);
        mAddress = findViewById(R.id.address);
        mRecyclerView = findViewById(R.id.recycler_view);
        mAliPayLayout = findViewById(R.id.rl_alipay);
        mWeChatLayout = findViewById(R.id.rl_wechat);
        mAliPayButton = findViewById(R.id.rb_alipay);
        mWeChatButton = findViewById(R.id.rb_webchat);
        mTotal = findViewById(R.id.txt_total);
        mOrderButton = findViewById(R.id.btn_createOrder);
        mDateText = findViewById(R.id.date);
        mTimeText = findViewById(R.id.time);

        FitsStatusBar(statusBar);
        init();
        initToolbar();
        initOrder();
        initAddress();
        initTime();
        initOrderButton();
        setStatusbar();

    }

    private void init() {

        Intent intent = getIntent();
        int buy = intent.getIntExtra("buy", 0);
        Wares wares = (Wares) intent.getSerializableExtra("ware");
        if (buy != 0 && wares != null) {
            isBuy = buy;
            mWare = wares;
        }
        channels.put(CHANNEL_ALIPAY, mAliPayButton);
        channels.put(CHANNEL_WECHAT, mWeChatButton);
        mAliPayLayout.setOnClickListener(this);
        mWeChatLayout.setOnClickListener(this);

        if (isBuy == 1) {
            total = mWare.getPrice();
            mTotal.setText("应付款："+total+"¥");
        }else {
            total = getIntent().getStringExtra("total");
            mTotal.setText("应付款："+total);
        }


    }

    private void initOrder() {
        RVSimpleAdapter mAdapter = new RVSimpleAdapter();
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        if (isBuy == 1 && mWare != null) {
            ShopCart cart = new ShopCart();
            cart.setImageurl(mWare.getImageurl());
            List<ShopCart> list = new ArrayList<>();
            list.add(cart);
            mAdapter.OnlyOneItem(new cell_order_wares(list), 1);
        }else {

            CartProvider cartProvider = new CartProvider(this);
            mAdapter.OnlyOneItem(new cell_order_wares(cartProvider.getAll()), cartProvider.getAll().size());
        }

        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initToolbar() {
        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //点击手机返回键时执行的操作
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initTime() {

        mDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOrderActivity.this, R.style.MyDatePickerDialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                        calendar.set(Calendar.YEAR,year);
                        calendar.set(Calendar.MONTH,month);
                        calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                        updataTime("date");
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Calendar.DAY_OF_MONTH);
                datePickerDialog.show();
                updataTime("date");
            }
        });

        mTimeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateOrderActivity.this, R.style.MyDatePickerDialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
                        calendar.set(Calendar.MINUTE,minute);
                        updataTime("time");
                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.show();
                updataTime("time");
            }
        });
    }

    private void updataTime(String time) {

        String times = format.format(calendar.getTime());
        if (time.equals("date")) {
            mDateText.setText(times.substring(0, times.indexOf(" ")));
        }else if (time.equals("time")) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
                String str = times.substring(times.indexOf(" "));
                mTimeText.setText(str.substring(0, str.indexOf(":")+3).trim());
            }
            else{
                mTimeText.setText(times.substring(times.indexOf(" ")).substring(0, 6).trim());
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initAddress() {

        User user = ShiXianApplication.getInstance().getUser();
        if (user != null) {
            SimpleHttpClient.newBuilder()
                    .get()
                    .url(Contants.API.ADDRESS_GET)
                    .addParams("user_id", Integer.parseInt(user.getId()))
                    .addParams("isdefault", 1)
                    .build()
                    .enqueue(new SimpleCallBack<BaseMsg<com.example.shixian.bean.Address>>() {
                        @Override
                        public void onSuccess(Response response, BaseMsg<com.example.shixian.bean.Address> address) {

                            List<com.example.shixian.bean.Address> addresses = address.getData();
                            String phone = addresses.get(0).getPhone();
                            mPhone.setText(phone.substring(0, 3) + "****" + phone.substring(7, 11));
                            mAddress.setText(addresses.get(0).getAddress());
                        }

                        @Override
                        public void onError(int code, Exception e) {

                        }
                    });
        }

    }

    private void initOrderButton() {

        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mDateText.getText().toString().trim().equals("请点击选择配送日期")){
                    ToastUtils.show(CreateOrderActivity.this, "请输入配送日期");
                    return;
                }

                if (mTimeText.getText().toString().trim().equals("请点击选择配送时间")){
                    ToastUtils.show(CreateOrderActivity.this, "请输入配送时间");
                    return;
                }

                postNewOrder();
            }
        });
    }

    private void postNewOrder() {
        openPaymentActivity("{\n" +
                "  \"amount\": 9533,\n" +
                "  \"amount_refunded\": 0,\n" +
                "  \"amount_settle\": 9533,\n" +
                "  \"app\": \"app_0yvTSGSOOSO44u13\",\n" +
                "  \"body\": \"订单Body\",\n" +
                "  \"channel\": \"alipay\",\n" +
                "  \"client_ip\": \"221.182.236.63\",\n" +
                "  \"created\": 1520776333,\n" +
                "  \"credential\": {\n" +
                "    \"object\": \"credential\",\n" +
                "    \"alipay\": {\n" +
                "      \"orderInfo\": \"_input_charset=\\\"utf-8\\\"&body=\\\"订单Body\\\"&it_b_pay=\\\"2018-03-12 21:52:19\\\"&notify_url=\\\"https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_ynbbP4bbDSCS1WbXH0GWvvPK\\\"&out_trade_no=\\\"20180311215218000100\\\"&partner=\\\"2008411905333973\\\"&payment_type=\\\"1\\\"&seller_id=\\\"2008411905333973\\\"&service=\\\"mobile.securitypay.pay\\\"&subject=\\\"您正在菜鸟商城购物\\\"&total_fee=\\\"95.36\\\"&sign=\\\"aTVXZm5IMWlEZUhTZUR1TE9HZWZQR3FU\\\"&sign_type=\\\"RSA\\\"\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"currency\": \"cny\",\n" +
                "  \"description\": null,\n" +
                "  \"extra\": {},\n" +
                "  \"failure_code\": null,\n" +
                "  \"failure_msg\": null,\n" +
                "  \"id\": \"ch_ynbbP4bbDSCS1WbXH0GWvvPK\",\n" +

                "  \"livemode\": false,\n" +
                "  \"metadata\": {},\n" +
                "  \"object\": \"charge\",\n" +
                "  \"order_no\": \"20180311215218000100\",\n" +
                "  \"paid\": false,\n" +
                "  \"refunded\": false,\n" +
                "  \"refunds\": {\n" +
                "    \"data\": [],\n" +
                "    \"has_more\": false,\n" +
                "    \"object\": \"list\",\n" +
                "    \"url\": \"/v1/charges/ch_ynbbP4bbDSCS1WbXH0GWvvPK/refunds\"\n" +
                "  },\n" +
                "  \"reversed\": null,\n" +
                "  \"subject\": \"您正在食鲜购物\",\n" +
                "  \"time_expire\": 1520862739,\n" +
                "  \"time_paid\": null,\n" +
                "  \"time_settle\": null,\n" +
                "  \"transaction_no\": null\n" +
                "}");
    }

    //得到服务端的支付凭证charge后发起支付跳转支付界面，没申请之前为模拟支付界面
    private void openPaymentActivity(String charge) {

        Pingpp.createPayment(CreateOrderActivity.this, charge);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if (result.equals("success"))
                    changeOrderStatus(1);
                else if (result.equals("fail"))
                    changeOrderStatus(-1);
                else if (result.equals("cancel"))
                    changeOrderStatus(-2);
                else
                    changeOrderStatus(0);
            /* 处理返回值
             * "success" - 支付
             * 成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
             //   String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
             //   String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
             //   showMsg(result, errorMsg, extraMsg);
            }
        }
    }

    //支付成功之后向，服务器改变订单的支付状态
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void changeOrderStatus(final int status) {

        int ispaid = -1;
        int userId = 0;
        final int[] flag = {0,1};
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        ShiXianApplication application = ShiXianApplication.getInstance();
        if (application.getUser() != null){
           userId = Integer.parseInt(application.getUser().getId());
        }
        String time = "'" + simpleDateFormat.format(date) + "'";
        if (status == 1)
            ispaid = 1;
        else if (status == -1)
            ispaid = 0;
        else if (status == -2) {
            toPayResultActivity(-2);
            return;
        }


        if (isBuy == 1 && mWare != null) {

            SimpleHttpClient.newBuilder()
                    .post()
                    .url(Contants.API.ORDER_ADD)
                    .addParams("user_id", userId)
                    .addParams("ware_id",mWare.getId())
                    .addParams("ispaid", ispaid)
                    .addParams("time", time)
                    .build()
                    .enqueue(new SimpleCallBack<BaseMsg>() {
                        @Override
                        public void onSuccess(Response response, BaseMsg msg) {

                            if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS){
                                toPayResultActivity(1);
                            }
                            if (msg.getResultcode() == BaseMsg.RESULTCODE_ERROR) {
                                toPayResultActivity(-1);
                            }
                        }

                        @Override
                        public void onError(int code, Exception e) {
                            toPayResultActivity(-1);
                        }
                    });

        }else {

            final CartProvider provider = new CartProvider(this);
            final List<ShopCart> carts  = provider.getAll();

            for (ShopCart cart : carts) {
                SimpleHttpClient.newBuilder()
                        .post()
                        .url(Contants.API.ORDER_ADD)
                        .addParams("user_id", userId)
                        .addParams("ware_id",Integer.parseInt(cart.getId()))
                        .addParams("ispaid", ispaid)
                        .addParams("time", time)
                        .build()
                        .enqueue(new SimpleCallBack<BaseMsg>() {
                            @Override
                            public void onSuccess(Response response, BaseMsg msg) {

                                flag[0] = flag[0] + 1;
                                if (flag[0] == carts.size() && msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS){
                                    toPayResultActivity(1);
                                    provider.clear();
                                }
                                if (msg.getResultcode() == BaseMsg.RESULTCODE_ERROR && flag[1] == 1) {
                                    flag[1] = 0;
                                    toPayResultActivity(-1);
                                }
                            }

                            @Override
                            public void onError(int code, Exception e) {
                                toPayResultActivity(-1);
                            }
                        });
            }
        }





    }

    private void toPayResultActivity(int status) {

        Intent intent = new Intent(this, PayResultActivity.class);
        if (isBuy == 1 && mWare != null) {

            intent.putExtra("total", total + "¥");
            intent.putExtra("buy", 1);
            intent.putExtra("ware", mWare);
            intent.putExtra("time", mTimeText.getText());
            intent.putExtra("date", mDateText.getText());
            intent.putExtra("address", mAddress.getText());
            intent.putExtra("phone", mPhone.getText());
            intent.putExtra("status", status);
        }else {

            intent.putExtra("total", total);
            intent.putExtra("time", mTimeText.getText());
            intent.putExtra("date", mDateText.getText());
            intent.putExtra("address", mAddress.getText());
            intent.putExtra("phone", mPhone.getText());
            intent.putExtra("status", status);
        }


        startActivity(intent);
        this.finish();

    }

    @Override
    public void onClick(View view) {
        selectPayChannle(view.getTag().toString());
    }

    public void selectPayChannle(String paychannel){

        for (Map.Entry<String, RadioButton> entry : channels.entrySet()) {
            payChannel = paychannel;
            RadioButton rb = entry.getValue();
            if (entry.getKey().equals(paychannel)){

                boolean isCheck = rb.isChecked();
                rb.setChecked(!isCheck);

            }else {
                rb.setChecked(false);
            }
        }
    }
}
