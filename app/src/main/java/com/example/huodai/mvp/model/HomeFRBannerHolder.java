package com.example.huodai.mvp.model;

import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeBannerBean;

import java.util.List;

public class HomeFRBannerHolder extends BaseMulDataModel {


    private NewHomeBannerBean newHomeBannerBean;

    public NewHomeBannerBean getNewHomeBannerBean() {
        return newHomeBannerBean;
    }

    public void setNewHomeBannerBean(NewHomeBannerBean newHomeBannerBean) {
        this.newHomeBannerBean = newHomeBannerBean;
    }
}
