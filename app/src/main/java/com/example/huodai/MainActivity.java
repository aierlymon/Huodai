package com.example.huodai;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.baselib.base.BaseMvpActivity;
import com.example.huodai.mvp.presenters.MainPrsenter;
import com.example.huodai.mvp.view.MainViewImpl;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseMvpActivity<MainViewImpl, MainPrsenter> implements MainViewImpl{

    @BindView(R.id.tx)
    TextView tx;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        tx.setText("你好啊");
    }

    @Override
    protected MainPrsenter createPresenter() {
        return new MainPrsenter();
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
}
