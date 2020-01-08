package com.xlh.study.pluginapk;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xlh.study.pluginlibrary.PluginBaseActivity;

/**
 * @author: Watler Xu
 * time:2020/1/7
 * description:此处继承PluginActivity
 * version:0.0.1
 */
public class FirstPluginActivity extends PluginBaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_plugin);

        findViewById(R.id.btn_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mProxyActivity, SecondPluginActivity.class);
                startActivity(intent);
            }
        });

    }
}
