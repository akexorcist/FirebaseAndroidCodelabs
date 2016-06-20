package com.akexorcist.myapplication.manager;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

/**
 * Created by Akexorcist on 6/20/2016 AD.
 */

public class EventTrackerManager {
    public static void onChangeName(Context context) {
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "change_name");
        FirebaseAnalytics.getInstance(context).logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
}
