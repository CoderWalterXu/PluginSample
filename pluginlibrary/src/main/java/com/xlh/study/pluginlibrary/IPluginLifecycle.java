package com.xlh.study.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:对插件Acitivity生命周期的接口规范
 * version:0.0.1
 */
public interface IPluginLifecycle {

    // 插件apk被系统加载时，标记为内部跳转
    int FROM_INTERNAL = 0;
    // 插件apk被主App加载时，标记为外部跳转
    int FROM_EXTERNAL = 1;

    // 给插件Activity指定上下文
    void attach(Activity proxyActivity);

    //----------------Activity生命周期函数begin------------------
    /**
     * 插件Activity本身 在被用作"插件"的时候(也就是不被系统加载，被主App加载)，不具备生命周期，由宿主里面的代理Activity类代为管理
     */

    void onCreate(Bundle saveInstanceState);

    void onResume();

    void onStart();

    void onRestart();

    void onPause();

    void onStop();

    void onDestory();

    void onActivityResult(int requestCode, int resultCode, Intent data);
    //-----------------Activity生命周期函数end-----------------

}
