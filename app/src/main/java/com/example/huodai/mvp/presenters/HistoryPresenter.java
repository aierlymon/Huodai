package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.model.HomeFRBodyHolderFH;
import com.example.huodai.mvp.view.HistoryImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HistoryBean;
import com.example.model.bean.HttpResult;
import com.example.model.bean.NewHomeBodyBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HistoryPresenter extends BasePresenter<HistoryImpl> {

    //分类的list
    List<BaseMulDataModel> list = new ArrayList<>();

    @Override
    public void showError(String msg) {

    }

    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    public void loadHistory(int id){

        HttpMethod.getInstance().userApplyRecordsList(id)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<List<HistoryBean>>>(this) {
                    @Override
                    public void onSuccess(HttpResult<List<HistoryBean>> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            HomeFRBodyHolderFH homeFRBodyHolder = new HomeFRBodyHolderFH();
                            homeFRBodyHolder.setHomeBodyBeanList(httpResult.getData());
                            list.clear();
                            list.add(homeFRBodyHolder);
                            getView().refreshHistory(list);

                        } else {
                            showError(httpResult.getMsg()+":"+httpResult.getStatusCode());
                        }
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
