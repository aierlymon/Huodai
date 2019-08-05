package com.example.huodai.mvp.view;

import com.example.baselib.mvp.IView;

import java.util.List;

/**
 * createBy ${huanghao}
 * on 2019/7/27
 */
public interface StartActImpl extends IView {
    void startSplash(List<String> urls);

    void startMain();

    void requestComplice();
}
