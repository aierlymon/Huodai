package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;

import java.util.List;

public class HomeFRBannerHolder extends BaseMulDataModel {
    private List<String> icon_urls;

    private List<String> urls;

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public List<String> getIcon_urls() {
       return icon_urls;
   }



    public void setIcon_urls(List<String> icon_urls) {
        this.icon_urls = icon_urls;
    }

}
