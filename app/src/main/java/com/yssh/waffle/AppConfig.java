package com.yssh.waffle;

import android.app.Application;
import android.content.res.Configuration;

/**
 * Created by SungHyun on 2017-09-11.
 */

public class AppConfig extends Application {

    public static String ServerAddress = "http://13.124.188.3/waffle/";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
