package com.example.shixian.utils;

import android.content.Context;
import android.util.SparseArray;

import com.example.shixian.bean.ShopCart;
import com.example.shixian.bean.Wares;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzf on 2018/2/25.
 */

public class CartProvider {

    public static final String CART_JSON = "cart_json";
    public static SparseArray<ShopCart> datas = new SparseArray<>(30);
    private Context mContext;

    public CartProvider(Context context){

        this.mContext = context;
        //初始化时要把缓存中的数据写入datas
        ListToSparse();
    }

    private List<ShopCart> SparseToList() {

        int size = datas.size();
        List<ShopCart> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++){
            list.add(datas.valueAt(i));
        }
        return list;
    }

    private List<ShopCart> ListToSparse() {

        List<ShopCart> carts = getDataFromLocal();
        if (carts != null && carts.size() != 0){

            for (ShopCart cart : carts){
                datas.put(Integer.parseInt(cart.getId()), cart);
            }
        }
        return carts;
    }

    //写入缓存
    public void Commit(){

        List<ShopCart> list = SparseToList();
        PreferencesUtils.putString(mContext, CART_JSON, JSONUtil.toJSON(list));
    }

    public List<ShopCart> getDataFromLocal() {

        String json = PreferencesUtils.getString(mContext, CART_JSON);
        List<ShopCart> carts = new ArrayList<>();
        if (json != null){
            carts = JSONUtil.fromJson(json, new TypeToken<List<ShopCart>>(){}.getType());
        }
        return carts;
    }

    public ShopCart ConvertData(Wares wares){

        ShopCart cart = new ShopCart();
        cart.setId(wares.getId());
        cart.setImageurl(wares.getImageurl());
        cart.setIntroduction(wares.getIntroduction());
        cart.setMenu_id(wares.getMenu_id());
        cart.setName(wares.getName());
        cart.setPrice(wares.getPrice());
        cart.setSort(wares.getSort());
        cart.setThought(wares.getThought());
        cart.setType(wares.getType());
        return cart;
    }

    public void put(Wares wares){

        ShopCart cart = ConvertData(wares);
        put(cart);
    }

    public void put(ShopCart cart){

        ShopCart temp = datas.get(Integer.parseInt(cart.getId()));
        if (temp != null){
            temp.setCheck(true);
            temp.setCount(temp.getCount()+1);
        }else{
            temp = cart;
            temp.setCount(1);
        }
        datas.put(Integer.parseInt(cart.getId()), temp);
        Commit();
    }

    public void update(ShopCart cart){
        datas.put(Integer.parseInt(cart.getId()), cart);
        Commit();
    }

    public void delete(ShopCart cart){
        datas.delete(Integer.parseInt(cart.getId()));
        Commit();
    }

    public void clear(){
        datas.clear();
        Commit();
    }

    public List<ShopCart> getAll(){
        return getDataFromLocal();
    }


}
