package com.example.huodai;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.StatusBarUtil;
import com.example.huodai.mvp.model.postbean.RecomBean;
import com.example.huodai.mvp.presenters.HistoryPresenter;
import com.example.huodai.mvp.view.HistoryImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HistoryActivity extends BaseMvpActivity<HistoryImpl, HistoryPresenter> implements HistoryImpl {

    //toolbar的标题
    @BindView(R.id.tx_title)
    TextView tx;

    //返回按钮
    @BindView(R.id.img_back)
    ImageView back;

    @BindView(R.id.history_recv)
    RecyclerView mRecyclerView;

    @BindView(R.id.history_refreshLayout)
    SmartRefreshLayout refreshLayout;

    private List<BaseMulDataModel> baseMulDataModels;
    private HomeFragRevAdapyer fragRevAdapyer;

    //body的当前刷新页面
    private int currentPage = 1;

    @Override
    protected HistoryPresenter createPresenter() {
        return new HistoryPresenter();
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容

        //这个就是设施沉浸式状态栏的主要方法了
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);

        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }


        super.onCreate(savedInstanceState);

        //黄油刀
        ButterKnife.bind(this);
        //设置标题和返回键
        //设置头部
        tx.setText(getString(R.string.history));
        back.setVisibility(View.VISIBLE);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        baseMulDataModels = new ArrayList<>();
        fragRevAdapyer = new HomeFragRevAdapyer(this, baseMulDataModels);
        mRecyclerView.setAdapter(fragRevAdapyer);


        //进行筛选请求
        mPresenter.loadHistory(ApplicationPrams.loginCallBackBean.getId());

        //刷新按钮的刷新是按
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            MyLog.i("我触发了上拉刷新");
            mPresenter.loadHistory(ApplicationPrams.loginCallBackBean.getId());
        });


        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            MyLog.i("我触发了上拉加载更多");
            //currentPage++;
            refreshLayout.finishLoadMore();
          //  mPresenter.loadHistory(0, recomBean.getMoneyMin(),recomBean.getMoneyMax(), currentPage);
        });
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_history;
    }

    @Override
    public boolean isUseLayoutRes() {
        return true;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        CustomToast.showToast(getApplicationContext(),msg,2000);
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    @Override
    public void refreshHistory(List<BaseMulDataModel> list) {
        fragRevAdapyer.setModelList(list);
        fragRevAdapyer.notifyDataSetChanged();
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    @Override
    public void addPage(List<BaseMulDataModel> list) {

    }

    @OnClick({R.id.img_back})
    public void OnClick(View v){
        finish();
    }
}
