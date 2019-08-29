package com.example.baselib.http;

import com.example.baselib.http.interrceptorebean.LoggingInterceptor;
import com.example.baselib.http.interrceptorebean.RetryInterceptor;
import com.example.model.bean.EditUserBean;
import com.example.model.bean.HistoryBean;
import com.example.model.bean.HttpResult;
import com.example.model.bean.LoginCallBackBean;
import com.example.model.bean.NewHomeBannerBean;
import com.example.model.bean.NewHomeBodyBean;
import com.example.model.bean.NewHomeMenuBean;
import com.example.model.bean.NewRecordsBean;
import com.example.model.bean.NewReservedUvBean;
import com.example.model.bean.RecommandStateBean;
import com.example.model.bean.SplashBean;
import com.example.model.bean.UpdateBean;
import com.example.model.bean.VerifyCodeBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.baselib.http.HttpConstant.DEFAULT_TIME_OUT;

/**
 * createBy ${huanghao}
 * on 2019/6/28
 * data 以后所有请求的调用方法写在这里,然后所有请求路径和方式放到MovieService,由一个MovieService同意调用
 */
public class UpdateHttpMethod {

    public static Retrofit mRetrofit;
    //以后所有请求的调用方法写在这里,然后所有请求路径和方式放到MovieService,由一个MovieService同意调用
    private MovieService mMovieService;

    private static UpdateHttpMethod httpMethods;

    //获取单例
    public static UpdateHttpMethod getInstance() {
        if (httpMethods == null) {
            httpMethods = new UpdateHttpMethod();
        }
        return httpMethods;
    }


    public UpdateHttpMethod() {
        if (mRetrofit == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//连接 超时时间
            builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//写操作 超时时间
            builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);//读操作 超时时间


            File cacheFile = new File(HttpConstant.context.getCacheDir(), HttpConstant.cacheFileName);//缓存文件路径
            // if(!cacheFile.exists())cacheFile.mkdirs();

            int cacheSize = 10 * 1024 * 1024;//设置缓存文件大小为10M
            Cache cache = new Cache(cacheFile, cacheSize);
            builder.cache(cache);

            // Log信息拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);


            //缓存拦截器
             builder.addInterceptor(new LoggingInterceptor());
            // 错误重连拦截器
            builder.addInterceptor(new RetryInterceptor(2, DEFAULT_TIME_OUT));
            //公共参数拦截器


         /*   if (BuildConfig.DEBUG) {
            }*/
            OkHttpClient okHttpClient = builder.build();
            mRetrofit = new Retrofit.Builder()
                    //设置基础地址
                    .baseUrl(HttpConstant.BASE_API_URL)
                    //这个把返回的数据转换为gson
                    .addConverterFactory(GsonConverterFactory.create())
                    //这个为了支持rxjva
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();

            mMovieService = mRetrofit.create(MovieService.class);
        }

    }


    //正式更新用的，但是url地址到时候自己改
    public Observable<HttpResult<UpdateBean>> checkUpdate() {
        return mMovieService.checkUpdate();
    }


}
