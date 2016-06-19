package com.akexorcist.myapplication.adpter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akexorcist.myapplication.R;
import com.akexorcist.myapplication.adpter.holder.OtherMessageViewHolder;
import com.akexorcist.myapplication.adpter.holder.UserMessageViewHolder;
import com.akexorcist.myapplication.model.MessageItem;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

/**
 * Created by Akexorcist on 6/20/2016 AD.
 */

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_OTHER = 0;
    private static final int TYPE_USER = 1;
    private ArrayList<MessageItem> messageItemList;
    private FirebaseUser currentFirebaseUser;

    public MessageAdapter(ArrayList<MessageItem> messageItemList, FirebaseUser currentFirebaseUser) {
        this.messageItemList = messageItemList;
        this.currentFirebaseUser = currentFirebaseUser;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_USER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_message_item, parent, false);
            return new UserMessageViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_other_message_item, parent, false);
            return new OtherMessageViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        String user = messageItemList.get(position).getUser();
        if (user.equalsIgnoreCase(currentFirebaseUser.getEmail()) || user.equalsIgnoreCase(currentFirebaseUser.getDisplayName())) {
            return TYPE_USER;
        }
        return TYPE_OTHER;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageItem messageItem = messageItemList.get(position);
        if (holder instanceof UserMessageViewHolder) {
            UserMessageViewHolder userMessageViewHolder = (UserMessageViewHolder) holder;
            userMessageViewHolder.tvUserName.setText(R.string.you);
            userMessageViewHolder.tvMessage.setText(messageItem.getText());
        } else if (holder instanceof OtherMessageViewHolder) {
            OtherMessageViewHolder otherMessageViewHolder = (OtherMessageViewHolder) holder;
            otherMessageViewHolder.tvUserName.setText(messageItem.getUser());
            otherMessageViewHolder.tvMessage.setText(messageItem.getText());
        }
    }

    @Override
    public int getItemCount() {
        return messageItemList.size();
    }
}
