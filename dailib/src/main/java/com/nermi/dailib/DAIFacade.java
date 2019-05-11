package com.example.nermi.dailib;

import android.support.v7.app.AppCompatActivity;

public interface DAIFacade {

    void initUI(AppCompatActivity activity, int frameLayoutId);

    void pitchForward(AppCompatActivity activity, int frameLayoutId, String label);

    void pitchBackwards(AppCompatActivity activity, int frameLayoutId, String label);

    void rollLeft(AppCompatActivity activity, int frameLayoutId, String label);

    void rollRight(AppCompatActivity activity, int frameLayoutId, String label);

    void yawLeft(AppCompatActivity activity, int frameLayoutId, String label);

    void yawRight(AppCompatActivity activity, int frameLayoutId, String label);

    void setupMobileRemoteController();

    void setupVirtualSticksListeners(AppCompatActivity activity, int frameLayoutId);

    void tearDownVirtualSticksListeners();
}
