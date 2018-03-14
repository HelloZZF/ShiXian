package com.example.shixian.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.TintTypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.shixian.R;

import java.util.zip.Inflater;

/**
 * Created by zzf on 2018/2/22.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener{

    public static final String TAG = "NumberAddSubView";
    public static final int DEFAULT_MAX = 1000;
    public static final int DEFAULT_MIN = 0;

    private TextView mEtxtNum;
    private ImageButton mBtnAdd;
    private ImageButton mBtnSub;

    private OnButtonClickListener mOnButtonClickListener;
    private LayoutInflater mInflater;

    private int value;
    private int minValue = DEFAULT_MIN;
    private int maxValue = DEFAULT_MAX;


    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @SuppressLint("RestrictedApi")
    public NumberAddSubView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mInflater = LayoutInflater.from(context);
        initView();

        if (attrs != null){

            @SuppressLint("RestrictedApi") final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.NumberAddSubView, defStyleAttr, 0);

            @SuppressLint("RestrictedApi") int val = a.getInt(R.styleable.NumberAddSubView_value, 0);
            setValue(val);

            @SuppressLint("RestrictedApi") int maxVal = a.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            if (maxVal != 0)
                setMaxValue(maxVal);

            @SuppressLint("RestrictedApi") int minVal = a.getInt(R.styleable.NumberAddSubView_minValue, 0);
            if (minVal != 0)
                setMinValue(minVal);

            @SuppressLint("RestrictedApi") Drawable etBackground = a.getDrawable(R.styleable.NumberAddSubView_editBackground);
            if(etBackground!=null)
                setEditTextBackground(etBackground);


            @SuppressLint("RestrictedApi") Drawable buttonAddBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonAddBackgroud);
            if(buttonAddBackground!=null)
                setButtonAddBackgroud(buttonAddBackground);

            @SuppressLint("RestrictedApi") Drawable buttonSubBackground = a.getDrawable(R.styleable.NumberAddSubView_buttonSubBackgroud);
            if(buttonSubBackground!=null)
                setButtonSubBackgroud(buttonSubBackground);

            a.recycle();
        }
    }

    private void setEditTextBackground(Drawable etBackground) {

        mEtxtNum.setBackgroundDrawable(etBackground);
    }

    private void setButtonAddBackgroud(Drawable buttonAddBackgroud){
        mBtnAdd.setBackgroundDrawable(buttonAddBackgroud);
    }

    private void setButtonSubBackgroud(Drawable buttonSubBackgroud){
        mBtnSub.setBackgroundDrawable(buttonSubBackgroud);
    }

    private void setMinValue(int minVal) {
        this.minValue = minVal;
    }

    private void setMaxValue(int maxVal) {
        this.maxValue = maxVal;
    }

    public void setValue(int value) {
        mEtxtNum.setText(value+"");
        this.value = value;
    }

    private void initView() {

        View view = mInflater.inflate(R.layout.widget_num_add_sub, this, true);
        mEtxtNum = view.findViewById(R.id.etxt_num);
        mEtxtNum.setInputType(InputType.TYPE_NULL);

        mBtnAdd = view.findViewById(R.id.btn_add);
        mBtnSub = view.findViewById(R.id.btn_sub);

        mBtnAdd.setOnClickListener(this);
        mBtnSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btn_add){

            numAdd();
            if (mOnButtonClickListener != null){
                mOnButtonClickListener.onButtonAddClick(view, this.value);
            }
        }else if (view.getId() == R.id.btn_sub){

            numSub();
            if (mOnButtonClickListener != null){
                mOnButtonClickListener.onButtonSubClick(view, this.value);
            }
        }
    }

    private void numSub() {

        getValue();
        if (this.value > minValue)
            this.value--;
        mEtxtNum.setText(this.value+"");
    }

    private void numAdd() {

        getValue();
        if (this.value < maxValue )
            this.value++;
        mEtxtNum.setText(this.value+"");
    }

    private int getValue() {

        String mValue = mEtxtNum.getText().toString();
        if (mValue != null && !"".equals(mValue)){
            this.value = Integer.parseInt(mValue);
        }

        return this.value;
    }

    public void setOnButtonClickListener(OnButtonClickListener listener){
        this.mOnButtonClickListener = listener;
    }

    public interface OnButtonClickListener{

        void onButtonAddClick(View view, int value);
        void onButtonSubClick(View view, int value);
    }
}
