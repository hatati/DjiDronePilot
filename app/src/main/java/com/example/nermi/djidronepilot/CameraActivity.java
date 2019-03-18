package com.example.nermi.djidronepilot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nermi.dailib.DAIFacade;

public class CameraActivity extends AppCompatActivity {


    private final String modelPath = "forward-left-right-CNN-RGB.tflite";
    private final String labelsPath = "labels_forward_left_right.txt";
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
    }
}
