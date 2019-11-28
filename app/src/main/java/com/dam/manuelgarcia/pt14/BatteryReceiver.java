package com.dam.manuelgarcia.pt14;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

public class BatteryReceiver extends BroadcastReceiver {


    private static final String TAG = "BatteryReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Start Receiver");

    }
}
