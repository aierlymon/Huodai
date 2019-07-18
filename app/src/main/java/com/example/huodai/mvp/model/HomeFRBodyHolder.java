package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HomeBodyBean;

import java.util.List;

public class HomeFRBodyHolder extends BaseMulDataModel {
    List<HomeBodyBean> homeBodyBeanList;

    public List<HomeBodyBean> getHomeBodyBeanList() {
        return homeBodyBeanList;
    }

    public void setHomeBodyBeanList(List<HomeBodyBean> homeBodyBeanList) {
        this.homeBodyBeanList = homeBodyBeanList;
    }
}
