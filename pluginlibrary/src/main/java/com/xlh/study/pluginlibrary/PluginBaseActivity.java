package com.xlh.study.pluginlibrary;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:所有插件Activity的父类
 * 加载外部apk文件的Activity，需要我们自己管理Activity的生命周期
 * version:0.0.1
 */
public class PluginBaseActivity extends Activity implements IPluginLifecycle {

    /**
     * 分为被系统加载和被主App加载
     * 默认是插件apk被系统加载
     */
    private int mFrom = FROM_INTERNAL;
    // 插件的上下文
    public Activity mProxyActivity;


    @Override
    public void attach(Activity proxyActivity) {
        this.mProxyActivity = proxyActivity;
    }

    @Override
    public void onCreate(Bundle saveInstanceState) {
        if (saveInstanceState != null) {
            mFrom = saveInstanceState.getInt("FROM");
        }
        if (mFrom == FROM_INTERNAL) {// 如果是被系统加载
            // 调用系统的方法
            super.onCreate(saveInstanceState);
            // 将上下文设为自己
            mProxyActivity = this;
        }
    }

    @Override
    public void onResume() {
        if (mFrom == FROM_INTERNAL) {
            super.onResume();
        }
    }

    @Override
    public void onStart() {
        if (mFrom == FROM_INTERNAL) {
            super.onStart();
        }
    }

    @Override
    public void onRestart() {
        if (mFrom == FROM_INTERNAL) {
            super.onRestart();
        }
    }

    @Override
    public void onPause() {
        if (mFrom == FROM_INTERNAL) {
            super.onPause();
        }
    }

    @Override
    public void onStop() {
        if (mFrom == FROM_INTERNAL) {
            super.onStop();
        }
    }

    @Override
    public void onDestory() {
        if (mFrom == FROM_INTERNAL) {
            super.onDestroy();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mFrom == FROM_INTERNAL) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // --------------生命周期以外的重写函数begin--------------
    @Override
    public void setContentView(int layoutResID) {
        if (mFrom == FROM_INTERNAL) {
            super.setContentView(layoutResID);
        } else {
            mProxyActivity.setContentView(layoutResID);
        }
    }

    @Override
    public View findViewById(int id) {
        if (mFrom == FROM_INTERNAL) {
            return super.findViewById(id);
        } else {
            return mProxyActivity.findViewById(id);
        }
    }

    @Override
    public void startActivity(Intent intent) {
        if (mFrom == IPluginLifecycle.FROM_INTERNAL) { // 如果是被系统加载
            // 调用系统的方法
            super.startActivity(intent);
        } else {
            /**
             * 如果是被主App加载，插件内的跳转控制权,仍然是在宿主上下文里面
             * 先跳到代理Activity，由代理Activity展示真正的Activity内容
             * 所以这里的intent，必须重新创建。把真正的跳转目标Activity放在参数中
             */
            Intent temp = new Intent(mProxyActivity, ProxyActivity.class);
            temp.putExtra("className", intent.getComponent().getClassName());
            mProxyActivity.startActivity(temp);
        }
    }

    // --------------生命周期以外的重写函数end--------------

}
