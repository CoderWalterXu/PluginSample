package com.xlh.study.pluginsample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xlh.study.pluginlibrary.PluginManager;
import com.xlh.study.pluginlibrary.ProxyActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoad;
    Button btnJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PluginManager.getInstance().init(this);


        btnLoad = findViewById(R.id.btn_load);
        btnJump = findViewById(R.id.btn_jump);

        btnLoad.setOnClickListener(this);
        btnJump.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                /**
                 * pluginapk-release-unsigned.apk是pluginapk生成的apk。
                 * 右侧Gradle-->pluginapk-->Tasks-->build-->assemble生成做测试用
                 * 该apk既可以被系统加载，也可以作为插件被主App加载
                 */
                // 加载主App中assets中的apk文件，作为插件
                String apkPath = Util.copyAssetAndWrite(MainActivity.this,"pluginapk-release-unsigned.apk");
                PluginManager.getInstance().loadApk(apkPath);
                break;
            case R.id.btn_jump:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ProxyActivity.class);
                // 此路径名就是被加载apk文件中的完整类名
                intent.putExtra("className","com.xlh.study.pluginapk.FirstPluginActivity");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
