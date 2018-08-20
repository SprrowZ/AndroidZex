package com.example.myappsecond.project.RxJava2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.myappsecond.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by ZZG on 2018/8/20.
 */
public class RxDemoOne extends BaseActivity {
private static  final  String TAG="DemoOne";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
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
        //只要onNext
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

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
