package com.example.huodai;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.RxPermissionUtil;
import com.example.baselib.utils.UpdateUtil;
import com.example.huodai.mvp.presenters.MainPrsenter;
import com.example.huodai.mvp.view.MainViewImpl;
import com.example.huodai.ui.adapter.MainVPAdapter;
import com.example.huodai.ui.fragments.HomeFragment;
import com.example.huodai.ui.fragments.LoanFragment;
import com.example.huodai.ui.fragments.MyFragment;
import com.example.huodai.widget.CustomScrollViewPager;
import com.example.model.bean.LoginCallBackBean;
import com.google.gson.Gson;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

public class MainActivity extends BaseMvpActivity<MainViewImpl, MainPrsenter> implements MainViewImpl {

    @BindView(R.id.viewpager)
    CustomScrollViewPager mViewPager;

    @BindView(R.id.group)
    RadioGroup mGroup;

    @BindView(R.id.rb_home)
    RadioButton mRb_Home;

    @BindView(R.id.rb_loan)
    RadioButton mRb_Load;

    @BindView(R.id.rb_my)
    RadioButton mRb_Mine;

    @BindView(R.id.background)
    ImageView imageViewBack;

    private SharedPreferences preferences;

    private String[] permissions = {
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,

    };


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        super.onCreate(savedInstanceState);
        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        init();
    }

    private NetWorkStateBroadcast netWorkStateBroadcast;

    private void init() {

        //拿取字体
        //ApplicationPrams.typeface=Typeface.createFromAsset(getAssets(),"PingFang_Bold.ttf");

        //获取权限
        RxPermissionUtil.getInstance().permission(this, permissions);

        //注册网络检测广播
        netWorkStateBroadcast = new NetWorkStateBroadcast();
        netWorkStateBroadcast = new NetWorkStateBroadcast();
        netWorkStateBroadcast.setmOnNetStateChangListener(new NetWorkStateBroadcast.OnNetStateChangListener() {
            @Override
            public void onNetWorkSuccess() {
            }

            @Override
            public void onNetWorkFail() {
            }
        });
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netWorkStateBroadcast, filter);


        //判断是否已经登陆过
        preferences = getSharedPreferences("cache", MODE_PRIVATE);
        String obj = preferences.getString("obj", null);
        if (!TextUtils.isEmpty(obj)) {
            Gson gson = new Gson();
            ApplicationPrams.loginCallBackBean = gson.fromJson(obj, LoginCallBackBean.class);
            ApplicationPrams.isLogin = true;
        }
        //butterknife的绑定
        ButterKnife.bind(this);
        //重新定义当前drawable的大小
        for (int i = 0; i < mGroup.getChildCount(); i++) {
            //挨着给每个RadioButton加入drawable限制边距以控制显示大小
            Drawable[] drawables = ((RadioButton) mGroup.getChildAt(i)).getCompoundDrawables();
            //获取drawables
            //给指定的radiobutton设置drawable边界
            Rect r = new Rect(0, 10, drawables[1].getMinimumWidth() * 7 / 12, drawables[1].getMinimumHeight() * 2 / 3);
            //定义一个Rect边界
            drawables[1].setBounds(r);
            //添加限制给控件
            ((RadioButton) mGroup.getChildAt(i)).setCompoundDrawables(null, drawables[1], null, null);
        }


        //初始化页面
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("home"));
        fragments.add(LoanFragment.newInstance("loan"));
        fragments.add(MyFragment.newInstance("mine"));
        MainVPAdapter mainVPagerAdapter = new MainVPAdapter(fragments, getSupportFragmentManager());

        mViewPager.setScrollable(false);
        mViewPager.setAdapter(mainVPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);

        //过度界面展示
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageViewBack.setVisibility(View.GONE);
                        //检测更新
                        if (NetWorkStateBroadcast.isOnline.get()&&mPresenter!=null)
                            mPresenter.checkUpdate(MainActivity.this);
                    }
                });
            }
        }).start();

    }


    @Override
    protected MainPrsenter createPresenter() {
        return new MainPrsenter();
    }


    @OnCheckedChanged({R.id.rb_home, R.id.rb_loan, R.id.rb_my})
    public void onCheckChange(RadioButton radioButton) {
        boolean checked = radioButton.isChecked();

        switch (radioButton.getId()) {
            case R.id.rb_home:
                if (checked) {
                    mViewPager.setCurrentItem(0, false);
                }
                break;
            case R.id.rb_loan:
                //展示标题栏
                if (checked) {
                    mViewPager.setCurrentItem(1, false);
                }
                break;
            case R.id.rb_my:
                if (checked) {
                    mViewPager.setCurrentItem(2, false);
                }
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        CustomToast.showToast(getApplicationContext(), msg, 2000);
    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginstate(Boolean isState) {
        if (!isState) {
            //因为跳转到登陆。清空shareprefence
            if (!TextUtils.isEmpty(preferences.getString("obj", null))) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("obj", null);
                editor.apply();
                editor.commit();
            }

            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("isAnim", true);
            startActivity(intent);
            return;
        }

        if (ApplicationPrams.loginCallBackBean != null) {
            Gson gson = new Gson();
            String obj = gson.toJson(ApplicationPrams.loginCallBackBean);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("obj", obj);
            editor.apply();
            editor.commit();
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void webState(String url) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkRadio(Integer radioIndex) {
        MyLog.i("MainActivity 我接收到了image的点击事件");
        ((RadioButton) mGroup.getChildAt(radioIndex)).setChecked(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //取消注册广播
        if (netWorkStateBroadcast != null)
            unregisterReceiver(netWorkStateBroadcast);
        //取消下载更新
        if (mPresenter != null)
            mPresenter.cancelIUpdate();
    }

    private ProgressDialog pd;

    @Override
    public void statrUpdateProgress() {
        runOnUiThread(() -> {
            pd = new ProgressDialog(MainActivity.this);
            pd.setOnKeyListener((dialog, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                    return true;
                } else {
                    return false; //默认返回 false
                }
            });
            pd.setTitle("请稍等");
            //设置对话进度条样式为水平
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            //设置提示信息
            pd.setMessage("正在玩命下载中......");
            //设置对话进度条显示在屏幕顶部（方便截图）
            pd.getWindow().setGravity(Gravity.CENTER);
            pd.setCancelable(false);
            pd.setMax(100);
            pd.show();//调用show方法显示进度条对话框
        });

    }

    @Override
    public void onUpdateProgress(int progress) {
        if (pd != null)
            pd.setProgress(progress);
        MyLog.i("下载进度为: " + progress);

    }

    @Override
    public void onUpdateFail(int errorType, String info) {
        runOnUiThread(() -> {
            switch (errorType) {
                case UpdateUtil.FILE_DOWNLOAD_ERROR:
                    break;
                case UpdateUtil.FILE_IO_ERROR:
                    break;
                case UpdateUtil.FILE_MD5_ERROR:
                    break;
                case UpdateUtil.FILE_NOTFOUND_ERROR:
                    break;
            }
            showError(info);
            if (pd != null)
                pd.dismiss();
        });

    }

    @Override
    public void onUpdateSuccess(Intent intent) {
        pd.dismiss();
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        if (intent.getBooleanExtra("isAnim", false))
            overridePendingTransition(R.anim.slide_bottom_in, R.anim.slide_bottom_out);
    }


}
