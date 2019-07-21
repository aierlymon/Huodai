package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpConstant;
import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.huodai.mvp.model.HomeFRBannerHolder;
import com.example.huodai.mvp.model.HomeFRBodyHolder;
import com.example.huodai.mvp.model.HomeFRMenuHolder;
import com.example.huodai.mvp.view.HomeFrgViewImpl;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HomeBannerBean;
import com.example.model.bean.HomeBodyBean;
import com.example.model.bean.HomeMenuBean;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HomeFrgPresenter extends BasePresenter<HomeFrgViewImpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    List<BaseMulDataModel> list = new ArrayList<>();

    public void requestHead() {
        HttpMethod.getInstance().loadHomeBanner()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<List<HomeBannerBean>>(this) {
                    @Override
                    public void onSuccess(List<HomeBannerBean> homeHeadBeans) {
                        MyLog.i("成功了: " + homeHeadBeans.size()+"  shuxing: ");
                        List<String> urls = new ArrayList<>();
                        for (HomeBannerBean homeHeadBean : homeHeadBeans) {
                            MyLog.i("看一看数据:=========> "+homeHeadBean.getTitle());
                            ///group1/default/20190630/22/32/8/微信图片_20190417112246.png
                            urls.add(HttpConstant.BASE_URL + homeHeadBean.getIcon());
                        }
                        HomeFRBannerHolder homeFRBannerHolder = new HomeFRBannerHolder();
                        homeFRBannerHolder.setUrls(urls);
                        list.add(homeFRBannerHolder);
                        getView().refreshHome(list);
                    }

                    @Override
                    public void onFail(Throwable e) {
                        MyLog.i("失败了: " + e.getMessage());
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }

    public void requestMenu() {
        HttpMethod.getInstance().loadHomeMenu()
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<List<HomeMenuBean>>(this) {
                    @Override
                    public void onSuccess(List<HomeMenuBean> homeMenuBeans) {
                        MyLog.i("请求menu成功: " + homeMenuBeans.size());
                        HomeFRMenuHolder homeFRMenuHolder = new HomeFRMenuHolder();
                        homeFRMenuHolder.setUrls(homeMenuBeans);
                        list.add(homeFRMenuHolder);

                    }

                    @Override
                    public void onFail(Throwable e) {

                    }

                    @Override
                    public void onCompleted() {

                    }
                });
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
