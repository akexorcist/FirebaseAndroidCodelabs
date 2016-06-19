package com.akexorcist.myapplication.manager;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.akexorcist.myapplication.R;

/**
 * Created by Akexorcist on 4/22/2016 AD.
 */
public class DialogManager {
    private static DialogManager dialogManager;

    public static DialogManager getInstance() {
        if (dialogManager == null) {
            dialogManager = new DialogManager();
        }
        return dialogManager;
    }

    private MaterialDialog materialDialog;

    public void showDialog(Context context, String title, String content, String positive, String negative, MaterialDialog.SingleButtonCallback callback) {
        dismissDialog();
        materialDialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(positive)
                .negativeText(negative)
                .onNegative(callback)
                .onPositive(callback)
                .show();
    }

    public void showLoading(Context context) {
        dismissDialog();
        materialDialog = new MaterialDialog.Builder(context)
                .content(R.string.loading)
                .progress(true, 0)
                .show();
    }

    public void dismissDialog() {
        if (materialDialog != null) {
            materialDialog.dismiss();
            materialDialog = null;
        }
    }
}
