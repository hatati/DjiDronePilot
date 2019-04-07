package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class GPSLocationReceiver extends BroadcastReceiver {

    public static final String GPS_RECEIVED = "location_updates";
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getExtras().get("coordinates").toString(), Toast.LENGTH_LONG).show();
    }
}
