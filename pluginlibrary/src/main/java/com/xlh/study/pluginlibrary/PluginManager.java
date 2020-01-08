package com.xlh.study.pluginlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:插件化处理操作的管理类
 * version:0.0.1
 */
public class PluginManager {

    private static final PluginManager instance = new PluginManager();

    private PluginManager() {
    }

    public static PluginManager getInstance() {
        return instance;
    }

    private Context mContext;
    private PluginApk mPluginApk;

    public void init(Context context) {
        // 要用application 因为这是单例，直接用Activity对象作为上下文会导致内存泄漏
        mContext = context.getApplicationContext();
    }

    /**
     * 加载插件apk
     *
     * @param apkPath
     */
    public void loadApk(String apkPath) {
        // 获取包信息。拿Activity或者services
        PackageInfo packageInfo = mContext.getPackageManager().getPackageArchiveInfo(apkPath,
                PackageManager.GET_ACTIVITIES | PackageManager.GET_SERVICES);
        if (packageInfo == null) {
            // 如果apkPath是传的有问题，那就拿不到包信息了，直接抛出异常
            throw new RuntimeException("该路径插件加载失败");
        }
        // DexClassLoader是专门加载外部dex的类加载器
        DexClassLoader classLoader = createDexClassLoader(apkPath);
        AssetManager am = createAssetManager(apkPath);
        Resources resources = createResource(am);
        mPluginApk = new PluginApk(classLoader, resources, packageInfo);
    }

    public PluginApk getmPluginApk() {
        return mPluginApk;
    }

    /**
     * 创建访问插件apk的DexClassloader对象
     *
     * @param apkPath
     * @return
     */
    public DexClassLoader createDexClassLoader(String apkPath) {
        File file = mContext.getDir("dex", Context.MODE_PRIVATE);
        return new DexClassLoader(apkPath, file.getAbsolutePath(), null, mContext.getClassLoader());
    }

    /**
     * 创建访问插件apk资源的Assetmanager对象
     *
     * @param apkPath
     * @return
     */
    public AssetManager createAssetManager(String apkPath) {
        try {
            AssetManager am = AssetManager.class.newInstance();
            Method method = AssetManager.class.getDeclaredMethod("addAssetPath", String.class);
            method.invoke(am, apkPath);
            return am;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建访问插件apk资源的Resource对象
     *
     * @param am
     * @return
     */
    private Resources createResource(AssetManager am) {
        Resources res = mContext.getResources();
        return new Resources(am, res.getDisplayMetrics(), res.getConfiguration());
    }

}
