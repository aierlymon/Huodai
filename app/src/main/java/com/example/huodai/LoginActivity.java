package com.example.huodai;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.baselib.base.BaseMvpActivity;
import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.utils.CustomToast;
import com.example.baselib.utils.Utils;
import com.example.huodai.mvp.presenters.LoginPresenter;
import com.example.huodai.mvp.view.LoginViewimpl;
import com.google.gson.annotations.Until;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseMvpActivity<LoginViewimpl, LoginPresenter> implements LoginViewimpl {

    @BindView(R.id.img_back)
    ImageView backIcon;

    @BindView(R.id.tx_title)
    TextView titleName;

    @BindView(R.id.btn_login)
    Button btnLogin;

    @BindView(R.id.edit_num)
    EditText editNumber;

    @BindView(R.id.check)
    TextView txCheck;

    @BindView(R.id.edit_check)
    EditText editCheck;




    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        backIcon.setVisibility(View.VISIBLE);
        Utils.setEditTextHintSize(editCheck,getString(R.string.chek_num),15);
        Utils.setEditTextHintSize(editNumber,getString(R.string.phone_num),15);
        titleName.setText(getResources().getString(R.string.logintxt));
    }

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {
        CustomToast.showToast(getApplicationContext(), msg, 2000);
    }

    @OnClick({R.id.btn_login, R.id.img_back, R.id.check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if(!NetWorkStateBroadcast.isOnline.get()){
                    showError(getString(R.string.nonet));
                    break;
                }
                if (!mPresenter.checkTelPhoneNumber(editNumber.getText().toString()) || TextUtils.isEmpty(editCheck.getText().toString())) {
                    showError(getResources().getString(R.string.numberorcheck_error));
                    break;
                }
                if (TextUtils.isEmpty(editCheck.getText().toString())) {
                    showError("请输入验证码");
                    break;
                }
                mPresenter.requeseLogin(editNumber.getText().toString(), editCheck.getText().toString());
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.check:
                //发起验证码请求
                mPresenter.checkVerificationCode(editNumber.getText().toString());
                break;
        }
    }

    private CountDownTimer timer;


    @Override
    public void startTime() {
        new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                txCheck.setText("还剩" + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                txCheck.setText(getResources().getString(R.string.getcheck));
            }
        }.start();
    }

    @Override
    public void loginSuccess() {
        EventBus.getDefault().post(true);
        //跳转回到
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
