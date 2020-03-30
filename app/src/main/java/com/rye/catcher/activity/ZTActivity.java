package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;

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
import com.dawn.zgstep.others.job1.JobOneActivity;
import com.google.gson.Gson;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zzg on 2017/10/12.
 */

public class ZTActivity extends BaseActivity {
    private String url="http://apis.juhe.cn/ip/ipNew?ip=112.112.11.11&key=8aa56ea9bf3d4dfd2ce7e678061179b5";
    @BindView(R.id.aa)
    Button aa;
    @BindView(R.id.bb)
    Button bb;
    @BindView(R.id.cc)
    Button cc;
    @BindView(R.id.bili)
    Button bili;
    @BindView(R.id.ee)
    Button ee;
    @BindView(R.id.ff)
    Button ff;
    @BindView(R.id.gg)
    Button gg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zt);
        ButterKnife.bind(this);
        initData();
    }

    private  void initData(){

    }

    @OnClick({ R.id.aa, R.id.bb, R.id.cc, R.id.bili, R.id.ee, R.id.ff,R.id.gg})
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
                BILIActivity.start(this);
                break;
            case R.id.ee:
                startActivity(new Intent(ZTActivity.this, SocketClientActivity.class));
                break;
            case R.id.ff:
                startActivity(new Intent(ZTActivity.this, CharactersDoActivity.class));
                break;
            case R.id.gg:
                startActivity(new Intent(ZTActivity.this, JobOneActivity.class));
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
                        .execute(new JsonCallback<BeanZ>() {
                            @Override
                            public void onSuccess(BeanZ result) {
                                Log.i("zzz","result:"+result.toString());
                            }

                            @Override
                            public void onError(String error) {

                            }
                        });
            }
        }).start();



    }
    private void testGson(){
        String json = "{\"nestedPojo\":{\"name\":\"dd\", \"value\":42}}";
        String gson=new Gson().fromJson(json,String.class);
        Log.i("zzz", "testGson: "+gson);
    }

}
class BeanZ{
    public Data result;
    public String resultcode;
    public String reason;
    public String error_code;
   class Data{
       String Country;
       String Province;
       String City;
       String Isp;
   }
}