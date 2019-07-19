package com.example.huodai.ui.fragments;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.huodai.R;
import com.example.huodai.mvp.model.MyFRFunctionHolder;
import com.example.huodai.mvp.presenters.MyFrgPresenter;
import com.example.huodai.mvp.view.MyViewImpl;
import com.example.huodai.ui.adapter.MyRevAdapter;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.ui.adapter.decoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MyFragment extends BaseMVPFragment<MyViewImpl, MyFrgPresenter> implements MyViewImpl {

    @BindView(R.id.recv_my)
    RecyclerView recyclerView;

    private int[] name = {R.string.any_question, R.string.about_us, R.string.serice_content};
    private int[] icon = {R.drawable.group,R.drawable.about, R.drawable.fuwu};


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
        MyRevAdapter myRevAdapter=new MyRevAdapter(myFRFunctionHolders,getContext());
        recyclerView.setAdapter(myRevAdapter);
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
