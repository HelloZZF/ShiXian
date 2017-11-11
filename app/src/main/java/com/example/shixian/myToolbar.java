package com.example.shixian;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by admin on 2017/11/11.
 */

public class myToolbar extends Toolbar{

    private LayoutInflater mInflater;

    private View mView;
    private TextView mTextTitle;
    private SearchView homeSearView;
    private SearchView classifySearView;
    private ImageButton mRightButton;
    private ImageButton mLeftButton;



    public myToolbar(Context context) {
        this(context, null);
    }

    public myToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context,attrs,0);
    }

    public myToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

//从app中设置标题因为会先调用父类的构造方法然后判断Title是否为空，所以要在setTitel中调用initView,这边也要写是因为还需要设置其他属性
        initView();
//        设置内部控件边距
        setContentInsetsRelative(10,10);

        if (attrs != null){
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(),attrs,R.styleable.myToolbar,defStyleAttr,0);
            final Drawable rightIcon = a.getDrawable(R.styleable.myToolbar_rightButtonIcon);
            if (rightIcon != null) {
                setRightButtonIcon(rightIcon);
            }

            final Drawable leftIcon = a.getDrawable(R.styleable.myToolbar_leftButtonIcon);
            if (leftIcon != null) {
                setLeftButtonIcon(leftIcon);
            }

            boolean isShowHomeSearchView = a.getBoolean(R.styleable.myToolbar_isShowHomeSearchView, false);
            boolean isShowClassifySearchView = a.getBoolean(R.styleable.myToolbar_isShowClassifySearchView, false);

            if (isShowHomeSearchView) {
                showHomeSearchView();
                hideTitleView();
            }

            if (isShowClassifySearchView) {
                showClassifySearchView();
                hideTitleView();
            }

            a.recycle();
        }

    }

    private void initView() {

        if (mView == null) {

            mInflater = LayoutInflater.from(getContext());
            mView = mInflater.inflate(R.layout.my_toolbar, null);

            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            homeSearView = (SearchView) mView.findViewById(R.id.home_searchView);
            classifySearView = (SearchView) mView.findViewById(R.id.classify_searchView);
            mRightButton = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);
            mLeftButton = (ImageButton) mView.findViewById(R.id.toolbar_leftButton);

            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);

        }


    }

    public void setRightButtonIcon(Drawable icon){

        if (mRightButton != null) {
            mRightButton.setImageDrawable(icon);
            mRightButton.setVisibility(VISIBLE);
        }
    }

    public void setRightButtonOnClickListener(OnClickListener li){

        mRightButton.setOnClickListener(li);

    }

    public void setLeftButtonIcon(Drawable icon){

        if (mLeftButton != null) {
            mLeftButton.setImageDrawable(icon);
            mLeftButton.setVisibility(VISIBLE);
        }
    }

    public void setLeftButtonOnClickListener(OnClickListener li){

        mLeftButton.setOnClickListener(li);

    }

    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
    }

    @Override
    public void setTitle(CharSequence title) {

        initView();
        mTextTitle.setText(title);
        showTitleView();
    }

    public void showHomeSearchView(){
        if (homeSearView != null)
            homeSearView.setVisibility(VISIBLE);
    }

    public void hideHomeSearchView(){
        if (homeSearView != null)
            homeSearView.setVisibility(GONE);
    }

    public void showClassifySearchView(){
        if (classifySearView != null)
            classifySearView.setVisibility(VISIBLE);
    }

    public void hideClassifySearchView(){
        if (classifySearView != null)
            classifySearView.setVisibility(GONE);
    }

    public void showTitleView(){
        if (mTextTitle != null)
            mTextTitle.setVisibility(VISIBLE);
    }

    public void hideTitleView(){
        if (mTextTitle != null)
            mTextTitle.setVisibility(GONE);
    }

    public void showRightButton(){
        if (mRightButton != null)
            mRightButton.setVisibility(VISIBLE);
    }

    public void hideRightButton(){
        if (mRightButton != null)
            mRightButton.setVisibility(GONE);
    }

    public void showLeftButton(){
        if (mLeftButton != null)
            mLeftButton.setVisibility(VISIBLE);
    }

    public void hideLeftButton(){
        if (mLeftButton != null)
            mLeftButton.setVisibility(GONE);
    }
}
