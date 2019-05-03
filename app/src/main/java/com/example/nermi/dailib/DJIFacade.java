package com.example.nermi.dailib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.nermi.djidronepilot.Camera2Fragment;
import com.example.nermi.djidronepilot.DJIApplication;
import com.example.nermi.djidronepilot.R;

import dji.sdk.mobilerc.MobileRemoteController;

public class DJIFacade implements DAIFacade {

    private OnScreenJoystick onScreenJoystickRight;
    private OnScreenJoystick onScreenJoystickLeft;
    private MobileRemoteController mMobileRemoteController;

    private static DJIFacade _djiFacade;

    private static class SingletonHelper{
        // Nested class is referenced when getDjiFacade is called
        private static final DJIFacade _djiFacade = new DJIFacade();
    }

    public static DJIFacade getDjiFacade(){
        return SingletonHelper._djiFacade;
    }

    private DJIFacade(){

    }

    private void setupDJIMobileRemoteController(MobileRemoteController mobileRemoteController){
            mMobileRemoteController = mobileRemoteController;
    }

    @Override
    public void initUI(AppCompatActivity activity, int frameLayoutId) {
        activity.getSupportFragmentManager().beginTransaction().replace(frameLayoutId, Camera2Fragment.newInstance())
                .commit();

        // Initialize the DJI Mobile remote controller and virtual sticks listeners in the fragment onResume method
        activity.getSupportFragmentManager().registerFragmentLifecycleCallbacks(new FragmentManager.FragmentLifecycleCallbacks() {
            @Override
            public void onFragmentResumed(FragmentManager fm, Fragment f) {
                super.onFragmentResumed(fm, f);

                try{
                    // Setup the Mobile Remote Controller
                    setupDJIMobileRemoteController(DJIApplication.getAircraftInstance().getMobileRemoteController());
                    // Setup virtual sticks listeners
                    setupVirtualSticksListeners(activity, frameLayoutId);
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
                    tearDownVirtualSticksListeners();
                }catch (NullPointerException ex){
                    ex.printStackTrace();
                }

            }
        }, false);
    }

    @Override
    public void pitchForward(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.PITCH_FORWARD);
    }

    @Override
    public void pitchBackwards(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.PITCH_BACKWARDS);
    }

    @Override
    public void rollLeft(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.ROLL_LEFT);
    }

    @Override
    public void rollRight(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.ROLL_RIGHT);
    }

    @Override
    public void yawLeft(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.YAW_LEFT);
    }

    @Override
    public void yawRight(AppCompatActivity activity, int frameLayoutId, String label) {
        ((Camera2Fragment)activity.getSupportFragmentManager().findFragmentById(frameLayoutId))
                .getLabels().put(label, DroneCommands.YAW_RIGHT);
    }

    @Override
    public void setupMobileRemoteController() {

    }

    /**
     * Creates listeners for the the virtual sticks
     * @param activity The activity containing the virtual sticks
     */
    public void setupVirtualSticksListeners(AppCompatActivity activity, int frameLayoutId){
        onScreenJoystickLeft = activity.getSupportFragmentManager()
                .findFragmentById(frameLayoutId).getView().findViewById(R.id.directionJoystickLeft);
        onScreenJoystickRight = activity.getSupportFragmentManager()
                .findFragmentById(frameLayoutId).getView().findViewById(R.id.directionJoystickRight);

        assert onScreenJoystickLeft != null;
        assert onScreenJoystickRight != null;

        onScreenJoystickLeft.setJoystickListener(new OnScreenJoystickListener() {
            @Override
            public void onTouch(OnScreenJoystick joystick, float pX, float pY) {
                if (Math.abs(pX) < 0.02) {
                    pX = 0;
                }

                if (Math.abs(pY) < 0.02) {
                    pY = 0;
                }


                if (mMobileRemoteController != null) {
                    mMobileRemoteController.setLeftStickHorizontal(pX);
                    mMobileRemoteController.setLeftStickVertical(pY);
                }

//                System.out.println("pX: " + String.valueOf(pX));
//                System.out.println("pY: " + String.valueOf(pY));

            }
        });

        onScreenJoystickRight.setJoystickListener(new OnScreenJoystickListener() {
            @Override
            public void onTouch(OnScreenJoystick joystick, float pX, float pY) {
                if (Math.abs(pX) < 0.02) {
                    pX = 0;
                }

                if (Math.abs(pY) < 0.02) {
                    pY = 0;
                }


                if (mMobileRemoteController != null) {
                    mMobileRemoteController.setRightStickHorizontal(pX);
                    mMobileRemoteController.setRightStickVertical(pY);
                }

//                System.out.println("pX: " + String.valueOf(pX));
//                System.out.println("pY: " + String.valueOf(pY));
            }
        });


    }


    public void tearDownVirtualSticksListeners(){
        onScreenJoystickRight.setJoystickListener(null);
        onScreenJoystickLeft.setJoystickListener(null);
    }

    public OnScreenJoystick getOnScreenJoystickRight() {
        return onScreenJoystickRight;
    }

    public void setOnScreenJoystickRight(OnScreenJoystick onScreenJoystickRight) {
        this.onScreenJoystickRight = onScreenJoystickRight;
    }

    public OnScreenJoystick getOnScreenJoystickLeft() {
        return onScreenJoystickLeft;
    }

    public void setOnScreenJoystickLeft(OnScreenJoystick onScreenJoystickLeft) {
        this.onScreenJoystickLeft = onScreenJoystickLeft;
    }

    public MobileRemoteController getmMobileRemoteController() {
        return mMobileRemoteController;
    }

    public void setmMobileRemoteController(MobileRemoteController mMobileRemoteController) {
        this.mMobileRemoteController = mMobileRemoteController;
    }

}
