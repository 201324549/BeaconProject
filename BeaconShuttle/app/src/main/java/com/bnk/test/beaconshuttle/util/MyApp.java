package com.bnk.test.beaconshuttle.util;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MyApp.context = getApplicationContext();
    }

    public static Context getAppContext(){
        return MyApp.context;
    }
}
