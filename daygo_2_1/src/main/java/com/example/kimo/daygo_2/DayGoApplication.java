package com.example.kimo.daygo_2;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * Created by Administrator on 2016/3/8 0008.
 */
public class DayGoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        FlowManager.init(this);
    }
}
