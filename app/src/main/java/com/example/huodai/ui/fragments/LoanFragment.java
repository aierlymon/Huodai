package com.example.huodai.ui.fragments;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.http.HttpConstant;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.MyLog;
import com.example.huodai.R;
import com.example.huodai.mvp.presenters.LoanFrgPresenter;
import com.example.huodai.mvp.view.LoanFrgViewImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class LoanFragment extends BaseMVPFragment<LoanFrgViewImpl, LoanFrgPresenter> implements LoanFrgViewImpl {

    @BindView(R.id.recv_loan)
    RecyclerView mRecyclerView;

    @BindView(R.id.tx_refrsh)
    TextView refreshTx;

    @BindView(R.id.noOnline)
    RelativeLayout relativeLayout;

    @BindView(R.id.tx_title)
    TextView txTitle;


    private HomeFragRevAdapyer fragRevAdapyer;
    private List<BaseMulDataModel> baseMulDataModels;

    public static LoanFragment newInstance(String info) {
        LoanFragment fragment = new LoanFragment();
        fragment.setTitle(info);
        return fragment;
    }

    @Override
    protected LoanFrgPresenter createPresenter() {
        return new LoanFrgPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fra_loan;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initView() {
        //不把它放到懒加载
        mPresenter.requestBody();
        txTitle.setText(getResources().getString(R.string.loan_num));
        baseMulDataModels = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        fragRevAdapyer = new HomeFragRevAdapyer(getActivity(), baseMulDataModels);
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
            mRecyclerView.setVisibility(View.GONE);
            relativeLayout.setVisibility(View.VISIBLE);
            CustomToast.showToast(getContext().getApplicationContext(),msg,2000);
    }

    @Override
    public void refreshHome(List<BaseMulDataModel> list) {
        fragRevAdapyer.getModelList().clear();
        fragRevAdapyer.getModelList().addAll(list);
        fragRevAdapyer.notifyDataSetChanged();
    }

    @OnClick({R.id.tx_refrsh})
    public void onClick(){
        MyLog.i("触发了刷新的点击事件");
        File cacheFile = new File(HttpConstant.context.getCacheDir(), HttpConstant.cacheFileName);//缓存文件路径
        mPresenter.requestBody();//请求body
        if(cacheFile.exists()){
            mRecyclerView.setVisibility(View.VISIBLE);
            relativeLayout.setVisibility(View.GONE);
        }
    }
}
