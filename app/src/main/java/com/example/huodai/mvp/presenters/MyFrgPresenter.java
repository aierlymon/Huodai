package com.example.huodai.mvp.presenters;

import com.example.baselib.mvp.BasePresenter;
import com.example.huodai.mvp.view.MyViewImpl;

public class MyFrgPresenter extends BasePresenter<MyViewImpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
    }


}
