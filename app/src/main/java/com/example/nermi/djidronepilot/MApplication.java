package com.example.nermi.djidronepilot;

import android.app.Application;
import android.content.Context;

import com.secneo.sdk.Helper;

public class MApplication extends Application {

    private DJIApplication simulatorApplication;
    @Override
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        Helper.install(MApplication.this);

        if (simulatorApplication == null) {
            simulatorApplication = new DJIApplication();
            simulatorApplication.setContext(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        simulatorApplication.onCreate();
    }
}