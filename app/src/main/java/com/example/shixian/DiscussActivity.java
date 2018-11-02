package com.example.shixian;

import android.graphics.Rect;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.shixian.adapter.base.RVSimpleAdapter;
import com.example.shixian.adapter.cell.cell_discuss;
import com.example.shixian.bean.Msg;
import com.example.shixian.utils.ToastUtils;
import com.example.shixian.widget.myToolbar;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DiscussActivity extends BaseActivity {

    private myToolbar mToolbar;
    private RecyclerView mRecyclerView;
    private EditText mEditText;
    private Button mButton;
    private View mStutas;
    private List<Msg> mMsgsList = new ArrayList<>();
    private List<String> contentList = new ArrayList<>();
    private List<String> iconList = new ArrayList<>();
    private RVSimpleAdapter mAdapter;
    private LinearLayout mEditLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        mToolbar = findViewById(R.id.discuss_toolbar);
        mRecyclerView = findViewById(R.id.discuss_recycler);
        mEditText = findViewById(R.id.discuss_edit);
        mButton = findViewById(R.id.discuss_send);
        mStutas = findViewById(R.id.discuss_status);
        mEditLayout = findViewById(R.id.edit_layout);
        init();
    }

    private void init() {

        FitsStatusBar(mStutas);
        setStatusbar();
        initToolbar();
        initMsg();
        initSend();
        onSoftKeyBoardStatusChange();
    }

    private void initToolbar() {

        mToolbar.setLeftButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscussActivity.this.finish();
            }
        });
    }

    private void initRecyclerView() {

        if (mAdapter == null) {

            mAdapter = new RVSimpleAdapter();
            mAdapter.OnlyOneItem(new cell_discuss(mMsgsList), mMsgsList.size());
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerView.setAdapter(mAdapter);
        }else {

            mAdapter.clear();
            mAdapter.OnlyOneItem(new cell_discuss(mMsgsList), mMsgsList.size());
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.scrollToPosition(mMsgsList.size() - 1);
        }

    }

    private void initMsg() {

        Msg msg = new Msg();
        msg.setContent("马大姐! 吃饭了没啊？");
        msg.setType(Msg.TYPE_RECEIVED);
        msg.setIcon_url(getIcon());
        Msg msg2 = new Msg();
        msg2.setContent("刚吃好，准备去打麻将!");
        msg2.setType(Msg.TYPE_SENT);
        if (ShiXianApplication.getInstance().getUser() != null)
            msg2.setIcon_url(ShiXianApplication.getInstance().getUser().getIcon());
        mMsgsList.add(msg);
        mMsgsList.add(msg2);
        initRecyclerView();
    }

    private void initSend() {

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String content = mEditText.getText().toString();
                if (!"".equals(content)) {

                    Msg msg3 = new Msg();
                    msg3.setContent(content);
                    msg3.setIcon_url(ShiXianApplication.getInstance().getUser().getIcon());
                    msg3.setType(Msg.TYPE_SENT);

                    Msg msg4 = new Msg();
                    msg4.setContent(getContent());
                    msg4.setIcon_url(getIcon());
                    msg4.setType(Msg.TYPE_RECEIVED);

                    mMsgsList.add(msg3);
                    mMsgsList.add(msg4);
                    mEditText.setText("");
                    initRecyclerView();

                }else {
                    ToastUtils.show(DiscussActivity.this, "请输入内容");
                }

            }
        });
    }

    private String getIcon() {

        iconList.add("http://img3.duitang.com/uploads/item/201608/12/20160812005801_kKHTy.jpeg");
        iconList.add("http://imgsrc.baidu.com/forum/w=580/sign=6507c9571f30e924cfa49c397c086e66/9aab05a4462309f7f25d794d720e0cf3d6cad6a5.jpg");
        iconList.add("http://img3.duitang.com/uploads/item/201505/23/20150523165343_M2dsr.jpeg");
        iconList.add("http://m.3fantizi.com/Article/pic/20141110333758.jpg");
        iconList.add("http://img3.imgtn.bdimg.com/it/u=2727319063,4252015704&fm=27&gp=0.jpg");
        iconList.add("http://www.pig66.com/uploadfile/2017/0509/20170509074111472.jpg");
        iconList.add("http://imgsrc.baidu.com/forum/w%3D580/sign=5c832626d0a20cf44690fed746084b0c/f01f3a292df5e0fe9c2428a05c6034a85edf7256.jpg");

        return iconList.get(new Random().nextInt(7));
    }

    private String getContent() {

        contentList.add("周大爷在吗？象棋走一走啊！");
        contentList.add("马大姐，你昨天推荐给我的那个糖醋排骨味道是真的好啊，食材又新鲜，分量又多，真实惠！");
        contentList.add("王大娘，最近有没有去附近菜市场啊，我用了这个软件之后都好久没有去过菜市场了，最近天气又热，菜场又那么远，赶紧叫你儿子下载一个食鲜啊");
        contentList.add("今天叫了份川味宫爆虾球给做给孙子吃，孙子直接吃了两碗饭哈哈！");
        contentList.add("大晚上的，有没有吃完晚饭一起去跳广场舞的啊");
        return contentList.get(new Random().nextInt(5));
    }

    private void onSoftKeyBoardStatusChange() {

        addSoftKeyboardChangedListener(new OnSoftKeyboardStateChangedListener() {
            @Override
            public void OnSoftKeyboardStateChananged(boolean isKeyBoardShow, int keyboardHeight) {

                if (isKeyBoardShow) {

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, keyboardHeight);
                    mEditLayout.setLayoutParams(lp);

                }else {

                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT);
                    lp.setMargins(0, 0, 0, 0);
                    mEditLayout.setLayoutParams(lp);

                }
            }
        });


    }
}
