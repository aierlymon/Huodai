package com.example.baselib.http;


import com.example.model.bean.HomeBannerBean;
import com.example.model.bean.HomeBodyBean;
import com.example.model.bean.HomeMenuBean;
import com.example.model.bean.TestBean;
import com.example.model.bean.UpdateBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * createBy ${huanghao}
 * on 2019/6/28
 */
public interface MovieService {

    //这个是以后也不能删除的
    @GET("adat/sk/")
    Observable<UpdateBean> checkUpdate();

    //这个是以后都不能删除的
    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);




    // http://www.weather.com.cn/adat/sk/101190201.html
    //加载天气
    @GET("adat/sk/{cityId}.html")
    Observable<TestBean> loadCityDate(@Path("cityId") String cityId);

    @GET("banners?filter[where][open]=true")
    Observable<List<HomeBannerBean>> loadHomeBanner();

    //http://tuershiting.com/api/loanCategories
    @GET("loanCategories")
    Observable<List<HomeMenuBean>> loadHomeMenu();

    @GET("loanProducts?filter={\"limit\":30, \"order\": [\"top DESC\", \"sortNum DESC\"], \"where\": {\"online\": true, \"allowClient\": {\"inq\": [0,1]}}}")
    Observable<List<HomeBodyBean>> loadHomeBody();
}
