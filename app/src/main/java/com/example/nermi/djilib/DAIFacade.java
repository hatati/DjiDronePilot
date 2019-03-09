package com.example.nermi.djilib;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.example.nermi.djidronepilot.Camera2Fragment;

public class DAIFacade {

    private Boolean isConnectedToDrone;
    DJIFacade djiFacade;

    public DAIFacade(){
        this.isConnectedToDrone = false;
        djiFacade = new DJIFacade();
    }

    public void connectToDJIDrone(){

    }

    public void initDjiUI(Activity activity, FragmentManager fragmentManager, int frameLayoutId){
        fragmentManager.beginTransaction().replace(frameLayoutId, Camera2Fragment.newInstance())
                .commit();


    }

    public void initParrotUI(){}



    public void setModelPath(String path){}

    public void setLabelPath(String path){}

    public void pitchForward(Double sensitivityRate){}

    public void pitchBackwards(Double sensitivityRate){}

    public void rollRight(Double sensitivityRate){}

    public void rollLeft(Double sensitivityRate){}

    public void yawRight(Double sensitivityRate){}

    public void yawLeft(Double sensitivityRate){}
}
