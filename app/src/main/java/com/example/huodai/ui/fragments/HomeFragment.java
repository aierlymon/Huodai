package com.example.huodai.ui.fragments;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.huodai.R;
import com.example.huodai.mvp.presenters.HomeFrgPresenter;
import com.example.huodai.mvp.view.HomeFrgViewImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeFragment extends BaseMVPFragment<HomeFrgViewImpl, HomeFrgPresenter> implements HomeFrgViewImpl {

    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerView;
    private List<BaseMulDataModel> baseMulDataModels;
    private HomeFragRevAdapyer fragRevAdapyer;

    public static HomeFragment newInstance(String info) {
        HomeFragment fragment = new HomeFragment();
        fragment.setTitle(info);
        return fragment;
    }

    @Override
    protected HomeFrgPresenter createPresenter() {
        return new HomeFrgPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fra_home;
    }

    @Override
    protected void lazyLoadData() {
        mPresenter.requestHead();
    }

    @Override
    protected void initView() {
        baseMulDataModels = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        fragRevAdapyer = new HomeFragRevAdapyer(getContext(),baseMulDataModels);
        mRecyclerView.setAdapter(fragRevAdapyer);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void refreshBannerViewpager(List<BaseMulDataModel> list) {
        fragRevAdapyer.getModelList().addAll(list);
        fragRevAdapyer.notifyDataSetChanged();
    }
}
