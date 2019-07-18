package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.HomeMenuBean;

import java.util.List;

public class HomeFRMenuHolder extends BaseMulDataModel {
    private List<HomeMenuBean> urls;

    public List<HomeMenuBean> getUrls() {
        return urls;
    }

    public void setUrls(List<HomeMenuBean> urls) {
        this.urls = urls;
    }
}
