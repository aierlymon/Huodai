package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.view.FliterImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HttpResult;
import com.example.model.bean.NewHomeBodyBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FliterPresenter extends BasePresenter<FliterImpl> {

    //分类的list
    List<BaseMulDataModel> list = new ArrayList<>();


    @Override
    public void showError(String msg) {

    }

    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    //请求Body内容的
    public void requestBody(int id,int min,int max) {
        if(max==0){
            HttpMethod.getInstance().loadBodyLimitHigh(id,min)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<HttpResult<NewHomeBodyBean>>(this) {
                        @Override
                        public void onSuccess(HttpResult<NewHomeBodyBean> httpResult) {
                            if (httpResult.getStatusCode() == 200) {
                                HomeFRBodyHolder homeFRBodyHolder = new HomeFRBodyHolder();
                                homeFRBodyHolder.setHomeBodyBeanList(httpResult.getData().getLoanProduct());
                                list.clear();
                                list.add(homeFRBodyHolder);
                                getView().refreshFilter(list);

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
        }else{
            HttpMethod.getInstance().loadBodyMintoMax(id,min,max)
                    .subscribeOn(Schedulers.single())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<HttpResult<NewHomeBodyBean>>(this) {
                        @Override
                        public void onSuccess(HttpResult<NewHomeBodyBean> httpResult) {
                            if (httpResult.getStatusCode() == 200) {
                                HomeFRBodyHolder homeFRBodyHolder = new HomeFRBodyHolder();
                                homeFRBodyHolder.setHomeBodyBeanList(httpResult.getData().getLoanProduct());
                                list.clear();
                                list.add(homeFRBodyHolder);
                                getView().refreshFilter(list);

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


    public void requestBodyPage(int id,int min,int max,int page){

        HttpMethod.getInstance().loadBodyMintoMaxToPage(id,min,max,page)
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<NewHomeBodyBean>>(this) {
                    @Override
                    public void onSuccess(HttpResult<NewHomeBodyBean> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            HomeFRBodyHolder homeFRBodyHolder = new HomeFRBodyHolder();
                            homeFRBodyHolder.setHomeBodyBeanList(httpResult.getData().getLoanProduct());
                            for(int i=0;i<list.size();i++){
                                if(list.get(i) instanceof HomeFRBodyHolder){
                                    ((HomeFRBodyHolder) list.get(i)).getHomeBodyBeanList().addAll(homeFRBodyHolder.getHomeBodyBeanList());
                                }
                            }
                            getView().addPage(list);
                            MyLog.i("requestBody(x) end : "+list.size());
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
