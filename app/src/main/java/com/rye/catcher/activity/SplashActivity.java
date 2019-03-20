package com.rye.catcher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.rye.catcher.BaseActivity;
import com.rye.catcher.BuildConfig;
import com.rye.catcher.R;
import com.rye.catcher.base.interfaces.FreeApi;
import com.rye.catcher.sdks.beans.TangBean;
import com.rye.catcher.sdks.gmap.AmapAPI;
import com.rye.catcher.sdks.gmap.AmapResult;
import com.rye.catcher.utils.ExtraUtil.Constant;
import com.rye.catcher.utils.ExtraUtil.test.utils.RetrofitManager;
import com.rye.catcher.utils.ToastUtils;
import com.rye.catcher.utils.permission.PermissionUtils;
import com.yanzhenjie.permission.Permission;

import org.greenrobot.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by ZZG on 2018/9/7.
 */
public class SplashActivity extends BaseActivity {
    private static  final  String TAG="SplashActivity";
    @BindView(R.id.image)
     ImageView image;
    private JumpHandler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_splash);
        ButterKnife.bind(this);
        initEx();

    }



    private void initEx() {
        //安装申请权限
        mHandler=new JumpHandler(this);
        authority();
        Glide.with(this).load(R.drawable.ling).into(image);
        tangObservable();
    }

    /**
     * Rxjava+Retrofit+Okhttp
     */
    private void tangObservable(){
        RetrofitManager.INSTANCE
                .getClient(Constant.TANG_POETRY)
                .create(FreeApi.class)
                .getTangPoetry()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Function<ResponseBody, ObservableSource<TangBean>>() {
                    @Override
                    public ObservableSource<TangBean> apply(ResponseBody responseBody) throws Exception {
                        String result=responseBody.string();
                        Log.i(TAG, "tangObservable:"+result);
                        JSONObject json=JSONObject.parseObject(result);
                        TangBean bean=JSONObject.toJavaObject(json,TangBean.class);
                        return Observable.just(bean);
                    }
                }).subscribe(new Consumer<TangBean>() {
            @Override
            public void accept(TangBean bean) throws Exception {
                Log.i(TAG, "acceptZZZ: "+ bean.toString());
                EventBus.getDefault().postSticky(bean);
            }
        });

    }
    private void authority() {
        PermissionUtils.requestPermission(this,"本地文件读写和手机状态需要此类权限,请去设置里授予权限",
                false,data ->{
                    Message message=mHandler.obtainMessage();
                    message.what=1;
                    mHandler.sendMessageDelayed(message,4000);
                },0,
                Permission.WRITE_EXTERNAL_STORAGE,Permission.CALL_PHONE,Permission.ACCESS_COARSE_LOCATION);
    }


    @Override
    protected void onDestroy() {
        AmapAPI.getInstance().destroyLocation();
        super.onDestroy();
    }

    /**
     * 静态内部类，弱引用防止内存泄露
     */
    private static class JumpHandler extends Handler{
        private WeakReference<SplashActivity> weakReference;
        public JumpHandler(SplashActivity activity){
            weakReference=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            SplashActivity zActivity=weakReference.get();
            switch (msg.what){
                case 1:
                    zActivity.startActivityByAlpha(new Intent(zActivity,MainActivityEx.class));
                    zActivity.finish();
                    break;
            }
            super.handleMessage(msg);
        }
    }
}
