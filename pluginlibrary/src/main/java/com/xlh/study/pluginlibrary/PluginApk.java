package com.xlh.study.pluginlibrary;

import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;

import dalvik.system.DexClassLoader;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:插件的实体对象
 * version:0.0.1
 */
public class PluginApk {

    public DexClassLoader mDexClassLoader;
    public Resources mResources;
    public PackageInfo mPacageInfo;
    public AssetManager mAssetManager;

    public PluginApk(DexClassLoader mDexClassLoader, Resources mResources, PackageInfo mPacageInfo) {
        this.mDexClassLoader = mDexClassLoader;
        this.mResources = mResources;
        this.mPacageInfo = mPacageInfo;
        this.mAssetManager = mResources.getAssets();
    }
}
