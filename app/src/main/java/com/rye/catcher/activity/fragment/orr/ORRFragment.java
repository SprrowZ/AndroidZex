package com.rye.catcher.activity.fragment.orr;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.activity.fragment.orr.interfaces.zRetrofitApi;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;
import com.rye.catcher.utils.SDHelper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.security.auth.login.LoginException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
*
 */
public class ORRFragment extends BaseFragment {
    private static  final  String TAG="ORRFragment";
   private Unbinder unbinder;
   private View view;
   @BindView(R.id.test1) Button test1;

   private static final String VEDIO_URL="https://media.w3.org/2010/05/sintel/";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_orr,container,false);
        unbinder=ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
   @OnClick({R.id.test1})
   public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.test1:
               downLoadRx();
                break;
        }
    }

    /**
     * Rxjava+Retrofit+Okhttp---测试方法1
     */
    private void downLoadRx(){
        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(VEDIO_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(OkHttpUtil.getInstance().getClient())
                .build();
        zRetrofitApi api=retrofit.create(zRetrofitApi.class);
        Observable<ResponseBody> observable=api.downLoadRx("trailer.mp4");

        showLoadingDlg(getContext());
        //Observer
        Observer<ResponseBody> observer=new Observer<ResponseBody>() {
            @Override
            public void onSubscribe(Disposable d) {

            }
            //成功走此
            @Override
            public void onNext(ResponseBody responseBody) {
                cancelLoadingDlg(getContext());
                try {
                    int fileSize=responseBody.bytes().length;
                    Log.i(TAG, "onNext:fileSize--> "+fileSize);
                    Log.i(TAG, "onNext: fileType-->"+responseBody.contentType());
                    //执行下载操作
                    download(responseBody.byteStream(),"z-Video.mp4");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: "+e.toString());
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete: ");
            }
        };
        observable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * 下载文件
     * @param inputStream
     * @param fileName
     */
    private void download(InputStream inputStream,String fileName){
        File file=new File(SDHelper.getVideoFolder(),fileName);
        try {
            BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(file));
            byte[] buf=new byte[2048];
            int length=-1;
            while((length=inputStream.read(buf))!=-1){
                bos.write(buf,0,length);
            }
            bos.flush();
            inputStream.close();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
