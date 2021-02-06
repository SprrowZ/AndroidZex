package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import com.catcher.zzsdk.okhttp.client.OkClient;
import com.catcher.zzsdk.okhttp.response.JsonCallback;

import com.rye.base.utils.ToastHelper;
import com.rye.catcher.R;
import butterknife.OnClick;

/**
 * Created by zzg on 2017/10/12.
 */

public class ZTActivity extends AppCompatActivity {
    private static final String url = "http://apis.juhe.cn/ip/ipNew?ip=112.112.11.11&key=8aa56ea9bf3d4dfd2ce7e678061179b5";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AsyncLayoutInflater(ZTActivity.this).inflate(R.layout.activity_zt, null,
                (view, resid, parent) -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setContentView(view);
                    initEvent();
                });
    }


    public void initEvent() {

    }


    @OnClick({R.id.aa, R.id.bb, R.id.cc, R.id.bili, R.id.ee, R.id.ff, R.id.gg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aa:
                testBuilder();
                break;
            case R.id.bb:
                startActivity(new Intent(ZTActivity.this, CartoonsDoActivity.class));
                break;
            case R.id.cc:
                startActivity(new Intent(ZTActivity.this, WebViewActivity.class));
                break;
            case R.id.bili:
                //     BILIActivity.start(this);
                break;
            case R.id.ee:
                startActivity(new Intent(ZTActivity.this, SocketClientActivity.class));
                break;
            case R.id.ff:
                startActivity(new Intent(ZTActivity.this, CharactersDoActivity.class));
                break;
            case R.id.gg:
                ToastHelper.showToastShort(this,"呕吼，已经干掉了~");
                break;

        }
    }


    private void testBuilder() {
        new Thread(() -> OkClient.Companion
                .get()
                .url(url)
                .build()
                .execute(new JsonCallback<BeanZ>() {
                    @Override
                    public void onSuccess(BeanZ result) {
                        Log.i("zzz", "result:" + result.toString());
                    }

                    @Override
                    public void onError(String error) {

                    }
                })).start();


    }


}

class BeanZ {
    public Data result;
    public String resultcode;
    public String reason;
    public String error_code;

    class Data {
        String Country;
        String Province;
        String City;
        String Isp;
    }
}