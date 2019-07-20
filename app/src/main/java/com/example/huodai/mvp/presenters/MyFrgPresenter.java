package com.example.huodai.mvp.presenters;

import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.mvp.view.MyViewImpl;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MyFrgPresenter extends BasePresenter<MyViewImpl> {
    @Override
    protected boolean isUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginstate(Boolean isState){
        MyLog.i("收到了已经登陆");
        if(isState){
            getView().loginSuceesee();
        }
    }


}
