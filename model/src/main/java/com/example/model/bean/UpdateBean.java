package com.example.model.bean;

/**
 * createBy ${huanghao}
 * on 2019/7/10
 */
public class UpdateBean {
    //新版本号
    private int version_code;
    //版本名
    private String version_name;
    //新app下载地址
    private String apk_url;
    //更新日志
    private String update_log;
    //配置默认更新dialog 的title
    private String update_def_dialog_title;
    //新app大小
    private String target_size;
    //是否强制更新
    private boolean constraint;
    //md5
    private String new_md5;
    //apk的名字
    private String apk_name;

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public String getApk_url() {
        return apk_url;
    }

    public void setApk_url(String apk_url) {
        this.apk_url = apk_url;
    }

    public String getUpdate_log() {
        return update_log;
    }

    public void setUpdate_log(String update_log) {
        this.update_log = update_log;
    }

    public String getUpdate_def_dialog_title() {
        return update_def_dialog_title;
    }

    public void setUpdate_def_dialog_title(String update_def_dialog_title) {
        this.update_def_dialog_title = update_def_dialog_title;
    }

    public String getTarget_size() {
        return target_size;
    }

    public void setTarget_size(String target_size) {
        this.target_size = target_size;
    }

    public boolean isConstraint() {
        return constraint;
    }

    public void setConstraint(boolean constraint) {
        this.constraint = constraint;
    }

    public String getNew_md5() {
        return new_md5;
    }

    public void setNew_md5(String new_md5) {
        this.new_md5 = new_md5;
    }

    public String getApk_name() {
        return apk_name;
    }

    public void setApk_name(String apk_name) {
        this.apk_name = apk_name;
    }
}
