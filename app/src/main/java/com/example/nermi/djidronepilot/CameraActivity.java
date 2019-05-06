package com.example.nermi.djidronepilot;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nermi.dailib.DJIFacade;

public class CameraActivity extends AppCompatActivity {

    BroadcastReceiver smsBroadcastReceiver;
    DJIFacade djiFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        smsBroadcastReceiver = SmsBroadcastReceiver.getSmsBroadcastReceiver();

        if (null == savedInstanceState) {
            djiFacade = DJIFacade.getDjiFacade();
            djiFacade.initUI(this, R.id.container);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        //Register SMSReceiver
        IntentFilter intentFilter = new IntentFilter(SmsBroadcastReceiver.SMS_RECEIVED);
        registerReceiver(smsBroadcastReceiver, intentFilter);

        djiFacade.pitchForward(this, R.id.container, "forward");
        djiFacade.rollLeft(this, R.id.container, "roll_left");
        djiFacade.rollRight(this, R.id.container, "roll_right");
        djiFacade.yawLeft(this, R.id.container, "yaw_left");
        djiFacade.yawRight(this, R.id.container, "yaw_right");

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Unregister Receivers
        unregisterReceiver(smsBroadcastReceiver);
    }

}
