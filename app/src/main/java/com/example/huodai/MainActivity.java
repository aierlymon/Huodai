package com.example.huodai;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.utils.RxPermissionUtil;
import com.example.huodai.mvp.presenters.MainPrsenter;
import com.example.huodai.mvp.view.MainViewImpl;
import com.example.huodai.ui.adapter.MainVPAdapter;
import com.example.huodai.ui.fragments.HomeFragment;
import com.example.huodai.ui.fragments.LoanFragment;
import com.example.huodai.ui.fragments.MyFragment;
import com.example.huodai.widget.CustomScrollViewPager;

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

    private String[] permissions = {
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_NETWORK_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    };


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setStatusBarColor(Color.BLACK);
        super.onCreate(savedInstanceState);
        init();
    }

    private NetWorkStateBroadcast netWorkStateBroadcast;

    private void init() {
        //获取权限
        RxPermissionUtil.getInstance().permission(this,permissions);

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


        //初始化三页
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(HomeFragment.newInstance("home"));
        fragments.add(LoanFragment.newInstance("loan"));
        fragments.add(MyFragment.newInstance("mine"));
        MainVPAdapter mainVPagerAdapter = new MainVPAdapter(fragments, getSupportFragmentManager());

        mViewPager.setScrollable(false);
        mViewPager.setAdapter(mainVPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);


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

    }

    @Override
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginstate(Boolean isState) {
        if (!isState) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (netWorkStateBroadcast != null)
            unregisterReceiver(netWorkStateBroadcast);
    }
}
