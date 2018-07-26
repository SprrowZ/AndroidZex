package com.example.myappsecond.project;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/10/16.
 */

public class Http_test extends Activity {
    private WebView webView;
    private ImageView imageView;
    private Handler handler=new Handler();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.http_test1);
        webView=findViewById(R.id.webView);
        imageView=findViewById(R.id.imageView);
        HttpThread httpThread=new HttpThread(
                "http://cdn.read.html5.qq.com/image?src=3gqq&subsrc=index&q=6&r=4&imgflag=5&w=640&h=331&imageUrl=http://zxpic.gtimg.com/infonew/0/news_pics_-6964075.jpg/0",imageView,handler);
        httpThread.start();
    }
}
