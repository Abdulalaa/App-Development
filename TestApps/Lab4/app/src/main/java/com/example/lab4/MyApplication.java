package com.example.lab4;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;


public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
