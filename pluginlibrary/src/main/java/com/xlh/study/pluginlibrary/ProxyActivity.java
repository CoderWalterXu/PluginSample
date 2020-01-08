package com.xlh.study.pluginlibrary;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:插件代理Activity。AppCompatActivity会检测上下文，所以继承Activity
 * 主App能够通过该类，来间接地管理真正插件Activity的生命周期
 * version:0.0.1
 */
public class ProxyActivity extends Activity {

    // 真正要跳转的类路径名
    private String mClassName;
    private PluginApk mPluginApk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClassName = getIntent().getStringExtra("className");
        mPluginApk = PluginManager.getInstance().getmPluginApk();

        launchPluginActivity();
    }

    private void launchPluginActivity() {
        if (mPluginApk == null) {
            Log.e("error", "加载不了apk文件");
            return;
        }

        try {
            // 通过DexClassLoader对象加载真正要跳转的插件Activity
            Class<?> loadRealActivitClass = mPluginApk.mDexClassLoader.loadClass(mClassName);
            // 实例化Activity，注意：这里的activity是没有生命周期，也没有上下文环境
            Object object = loadRealActivitClass.newInstance();
            if (object instanceof IPluginLifecycle) { // 所有的插件Activity，都必须是IPluginLifecycle的实现类
                IPluginLifecycle iPlugin = (IPluginLifecycle) object;
                Bundle bundle = new Bundle();
                // FROM_EXTERNAL表示作为主App的插件加载
                bundle.putInt("FROM", IPluginLifecycle.FROM_EXTERNAL);
                // 传入上下文
                iPlugin.attach(this);
                // 反射创建的插件Activity的生命周期函数不会被执行，那么就由ProxyActivity代为执行
                iPlugin.onCreate(bundle);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public Resources getResources() {
        return mPluginApk != null ? mPluginApk.mResources : super.getResources();
    }

    @Override
    public AssetManager getAssets() {
        return mPluginApk != null ? mPluginApk.mAssetManager : super.getAssets();
    }

    @Override
    public ClassLoader getClassLoader() {
        return mPluginApk != null ? mPluginApk.mDexClassLoader : super.getClassLoader();
    }
}
