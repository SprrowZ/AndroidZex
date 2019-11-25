package com.rye.catcher.activity.fragment;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.webkit.WebView;

import android.widget.TextView;

import com.rye.catcher.R;


/**
 * Created by ZZG on 2017/10/13.
 */

public class HttpT1Fragment extends Fragment {
    private WebView webView;
    private View view1;
    String url;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          View view =inflater.inflate(R.layout.http_test1,container,false);
             this.view1=view;
        TextView text;
        text=view1.findViewById(R.id.text);
        text.setText("faker...");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        TextView text;
        text=view1.findViewById(R.id.text);
        text.setText("faker...");
  webView=view1.findViewById(R.id.webView);
         url="https://www.baidu.com";
                webView.loadUrl(url);
        Log.i("info", "onActivityCreated: ..............");
        super.onActivityCreated(savedInstanceState);
    }
}
