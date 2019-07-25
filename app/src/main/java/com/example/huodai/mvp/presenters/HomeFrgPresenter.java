package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpConstant;
import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.HttpResult;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.mvp.model.HomeFRBannerHolder;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.model.HomeFRMenuHolder;
import com.example.huodai.mvp.view.HomeFrgViewImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeBannerBean;
import com.example.model.bean.NewHomeBodyBean;
import com.example.model.bean.NewHomeMenuBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFrgPresenter extends BasePresenter<HomeFrgViewImpl> {


    @Override
    public void showError(String msg) {
        getView().showError("连接错误: " + msg);
    }

    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    List<BaseMulDataModel> list = new ArrayList<>();



    //这个是banner头部的请求，就是轮播图
    public void requestHead() {
        HttpMethod.getInstance().loadHomeBanner()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<NewHomeBannerBean>>(this) {
                    @Override
                    public void onSuccess(HttpResult<NewHomeBannerBean> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            List<String> urls = new ArrayList<>();
                            List<String> icon_urls = new ArrayList<>();
                            NewHomeBannerBean homeHeadBean = httpResult.getData();
                            List<NewHomeBannerBean.BannersBean> bannersBeanList = homeHeadBean.getBanners();
                            for (NewHomeBannerBean.BannersBean bannersBean : bannersBeanList) {
                                MyLog.i("看一看数据:=========> " + homeHeadBean);
                                ///group1/default/20190630/22/32/8/微信图片_20190417112246.png
                                icon_urls.add(HttpConstant.BASE_URL + bannersBean.getIcon());
                                urls.add(bannersBean.getUrl());
                            }
                            HomeFRBannerHolder homeFRBannerHolder = new HomeFRBannerHolder();
                            homeFRBannerHolder.setIcon_urls(icon_urls);
                            homeFRBannerHolder.setUrls(urls);
                            list.add(homeFRBannerHolder);
                            getView().refreshHome(list);
                        } else {
                            showError(httpResult.getMsg() + ":" + httpResult.getStatusCode());
                        }
                    }

                    @Override
                    public void onFail(Throwable e) {
                        MyLog.i("失败了: " + e.getMessage());
                        showError(e.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }


    //请求清单选项卡
    public void requestMenu() {
        HttpMethod.getInstance().loadHomeMenu()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<NewHomeMenuBean>>(this) {
                    @Override
                    public void onSuccess(HttpResult<NewHomeMenuBean> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            HomeFRMenuHolder homeFRMenuHolder = new HomeFRMenuHolder();
                            homeFRMenuHolder.setUrls(httpResult.getData().getLoanCategories());
                            list.add(homeFRMenuHolder);
                        } else {
                            showError(httpResult.getMsg() + ":" + httpResult.getStatusCode());
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
                            showError(httpResult.getMsg() + ":" + httpResult.getStatusCode());
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
