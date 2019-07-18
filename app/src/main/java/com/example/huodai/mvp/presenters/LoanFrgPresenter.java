package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.view.LoanFrgViewImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HomeBodyBean;

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

    public void requestBody() {
        HttpMethod.getInstance().loadBody()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<List<HomeBodyBean>>(this) {
                    @Override
                    public void onSuccess(List<HomeBodyBean> homeBodyBeans) {
                        MyLog.i("请求body成功: " + homeBodyBeans.size());
                        HomeFRBodyHolder homeFRBodyHolder = new HomeFRBodyHolder();
                        homeFRBodyHolder.setHomeBodyBeanList(homeBodyBeans);
                        list.add(homeFRBodyHolder);
                        getView().refreshHome(list);
                    }

                    @Override
                    public void onFail(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
