package com.example.mymusic;

import android.app.Application;
import android.content.Context;

public class MyApp extends Application {

    public static MyApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
