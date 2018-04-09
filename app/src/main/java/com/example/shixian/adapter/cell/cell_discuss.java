package com.example.shixian.adapter.cell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.shixian.R;
import com.example.shixian.adapter.base.RVBaseAdapter;
import com.example.shixian.adapter.base.RVBaseCell;
import com.example.shixian.adapter.base.RVBaseViewHolder;
import com.example.shixian.bean.Msg;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by zzf on 2018/3/28.
 */

public class cell_discuss extends RVBaseCell<List<Msg>>{

    private Context mContext;
    private List<Msg> mMsgList;
    private int Type = 1;

    public cell_discuss(List<Msg> msgs) {
        super(msgs);
        this.mMsgList = msgs;
    }

    @Override
    public int getItemType() {
        return Type;
    }

    @Override
    public RVBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType, RVBaseAdapter.OnItemClickListener listener) {

        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.template_discuss, parent, false);

        return new RVBaseViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(RVBaseViewHolder holder, int position) {

        Msg msg = mMsgList.get(position);

        if (msg.getType() == Msg.TYPE_RECEIVED) {

            holder.getTextView(R.id.discuss_left_text).setText(msg.getContent());
            Picasso.with(mContext).load(msg.getIcon_url())
                    .into(holder.getImageView(R.id.discuss_left_icon));
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.discuss_right);
            linearLayout.setVisibility(View.GONE);

        }else if (msg.getType() == Msg.TYPE_SENT) {

            holder.getTextView(R.id.discuss_right_text).setText(msg.getContent());
            Picasso.with(mContext).load(msg.getIcon_url())
                    .into(holder.getImageView(R.id.discuss_right_icon));
            LinearLayout linearLayout = (LinearLayout) holder.getView(R.id.discuss_left);
            linearLayout.setVisibility(View.GONE);

        }


    }
}
