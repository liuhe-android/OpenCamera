package com.chronos;

import android.app.Application;

/**
 * Created by Liuhe on 2017/3/1.
 */
public class App extends Application {

    private static App application;
    private int screenWidth;
    private int screenHeight;

    @Override
    public void onCreate() {
        super.onCreate();
        application=this;
    }

    public static App getApplication() {
        return application;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }
}
