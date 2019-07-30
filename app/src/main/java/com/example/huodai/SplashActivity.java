package com.example.huodai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.baselib.utils.StatusBarUtil;
import com.example.huodai.ui.adapter.SplashVPAdapter;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * createBy ${huanghao}
 * on 2019/7/27
 */
public class SplashActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清

        //这个就是设施沉浸式状态栏的主要方法了
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);

        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }



        setContentView(R.layout.activity_splash);
        List<String> list = getIntent().getStringArrayListExtra("urls");
        //加载好话筒图
        mViewPager = ((ViewPager) findViewById(R.id.splash_viewpager));
        SplashVPAdapter splashVPAdapter = new SplashVPAdapter(this, list);
        splashVPAdapter.setOnItemClickListener(view -> {
            //点击事件
        });
        mViewPager.setAdapter(splashVPAdapter);

        //跳过按钮
        ((Button) findViewById(R.id.jump)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toMain();
            }
        });

        //开启一个定时器，定时时长为5秒
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        toMain();
                    }
                });
            }
        },5000);


    }

    public void toMain() {
        if(!isFinishing()){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
