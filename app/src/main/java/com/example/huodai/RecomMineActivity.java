package com.example.huodai;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.StatusBarUtil;
import com.example.huodai.mvp.model.postbean.RecomBean;
import com.example.huodai.mvp.presenters.RecomMinePresenter;
import com.example.huodai.mvp.view.RecomMineImpl;
import com.example.huodai.mvp.view.RecomViewImpl;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecomMineActivity extends BaseMvpActivity<RecomMineImpl, RecomMinePresenter> implements RecomViewImpl {

    private RecomBean recomBean;

    @BindView(R.id.edit_phone_number)
    EditText edPhomeNum;

    @BindView(R.id.edit_name)
    EditText edName;

    @BindView(R.id.edit_whonum)
    EditText edWhoName;

    @BindView(R.id.tx_title)
    TextView tx;

    @BindView(R.id.img_back)
    ImageView back;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_recommand_mine;
    }

    @Override
    public boolean isUseLayoutRes() {
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
     /*   if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this,0x55000000);
        }*/

        //这个就是设施沉浸式状态栏的主要方法了
        StatusBarUtil.setRootViewFitsSystemWindows(this, false);

        //首次启动 Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT 为 0，再次点击图标启动时就不为零了
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        super.onCreate(savedInstanceState);
        //拿到了intent，然后拿到值存起来准备用来下一步
        recomBean = new RecomBean();
        Intent intent = getIntent();
        int[] moneys = intent.getIntArrayExtra("moneys");
        int[] dates = intent.getIntArrayExtra("dates");
        recomBean.setMoneyMin(moneys[0]);
        recomBean.setMoneyMax(moneys[1]);
        recomBean.setDateMin(dates[0]);
        recomBean.setDateMax(dates[1]);

        //黄油刀
        ButterKnife.bind(this);

        //设置头部
        tx.setText(getString(R.string.recommand_title));
        back.setVisibility(View.VISIBLE);
    }


    @Override
    protected RecomMinePresenter createPresenter() {
        return null;
    }

    @Override
    public void refreshTitle(String title) {

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

    @OnClick({R.id.btn_mine_next, R.id.img_back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.btn_mine_next:
                //先确认是否登陆用户，登陆之后才可以提交按钮\
                if (ApplicationPrams.loginCallBackBean == null) {
                    //请求登陆
                    EventBus.getDefault().post(false);
                    return;
                }

                //先提交身份注册按钮
                break;
        }
    }
}
