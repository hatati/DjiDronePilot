package com.example.nermi.djilib;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.nermi.djidronepilot.R;

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
     * Initialize and add the view_virtual_sticks.xml layout view to a viewgroup
     * @param activity The current activity
     * @param viewGroup The target ViewGroup element
     * @param bellowViewId The target view that the virtual sticks layout need to be placed bellow
     * @return Return the view_virtual_sticks.xml view for later access
     */
    public View initVirtualSticksUI(Activity activity, ViewGroup viewGroup, int bellowViewId){
        onScreenJoystickLeft = new OnScreenJoystick(activity, null);
        onScreenJoystickRight = new OnScreenJoystick(activity, null);
        View virtualSticks;

        //Define Layout parameters
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );

        //Add rule for the relative layout
        params.addRule(RelativeLayout.BELOW, bellowViewId);

        //Inflate and add the Virtual Stick layout to the viewgroup
        virtualSticks = View.inflate(activity, R.layout.view_virtual_sticks, null);
        viewGroup.addView(virtualSticks, params);

        return virtualSticks;
    }

    /**
     * Creates listeners for the the virtual sticks
     * @param activity The activity containing the virtual sticks
     */
    public void setupVirtualSticksListeners(Activity activity){
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
