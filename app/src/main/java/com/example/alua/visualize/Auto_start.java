package com.example.alua.visualize;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ALUA on 23-Apr-15.
 */
public class Auto_start extends BroadcastReceiver {
    public void onReceive(Context arg0, Intent arg1) {
        Intent intent = new Intent(arg0, RunService.class);
        arg0.startService(intent);
        Log.i("Autostart", "started");
    }

}
