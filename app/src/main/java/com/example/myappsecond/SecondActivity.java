package com.example.myappsecond;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.myappsecond.fragment.HttpT1Fragment;
import com.example.myappsecond.interfaces.Api;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class SecondActivity extends BaseActivity   {
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.framelayout)
    FrameLayout framelayout;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.content1)
    TextView content1;
    private static  final  String BASE_URL="https://api.github.com/";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab04_second_activity);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        setBarTitle("進撃の巨人");
    }

    private void addView() {
        HttpT1Fragment fragment = new HttpT1Fragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.framelayout, fragment);
        transaction.commit();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                addView();
                break;
            case R.id.btn2:
                retrofitGet1();
                break;
            case R.id.btn3:
                retrofitGet2();
                break;
            case R.id.btn4:
                retrofitGet3();
                break;
            case R.id.btn5:
                retrofitGet4();
                break;
            case R.id.btn6:
                retrofitGet5();
                break;
        }
    }

    private void retrofitGet5() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api=retrofit.create(Api.class);
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

            }
        }).start();
    }

    private void retrofitGet4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api infoService=retrofit2.create(Api.class);
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
            }
        }).start();
    }

    private void retrofitGet3() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api infoService=retrofit2.create(Api.class);
                Call<ResponseBody> call=infoService.getMessages3("SprrowZ","AndroidZex");
                try {
                    final Response<ResponseBody> response=call.execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                content1.setText("路径三:"+"\n"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void retrofitGet2() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                //retrofit的构建也要放在thread中
                final Retrofit retrofit2=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api infoService=retrofit2.create(Api.class);
                Call<ResponseBody> call=infoService.getMessages2();
                try {
                    final Response<ResponseBody> response=call.execute();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                content1.setText("路径二:"+"\n"+response.body().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void retrofitGet1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Retrofit retrofit=new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api service=retrofit.create(Api.class);
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


            }
        }).start();

    }






















}
