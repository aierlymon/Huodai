package com.example.huodai.mvp.presenters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.baselib.http.listener.JsDownloadListener;
import com.example.baselib.mvp.BasePresenter;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.UpdateUtil;
import com.example.huodai.mvp.view.MainViewImpl;
import com.example.model.bean.UpdateBean;

public class MainPrsenter extends BasePresenter<MainViewImpl> {
    @Override
    protected boolean isUseEventBus() {
        return false;
    }

    @Override
    public void showError(String msg) {
        getView().showError("连接错误: " + msg);
    }

    private UpdateUtil updateUtil;

    public void checkUpdate(Activity context){
        updateUtil = new UpdateUtil(context, new JsDownloadListener() {
            @Override
            public void onStartDownload(long length) {
                MyLog.i("我已经来到了开始下载了");
                getView().statrUpdateProgress();
            }

            @Override
            public void onProgress(int progress) {
                getView().onUpdateProgress(progress);
            }

            @Override
            public void onFail(int errorType, String errorInfo) {
                getView().onUpdateFail(errorType,errorInfo);
            }

            @Override
            public void onDownSuccess(Intent intent) {
                getView().onUpdateSuccess(intent);

            }


        });
        updateUtil.checkUpdate();


       /* UpdateBean updateBean = new UpdateBean();
        updateBean.setApk_name("huodai.apk");
        updateBean.setApk_url("http://192.168.1.103:8080/huodai.apk");
        updateBean.setVersion_code(2);
        updateBean.setNew_md5("cbbc391b297986987a5811b9f9ff8ed6");
        updateBean.setTarget_size("3M");
        updateBean.setVersion_name("2.0.0");
        updateBean.setUpdate_log("测试用例");
*/
        //updateUtil.setAppPackName("com.example.huodai");
       // updateUtil.testUpdate(context,updateBean);
    }

    public void cancelIUpdate(){
        if(updateUtil!=null){
            updateUtil.clearDisposable();
        }
    }
}
