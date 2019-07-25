package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeMenuBean;

import java.util.List;

public class HomeFRMenuHolder extends BaseMulDataModel {
    private List<NewHomeMenuBean.LoanCategoriesBean> urls;

    public List<NewHomeMenuBean.LoanCategoriesBean> getUrls() {
        return urls;
    }

    public void setUrls(List<NewHomeMenuBean.LoanCategoriesBean> urls) {
        this.urls = urls;
    }
}
