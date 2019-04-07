package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nermi.dailib.DAIFacade;

public class CameraActivity extends AppCompatActivity {

    private final String modelPath = "landing_stripes-CNN-RGB.tflite";
    private final String labelsPath = "labels_landing_stripe.txt";
    BroadcastReceiver smsBroadcastReceiver;
    BroadcastReceiver gpsLocationReceiver;
    DAIFacade daiFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        smsBroadcastReceiver = SmsBroadcastReceiver.getSmsBroadcastReceiver();
        gpsLocationReceiver = new GPSLocationReceiver();

        if (null == savedInstanceState) {
            daiFacade = new DAIFacade();

            daiFacade.initDjiUI(this, R.id.container);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(getApplicationContext(), GPSLog_Service.class);
        startService(intent);

        IntentFilter intentFilter = new IntentFilter(SmsBroadcastReceiver.SMS_RECEIVED);
        registerReceiver(smsBroadcastReceiver, intentFilter);

        IntentFilter gpsFilter = new IntentFilter(GPSLocationReceiver.GPS_RECEIVED);
        registerReceiver(gpsLocationReceiver, gpsFilter);

        daiFacade.initCNNModel(this, modelPath, labelsPath, 70,70, R.id.container);
        daiFacade.djiPitchForward(this, R.id.container, "forward", 0.0);
        daiFacade.djiRollLeft(this, R.id.container, "left", 0.0);
        daiFacade.djiRollRight(this, R.id.container, "right", 0.0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(smsBroadcastReceiver);
        unregisterReceiver(gpsLocationReceiver);
        Intent intent = new Intent(getApplicationContext(), GPSLog_Service.class);
        stopService(intent);
    }

}
