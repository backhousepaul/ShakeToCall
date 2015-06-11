package com.example.alua.visualize;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ALUA on 17-Apr-15.
 */
public class AdminReceiver extends DeviceAdminReceiver {


    static SharedPreferences getSamplePreferences(Context context) {
        return context.getSharedPreferences(
                DeviceAdminReceiver.class.getName(), 0);
    }
}