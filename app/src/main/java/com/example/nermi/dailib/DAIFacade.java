package com.example.nermi.dailib;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nermi.djidronepilot.Camera2Fragment;
import com.example.nermi.djidronepilot.DJIApplication;
import com.example.nermi.djidronepilot.R;

public class DAIFacade {

    private Boolean isConnectedToDrone;
    DJIFacade djiFacade;

    public DAIFacade(){
        this.isConnectedToDrone = false;
        djiFacade = new DJIFacade();
    }

    public void initDjiUI(AppCompatActivity activity, int frameLayoutId){
        activity.getSupportFragmentManager().beginTransaction().replace(frameLayoutId, Camera2Fragment.newInstance())
                .commit();

        // Initialize the DJI Mobile remote controller and virtual sticks listeners in the fragment onResume method
        activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);

                try{
                    // Setup the Mobile Remote Controller
                    djiFacade.setupDJIMobileRemoteController(DJIApplication.getAircraftInstance().getMobileRemoteController());
                    // Setup virtual sticks listeners
                    djiFacade.setupVirtualSticksListeners(activity, frameLayoutId);
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(activity, "No Mobile Remote Controller connection", Toast.LENGTH_LONG);
                            toast.show();
                        }
                    });
                }
            }

            @Override
            public void onFragmentPaused(FragmentManager fm, Fragment f) {
                super.onFragmentPaused(fm, f);

                //Tear down virtual sticks listeners
                djiFacade.tearDownVirtualSticksListeners();
            }
        }, false);

    }

    public void initParrotUI(){}

    public void initCNNModel(AppCompatActivity activity, String modelPath, String labelsPath, int imageSizeX, int imageSizeY, int frameLayoutId){
        Camera2Fragment fragment = (Camera2Fragment) activity.getSupportFragmentManager().findFragmentById(frameLayoutId);
        fragment.setModelPath(modelPath);
        fragment.setLabelsPath(labelsPath);
        fragment.setImageSizeX(imageSizeX);
        fragment.setImageSizeY(imageSizeY);
    }

    public void pitchForward(Double sensitivityRate){}

    public void pitchBackwards(Double sensitivityRate){}

    public void rollRight(Double sensitivityRate){}

    public void rollLeft(Double sensitivityRate){}

    public void yawRight(Double sensitivityRate){}

    public void yawLeft(Double sensitivityRate){}
}
