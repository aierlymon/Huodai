package com.example.baselib.http;


import com.example.model.bean.LoginCallBackBean;
import com.example.model.bean.NewHomeBannerBean;
import com.example.model.bean.NewHomeBodyBean;
import com.example.model.bean.NewHomeMenuBean;
import com.example.model.bean.UpdateBean;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * createBy ${huanghao}
 * on 2019/6/28
 */
public interface MovieService {

    //这个是以后也不能删除的，更新通用的！！！！！
    @GET("adat/sk/")
    Observable<UpdateBean> checkUpdate();

    //这个是以后都不能删除的，更新通用的！！！！！
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);


    //就是home页的轮播图banner数据
    @GET("banners")
    Observable<HttpResult<NewHomeBannerBean>> loadHomeBanner();

    //这个是读取home界面的menu选项卡的请求
    @GET("loanCategories")
    Observable<HttpResult<NewHomeMenuBean>> loadHomeMenu(@Query("allowClient") int index);



    //或取home页内容最多的body内容
    @GET("loanProducts")
    Observable<HttpResult<NewHomeBodyBean>> loadHomeBody(@Query("allowClient") int index);

    //或取home页内容最多的body内容
    @GET("loanProducts")
    Observable<HttpResult<NewHomeBodyBean>> loadHomeBody(@Query("allowClient") int index,@Query("categoryId") int id);

    //或取home页内容最多的body内容(联合查询,最小金额小于)
    @GET("loanProducts")
    Observable<HttpResult<NewHomeBodyBean>> loadHomeBodyLimitLlte(@Query("allowClient") int index,@Query("categoryId") int id,@Query("limitLlte") int limitLlte);

    //或取home页内容最多的body内容(联合查询,最小金额大于)
    @GET("loanProducts")
    Observable<HttpResult<NewHomeBodyBean>> loadHomeBodyLimitLgte(@Query("allowClient") int index,@Query("categoryId") int id,@Query("limitLgte") int limitLgte);



    //http://tuershiting.com/api/sendVerifyCode?phone=15914855180
    @POST("sendVerifyCode")
    Observable<HttpResult<JsonObject>> getVerificationCode(@Body RequestBody requestBody);

    @POST("quickLogin")
    Observable<HttpResult<LoginCallBackBean>> requestLogin(@Body RequestBody requestBody);

   /* @FormUrlEncoded
    @Headers({"Content-type:application/json;charset=UTF-8"})
    @POST("users/quickLogin")
    Observable<LoginCallBackBean> requestLogin(@FieldMap Map requestMap);*/

}
