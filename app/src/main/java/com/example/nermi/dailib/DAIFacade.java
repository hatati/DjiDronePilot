package com.example.nermi.dailib;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.nermi.djidronepilot.Camera2Fragment;
import com.example.nermi.djidronepilot.R;

public class DAIFacade {

    private Boolean isConnectedToDrone;
    DJIFacade djiFacade;

    public DAIFacade(){
        this.isConnectedToDrone = false;
        djiFacade = new DJIFacade();
    }

    public void connectToDJIDrone(){

    }

    public void initDjiUI(AppCompatActivity activity, int frameLayoutId){
        activity.getSupportFragmentManager().beginTransaction().replace(frameLayoutId, Camera2Fragment.newInstance())
                .commit();


    }

    public void initParrotUI(){}

    public void initCNNModel(AppCompatActivity activity, String modelPath, String labelsPath, int imageSizeX, int imageSizeY, int frameLayoutId){
        Camera2Fragment fragment = (Camera2Fragment) activity.getSupportFragmentManager().findFragmentById(frameLayoutId);
        fragment.setModelPath(modelPath);
        fragment.setLabelsPath(labelsPath);
        fragment.setImageSizeX(imageSizeX);
        fragment.setImageSizeY(imageSizeY);
    }

    public void setModelPath(String path){}

    public void setLabelPath(String path){}

    public void pitchForward(Double sensitivityRate){}

    public void pitchBackwards(Double sensitivityRate){}

    public void rollRight(Double sensitivityRate){}

    public void rollLeft(Double sensitivityRate){}

    public void yawRight(Double sensitivityRate){}

    public void yawLeft(Double sensitivityRate){}
}
