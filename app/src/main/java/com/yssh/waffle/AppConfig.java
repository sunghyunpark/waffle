package com.yssh.waffle;

import android.app.Application;
import android.content.res.Configuration;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by SungHyun on 2017-09-11.
 */

public class AppConfig extends Application {

    public static String ServerAddress = "http://13.124.188.3/waffle/";
    public static int DISPLAY_WIDTH;
    public static int DISPLAY_HEIGHT;

    @Override
    public void onCreate() {
        super.onCreate();
        Display display;
        display = ((WindowManager)getApplicationContext().getSystemService(getApplicationContext().WINDOW_SERVICE)).getDefaultDisplay();
        DISPLAY_HEIGHT = display.getHeight();
        DISPLAY_WIDTH = display.getWidth();
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

    public int getDISPLAY_WIDTH() {
        return DISPLAY_WIDTH;
    }

    public int getDISPLAY_HEIGHT() {
        return DISPLAY_HEIGHT;
    }
}
