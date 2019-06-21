package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.catcher.zzsdk.okhttp.client.OkClient;
import com.catcher.zzsdk.okhttp.others.OkHttpException;
import com.catcher.zzsdk.okhttp.others.DisposeDataHandle;
import com.catcher.zzsdk.okhttp.others.DisposeDataListener;
import com.catcher.zzsdk.okhttp.request.CommonRequest;
import com.catcher.zzsdk.okhttp.response.JsonCallback;
import com.catcher.zzsdk.okhttp.response.NormalCallback;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by zzg on 2017/10/12.
 */

public class ZTActivity extends BaseActivity {
    private String url="https://api.github.com/";
    @BindView(R.id.aa)
    Button aa;
    @BindView(R.id.bb)
    Button bb;
    @BindView(R.id.cc)
    Button cc;
    @BindView(R.id.dd)
    Button dd;
    @BindView(R.id.ee)
    Button ee;
    @BindView(R.id.ff)
    Button ff;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zt);
        ButterKnife.bind(this);
    }
    @OnClick({ R.id.aa, R.id.bb, R.id.cc, R.id.dd, R.id.ee, R.id.ff})
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
            case R.id.dd:
                startActivity(new Intent(ZTActivity.this, CartoonsListActivity.class));
                break;
            case R.id.ee:
                startActivity(new Intent(ZTActivity.this, SocketClientActivity.class));
                break;
            case R.id.ff:
                startActivity(new Intent(ZTActivity.this, CharactersDoActivity.class));
                break;
        }
    }

    private void testOKClient(){
        OkClient.Companion.sendRequest(CommonRequest.Companion.createGetRequest(url,null),
                new NormalCallback(new DisposeDataHandle(new DisposeDataListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        Log.i("zzz", "onSuccess: "+s);
                    }

                    @Override
                    public void onFailure(OkHttpException e) {
                        Log.i("zzz", "onFailure: "+e.toString());
                    }
                })));
    }
    private void testBuilder(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkClient.Companion
                        .get()
                        .url(url)
                        .build()
                        .execute(new JsonCallback<String>() {
                            @Override
                            public void onSuccess(String result) {
                                Log.i("zzz","result:"+result);
                            }
                            @Override
                            public void onError(String error) {

                            }
                        });
            }
        }).start();



    }
}
