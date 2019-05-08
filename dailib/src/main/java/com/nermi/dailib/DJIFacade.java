package com.nermi.dailib;

import android.support.v7.app.AppCompatActivity;

import dji.sdk.mobilerc.MobileRemoteController;

public class DJIFacade {

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

    public void setupDJIMobileRemoteController(MobileRemoteController mobileRemoteController){
            mMobileRemoteController = mobileRemoteController;
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
