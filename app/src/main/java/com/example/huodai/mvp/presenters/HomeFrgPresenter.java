package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpConstant;
import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.HttpResult;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.ApplicationPrams;
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
                            NewHomeBannerBean homeHeadBean = httpResult.getData();
                            HomeFRBannerHolder homeFRBannerHolder = new HomeFRBannerHolder();
                            homeFRBannerHolder.setNewHomeBannerBean(homeHeadBean);//这个预留出来的page,pageCout而已，其实都一样最好的
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
                            MyLog.i("requestMenuc成功了");
                            HomeFRMenuHolder homeFRMenuHolder = new HomeFRMenuHolder();
                            //这个地方因为和商品的筛选是动态的，所以这个也要是动态

                            homeFRMenuHolder.setLoanCategoriesBean(httpResult.getData().getLoanCategories());
                            homeFRMenuHolder.setNewHomeMenuBean(httpResult.getData());//这个预留出来的page,pageCout而已，其实都只要上面那个就够了这个
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

    public void clear() {
        list.clear();
    }
}
