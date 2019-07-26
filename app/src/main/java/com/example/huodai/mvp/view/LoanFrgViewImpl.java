package com.example.huodai.mvp.view;

import com.example.baselib.mvp.IView;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.model.bean.NewHomeMenuBean;

import java.util.List;

public interface LoanFrgViewImpl extends IView {
    void refreshHome(List<BaseMulDataModel> list);

    void refreshTypeFliter(List<NewHomeMenuBean.LoanCategoriesBean> loanCategories);

    void addPage(List<BaseMulDataModel> list);
}
