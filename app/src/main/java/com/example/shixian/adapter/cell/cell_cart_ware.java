package com.example.shixian.adapter.cell;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.ShopCart;
import com.example.shixian.bean.Wares;
import com.example.shixian.utils.CartProvider;
import com.example.shixian.widget.NumberAddSubView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/2/26.
 */

public class cell_cart_ware extends RVBaseCell<List<ShopCart>>{

    public static final int Type = 8;
    private List<ShopCart> mShopCarts;
    private Context mContext;
    private CartProvider mCartProvider;
    private OnCheckButtonListener mOnCheckButtonListener;
    private OnAddSubViewListener mOnAddSubViewListener;

    public cell_cart_ware(List<ShopCart> shopCarts, Context context) {
        super(shopCarts);
        this.mShopCarts = shopCarts;
        this.mContext = context;
        mCartProvider = new CartProvider(mContext);
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.template_cart_layout, parent,false);
        RVBaseViewHolder holder = new RVBaseViewHolder(view, listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        if (mShopCarts != null){

            final ShopCart shopCart = mShopCarts.get(position);
            Picasso.with(mContext).load(shopCart.getImageurl()).into(holder.getImageView(R.id.foodimage));
            final CheckBox checkBox = (CheckBox)holder.getView(R.id.check_button);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    shopCart.setCheck(!shopCart.isCheck());
                    mCartProvider.update(shopCart);
                    checkBox.setChecked(shopCart.isCheck());
                    if (mOnCheckButtonListener != null)
                        mOnCheckButtonListener.after();
                }
            });
            checkBox.setChecked(shopCart.isCheck());

            holder.getTextView(R.id.name).setText(shopCart.getName());
            holder.getTextView(R.id.introduction).setText(shopCart.getIntroduction());
            holder.getTextView(R.id.price).setText(shopCart.getPrice()+"¥");

            final NumberAddSubView numberView = (NumberAddSubView) holder.getView(R.id.add_sub_view);
            numberView.setOnButtonClickListener(new NumberAddSubView.OnButtonClickListener() {
                @Override
                public void onButtonAddClick(View view, int value) {
                    shopCart.setCount(value);
                    mCartProvider.update(shopCart);
                    if (mOnAddSubViewListener != null)
                        mOnAddSubViewListener.after();

                }

                @Override
                public void onButtonSubClick(View view, int value) {
                    shopCart.setCount(value);
                    mCartProvider.update(shopCart);
                    if (mOnAddSubViewListener != null)
                        mOnAddSubViewListener.after();
                }
            });
            numberView.setValue(shopCart.getCount());

        }

    }

    public void setOnCheckButtonListener(OnCheckButtonListener listener){

        this.mOnCheckButtonListener = listener;
    }

    public void setOnAddSubViewListener(OnAddSubViewListener listener){

        this.mOnAddSubViewListener = listener;
    }

    public interface OnCheckButtonListener{

        void after();
    }

    public interface OnAddSubViewListener{

        void after();
    }
}
