package com.example.huodai.mvp.presenters;

import com.example.baselib.mvp.BasePresenter;
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
        getView().loginSuceesee();
    }

}
