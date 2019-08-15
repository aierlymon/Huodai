package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.myapplication.App;
import com.example.model.bean.HttpResult;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.Utils;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.mvp.view.LoginViewimpl;
import com.example.model.bean.LoginCallBackBean;
import com.example.model.bean.VerifyCodeBean;
import com.google.gson.JsonObject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginViewimpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {
        MyLog.i("!!!msg: " + msg);
        getView().showError("连接错误: " + msg);
    }

    public boolean checkTelPhoneNumber(String number) {
        number.replaceAll(" ", "");
        return Utils.isPhoneNumber(number);
    }

    public void checkVerificationCode(String number) {
        if (checkTelPhoneNumber(number)) {
            //发起请求
            //让验证码的textView进入倒计时更新
            startTime();
            HttpMethod.getInstance().getVerificationCode(number)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MySubscriber<HttpResult<VerifyCodeBean>>(this) {
                        @Override
                        public void onSuccess(HttpResult<VerifyCodeBean> httpResult) {
                            if (httpResult.getStatusCode() == 200) {
                                //拿去到了验证吗
                            } else {
                                showError(httpResult.getMsg() + ":" + httpResult.getStatusCode());
                            }
                        }

                        @Override
                        public void onFail(Throwable e) {
                            showError(e.getMessage());
                        }

                        @Override
                        public void onCompleted() {

                        }
                    });
        } else {
            getView().showError("手机号码输入不正确");
        }
    }

    private void startTime() {
        getView().startTime();
    }

    public void requeseLogin(String number, String code) {
        number = number.replaceAll(" ", "");
        code = code.replaceAll(" ", "");
        MyLog.i("电话:  " + number + " 验证码: " + code);
        HttpMethod.getInstance().requestLogin(number, code)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MySubscriber<HttpResult<LoginCallBackBean>>(this) {
                    @Override
                    public void onSuccess(HttpResult<LoginCallBackBean> httpResult) {
                        if (httpResult.getStatusCode() == 200) {
                            //拿去到了验证吗
                            ApplicationPrams.loginCallBackBean = httpResult.getData().getUser();
                            App.token = httpResult.getData().getToken();
                            getView().loginSuccess();
                        } else {
                            showError(httpResult.getMsg() + ":" + httpResult.getStatusCode());
                        }

                    }

                    @Override
                    public void onFail(Throwable e) {
                        MyLog.i("我失败了: " + e.getMessage());
                        showError(e.getMessage());
                        getView().showError("验证码不正确");
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
