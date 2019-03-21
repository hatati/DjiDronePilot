package com.example.nermi.dailib;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.nermi.djidronepilot.R;

import dji.sdk.flightcontroller.FlightController;
import dji.sdk.mobilerc.MobileRemoteController;

public class DJIFacade {

    private OnScreenJoystick onScreenJoystickRight;
    private OnScreenJoystick onScreenJoystickLeft;
    private MobileRemoteController mMobileRemoteController;

    public DJIFacade(){

    }

    //TODO: Still needs testing on a real drones
    public void setupDJIMobileRemoteController(MobileRemoteController mobileRemoteController){
        mMobileRemoteController = mobileRemoteController;
    }

    /**
     * Creates listeners for the the virtual sticks
     * @param activity The activity containing the virtual sticks
     */
    //TODO: This method is called in Camera2Fragment. Should it? Maybe call it in DAIFacade instead
    public void setupVirtualSticksListeners(AppCompatActivity activity, int frameLayoutId){
        onScreenJoystickLeft = activity.getSupportFragmentManager()
                .findFragmentById(frameLayoutId).getView().findViewById(R.id.directionJoystickLeft);
        onScreenJoystickRight = activity.getSupportFragmentManager()
                .findFragmentById(frameLayoutId).getView().findViewById(R.id.directionJoystickRight);

        //TODO: Uncomment when you have a drone
        if(mMobileRemoteController == null){
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast toast = Toast.makeText(activity, "No Mobile Remote Controller connection", Toast.LENGTH_LONG);
                    toast.show();
                }
            });

            return;
        }

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
