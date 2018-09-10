package com.example.myappsecond.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myappsecond.BaseActivity;
import com.example.myappsecond.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by ZZG on 2018/3/21.
 */

public class OkHttpMainActivity extends BaseActivity {
    private static final String TAG = "oKHttpMain";
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv)
    ImageView iv;
    //    private String URL="https://www.imooc.com/u/5467471/courses";
    //本机IP地址
    private String URL = "http://192.168.43.197:8080/ServerToAndroid/";

    //拿到对象
    OkHttpClient okHttpClient = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.okhttpmain);
        ButterKnife.bind(this);
    }

    public void doDownLoadImg(View view) {
        Request request = new Request.Builder().url(URL + "files/test.jpg").build();//电脑上这个路径下要有test.jpg
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //读出来图片后显示出来
                InputStream is = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(is);
                runOnUiThread(()->{
                        iv.setImageBitmap(bitmap);//这里Bitmap.Options对图片进行压缩等操作，二次取样
                });
            }
        });
    }

    public void doDownLoad(View view) {
        final Request request = new Request.Builder().url(URL + "files/test.jpg").build();//电脑上这个路径下要有test.jpg
        //输出路径
        Log.i(TAG, "doDownLoad: " + URL + "files/test.jpg");
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(()->{
                        Toast.makeText(OkHttpMainActivity.this, "下载成功", Toast.LENGTH_SHORT).show();

                });
                //******************追踪进度*********************
                long total = request.body().contentLength();//文件的大小
                long sum = 0L;

                InputStream is = response.body().byteStream();
                int len = 0;//每次读多少字节
                File file = new File(Environment.getExternalStorageDirectory(), "download.jpg");//这个名字本地不能有
                byte[] buf = new byte[128];
                FileOutputStream fos = new FileOutputStream(file);
                while ((len = is.read(buf)) != -1) {
                    fos.write(buf, 0, len);
                    //输出进度
                    sum += len;
                    Log.e(TAG, sum + "/" + total);//输出下载进度
                }
                fos.flush();
                fos.close();
                is.close();
            }
        });
    }

    public void doUpLoad(View view) {
        File file = new File(Environment.getExternalStorageDirectory(), "test2.jpg");
        MultipartBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("name", "zzg")
                .addFormDataPart("password", "zzz")
                .addFormDataPart("mPhoto", "test2.jpg", RequestBody.create(//mPhoto是Key，服务器端通过这个取内容
                        //上传成功后，文件名还是为yy.jpg
                        MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder().url(URL + "uploadInfo").post(multipartBody).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(TAG, "onFailure: ....." + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    public void doPostFile(View view) {
        //本地必须得有这个图片呀，呀呀呀，嘤嘤婴
        File file = new File(Environment.getExternalStorageDirectory(), "test.jpg");
        if (!file.exists()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(OkHttpMainActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
                }
            });
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = new Request.Builder().url(URL + "postFile").post(requestBody).build();//拿到号码
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    public void doPostString(View view) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"), "fuck.......");
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.url(URL + "postString").post(requestBody).build();
        //封装Request成Call
        Call call = okHttpClient.newCall(request);
        //执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.getMessage获得错误信息，e.printStackTrace打印出错误路径
                Log.i(TAG, "onFailure: ....." + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String s = response.body().string();
                Log.i(TAG, "onResponse: " + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv1.setText(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void doPost(View view) {
        RequestBody requestBody = new FormBody.Builder()
                .add("name", "空山鸟语")
                .add("password", String.valueOf(12132))
                .build();
        Request.Builder requestBuilder = new Request.Builder();
        Request request = requestBuilder.url(URL + "login").post(requestBody).build();
        //封装Request成Call
        Call call = okHttpClient.newCall(request);
        //执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.getMessage获得错误信息，e.printStackTrace打印出错误路径
                Log.i(TAG, "onFailure: ....." + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String s = response.body().string();
                Log.i(TAG, "onResponse: " + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv1.setText(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    public void doGet(View view) throws IOException {


        //构造Request
        Request.Builder builder = new Request.Builder();
        Request request = builder
                .get()
                .url(URL + "login?name=zzg&password=1234")
                .build();
        //封装Request成Call
        Call call = okHttpClient.newCall(request);
        //执行Call
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //e.getMessage获得错误信息，e.printStackTrace打印出错误路径
                Log.i(TAG, "onFailure: ....." + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(OkHttpMainActivity.this, "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String s = response.body().string();
                Log.i(TAG, "onResponse: " + s);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            tv1.setText(s);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                try {
                    doGet(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn2:
                doPost(view);
                break;
            case R.id.btn5:
                doPostString(view);
                break;
            case R.id.btn6:
                doPostFile(view);
                break;
            case R.id.btn7:
                doUpLoad(view);
                break;
            case R.id.btn8:
                doDownLoad(view);
                break;
            case R.id.btn9:
                doDownLoadImg(view);
                break;
            case R.id.btn10:
                break;
        }
    }
}
