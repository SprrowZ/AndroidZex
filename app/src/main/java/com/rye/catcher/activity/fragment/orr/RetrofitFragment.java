package com.rye.catcher.activity.fragment.orr;


import android.os.Bundle;
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

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
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
                postFile(view);
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

    private void postFile(View view) {

    }

    private void DownloadPost(View view){
    }
}
