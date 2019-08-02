package com.example.huodai.mvp.view;

import com.example.baselib.mvp.IView;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;

import java.util.List;

public interface FliterImpl extends IView {
    void refreshFilter(List<BaseMulDataModel> list);

    void addPage(List<BaseMulDataModel> list);
}
