package com.akexorcist.myapplication.common;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

/**
 * Created by Akexorcist on 6/20/2016 AD.
 */

public class BaseActivity extends AppCompatActivity {
    protected View getRootView() {
        return findViewById(android.R.id.content);
    }

    protected void showBottomMessage(String message) {
        Snackbar.make(getRootView(), message, Snackbar.LENGTH_SHORT).show();
    }

    protected void showBottomMessage(int strResId) {
        Snackbar.make(getRootView(), strResId, Snackbar.LENGTH_SHORT).show();
    }

    protected void showPopupMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showPopupMessage(int strResId) {
        Toast.makeText(this, strResId, Toast.LENGTH_SHORT).show();
    }

    protected void openActivity(Class<?> cls) {
        startActivity(new Intent(this, cls));
        finish();
    }

    protected void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getRootView().getWindowToken(), 0);
    }
}
