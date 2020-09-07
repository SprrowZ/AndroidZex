package com.rye.catcher.activity.fragment.orr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.OkHttpAdapter;
import com.rye.catcher.BaseOldFragment;

import com.rye.base.utils.DeviceUtils;
import com.rye.catcher.utils.Old_ApplicationUtil;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpManager;
import com.rye.base.utils.SDHelper;
import com.rye.catcher.utils.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
public class OkhttpFragment extends BaseOldFragment {
    private static  final String TAG="OkhttpFragment";
//    @BindViewEx(R.id.tv1)
//    TextView tv1;
//    @BindViewEx(R.id.iv)
//    ImageView iv;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private OkHttpAdapter adapter;

    private String URL = "http://192.168.43.231:8088/OkhttpServer/";
    //拿到对象
    private final OkHttpClient okHttpClient = OkHttpManager.getInstance().getClient();

    //向服务器传递字符串
    private static final  String  jsonString="{\"username\":\"zzg\",\"job\":\"coder\"}";
    //假数据
    private List<String> dataList;




    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_okhttp;
    }

    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        //假数据
        fakedata();
        adapter=new OkHttpAdapter(getActivity(),dataList);
        GridLayoutManager manager=new GridLayoutManager(recyclerView.getContext(),5);

        //item之间加分割线，也可以在item上画
//        recyclerView.addItemDecoration(new SimpleItemDecoration(DensityUtil.dip2px(getActivity(),1),
//                DensityUtil.dip2px(getActivity(),1),getActivity().getColor(R.color.soft12)));
        //一个item占多少列
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if ("康娜".equals(dataList.get(position))){
                    return 2;
                }else{
                   // manager.getSpanCount();
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(manager);
        //同上分割线
       // recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        //
        adapter.setOnItemClickLister((view, pos) -> itemClick(pos));
    }

    /**
     * 子View的点击事件
     * @param pos
     */
    private void itemClick(int pos){
        switch (pos){
            case 0:
                ToastUtils.shortMsg("....");
                doGet();
                break;
            case 1:
                doPost();
                break;
            case 2:
              doPostString();
                break;
            case 3:
              doPostFile();
                break;
            case 4:
                doUpLoad();
                break;
            case 5:
                doDownLoad();
                break;
            case 6:
                doDownLoadImg();
                break;
            case 7:
                break;
        }
    }
    private void fakedata() {
        dataList=new ArrayList<>();
        dataList.add("doGet");
        dataList.add("doPost");
        dataList.add("PostString");
        dataList.add("PostFile");
        dataList.add("UploadFile");
        dataList.add("DownLoad");
        dataList.add("DownLoadImg");
        dataList.add("蕾姆");
        dataList.add("康娜");
        dataList.add("焰灵姬");
        dataList.add("女神彦");
        dataList.add("炮姐");
        dataList.add("康娜");
        dataList.add("雏田");
        dataList.add("康娜");
        dataList.add("康娜");
        dataList.add("雏田");
        dataList.add("康娜");
        dataList.add("康娜");
        dataList.add("雏田");
        dataList.add("康娜");
    }


    /**
     * 同步GET请求execute
     * @param
     * @throws IOException
     */
    public void doGet()  {
        new Thread(()->{
            Request request = new Request.Builder()
                    .get()
                    .url(URL + "login?userName=zzg&password=1234")
                    .addHeader("S-ZZG-TOKEN", DeviceUtils.getUUID())
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
                        ToastUtils.longMsg(resp);
//                        tv1.setText(resp);
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 异步Post请求
     * @param
     */
    public void doPost() {
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
                              //  tv1.setText("服务器返回数据："+s);
                                ToastUtils.longMsg(s);
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
     * @param
     */
    public void doPostString() {
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
                              //  tv1.setText(s);
                                ToastUtils.longMsg(s);
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
     * @param
     */
    public void doPostFile() {
        //本地必须得有这个图片
        File file = new File(SDHelper.getImageFolder(), "portrait.png");
        if (!file.exists()) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getActivity(), "文件不存在", Toast.LENGTH_SHORT).show();
                }
            });
        }

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        Request request = new Request.Builder()
                .url(URL + "postFile")
                .post(requestBody)
                .build();//拿到号码
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

    /**
     * 上传文件...跟上面差在可能是也传参数过去吧
     * @param
     */
    public void doUpLoad() {
        File file = new File(SDHelper.getImageFolder(), "portrait.png");
        MultipartBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("userName", "SprrowZ")
                .addFormDataPart("password", "0517")
                .addFormDataPart("mPhoto", "SprrowZ.png", RequestBody.create(//mPhoto是Key，服务器端通过这个取内容
                        //上传成功后，文件名还是为yy.jpg
                        MediaType.parse("application/octet-stream"), file))
                .build();
        Request request = new Request.Builder().url(URL + "uploadInfo").post(multipartBody).build();
       okHttpClient.newCall(request).enqueue(new Callback() {
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

    /**
     *下载文件
     * @param
     */
    public void doDownLoad() {
        final Request request = new Request.Builder().url(URL + "files/zzzz.jpg").build();//电脑上这个路径下要有test.jpg
        //输出路径
        Log.i(TAG, "doDownLoad: " + URL + "files/zzg.jpg");
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
                    Toast.makeText(getActivity(), "下载成功!已保存到"+SDHelper.getImageFolder()+"目录下！",
                            Toast.LENGTH_SHORT).show();
                });
                //******************追踪进度*********************
                long total = request.body().contentLength();//文件的大小
                InputStream is = response.body().byteStream();
                monitorProgress(is,total);

            }
        });
    }
    public void doDownLoadImg() {
        Request request = new Request.Builder().url(URL + "files/zzzz.jpg").build();//电脑上这个路径下要有test.jpg
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
//                    iv.setImageBitmap(bitmap);//这里Bitmap.Options对图片进行压缩等操作，二次取样
//                    //加动画
//                    AlphaAnimation animation=new AlphaAnimation(0,1);
//                    animation.setDuration(1500);
//                    iv.setAnimation(animation);
                });
            }
        });
    }

    /**
     * 监控文件下载进度
     * @param is
     * @param total
     */
   private void monitorProgress(InputStream is,long total){
       long sum = 0L;
       int len = 0;
       File file = new File(SDHelper.getImageFolder(), "fromServer.png");//这个名字本地不能有
       byte[] buf = new byte[128];
       FileOutputStream fos = null;
       try {
           fos = new FileOutputStream(file);
           while ((len = is.read(buf)) != -1) {
               fos.write(buf, 0, len);
               //输出进度
               sum += len;
               Log.e(TAG, sum + "/" + total);//输出下载进度
           }
           fos.flush();
           fos.close();
           is.close();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       } catch (IOException e) {
           e.printStackTrace();
       }

   }


    /*******************************************写着玩************************************************/
    private void doGet2(){
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

}
