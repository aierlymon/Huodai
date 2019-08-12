package com.example.huodai.mvp.presenters;

import android.content.SharedPreferences;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.myapplication.App;
import com.example.baselib.utils.MyLog;
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
                        MyLog.i("广告请求成功: "+splashBean.getData().get(0) );
                        //拿取TOKEN
                        getView().requestComplice();
                        //跳转到广告页面
                        getView().startSplash(splashBean.getData());
                    }

                    @Override
                    public void onFail(Throwable e) {
                        MyLog.i("广告页面异常: "+e.getMessage());
                        //跳转到主界面
                        //拿取TOKEN
                        getView().requestComplice();
                        getView().startMain();
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
