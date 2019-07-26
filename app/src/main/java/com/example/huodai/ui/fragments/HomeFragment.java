package com.example.huodai.ui.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.http.HttpConstant;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.LoadDialogUtil;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.StatusBarUtil;
import com.example.baselib.utils.Utils;
import com.example.baselib.widget.CustomDialog;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.R;
import com.example.huodai.mvp.presenters.HomeFrgPresenter;
import com.example.huodai.mvp.view.HomeFrgViewImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class HomeFragment extends BaseMVPFragment<HomeFrgViewImpl, HomeFrgPresenter> implements HomeFrgViewImpl {

    @BindView(R.id.home_recyclerview)
    RecyclerView mRecyclerView;
    private List<BaseMulDataModel> baseMulDataModels;
    private HomeFragRevAdapyer fragRevAdapyer;

    @BindView(R.id.tx_refrsh)
    TextView refreshTx;

    @BindView(R.id.noOnline)
    RelativeLayout relativeLayout;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    //body的当前刷新页面
    private int currentPage = 1;

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
        //   showLoading();
        mPresenter.requestHead();//请求banner
        mPresenter.requestMenu();//请求菜单
        mPresenter.requestBody();//请求body
    }

    @Override
    protected void initView() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);


        fragRevAdapyer = new HomeFragRevAdapyer(getActivity(), mPresenter.getList());
        mRecyclerView.setAdapter(fragRevAdapyer);

        //刷新设置
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            currentPage = 1;
            mPresenter.requestHead();//请求banner
            mPresenter.requestMenu();//请求菜单
            mPresenter.requestBody();//请求body
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            MyLog.i("我触发了2");
            currentPage++;
            mPresenter.requestBodyPage(0, 0, 0, currentPage);
        });
    }

    @Override
    public void showLoading() {
        //  LoadDialogUtil.getInstance(getActivity(), "正在加载", CustomDialog.DoubleBounce).show();
    }


    @Override
    public void hideLoading() {
        //   LoadDialogUtil.getInstance(getActivity(), "正在加载", CustomDialog.DoubleBounce).cancel();
    }

    @Override
    public void showError(String msg) {

        mRecyclerView.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);
        CustomToast.showToast(getContext().getApplicationContext(), msg, 2000);

    }

    @Override
    public void refreshHome(List<BaseMulDataModel> list) {
       // fragRevAdapyer.setModelList(list);
        fragRevAdapyer.notifyDataSetChanged();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void addPage(List<BaseMulDataModel> list) {
        fragRevAdapyer.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
    }

    @OnClick({R.id.tx_refrsh})
    public void onClick() {
        MyLog.i("触发了刷新的点击事件");
        File cacheFile = new File(HttpConstant.context.getCacheDir(), HttpConstant.cacheFileName);//缓存文件路径

        mPresenter.requestHead();//请求banner
        mPresenter.requestMenu();//请求菜单
        mPresenter.requestBody();//请求body
        if (cacheFile.exists()) {
            mRecyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
    }


}
