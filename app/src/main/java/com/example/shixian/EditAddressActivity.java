package com.example.shixian;

import android.content.res.AssetManager;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.OptionsPickerView;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.bean.Address;
import com.example.shixian.bean.BaseMsg;
import com.example.shixian.city.XmlParserHandler;
import com.example.shixian.city.model.CityModel;
import com.example.shixian.city.model.DistrictModel;
import com.example.shixian.city.model.ProvinceModel;
import com.example.shixian.http.SimpleCallBack;
import com.example.shixian.http.SimpleHttpClient;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Response;

public class EditAddressActivity extends BaseActivity {

    private myToolbar mToolbar;
    private View mStatus;
    private EditText mNameEdit, mPhoneEdit, mAddressEdit, mDAddressEdit;
    private Button mButton;
    private OptionsPickerView mCityPikerView;
    private Address mAddress = null;

    private List<ProvinceModel> mProvinces = new ArrayList<>();
    private ArrayList<ArrayList<String>> mCities = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> mDistricts = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);
        mToolbar = findViewById(R.id.ea_toolbar);
        mStatus = findViewById(R.id.ea_status);
        mNameEdit = findViewById(R.id.ea_name);
        mPhoneEdit = findViewById(R.id.ea_phone);
        mAddressEdit = findViewById(R.id.ea_address);
        mDAddressEdit = findViewById(R.id.ea_detail_address);
        mButton = findViewById(R.id.ea_button);

        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void init() {

        FitsStatusBar(mStatus);
        setStatusbar();
        initToolbar();
        initAddrPicker();

        mAddress = (Address) getIntent().getSerializableExtra("address");
        if (mAddress != null){
            mNameEdit.setText(mAddress.getName());
            mPhoneEdit.setText(mAddress.getPhone());

        }
        initAddress();


    }

    private void initAddrPicker() {

        initProvinceDatas();

        mCityPikerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String address = mProvinces.get(options1).getName()+" "+
                        mCities.get(options1).get(options2)+" "+
                        mDistricts.get(options1).get(options2).get(options3);
                mAddressEdit.setText(address);
            }
        }).setTitleText("选择地址")
                .setCyclic(false, false, false)
                .setTextColorCenter(Color.BLACK)
                .build();

        mCityPikerView.setPicker(mProvinces, mCities, mDistricts );
        //设置点击EditView的时候不弹出键盘
        mAddressEdit.setInputType(InputType.TYPE_NULL);
        mAddressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCityPikerView.show();
            }
        });
    }

    protected void initProvinceDatas() {

        AssetManager asset = getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            mProvinces = handler.getDataList();

        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }

        if(mProvinces != null){

            for (ProvinceModel p : mProvinces){//遍历省

                List<CityModel> cities =  p.getCityList();//从每个省拿到市列表
                ArrayList<String> cityStrs = new ArrayList<>(cities.size()); //每个省对应一个市ArraryList
                ArrayList<ArrayList<String>> dts = new ArrayList<>();//每个市对应一个区ArraryList

                for (CityModel c : cities){//遍历市

                    cityStrs.add(c.getName()); // 把城市名称放入市ArraryList

                    List<DistrictModel> districts = c.getDistrictList();//从每个市拿到区列表
                    ArrayList<String> districtStrs = new ArrayList<>(districts.size());//区ArraryList（一个城市对应一个区ArraryList）

                    for (DistrictModel d : districts){
                        districtStrs.add(d.getName()); // 把区名放入区ArraryList
                    }
                    dts.add(districtStrs);//把区ArraryList放入市ArraryList

                }
                mDistricts.add(dts);//组装区数据
                mCities.add(cityStrs); // 组装市数据
            }

        }

    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditAddressActivity.this.finish();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initAddress() {

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String address = mAddressEdit.getText().toString();
                String deaddress = mDAddressEdit.getText().toString();
                String phone = mPhoneEdit.getText().toString();
                String name = mNameEdit.getText().toString();


                if (phone.length() != 11) {
                    ToastUtils.show(EditAddressActivity.this, "请检查手机号位数");
                    return;
                }

                if (!"".equals(address) && !"".equals(deaddress)
                        && !"".equals(phone) && !"".equals(name)) {

                    String Address = address + deaddress;
                    if (mAddress != null) {
                        initUpdateData(Address, phone, name, mAddress.getId());
                    }else{
                        initEditData(Address, phone, name);
                    }

                }else{
                    ToastUtils.show(EditAddressActivity.this, "请输入完整信息");
                }
            }
        });



    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initEditData(String address, String phone, String name) {

        SimpleHttpClient.newBuilder()
                .post()
                .url(Contants.API.ADDRESS_ADD)
                .addParams("user_id", Integer.parseInt(ShiXianApplication.getInstance().getUser().getId()))
                .addParams("address", "'" + address + "'")
                .addParams("phone", "'" + phone + "'")
                .addParams("isdefault", "'0'")
                .addParams("name", "'" + name + "'")
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Address>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Address> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            ToastUtils.show(EditAddressActivity.this, "添加成功");
                            //setResult(RESULT_OK);
                            EditAddressActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {
                        ToastUtils.show(EditAddressActivity.this, "添加失败");
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void initUpdateData(String address, String phone, String name, String address_id) {

        SimpleHttpClient.newBuilder()
                .post()
                .url(Contants.API.ADDRESS_UPDATE)
                .addParams("address", "'" + address + "'")
                .addParams("phone", "'" + phone + "'")
                .addParams("isdefault", "'0'")
                .addParams("name", "'" + name + "'")
                .addParams("address_id", "'" + address_id + "'")
                .build()
                .enqueue(new SimpleCallBack<BaseMsg<Address>>() {
                    @Override
                    public void onSuccess(Response response, BaseMsg<Address> msg) {

                        if (msg.getResultcode() == BaseMsg.RESULTCODE_SUCCESS) {

                            ToastUtils.show(EditAddressActivity.this, "更新成功");
                            setResult(RESULT_OK);
                            EditAddressActivity.this.finish();
                        }
                    }

                    @Override
                    public void onError(int code, Exception e) {
                        ToastUtils.show(EditAddressActivity.this, "更新失败");
                    }
                });
    }
}
