package com.app3c.application;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;



public class MedicineApp extends Application {

    @SuppressLint("StaticFieldLeak")
    private static Context mInstance;


    @Override
    public void onCreate() {
        super.onCreate();
        if (mInstance == null) {
            mInstance = getApplicationContext();
        }
    }

    public static Context getInstance() {
        return mInstance;
    }
}
