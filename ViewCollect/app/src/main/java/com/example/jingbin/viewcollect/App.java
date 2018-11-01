package com.example.jingbin.viewcollect;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;



public class App extends MultiDexApplication {

    private static App app;

    public static App getInstance() {
        return app;
    }

    @SuppressWarnings("unused")
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    /**
     * 解决debug包在4.4以下手机，安装后能打开
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
