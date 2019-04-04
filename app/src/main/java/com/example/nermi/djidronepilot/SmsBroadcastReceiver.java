package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;

public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";

    private static class SingletonHelper{
        // Nested class is referenced when getDjiFacade is called
        private static final SmsBroadcastReceiver _sms_broadcast_receiver = new SmsBroadcastReceiver();
    }

    public static SmsBroadcastReceiver getSmsBroadcastReciever(){
        return SingletonHelper._sms_broadcast_receiver;
    }

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

                switch (msg.getMessageBody()){
                    case "START":
                        Intent cameraIntent = new Intent(context, CameraActivity.class);
                        context.startActivity(cameraIntent);
                        break;
                    case "LAND":
                        View land = ((MainActivity)context).findViewById(R.id.btn_land);
                        ((MainActivity)context).onClick(land);
                        break;
                    case "FORCE LAND":
                        View force_land = ((MainActivity)context).findViewById(R.id.btn_force_land);
                        ((MainActivity)context).onClick(force_land);
                        break;
                    case "TAKE OFF":
                        View take_off = ((MainActivity)context).findViewById(R.id.btn_take_off);
                        ((MainActivity)context).onClick(take_off);
                        break;
                    default: break;
                }

            }


        }

    }


}
