package com.dam.manuelgarcia.pt14;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BatteryReceiver extends BroadcastReceiver {


    private static final String TAG = "BatteryReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Toast.makeText(context, "Boot completed!!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Boot completed!, Entra a onReceive-" + intent.getAction());
        }

        if (Intent.ACTION_BATTERY_LOW.equals(intent.getAction())) {
            Intent batt = new Intent(context, LoadBattery.class);
            context.startActivity(batt);

            Toast.makeText(context, "Battery Low!!", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Battery Low!, Entra a onReceive-" + intent.getAction());
        }


    }
}
