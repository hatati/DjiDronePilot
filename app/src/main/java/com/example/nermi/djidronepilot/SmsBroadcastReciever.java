package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SmsBroadcastReciever extends BroadcastReceiver {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {
            Toast.makeText(context, "TESTING BROADCAST RECIEVER", Toast.LENGTH_LONG).show();
        }
    }
}
