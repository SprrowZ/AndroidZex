package com.rye.catcher.activity.fragment.orr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.project.dao.ServiceContext;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.FileUtils;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
 *
 */
public class OkhttpFragment extends BaseFragment {
    private static  final String TAG="OkhttpFragment";
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.iv)
    ImageView iv;

    private  Unbinder unbinder;
    private View view;

    private String URL = "http://192.168.43.231:8088/OkhttpServer/";

    //拿到对象
    private final OkHttpClient okHttpClient = OkHttpUtil.getInstance().getClient();

    //向服务器传递字符串
    private static final  String  jsonString="{\"username\":\"zzg\",\"job\":\"coder\"}";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_okhttp,container,false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }


    /**
     * 同步GET请求execute
     * @param view
     * @throws IOException
     */
    public void doGet(View view)  {
        new Thread(()->{
            Request request = new Request.Builder()
                    .get()
                    .url(URL + "login?userName=zzg&password=1234")
                    .addHeader("S-ZZG-TOKEN", ServiceContext.getUUID())
                    .build();
            Response response= null;
            try {
                response = okHttpClient.newCall(request).execute();
                if (!response.isSuccessful()){
                    Log.e(TAG, "doGet: "+response.code());
                    Response finalResponse = response;
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("连接服务器失败!错误码：--"+String.valueOf(finalResponse.code()));
                    });
                }else{
                    String resp=response.body().string();
                    getActivity().runOnUiThread(()->{
                        tv1.setText(resp);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 异步Post请求
     * @param view
     */
    public void doPost(View view) {
        new Thread(()->{
            RequestBody requestBody = new FormBody.Builder()
                    .add("userName", "空山鸟语")
                    .add("password", String.valueOf(12132))
                    .build();
            Request request = new Request.Builder()
                    .url(URL + "login")
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //e.getMessage获得错误信息，e.printStackTrace打印出错误路径
                    Log.i(TAG, "onFailure: ....." + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String s = response.body().string();
                    Log.i(TAG, "onResponse: " + s);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tv1.setText("服务器返回数据："+s);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            });
        }).start();

    }

    /**
     * 上传json字符串
     * @param view
     */
    public void doPostString(View view) {
        new  Thread(()->{
            RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;chaset=utf-8"), jsonString);
            Request request = new Request.Builder()
                    .url(URL + "postString")
                    .post(requestBody)
                    .build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    //e.getMessage获得错误信息，e.printStackTrace打印出错误路径
                    Log.i(TAG, "onFailure: ....." + e.getMessage());
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                        }
                    });

                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, final Response response) throws IOException {
                    final String s = response.body().string();
                    Log.i(TAG, "onResponse: " + s);
                    getActivity().runOnUiThread(new Runnable() {
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
        }).start();

    }

    /**
     * 上传文件
     * @param view
     */
    public void doPostFile(View view) {
        //本地必须得有这个图片
        File file = new File(SDHelper.getImageFolder(), "portrait.jpg");
        if (!file.exists()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_SHORT).show();
                }
            });
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = new Request.Builder().url(URL + "postFile").post(requestBody).build();//拿到号码
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
    public void doDownLoadImg(View view) {
        Request request = new Request.Builder().url(URL + "files/test.jpg").build();//电脑上这个路径下要有test.jpg
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //读出来图片后显示出来
                InputStream is = response.body().byteStream();
                final Bitmap bitmap = BitmapFactory.decodeStream(is);
                getActivity().runOnUiThread(()->{
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
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(()->{
                    Toast.makeText(getActivity(), "下载成功", Toast.LENGTH_SHORT).show();

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
               getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "连接服务器失败", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }









    @OnClick({R.id.btn1, R.id.btn2, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                doGet(view);
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
    /*******************************************写着玩************************************************/
    private void doGet(){
        Request request=new Request.Builder()
                .addHeader("password","hello")
                .url(URL)
                .build();
        try {
            Response response=okHttpClient.newCall(request).execute();
            if (!response.isSuccessful()){
                throw new IOException("Unexpected code" +response.code());
            }else{
                Log.i(TAG, "doGet: "+response.body().string());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

}
