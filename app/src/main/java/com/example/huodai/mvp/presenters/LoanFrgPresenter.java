package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.HttpResult;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.view.LoanFrgViewImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeBodyBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoanFrgPresenter extends BasePresenter<LoanFrgViewImpl> {

    List<BaseMulDataModel> list = new ArrayList<>();

    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {
        getView().showError("连接错误: " + msg);
    }

    //请求Body内容的
    public void requestBody() {
        HttpMethod.getInstance().loadBody()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<NewHomeBodyBean>>(this) {
                    @Override
                    public void onSuccess(HttpResult<NewHomeBodyBean> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            HomeFRBodyHolder homeFRBodyHolder = new HomeFRBodyHolder();
                            homeFRBodyHolder.setHomeBodyBeanList(httpResult.getData().getLoanProduct());
                            list.add(homeFRBodyHolder);
                            getView().refreshHome(list);
                        } else {
                            showError(httpResult.getMsg()+":"+httpResult.getStatusCode());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        showError(String.valueOf(e.getMessage()));
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
