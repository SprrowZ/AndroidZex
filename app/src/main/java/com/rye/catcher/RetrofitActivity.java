package com.rye.catcher;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.rye.base.BaseActivity;
import com.rye.catcher.activity.fragment.HttpT1Fragment;
import com.rye.catcher.base.interfaces.GithubApi;
import com.rye.base.common.Constant;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ZZG on 2017/10/12.
 */

public class RetrofitActivity extends BaseActivity {
    @BindView(R.id.alpha)
    Button btn1;
    @BindView(R.id.scale)
    Button btn2;
    @BindView(R.id.translate)
    Button btn3;
    @BindView(R.id.rotate)
    Button btn4;
    @BindView(R.id.animation_set_one)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content1)
    TextView content1;
    @Override
    public int getLayoutId() {
        return R.layout.tab04_second_activity;
    }

    @Override
    public void initEvent() {

    }


    private void addView() {
        HttpT1Fragment fragment = new HttpT1Fragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }

    @OnClick({R.id.alpha, R.id.scale, R.id.translate, R.id.rotate, R.id.animation_set_one, R.id.btn6,R.id.btn7})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alpha:
                addView();
                break;
            case R.id.scale:
                retrofitGet1();
                break;
            case R.id.translate:
                retrofitGet2();
                break;
            case R.id.rotate:
                retrofitGet3();
                break;
            case R.id.animation_set_one:
                retrofitGet4();
                break;
            case R.id.btn6:
                retrofitGet5();
                break;
            case R.id.btn7:
                retrofitPost1();
                break;
        }
    }

    /**
     * 最简单的Post请求
     */
    private void retrofitPost1() {
        new Thread(()->{
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(Constant.GITHUB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GithubApi githubApi=retrofit.create(GithubApi.class);
            Call<ResponseBody> call= githubApi.getPostMessage1(Constant.GITHUB_NAME,"0517");
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        String result=response.body().string();
                        Log.i("retrofitPost1", "onResponse:success===>>> "+result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("retrofitPost1", "onFailure:failure ");
                }
            });
        }).start();
    }

    /**
     * 比较复杂的Get请求
     */
    private void retrofitGet5() {
        new Thread(()-> {
            Retrofit retrofit=new Retrofit.Builder()
                    .baseUrl(Constant.GITHUB_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            GithubApi api=retrofit.create(GithubApi.class);
            //QueryMap传参
            HashMap<String,Integer> params=new HashMap<>();
            params.put("page",1);
            params.put("per_page",3);
            Call<ResponseBody> call=api.getMessages5("SprrowZ",params);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        content1.setText("QueryMap:"+"\n"+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }).start();
    }

    private void retrofitGet4() {
        new Thread(()->{
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(Constant.GITHUB_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GithubApi infoService=retrofit2.create(GithubApi.class);
                Call<ResponseBody> call=infoService.getMessages4("SprrowZ",1,2);
                call.enqueue(new Callback<ResponseBody>() {//异步请求
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try {
                            content1.setText("路径带参:"+"\n"+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

        }).start();
    }

    private void retrofitGet3() {
        new Thread(()->{
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(Constant.GITHUB_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GithubApi infoService=retrofit2.create(GithubApi.class);
                Call<ResponseBody> call=infoService.getMessages3("SprrowZ","AndroidZex");
                try {
                    final Response<ResponseBody> response=call.execute();
                    runOnUiThread(()->{
                            try {
                                content1.setText("路径三:"+"\n"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }).start();
    }

    private void retrofitGet2() {

        new Thread(()->{
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(Constant.GITHUB_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GithubApi infoService=retrofit2.create(GithubApi.class);
                Call<ResponseBody> call=infoService.getMessages2();
                try {
                    final Response<ResponseBody> response=call.execute();
                    runOnUiThread(()->{
                            try {
                                content1.setText("路径二:"+"\n"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
        }).start();

    }

    private void retrofitGet1() {
        new Thread(()->{
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(Constant.GITHUB_BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                GithubApi service=retrofit.create(GithubApi.class);
                Call<ResponseBody> call=service.getMessages();
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        System.out.println(response.body().toString());
                        try {
                            content1.setText("get无参:"+"\n"+response.body().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
        }).start();

    }

}
