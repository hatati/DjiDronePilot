package com.nermi.dailib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nermi.djidronepilot.Camera2Fragment;
import com.example.nermi.djidronepilot.DJIApplication;

public class DAIFacade {

    private Boolean isConnectedToDrone;
    DJIFacade djiFacade;

    public DAIFacade(){
        this.isConnectedToDrone = false;
        djiFacade = DJIFacade.getDjiFacade();
    }

    // DJI methods
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

                try{
                    //Tear down virtual sticks listeners
                    djiFacade.tearDownVirtualSticksListeners();
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }

            }
        }, false);

    }

    public void initCNNModel(AppCompatActivity activity, String modelPath, String labelsPath, int imageSizeX, int imageSizeY, int frameLayoutId){
        Camera2Fragment fragment = (Camera2Fragment) activity.getSupportFragmentManager().findFragmentById(frameLayoutId);
    }

    public void djiPitchForward(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.PITCH_FORWARD);

    }

    public void djiPitchBackwards(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.PITCH_BACKWARDS);
    }

    public void djiRollRight(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.ROLL_RIGHT);
    }

    public void djiRollLeft(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.ROLL_LEFT);
    }

    public void djiYawRight(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.YAW_RIGHT);
    }

    public void djiYawLeft(AppCompatActivity activity, int frameLayoutId, String label, Double sensitivityRate){
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.YAW_LEFT);
    }

    //Parrot methods
    public void initParrotUI(){}
}
