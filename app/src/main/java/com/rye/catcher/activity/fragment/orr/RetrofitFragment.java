package com.rye.catcher.activity.fragment.orr;


import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.View;

import com.rye.catcher.R;
import com.rye.catcher.BaseOldFragment;
import com.rye.catcher.beans.MultiBean;
import com.rye.catcher.beans.PostBean;
import com.rye.catcher.activity.fragment.orr.interfaces.zRetrofitApi;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.ToastUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link Fragment} subclass.
 */
public class RetrofitFragment extends BaseOldFragment {
    private static  final String TAG="RetrofitFragment";


    private static final String BASE_URL="http://192.168.43.231:8088/OkhttpServer/";

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_retrofit;
    }

    @Override
    protected void initData() {

    }






    @OnClick({R.id.orr, R.id.javaMore, R.id.translate, R.id.retrofit,
            R.id.btn5, R.id.btn6,R.id.btn7, R.id.btn8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.orr:
                postString();
                break;
            case R.id.javaMore:
                uploadFile(view);
                break;
            case R.id.translate:
                downLoadFile(view);
            case R.id.retrofit:
                upLoadFileEx();//通过RequestBody
                break;
        }
    }

    /**
     * 上传json字符串
     */
    private void postString() {
        new Thread(()->{
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpUtil.getInstance().getClient())
                    .build();
            zRetrofitApi serverApi=retrofit.create(zRetrofitApi.class);
            PostBean bean=new PostBean();
            bean.setCity("ShangHai");
            bean.setJob("程序员");
            bean.setSex(true);
            try {
                Response<ResponseBody> response= serverApi.postMessage("postString","zzg","0517",bean)
                        .execute();
                if (!response.isSuccessful()){
                    throw  new IOException("Unexpected result:"+response.code());
                }
                String result=response.body().string();
                Log.i(TAG, "postString: "+result);
                getActivity().runOnUiThread(()->{
                    ToastUtils.shortMsg(result);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 上传文件
     * @param view
     */
    private void uploadFile(View view) {
        new Thread(()->{
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpUtil.getInstance().getClient())
                    .build();

            File file=new File(SDHelper.getImageFolder(),"portrait.png");
            if (!file.exists()){
                ToastUtils.shortMsg("文件不存在！");
                return;
            }
            RequestBody requestBody=RequestBody.create(MediaType.parse("image/png"),file);
            //创建上传文件,这里mPhoto是服务器的一个属性，我们还有userName和password

            MultipartBody.Part part=MultipartBody.Part.createFormData("mPhoto","retrofit.png",requestBody);
            zRetrofitApi zServerApi=retrofit.create(zRetrofitApi.class);
            //上传文件
            Call<ResponseBody> call=zServerApi.uploadFile("uploadInfo","zzg","111",part);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("上传成功");
                    });
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i(TAG, "onFailure: ");
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("上传失败");
                    });
                }
            });
        }).start();

    }

    /**
     *暂不行。。。
     */
    private void upLoadFileEx(){
        new Thread(()->{
            Retrofit retrofit=new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .client(OkHttpUtil.getInstance().getClient())
                    .build();
            zRetrofitApi api=retrofit.create(zRetrofitApi.class);
            File file=new File(SDHelper.getImageFolder(),"zAndroid-1.png");
            RequestBody body=RequestBody.create(MediaType.parse("image/png"),file);
            Call<ResponseBody> call=api.uploadFileEx("uploadInfo","SprrowZ","sss",body);
            try {
                Response response= call.execute();
                if (response.isSuccessful()){
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("上传文件成功！");
                    });
                }else{
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("上传文件失败,错误码："+response.code());
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 下载文件
     * @param view
     */
    private void downLoadFile(View view){
        new Thread(()->{
            Retrofit retrofit=new Retrofit.Builder()
                    .client(OkHttpUtil.getInstance().getClient())
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            zRetrofitApi api=retrofit.create(zRetrofitApi.class);
            api.downloadFile("files/retrofit.png").enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    getActivity().runOnUiThread(()->{
                        ToastUtils.shortMsg("文件下载成功"+response.message());
                    });
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                   getActivity().runOnUiThread(()->{
                       ToastUtils.shortMsg("文件下载失败！错误码："+t.getMessage());
                   });
                }
            });
        }).start();
    }

    /**
     * 上传多个文件
     */
    private void upLoadFilesWithParts(){
        Retrofit retrofit=new Retrofit.Builder()
                .client(OkHttpUtil.getInstance().getClient())
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        zRetrofitApi api=retrofit.create(zRetrofitApi.class);
        File fileOne = new File(SDHelper.getImageFolder(),"zAndroid-10.png");
        File fileTwo = new File(SDHelper.getImageFolder(),"zAndroid-11.png");

        RequestBody body1=RequestBody.create(MediaType.parse("image/png"),fileOne);
        RequestBody body2=RequestBody.create(MediaType.parse("image/png"),fileTwo);
        //Form name keep same with server
        MultipartBody.Part part1=MultipartBody.Part.createFormData("mPhoto",fileOne.getName(),
                body1);
        MultipartBody.Part part2=MultipartBody.Part.createFormData("mPhoto2",fileTwo.getName(),
                body2);

        List<MultipartBody.Part> parts=new ArrayList<>();
        parts.add(part1);
        parts.add(part2);

        try {
            Response<MultiBean> response=api.uploadFilesWithParts("uploadFiles",
                    parts).execute();
            if (!response.isSuccessful()){
                getActivity().runOnUiThread(()->{
                    ToastUtils.shortMsg("上传文件失败！错误码："+response.code());
                });
            }else{
                getActivity().runOnUiThread(()->{
                    ToastUtils.shortMsg("上传文件成功！");
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
