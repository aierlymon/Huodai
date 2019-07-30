package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.huodai.mvp.view.StartActImpl;
import com.example.model.bean.SplashBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * createBy ${huanghao}
 * on 2019/7/27
 */
public class StartActPresenter extends BasePresenter<StartActImpl> {
    @Override
    public void showError(String msg) {

    }

    @Override
    protected boolean isUseEventBus() {
        return false;
    }


    public void requestSplash() {
        HttpMethod.getInstance().loadSplash()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<SplashBean>(this) {
                    @Override
                    public void onSuccess(SplashBean splashBean) {
                        //跳转到广告页面
                        getView().startSplash(splashBean.getData());
                    }

                    @Override
                    public void onFail(Throwable e) {
                        //跳转到主界面
                        getView().startMain();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
