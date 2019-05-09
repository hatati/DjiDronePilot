package com.nermi.dailib;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    private final String modelPath = "landing_stripes-CNN-RGB.tflite";
    private final String labelsPath = "labels_landing_stripe.txt";
    DAIFacade daiFacade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (null == savedInstanceState) {
            daiFacade = new DAIFacade();

            daiFacade.initDjiUI(this, R.id.container);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        daiFacade.initCNNModel(this, modelPath, labelsPath, 70,70, R.id.container);
        daiFacade.djiPitchForward(this, R.id.container, "forward", 0.0);
        daiFacade.djiRollLeft(this, R.id.container, "roll_left", 0.0);
        daiFacade.djiRollRight(this, R.id.container, "roll_right", 0.0);
        daiFacade.djiYawLeft(this, R.id.container, "yaw_left", 0.0);
        daiFacade.djiYawRight(this, R.id.container, "yaw_right", 0.0);

    }

    @Override
    protected void onStop() {
        super.onStop();
        //Unregister Receivers
    }

}
