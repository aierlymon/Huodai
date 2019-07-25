package com.example.huodai.ui.fragments;

import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.http.HttpConstant;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.Utils;
import com.example.huodai.R;
import com.example.huodai.mvp.model.postbean.LoanFraFliterBean;
import com.example.huodai.mvp.model.postbean.LoanFraTypeBean;
import com.example.huodai.mvp.presenters.LoanFrgPresenter;
import com.example.huodai.mvp.view.LoanFrgViewImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.widget.TestPopWindow;
import com.example.model.bean.NewHomeMenuBean;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.spinner_layout)
    LinearLayout layout;

    @BindView(R.id.spinner_type_text)
    TextView bannerFirst;

    @BindView(R.id.spinner_loannum_text)
    TextView bannerSecond;


    @BindView(R.id.spinner_type_img)
    ImageView bannerImgF;

    @BindView(R.id.spinner_loannum_img)
    ImageView bannerImgS;


    private List<BaseMulDataModel> list;
    private HomeFragRevAdapyer fragRevAdapyer;
    private List<BaseMulDataModel> baseMulDataModels;
    private TestPopWindow mPopWindow;

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
    public boolean isUseEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkfliter(LoanFraFliterBean loanFraFliterBean) {
        //通过ID，选中类型，发起类型请求
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void checkfliter(LoanFraTypeBean loanFraTypeBean) {
        //通过ID，选中类型，发起类型请求
        MyLog.i("我接受到了eventbus: " + loanFraTypeBean.toString());
        //这里请求body数据
        mPresenter.requestBody(loanFraTypeBean.getId());
        //更新textview
        bannerFirst.setText(loanFraTypeBean.getName());
    }


    @Override
    protected void initView() {

        mPopWindow = new TestPopWindow(getContext());
        mPopWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                int[] xys = mPopWindow.getAnchorLoc();
                //因为只能在下部分的半透明区隐藏，所以价格判断限制
                if (xys[1] != 0 && motionEvent.getRawY() >= (xys[1] + layout.getHeight())) {
                    return false;
                }
                //然后这里设置的是，因为不能让popwindow消失，所以在可隐藏区域外要拦截点击事件
                //然后再通过区域判断是否在按钮内来更新数据
                Point point = new Point();
                point.set((int) motionEvent.getRawX(), (int) motionEvent.getRawY());

                if (Utils.isContaint(bannerFirst, point) || Utils.isContaint(bannerImgF, point)) {
                    if(list!=null){
                        mPopWindow.selectType(TestPopWindow.TYPE, list);
                    }else{
                        mPresenter.requestMenu();
                    }

                }

                if (Utils.isContaint(bannerSecond, point) || Utils.isContaint(bannerImgS, point)) {
                    mPopWindow.selectType(TestPopWindow.LOAN, null);
                }

                return true;
            }
        });
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
        CustomToast.showToast(getContext().getApplicationContext(), msg, 2000);
    }

    @Override
    public void refreshHome(List<BaseMulDataModel> list) {
        fragRevAdapyer.setModelList(list);
        fragRevAdapyer.notifyDataSetChanged();
    }

    @Override
    public void refreshTypeFliter(List<NewHomeMenuBean.LoanCategoriesBean> loanCategories) {
        //这里类型筛选的item的时候了
        list = new ArrayList<>();
        for (NewHomeMenuBean.LoanCategoriesBean loanCategoriesBean : loanCategories) {
            LoanFraTypeBean loanFraTypeBean = new LoanFraTypeBean();
            loanFraTypeBean.setId(loanCategoriesBean.getId());
            loanFraTypeBean.setName(loanCategoriesBean.getName());
            list.add(loanFraTypeBean);
        }
        mPopWindow.selectType(TestPopWindow.TYPE, list);
        if (!mPopWindow.isShowing())
            mPopWindow.showAsDropDown(layout, getContext().getApplicationContext());
    }

    @OnClick({R.id.tx_refrsh, R.id.spinner_type_text, R.id.spinner_loannum_text, R.id.spinner_type_img, R.id.spinner_loannum_img})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_refrsh:
                File cacheFile = new File(HttpConstant.context.getCacheDir(), HttpConstant.cacheFileName);//缓存文件路径
                mPresenter.requestBody();//请求body
                if (cacheFile.exists()) {
                    mRecyclerView.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                }
                break;
            case R.id.spinner_type_text:
            case R.id.spinner_type_img:
                mPresenter.requestMenu();
                break;
            case R.id.spinner_loannum_text:
            case R.id.spinner_loannum_img:
                mPopWindow.selectType(TestPopWindow.LOAN, null);
                if (!mPopWindow.isShowing())
                    mPopWindow.showAsDropDown(layout, getContext().getApplicationContext());
                break;
        }

    }
}
