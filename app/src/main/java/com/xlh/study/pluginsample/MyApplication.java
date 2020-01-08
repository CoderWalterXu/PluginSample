package com.xlh.study.pluginsample;

import android.app.Application;

import com.xlh.study.pluginlibrary.PluginManager;

/**
 * @author: Watler Xu
 * time:2020/1/8
 * description:
 * version:0.0.1
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // 插件初始化
        PluginManager.getInstance().init(this);

    }
}
