package com.rye.catcher.activity.fragment.orr;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rye.catcher.R;
import com.rye.catcher.activity.fragment.BaseFragment;
import com.rye.catcher.project.dao.ServiceContext;
import com.rye.catcher.project.dialog.ctdialog.ExDialog;
import com.rye.catcher.utils.ExtraUtil.test.utils.OkHttpUtil;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RxjavaFragment extends BaseFragment {

    private static  final String TAG="RxjavaFragment";
    private Unbinder unbinder;
    private View view;
    private OkHttpClient client= OkHttpUtil.getInstance().getClient();
    private static  final  String url="http://v.juhe.cn/weather/index?cityname=%E4%B8%8A%E6%B5%B7&dtype=&format=&key=3444d95f001d7765de768376c3a2d870";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_rxjava, container, false);
        unbinder=ButterKnife.bind(this,view);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initEvent();
    }

    private void initEvent() {

            //上游
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("1---秦时明月");
                    emitter.onNext("2---空山鸟语");
                    emitter.onNext("3---天行九歌");
                    emitter.onComplete();
                }
            }).subscribe(new Observer<String>() {//通过subscribe连接下游
                private Disposable mDisposable;
                private int i=0;//计数器
                @Override
                public void onSubscribe(Disposable d) {
                    Log.i(TAG, "onSubscribe: ");
                    mDisposable=d;
                }
                @Override
                public void onNext(String s) {
                    Log.i(TAG, "onNext: ");
                    i++;
                    if (i==2){
                        mDisposable.dispose();//阻断,dls上线！
                    }
                }
                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
            /***********************只要onNext,x需要一个Consumer*********************/
            Observable.create(new ObservableOnSubscribe<Integer>() {
                @Override
                public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                    emitter.onNext(666);
                }
            }).subscribe(new Consumer<Integer>() {
                @Override
                public void accept(Integer integer) throws Exception {
                    Log.i(TAG, "accept: "+integer);
                }
            });
            /************************在子线程中运行***************************/
            final Observable<String> observable=Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("大浪淘沙");
                }
            });
            Consumer consumer=new Consumer() {
                @Override
                public void accept(Object o) throws Exception {
                    Log.i(TAG, "accept: "+o.toString());
                }
            };
            observable.subscribeOn(Schedulers.newThread())//可以看出来提交是新开了一个线程
                    .observeOn(AndroidSchedulers.mainThread())//接收还是在mainThread中的
                    .subscribe(consumer);

            /******************************Map操作变化符******************************/
            Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) {
                    emitter.onNext("你是一个好人");
                    emitter.onNext("现在想好好学习，不想谈恋爱");
                }
            }).map(new Function<String, String>() {
                @Override
                public String apply(String s) {
                    return s+"====丑拒";
                }
            }).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Log.i(TAG, "accept: "+"拒绝的原因很简单--》"+s);
                }
            });

            Observable<String> observable1=Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("zzg->");
                }
            });
            Consumer<String> consumer1=new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {
                    Log.i(TAG, "accept: "+s);
                }
            };
            observable1.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(consumer);

    }

    @OnClick({R.id.btn1,R.id.btn2,R.id.btn3,
            R.id.btn4,R.id.btn5,R.id.btn6})
    public void onViewClicked(View view){
        switch (view.getId()){
            case R.id.btn1:
                getWeatherData();
                break;
            case R.id.btn2:
                break;
        }
    }

    /**
     * 通过Dialog显示
     * @param result
     */
    private void showDialog(String result){
        TextView tv=new TextView(getContext());
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        tv.setText(result);
        new ExDialog.Builder(getContext())
                .setDialogView(tv)
                .setCancelable(true)
                .setGravity(Gravity.CENTER)
                .setAnimStyle(R.style.translate_style)
                .show();

    }

    /**
     * 以rxjava方式查询天气数据
     */
    private void getWeatherData() {
        //被观察者
       Observable<String> observable=Observable.create(new ObservableOnSubscribe<String>() {
           @Override
           public void subscribe(ObservableEmitter<String> emitter) {
                   emitter.onNext(getResponse());
           }
       });
       Consumer<String> consumer=new Consumer<String>() {
           @Override
           public void accept(String o) throws Exception {
               showDialog(o);
           }
       };

       observable.subscribeOn(Schedulers.newThread())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(consumer);
    }
    private String getResponse(){
        Request request=new Request.Builder()
                .get()
                .url(url)
                .addHeader("X-ZZ-TOKEN", ServiceContext.getUUID())
                .build();
        try {
            Response response=client.newCall(request).execute();
            if (!response.isSuccessful()){
               throw  new Exception("Unexpected result "+String.valueOf(response.code()));
            }
            String result=response.body().string();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
