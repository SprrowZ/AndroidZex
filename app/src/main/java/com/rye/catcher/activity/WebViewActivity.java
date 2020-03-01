package com.rye.catcher.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.utils.NetworkUtils;

/**
 * Created by Zzg on 2018/6/26.
 */
public class WebViewActivity extends Activity {
    private WebView mWebView;
    private String URL;
    private LinearLayout parent_view;
    private TextView titleView;
    private TextView right_text;
    private ImageView back;
    private LinearLayout parent;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_activity);
        parent=findViewById(R.id.parent);
        mWebView=findViewById(R.id.webView);
        titleView=findViewById(R.id.title);
        right_text=findViewById(R.id.right_text);
        back=findViewById(R.id.back);
        right_text.setVisibility(View.VISIBLE);
        URL="https://www.imooc.com";
        WebSettings settings=mWebView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);//缩放至屏幕大小
        settings.setDomStorageEnabled(true);//设置适应Html5 //重点是这个设置
       // settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);//优先使用缓存，存在个问题：
        //如果用户在没网的情况下，按理说应该不加载出页面，但是优先使用缓存的话，就会显示出来，这是不好的
        if (NetworkUtils.isConnected(this)){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        }

        mWebView.loadUrl(URL);//加载网页
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    view.loadUrl(request.getUrl().toString());
                }
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);//开始时，可以调用loading页面
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);//页面加载完成
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                 view.loadUrl("file:///android_asset/error.html");//加载错误网页,其中file:///android_asset/，这个是固定的
//                String data = "Page NO FOUND！";
//                viewRx.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.i("zzg", "onReceivedSslError: ....loading fail");
                super.onReceivedSslError(view, handler, error);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onReceivedTitle(WebView view, String title) {
                titleView.setText(title);
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                String progress="";
                if (newProgress<100){
                    right_text.setText(newProgress+"%");
                }else {
                    right_text.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);//获取进度
            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){//按返回键不会退回到activity中，而是后退网页
            mWebView.goBack();
            return  true;
        }else {
            finish();//退不了就返回activity
        }
      return  false;
    }
    //别忘了销毁WebView！！
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (parent!=null){
            parent.removeView(mWebView);//先从父布局移除
        }
        mWebView.removeAllViews();
        mWebView.destroy();//顺序不要搞混
    }
}
