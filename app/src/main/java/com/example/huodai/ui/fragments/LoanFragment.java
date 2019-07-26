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
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.example.baselib.base.BaseMVPFragment;
import com.example.baselib.http.HttpConstant;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.Utils;
import com.example.huodai.R;
import com.example.huodai.mvp.model.postbean.LoanFraTypeBean;
import com.example.huodai.mvp.model.postbean.LoanMoneyBean;
import com.example.huodai.mvp.presenters.LoanFrgPresenter;
import com.example.huodai.mvp.view.LoanFrgViewImpl;
import com.example.huodai.ui.adapter.HomeFragRevAdapyer;
import com.example.huodai.ui.adapter.base.BaseMulDataModel;
import com.example.huodai.widget.LoanFraPopWindow;
import com.example.model.bean.NewHomeMenuBean;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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

    @BindView(R.id.tx_all)
    TextView txAll;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    //商品查询的条件
    private int typeId = 0;
    private int moneyMaxLit = 0;
    private int monetLitmitHigh = 0;

    //body的当前刷新页面
    private int currentPage = 1;

    private List<BaseMulDataModel> list;
    private HomeFragRevAdapyer fragRevAdapyer;
    private List<BaseMulDataModel> baseMulDataModels;
    private LoanFraPopWindow mPopWindow;


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
    public void cFliterMoney(LoanMoneyBean loanMoneyBean) {
        currentPage=1;
        monetLitmitHigh = loanMoneyBean.getLimit();
        moneyMaxLit = loanMoneyBean.getMax();
        if (loanMoneyBean.getName().trim().equals(getString(R.string.loan_all))) {
            //moneyMaxLit,monetLitmitHigh
            moneyMaxLit = 0;
            monetLitmitHigh = 0;
            mPresenter.requestBody(typeId);
        }
        mPresenter.requestBody(typeId, monetLitmitHigh, moneyMaxLit);
        bannerSecond.setText(loanMoneyBean.getName());
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void cFliterType(LoanFraTypeBean loanFraTypeBean) {
        currentPage=1;
        //通过ID，选中类型，发起类型请求
        typeId = loanFraTypeBean.getId();
        MyLog.i("我接受到了eventbus: " + loanFraTypeBean.toString());
        //这里请求body数据
        mPresenter.requestBody(typeId, monetLitmitHigh, moneyMaxLit);

        //更新选项item
        bannerFirst.setText(loanFraTypeBean.getName());
        //然后留出一个全局的id，方便做联合查询
        typeId = loanFraTypeBean.getId();

        //文字添加说明
        bannerFirst.setText(loanFraTypeBean.getName());
    }


    @Override
    protected void initView() {

        mPopWindow = new LoanFraPopWindow(getContext());
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
                    if (list != null) {
                        mPopWindow.selectType(LoanFraPopWindow.TYPE, list);
                    } else {
                        mPresenter.requestMenu();
                    }

                }

                if (Utils.isContaint(bannerSecond, point) || Utils.isContaint(bannerImgS, point)) {
                    mPopWindow.selectType(LoanFraPopWindow.LOAN, null);
                }

                if (Utils.isContaint(txAll, point)) {
                    mPresenter.requestBody(0);
                    if (mPopWindow.isShowing())
                        mPopWindow.dismiss();
                }

                return true;
            }
        });
        //不把它放到懒加载
        mPresenter.requestBody(0);

        txTitle.setText(getResources().getString(R.string.loan_num));
        baseMulDataModels = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        fragRevAdapyer = new HomeFragRevAdapyer(getActivity(), baseMulDataModels);
       // fragRevAdapyer.setHasStableIds(true);
        mRecyclerView.setAdapter(fragRevAdapyer);

        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnRefreshListener(refreshLayout -> {
            MyLog.i("我触发了1");
            mPresenter.requestBody(typeId, monetLitmitHigh, moneyMaxLit);
        });

        refreshLayout.setOnLoadMoreListener(refreshLayout1 -> {
            MyLog.i("我触发了2");
            currentPage++;
            mPresenter.requestBodyPage(typeId, monetLitmitHigh, moneyMaxLit, currentPage);
        });
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
        if (refreshLayout.isRefreshing())
            refreshLayout.finishRefresh();
    }

    @Override
    public void addPage(List<BaseMulDataModel> list) {
        fragRevAdapyer.notifyDataSetChanged();
        refreshLayout.finishLoadMore();
    }


    @Override
    public void refreshTypeFliter(List<NewHomeMenuBean.LoanCategoriesBean> loanCategories) {
        //这里类型筛选的item的时候了
        list = new ArrayList<>();
        LoanFraTypeBean allloanFraTypeBean = new LoanFraTypeBean();
        allloanFraTypeBean.setName(getString(R.string.loan_all));
        allloanFraTypeBean.setId(0);
        list.add(allloanFraTypeBean);
        for (NewHomeMenuBean.LoanCategoriesBean loanCategoriesBean : loanCategories) {
            LoanFraTypeBean loanFraTypeBean = new LoanFraTypeBean();
            loanFraTypeBean.setId(loanCategoriesBean.getId());
            loanFraTypeBean.setName(loanCategoriesBean.getName());
            list.add(loanFraTypeBean);
        }
        mPopWindow.selectType(LoanFraPopWindow.TYPE, list);
        if (!mPopWindow.isShowing())
            mPopWindow.showAsDropDown(layout, getContext().getApplicationContext());
    }


    @OnClick({R.id.tx_refrsh, R.id.spinner_type_text, R.id.spinner_loannum_text, R.id.spinner_type_img, R.id.spinner_loannum_img, R.id.tx_all})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_refrsh:
                File cacheFile = new File(HttpConstant.context.getCacheDir(), HttpConstant.cacheFileName);//缓存文件路径
                mPresenter.requestBody(typeId);//请求body
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
                mPopWindow.selectType(LoanFraPopWindow.LOAN, null);
                if (!mPopWindow.isShowing())
                    mPopWindow.showAsDropDown(layout, getContext().getApplicationContext());
                break;
            case R.id.tx_all:
                //全部原本的记录预留用来联合查询的id，min,max清空
                typeId = 0;
                monetLitmitHigh = 0;
                moneyMaxLit = 0;
                mPresenter.requestBody(0);
                bannerFirst.setText(getString(R.string.type));
                bannerSecond.setText(getString(R.string.money));
                break;
        }

    }
}
