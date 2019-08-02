package com.example.huodai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.myapplication.App;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.StatusBarUtil;
import com.example.huodai.mvp.presenters.StartActPresenter;
import com.example.huodai.mvp.view.StartActImpl;
import com.example.huodai.widget.myGlide.GlideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * createBy ${huanghao}
 * on 2019/7/27
 */
public class StartActivity extends BaseMvpActivity<StartActImpl, StartActPresenter> implements StartActImpl {


    @Override
    protected StartActPresenter createPresenter() {
        return new StartActPresenter();
    }

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


        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }



        //判断是否有网络
        //有网络
        if (NetWorkStateBroadcast.isOnline.get()) {
            //请求广告页面
            mPresenter.requestSplash();
        } else {
            //直接跳转到主界面
            startMain();
        }

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_startapp;
    }

    @Override
    public boolean isUseLayoutRes() {
        return true;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void startSplash(List<String> urls) {
        //拿取TOKEN
        SharedPreferences preferences = getSharedPreferences("cache", MODE_PRIVATE);
        String token = preferences.getString("token", "");
        App.token = token;
        //判断是否存在缓存
     /*   urls.clear();
        urls.add("http://ihoufeng.com//group1/default/20190725/14/51/8/4.png");
        urls.add("http://ihoufeng.com//group1/default/20190725/14/51/8/4.png");
        urls.add("http://ihoufeng.com//group1/default/20190725/14/51/8/4.png");*/
        ArrayList<String> newUrls = new ArrayList<>();
        //拿去所有的urls，把已经缓存的拿去加载
        MyLog.i("我到准备跳转广告页: " + urls.size() + "  0: " + urls.get(0));

        for (int i = 0; i < urls.size(); i++) {
            File file = GlideUtil.getCacheFile2(urls.get(i), this);
            if (file != null) {
                //已经缓存的
                MyLog.i("我到准备跳转广告页 文件名称为: " + file.toString());
                newUrls.add(file.toString());
            }
        }
        //如果新的urls集合大于0，进行广告页面跳转


        Intent splashIntent = new Intent(this, SplashActivity.class);
        splashIntent.putStringArrayListExtra("urls", newUrls);
        startActivity(splashIntent);
        overridePendingTransition(0, 0);
        finish();


    }

    @Override
    public void startMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
