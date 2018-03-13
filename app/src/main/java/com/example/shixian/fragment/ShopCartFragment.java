package com.example.shixian.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.shixian.CreateOrderActivity;
import com.example.shixian.R;
import com.example.shixian.ShiXianApplication;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_cart_ware;
import com.example.shixian.bean.ShopCart;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.util.Iterator;
import java.util.List;

/**
 * Created by admin on 2017/9/2.
 */

public class ShopCartFragment extends BaseFragment implements View.OnClickListener{

    private View statusBar;
    private RecyclerView mRecyclerView;
    private RVSimpleAdapter mAdapter;
    private CartProvider mCartProvider;
    private CheckBox mCheckBox;
    private TextView mTotal;
    private TextView mTogether;
    private Button mOrderButton;
    private myToolbar mToolbar;
    public static final int ACTION_EDIT = 1;
    public static final int ACTION_CAMPLATE = 2;
    public static final int ACTION_ORDER = 3;
    public static final int ACTION_DEL = 4;

    @Override
    public View CreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_shop_cart, container, false);
        statusBar = view.findViewById(R.id.shop_StatusbarView);
        mRecyclerView = view.findViewById(R.id.cart_recyclerview);
        mTotal = view.findViewById(R.id.shopcart_price);
        mCartProvider = new CartProvider(getContext());
        mCheckBox = view.findViewById(R.id.shopcart_radio);
        mToolbar = view.findViewById(R.id.shopcart_toolbar);
        mOrderButton = view.findViewById(R.id.order_button);
        mTogether = view.findViewById(R.id.together);


        return view;
    }

    @Override
    public void init() {
        FitsStatusBar(statusBar);
        changeToolbar();
        initCart();
        initTotal();
        initChoose();
        initOrderButton();
    }

    private void initOrderButton(){

        mOrderButton.setTag(ACTION_ORDER);
        mOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int tag = (int) view.getTag();
                if (tag == ACTION_ORDER){
                    if (mCartProvider.getAll() != null && mCartProvider.getAll().size() != 0) {
                        Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                        intent.putExtra("total", mTotal.getText());
                        startActivity(intent);
                    }else{
                        ToastUtils.show(ShopCartFragment.this.getContext(),"购物车为空");
                    }

                }else if (tag == ACTION_DEL){
                    if (!delWare())
                        ToastUtils.show(getContext(), "请选择需要删除的商品");
                }

            }
        });

    }
    //从另外的Activity返回时执行的操作
    @Override
    public void onResume() {
        super.onResume();
        refData();
    }

    private void initChoose() {

        List<ShopCart> carts = mCartProvider.getAll();
        int sum = carts.size();
        int count = 0;

        if (carts != null && carts.size() > 0){
            for (ShopCart cart : carts){

                if (!cart.isCheck()){
                    mCheckBox.setChecked(false);
                    break;
                }else{
                    count++;
                }
            }
            if (sum == count){
                mCheckBox.setChecked(true);
            }
            mOrderButton.setEnabled(true);
        }else{
            mOrderButton.setEnabled(false);
        }

        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setCheckAll(mCheckBox.isChecked());
            }
        });
    }

    private void setCheckAll(boolean isCheck) {

        List<ShopCart> carts = mCartProvider.getAll();
        if (carts != null && carts.size() > 0){

            for (ShopCart cart : carts){
                if (cart.isCheck() != isCheck){
                    cart.setCheck(isCheck);
                    mCartProvider.update(cart);
                }
            }
            refData();
        }
    }

    private void initTotal() {

        int sum = 0;
        List<ShopCart> carts = mCartProvider.getAll();
        if (carts != null && carts.size() >= 0){
            for (int i = 0; i < carts.size(); i++){
                ShopCart cart = carts.get(i);
                if (cart.isCheck()){
                    sum+=(cart.getCount()*Integer.parseInt(cart.getPrice()));
                }
            }
            mTotal.setText(sum+"¥");
        }

    }

    private void initCart() {

        if (mCartProvider.getAll() != null){
            mCheckBox.setChecked(true);
            LinearLayoutManager manager = new LinearLayoutManager(getContext());
            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(initCell(), mCartProvider.getAll().size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.setLayoutManager(manager);
        }

    }

    public void refData() {

        List<ShopCart> carts = mCartProvider.getAll();
        if (carts != null && carts.size() >= 0){

            mAdapter.clear();
            mAdapter.OnlyOneItem(initCell(), carts.size());
            if (!mOrderButton.isEnabled())
                mOrderButton.setEnabled(true);
            initTotal();
        }

    }

    private cell_cart_ware initCell(){

        cell_cart_ware cellCartWare = new cell_cart_ware(mCartProvider.getAll(), getContext());
        cellCartWare.setOnAddSubViewListener(new cell_cart_ware.OnAddSubViewListener() {
            @Override
            public void after() {
                initTotal();
            }
        });
        cellCartWare.setOnCheckButtonListener(new cell_cart_ware.OnCheckButtonListener() {
            @Override
            public void after() {
                initChoose();
                initTotal();
            }
        });

        return cellCartWare;
    }

    private void changeToolbar() {

        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mToolbar.setRightButtonOnClickListener(this);
        mToolbar.setRightButtonIcon(R.drawable.edit);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
    }

    private void showDelControl(){

        mToolbar.setRightButtonIcon(R.drawable.ok);
        mToolbar.getRightButton().setTag(ACTION_CAMPLATE);
        mOrderButton.setText("删除");
        mOrderButton.setTag(ACTION_DEL);
        setCheckAll(false);
        mCheckBox.setChecked(false);

    }

    private void hideDelControl(){

        mToolbar.setRightButtonIcon(R.drawable.edit);
        mToolbar.getRightButton().setTag(ACTION_EDIT);
        mTotal.setVisibility(View.VISIBLE);
        mOrderButton.setText("结算");
        mOrderButton.setTag(ACTION_ORDER);
        mTogether.setVisibility(View.VISIBLE);
        setCheckAll(true);
        mCheckBox.setChecked(true);
        initTotal();

    }

    private boolean delWare(){
        int flag = 1;
        List<ShopCart> carts = mCartProvider.getAll();
        if (carts != null && carts.size() > 0){

            for (Iterator iterator = carts.iterator(); iterator.hasNext();){
                ShopCart cart = (ShopCart) iterator.next();
                if (cart.isCheck()){
                    flag = 0;
                    iterator.remove();
                    mCartProvider.delete(cart);
                }
            }
            refData();
        }
        if (flag == 0)
            return true;
        return false;
    }

    @Override
    public void onClick(View view) {

        int action = (int) view.getTag();
        if (action == ACTION_EDIT){
            showDelControl();
        }else if (action == ACTION_CAMPLATE){
            hideDelControl();
        }
    }
}
