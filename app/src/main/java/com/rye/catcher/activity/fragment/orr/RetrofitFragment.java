package com.rye.catcher.activity.fragment.orr;


import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.activity.fragment.orr.interfaces.PostBean;
import com.rye.catcher.activity.fragment.orr.interfaces.zServerApi;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.SDHelper;
import com.rye.catcher.utils.ToastUtils;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
public class RetrofitFragment extends BaseFragment {
    private static  final String TAG="RetrofitFragment";
    private Unbinder unbinder;
    private View view;

    private static final String BASE_URL="http://192.168.43.231:8088/OkhttpServer/";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.fragment_retrofit, container, false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @OnClick({R.id.btn1, R.id.btn2, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                postString();

                break;
            case R.id.btn2:
                DownloadPost(view);
                break;

            case R.id.btn3:
                uploadFile(view);
              break;
        }
    }

    private void postString() {
        new Thread(()->{
            Retrofit retrofit =new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(OkHttpUtil.getInstance().getClient())
                    .build();
            zServerApi serverApi=retrofit.create(zServerApi.class);
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
                Log.i(TAG, "postString: "+response.body().string());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

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
            MultipartBody.Part part=MultipartBody.Part.createFormData("picture","retrofit.png",requestBody);
            zServerApi zServerApi=retrofit.create(zServerApi.class);
            Call<ResponseBody> call=zServerApi.uploadFile("uploadFile",part);
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

    private void DownloadPost(View view){
    }
}