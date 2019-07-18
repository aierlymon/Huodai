package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;

import java.util.List;

public class HomeFRMenuHolder extends BaseMulDataModel {
    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
