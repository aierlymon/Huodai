package com.example.baselib.http;

import android.content.Context;

/**
 * createBy ${huanghao}
 * on 2019/6/28
 * data http的一些常量值
 */
public abstract class HttpConstant {
   public static int DEFAULT_TIME_OUT=8;//超时时间 单位（s）秒

    public static String BASE_URL = "http://tuershiting.com/";

    //http://tuershiting.com/api/
    public static String BASE_API_URL = BASE_URL+"api/";

    public  static Context context;

    //http://tuershiting.com/api/banners?filter[where][open]=true
}
