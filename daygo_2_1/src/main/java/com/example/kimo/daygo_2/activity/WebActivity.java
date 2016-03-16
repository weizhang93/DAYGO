package com.example.kimo.daygo_2.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.example.kimo.daygo_2.DayGoCache;
import com.example.kimo.daygo_2.R;
import com.example.kimo.daygo_2.util.BrowserUtils;
import com.example.kimo.daygo_2.util.ShareUtils;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 记得打开网络权限
 */
public class WebActivity extends BaseActivity {

    private FrameLayout loadingView;
    private WebView webView;
    private SwipyRefreshLayout swipyRefreshLayout;

    private String url;
    private String desc;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        DayGoCache.isBackFromWeborImage = true;
        url = getIntent().getStringExtra("url");
        desc = getIntent().getStringExtra("desc");

//        url = "http://www.baidu.com";
//        desc = "baidu";
        initToolbar();
        initRefreshLayout();
        initWebView();
    }

    //

    /**
     * 初始化webWiew
     */
    private void initWebView() {
        loadingView = (FrameLayout) findViewById(R.id.loadingview);
        webView = (WebView) findViewById(R.id.webview);
        //当为Chrome浏览器时?
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    loadingView.setVisibility(View.GONE);
                    url = webView.getUrl();
                }else{
                    loadingView.setVisibility(View.VISIBLE);
                }
            }
        });

        //为一般浏览器时?
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.loadUrl(url);
    }

    private void initRefreshLayout() {
        swipyRefreshLayout = (SwipyRefreshLayout) findViewById(R.id.swipyrefreshlayout);
        swipyRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_green_light
        );
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                Observable.timer(2, TimeUnit.SECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Action1<Long>() {
                            @Override
                            public void call(Long aLong) {
                                swipyRefreshLayout.setRefreshing(false);
                            }
                        });
                loadingView.setVisibility(View.GONE);
                webView.loadUrl(url);
            }
        });
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //并不好用，它只会返回到最初的Activity状态
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(desc);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_share:
                String shareMessage = webView.getTitle()+":"+webView.getUrl()+"--daygo";
                ShareUtils.shareText(this,shareMessage);
                break;
            case R.id.action_refresh:
                webView.reload();
                break;
            case R.id.action_copy:
                BrowserUtils.copyToClipBoart(this,webView.getUrl());
                break;
            case R.id.action_open_in_browser:
                BrowserUtils.openInBrowser(this,webView.getUrl());
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //按下返回按钮

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    webView.goBack();
                }else {
                    finish();
                }
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
