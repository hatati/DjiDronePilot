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
    DAIFacade daiFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        smsBroadcastReceiver = SmsBroadcastReceiver.getSmsBroadcastReceiver();

        if (null == savedInstanceState) {
            daiFacade = new DAIFacade();

            daiFacade.initDjiUI(this, R.id.container);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Register SMSReceiver
        IntentFilter intentFilter = new IntentFilter(SmsBroadcastReceiver.SMS_RECEIVED);
        registerReceiver(smsBroadcastReceiver, intentFilter);

        daiFacade.initCNNModel(this, modelPath, labelsPath, 70,70, R.id.container);
        daiFacade.djiPitchForward(this, R.id.container, "forward", 0.0);
        daiFacade.djiRollLeft(this, R.id.container, "roll_left", 0.0);
        daiFacade.djiRollRight(this, R.id.container, "roll_right", 0.0);
        daiFacade.djiYawLeft(this, R.id.container, "yaw_left", 0.0);
        daiFacade.djiRollRight(this, R.id.container, "yaw_right", 0.0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Unregister Receivers
        unregisterReceiver(smsBroadcastReceiver);
    }

}
