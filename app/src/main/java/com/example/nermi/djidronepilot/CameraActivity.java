package com.example.nermi.djidronepilot;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.nermi.djilib.DAIFacade;

public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            DAIFacade daiFacade = new DAIFacade();

            daiFacade.initDjiUI(this, getSupportFragmentManager(), R.id.container);
        }
    }
}
