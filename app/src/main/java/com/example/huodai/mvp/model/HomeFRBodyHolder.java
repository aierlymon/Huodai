package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeBodyBean;

import java.util.List;

public class HomeFRBodyHolder extends BaseMulDataModel {
    List<NewHomeBodyBean.LoanProductBean> homeBodyBeanList;

    public List<NewHomeBodyBean.LoanProductBean> getHomeBodyBeanList() {
        return homeBodyBeanList;
    }

    public void setHomeBodyBeanList(List<NewHomeBodyBean.LoanProductBean> homeBodyBeanList) {
        this.homeBodyBeanList = homeBodyBeanList;
    }
}
