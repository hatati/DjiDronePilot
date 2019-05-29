package com.example.nermi;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nermi.djidronepilot.R;
import com.nermi.dailib.DJIFacade;


public class CameraActivity extends AppCompatActivity {

    BroadcastReceiver smsBroadcastReceiver;

    /**
     * Use DJIFacede since we are comunicating with a DJI drone
     */
    DJIFacade djiFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        // Init broadcast receiver
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

        // Setup control commands and their respective labels
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
