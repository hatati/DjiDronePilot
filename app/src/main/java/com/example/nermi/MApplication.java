package com.example.nermi;

import android.app.Application;
import android.content.Context;

import com.nermi.dailib.DJIApplication;
import com.secneo.sdk.Helper;

public class MApplication extends Application {

    private DJIApplication djiApplication;
    @Override
    protected void attachBaseContext(Context paramContext) {
        super.attachBaseContext(paramContext);
        Helper.install(MApplication.this);

        if (djiApplication == null) {
            djiApplication = new DJIApplication();
            djiApplication.setContext(this);
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // Invoke the onCreate() method of djiApplication
        djiApplication.onCreate();
    }
}