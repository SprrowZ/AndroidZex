package com.rye.catcher.activity.ctmactivity;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.rye.catcher.BaseActivity;
import com.rye.catcher.R;
import com.rye.catcher.activity.adapter.PullToRefreshAdapter;
import com.rye.catcher.beans.ImageBean;
import com.rye.catcher.project.ctmviews.zPullToRefreshView;
import com.rye.catcher.utils.ToastUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CtmPTRActivity extends BaseActivity {
    private static  final  String TAG="CtmPTRActivity";
    private zPullToRefreshView pullToRefreshView;
    private DataHandler zHandler=new DataHandler(this);
    private RecyclerView recyclerView;
    private PullToRefreshAdapter adapter;

    private List<ImageBean> beanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctm_ptr);
        initEvent();

    }

    /**
     *
     */
    private void initEvent(){
        pullToRefreshView=findViewById(R.id.pullToRefreshView);
        recyclerView=findViewById(R.id.recyclerView);

        adapter=new PullToRefreshAdapter(this);
        beanList=new ArrayList<>();
        //设置布局
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left=10;
                outRect.top=20;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                super.onDraw(c, parent, state);
            }
        });
        recyclerView.setAdapter(adapter);
        pullToRefreshView.setRefreshListener(()->{
            new Thread(()->{
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                zHandler.sendEmptyMessage(1);
            }).start();
        });
        initDatas();
    }

    private void initDatas(){
        Observable<ImageBean> observable=Observable.create(new ObservableOnSubscribe<ImageBean>() {
            @Override
            public void subscribe(ObservableEmitter<ImageBean> emitter) throws Exception {
               //
               adapter.setDataList(addDatas());
            }
        });

        Consumer consumer=new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Log.i(TAG, "accept: "+(ImageBean)o);
            }
        };
        //提交
        observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(consumer);
    }
    /**
     * 防止内存泄露,静态内部类
     */
    private static class DataHandler extends Handler{
        //弱引用
        WeakReference<CtmPTRActivity> mActivity;
        public DataHandler(CtmPTRActivity activity){
            mActivity=new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CtmPTRActivity ctmPTRActivity=mActivity.get();
            switch (msg.what){
                case 1:
                    //数据加载完毕
                    ctmPTRActivity.addDatas();
                    ctmPTRActivity.adapter.notifyDataSetChanged();
                    ToastUtils.shortMsg("数据已更新！");
                    ctmPTRActivity.pullToRefreshView.dataCompleated();
                    break;
            }
            super.handleMessage(msg);
        }
    }

    private List<ImageBean> addDatas(){

        for (int i=0;i<20;i++){
            ImageBean bean=new ImageBean();
            String url="https://picsum.photos/300/300?image="+(int)(Math.random()*1048);
            bean.setUrl(url);
            bean.setDescription("-人生若只如初见-");
            Log.i(TAG, "addDatas: "+url);
            beanList.add(bean);
        }
        return beanList;
    }

}
