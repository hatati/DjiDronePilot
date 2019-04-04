package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

public class SmsBroadcastReciever extends BroadcastReceiver {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SMS_RECEIVED)) {

            Bundle bundle = intent.getExtras();
            Object[] messages = (Object[]) bundle.get("pdus");
            SmsMessage[] sms = new SmsMessage[messages.length];
            // Create messages for each incoming PDU
            for (int n = 0; n < messages.length; n++) {
                sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            }
            for (SmsMessage msg : sms) {
                Log.e("RECEIVED MSG",":"+msg.getMessageBody());
                // Verify if the message came from our known sender
                Toast.makeText(context, "RECEIVED MSG: " + msg.getMessageBody(), Toast.LENGTH_LONG).show();
            }


        }

    }
}
