package com.example.lifecycledemo;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private static MyApplication instance;
    public Context context;
    public static MyApplication getInstance(){
        return instance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = this;
    }
}
