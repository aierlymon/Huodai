package com.example.huodai;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baselib.utils.MyLog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import retrofit2.http.FormUrlEncoded;

/**
 * createBy ${huanghao}
 * on 2019/7/20
 */
public class WebActivity extends AppCompatActivity {
    WebView webView;
    String url;
    private boolean isLoad;
    private ProgressBar mprogressBar;
    private RelativeLayout relativeLayout;
    private RelativeLayout backLayout;
    private ImageView mBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        MyLog.i("web url: " + url);
        webView = ((WebView) findViewById(R.id.webview));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        mprogressBar = ((ProgressBar) findViewById(R.id.progress_horizontal));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    // 网页加载完成
                    webSettings.setBlockNetworkImage(false);
                    mprogressBar.setVisibility(View.GONE);
                } else {
                    // 网页加载中
                    mprogressBar.setProgress(newProgress);

                }

            }
        });

        mBack = ((ImageView) findViewById(R.id.img_back));
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        MyLog.i("执行到了onStart");
        if (!isLoad)
            webView.loadUrl(url);
        isLoad = true;
    }
}
