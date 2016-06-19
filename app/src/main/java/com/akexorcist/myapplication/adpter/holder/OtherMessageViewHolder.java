package com.akexorcist.myapplication.adpter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.myapplication.R;

/**
 * Created by Akexorcist on 6/20/2016 AD.
 */

public class OtherMessageViewHolder extends RecyclerView.ViewHolder {
    public TextView tvUserName;
    public TextView tvMessage;

    public OtherMessageViewHolder(View itemView) {
        super(itemView);
        tvUserName = (TextView) itemView.findViewById(R.id.tv_user_name);
        tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
    }
}
