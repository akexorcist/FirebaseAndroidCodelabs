package com.akexorcist.myapplication.model;

/**
 * Created by Akexorcist on 6/20/2016 AD.
 */

public class MessageItem {
    String text;
    String user;

    public MessageItem() {
    }

    public MessageItem(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
