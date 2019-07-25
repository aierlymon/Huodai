package com.example.huodai;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.baselib.broadcast.NetWorkStateBroadcast;
import com.example.baselib.utils.MyLog;
import com.example.baselib.utils.StatusBarUtil;

/**
 * createBy ${huanghao}
 * on 2019/7/20
 */
//这里没什么其他逻辑，就这样子写了，哈哈哈哈！！！
public class WebActivity extends AppCompatActivity {
    WebView webView;
    String url;
    private boolean isLoad;
    private ProgressBar mprogressBar;
    private ImageView mBack;
    private TextView txTitle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setStatusBarColor(this, getResources().getColor(R.color.my_login_color));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        String tag = intent.getStringExtra("tag");

        //设置标题名字
        txTitle = ((TextView) findViewById(R.id.tx_title));
        if (!TextUtils.isEmpty(tag)) {
            txTitle.setText(tag);
        }else{
            txTitle.setVisibility(View.GONE);
        }

        MyLog.i("web url: " + url + "  tag: " + tag);

        //配置webview
        webView = ((WebView) findViewById(R.id.webview));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setBlockNetworkImage(true);
        if (NetWorkStateBroadcast.isOnline.get()) {
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);//设置渲染的优先级
        String cacheDirPath = getFilesDir().getAbsolutePath() + "/webcache";
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        //更新读条
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
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (request.getUrl().toString().contains("sina.cn")){
                        view.loadUrl("http://ask.csdn.net/questions/178242");
                        return true;
                    }
                }*/

                return false;
            }

        });

        //返回键
        mBack = ((ImageView) findViewById(R.id.img_back));
        mBack.setVisibility(View.VISIBLE);
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
        //这个是之前好像遇到一直加载，一直加载的情况，加上去的，以后留待有缘人看看是否需要解除
        if (!isLoad)
            webView.loadUrl(url);
        isLoad = true;
    }
}
