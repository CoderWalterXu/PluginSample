package com.xlh.study.pluginapk;

import android.os.Bundle;
import android.util.Log;

import com.xlh.study.pluginlibrary.PluginBaseActivity;

/**
 * @author: Watler Xu
 * time:2020/1/8
 * description:此处继承PluginActivity
 * version:0.0.1
 */
public class SecondPluginActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_second);
    }
}
