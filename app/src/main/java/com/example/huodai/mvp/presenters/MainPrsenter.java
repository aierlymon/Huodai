package com.example.huodai.mvp.presenters;

import com.example.baselib.mvp.BasePresenter;
import com.example.huodai.mvp.view.MainViewImpl;

public class MainPrsenter extends BasePresenter<MainViewImpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
    }
}
