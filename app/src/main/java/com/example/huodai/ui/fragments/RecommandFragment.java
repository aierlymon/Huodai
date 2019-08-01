package com.example.huodai.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.utils.CustomToast;
import com.example.huodai.R;
import com.example.huodai.RecomMineActivity;
import com.example.huodai.mvp.model.postbean.RecomBean;
import com.example.huodai.mvp.presenters.RecomFrgPresenter;
import com.example.huodai.mvp.view.RecomViewImpl;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class RecommandFragment extends BaseMVPFragment<RecomViewImpl, RecomFrgPresenter> implements RecomViewImpl {
    @BindView(R.id.tx_title)
    TextView txTitle;

    @BindView(R.id.tx_howmuch)
    TextView txHowMuch;

    @BindView(R.id.tx_howlong)
    TextView txHowLong;

    @BindView(R.id.group_howmuch)
    RadioGroup groupHowMuch;

    @BindView(R.id.group_howlong)
    RadioGroup getGroupHowLong;

    private RecomBean recomBean;

    @Override
    protected RecomFrgPresenter createPresenter() {
        return new RecomFrgPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fra_recommend_how;
    }

    public static RecommandFragment newInstance(String info) {
        RecommandFragment fragment = new RecommandFragment();
        fragment.setTitle(info);
        return fragment;
    }

    @Override
    protected void lazyLoadData() {
        //这个进行懒加载
    }

    @Override
    protected void initView() {
        recomBean=new RecomBean();
        txTitle.setText(getResources().getString(R.string.recommand_title));
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        CustomToast.showToast(getContext().getApplicationContext(), "服务器异常: " + msg, 2000);
    }

    @Override
    public void refreshTitle(String title) {
        if (title.contains(":")) {
            String[] titles = title.split(":");
            txHowMuch.setText(titles[0]);
            txHowLong.setText(titles[1]);
        }
    }

    @OnCheckedChanged({R.id.oneMonth, R.id.threeMonth, R.id.sixMonth, R.id.twiceMonth, R.id.eightMonth, R.id.yearMonth, R.id.moneyFirst,
            R.id.moneySecond, R.id.moneyThird, R.id.moneyFour,R.id.moneyFive})
    public void OnCheckChange(CompoundButton view, boolean ischanged) {
        switch (view.getId()) {
            case R.id.oneMonth:
                recomBean.setDateMin(1);
                recomBean.setDateMax(3);
                break;
            case R.id.threeMonth:
                recomBean.setDateMin(3);
                recomBean.setDateMax(6);
                break;
            case R.id.sixMonth:
                recomBean.setDateMin(6);
                recomBean.setDateMax(12);
                break;
            case R.id.twiceMonth:
                recomBean.setDateMin(12);
                recomBean.setDateMax(18);
                break;
            case R.id.eightMonth:
                recomBean.setDateMin(18);
                recomBean.setDateMax(24);
                break;
            case R.id.yearMonth:
                recomBean.setDateMin(24);
                recomBean.setDateMax(36);
                break;
            case R.id.moneyFirst:
                recomBean.setMoneyMax(5000);
                break;
            case R.id.moneySecond:
                recomBean.setMoneyMin(5000);
                recomBean.setMoneyMax(10000);
                break;
            case R.id.moneyThird:
                recomBean.setMoneyMin(10000);
                recomBean.setMoneyMax(20000);
                break;
            case R.id.moneyFour:
                recomBean.setMoneyMin(20000);
                recomBean.setMoneyMax(30000);
                break;
            case R.id.moneyFive:
                recomBean.setMoneyMin(30000);
                break;
        }
    }

    @OnClick({R.id.btn_next})
    public void OnClick(View v){
        Intent intent=new Intent(getActivity(), RecomMineActivity.class);
        int[] moneys=new int[2];
        int[] dates=new int[2];
        moneys[0]=recomBean.getMoneyMin();
        moneys[1]=recomBean.getMoneyMax();
        dates[0]=recomBean.getDateMin();
        dates[1]=recomBean.getDateMax();

        intent.putExtra("moneys",moneys);
        intent.putExtra("dates",dates);
        startActivity(intent);
    }
}
