package com.example.huodai.ui.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.utils.MyLog;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.LoginActivity;
import com.example.huodai.R;
import com.example.huodai.mvp.model.MyFRFunctionHolder;
import com.example.huodai.mvp.presenters.MyFrgPresenter;
import com.example.huodai.mvp.view.MyViewImpl;
import com.example.huodai.ui.adapter.MyRevAdapter;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.ui.adapter.decoration.SpaceItemDecoration;
import com.example.huodai.widget.CircleImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFragment extends BaseMVPFragment<MyViewImpl, MyFrgPresenter> implements MyViewImpl {

    @BindView(R.id.recv_my)
    RecyclerView recyclerView;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.user_icon)
    CircleImageView imgIcon;

    @BindView(R.id.tx_username)
    TextView txUsername;

    @BindView(R.id.exit)
    TextView txExit;

    private int[] name = {R.string.any_question, R.string.about_us, R.string.serice_content};
    private int[] icon = {R.drawable.group, R.drawable.about, R.drawable.fuwu};


    public static MyFragment newInstance(String info) {
        MyFragment fragment = new MyFragment();
        fragment.setTitle(info);
        return fragment;
    }


    @Override
    protected MyFrgPresenter createPresenter() {
        return new MyFrgPresenter();
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fra_my;
    }

    @Override
    protected void lazyLoadData() {

    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(2));
        List<BaseMulDataModel> myFRFunctionHolders = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MyFRFunctionHolder myFRFunctionHolder = new MyFRFunctionHolder();
            myFRFunctionHolder.setF_icon(icon[i]);
            myFRFunctionHolder.setF_name(getResources().getString(name[i]));
            myFRFunctionHolders.add(myFRFunctionHolder);
        }
        MyRevAdapter myRevAdapter = new MyRevAdapter(myFRFunctionHolders, getContext());
        recyclerView.setAdapter(myRevAdapter);

        if(ApplicationPrams.isLogin){
            loginSuceesee();
        }
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

    @OnClick({R.id.btn_login, R.id.exit})
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btn_login:
                intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.exit:
                noLogin();
                EventBus.getDefault().post(false);
                ApplicationPrams.loginCallBackBean = null;
                break;
        }

    }

    private void noLogin() {
        ApplicationPrams.isLogin=false;
        btnLogin.setVisibility(View.VISIBLE);
        imgIcon.setVisibility(View.GONE);
        txUsername.setVisibility(View.GONE);
        txUsername.setText(ApplicationPrams.loginCallBackBean.getUser().getPhone());
        txExit.setVisibility(View.GONE);
    }


    @Override
    public boolean isUseEventBus() {
        return false;
    }

    @Override
    public void loginSuceesee() {
        btnLogin.setVisibility(View.GONE);
        imgIcon.setVisibility(View.VISIBLE);
        txUsername.setVisibility(View.VISIBLE);
        txUsername.setText(ApplicationPrams.loginCallBackBean.getUser().getPhone());
        txExit.setVisibility(View.VISIBLE);
    }
}
