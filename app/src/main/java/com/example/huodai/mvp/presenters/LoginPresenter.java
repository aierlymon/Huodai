package com.example.huodai.mvp.presenters;

import com.example.baselib.http.HttpMethod;
import com.example.baselib.http.myrxsubcribe.MySubscriber;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.Utils;
import com.example.huodai.ApplicationPrams;
import com.example.huodai.mvp.view.LoginViewimpl;
import com.example.model.bean.LoginCallBackBean;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LoginPresenter extends BasePresenter<LoginViewimpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
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
                    .subscribe(new MySubscriber<String>(this) {
                        @Override
                        public void onSuccess(String s) {
                            MyLog.i("获取消息: " + s);
                            if ("ok".equals(s)) {
                                //拿去到了验证吗
                                MyLog.i("通知成功");
                            } else {
                                getView().showError("获取验证码失败");
                            }
                        }

                        @Override
                        public void onFail(Throwable e) {
                            MyLog.i("获取消息: onFail" + e.getMessage());
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
                .subscribe(new MySubscriber<LoginCallBackBean>(this) {
                    @Override
                    public void onSuccess(LoginCallBackBean loginCallBackBean) {
                        ApplicationPrams.loginCallBackBean = loginCallBackBean;
                        getView().loginSuccess();
                    }

                    @Override
                    public void onFail(Throwable e) {
                        MyLog.i("我失败了");
                    }

                    @Override
                    public void onCompleted() {

                    }
                });
    }
}
