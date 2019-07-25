package com.example.baselib.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.example.baselib.R;
import com.example.baselib.widget.PermissionDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * createBy ${huanghao}
 * on 2019/7/3
 */
public class RxPermissionUtil {


    private Map<String, String> permissionMap;

    private RxPermissionUtil() {
        permissionMap = new HashMap<>();
        permissionMap.put("android.permission.REQUEST_INSTALL_PACKAGES", "安装应用");
        permissionMap.put("android.permission.READ_EXTERNAL_STORAGE", "读取SD卡");
        permissionMap.put("android.permission.WRITE_EXTERNAL_STORAGE", "写入SD卡");
    }


    private static RxPermissionUtil rxPermissionUtil;


    //肯定在主线程，不用同步
    public static RxPermissionUtil getInstance() {
        if (rxPermissionUtil == null) {
            rxPermissionUtil = new RxPermissionUtil();
        }
        return rxPermissionUtil;
    }

    private StringBuilder builder = new StringBuilder();
    ;
    private int leng = 0;

    private PermissionDialog build;

    public void permission(Activity activity, String[] list) {
        builder.delete(0, builder.length());
        RxPermissions rxPermissions = new RxPermissions((FragmentActivity) activity);
        rxPermissions.requestEach(list).subscribe(permission -> {

            MyLog.i("leng: " + leng);
            if (permission.granted) {
                MyLog.i("获取了" + permission.name + "权限");
            } else if (permission.shouldShowRequestPermissionRationale) {
                ++leng;
                //填写弹窗+权限名字
                if (leng == list.length) {
                    builder.append("[" + permissionMap.get(permission.name) + "]权限");
                } else {
                    builder.append("[" + permissionMap.get(permission.name) + "]-");
                }
                MyLog.i("应该需要获取" + permission.name + "权限");
            } else {
            }
            //判断是否最后一个权限请求，是的话就弹出权限提示

            if (leng != 0 && leng == list.length) {
                if (!TextUtils.isEmpty(builder.toString())) {
                    build = PermissionDialog.Builder(activity)
                            .setTitle(activity.getString(R.string.power_ps))
                            .setMessage("需要获取" + builder.toString())
                            .setOnConfirmClickListener("去设置", view -> {
                                //跳转app权限设置
                                Intent localIntent = new Intent();
                                localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                if (Build.VERSION.SDK_INT >= 9) {
                                    localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                                    localIntent.setData(Uri.fromParts("package", activity.getPackageName(), null));
                                } else if (Build.VERSION.SDK_INT <= 8) {
                                    localIntent.setAction(Intent.ACTION_VIEW);
                                    localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                                    localIntent.putExtra("com.android.settings.ApplicationPkgName", activity.getPackageName());
                                }
                                build.dismiss();
                                activity.startActivity(localIntent);
                            }).build();
                    build.setOnKeyListener((dialog, keyCode, event) -> {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            return true;
                        } else {
                            return false; //默认返回 false
                        }
                    });
                    build.shown();

                }
            }
        });


    }
}
