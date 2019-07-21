package com.example.huodai.mvp.view;

import android.content.Intent;

import com.example.baselib.mvp.IView;

public interface MainViewImpl extends IView {
    void statrUpdateProgress();

    void onUpdateProgress(int progress);

    void onUpdateFail(int errorInfo, String info);

    void onUpdateSuccess(Intent intent);
}
