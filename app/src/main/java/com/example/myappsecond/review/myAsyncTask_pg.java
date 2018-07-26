package com.example.myappsecond.review;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ProgressBar;

import com.example.myappsecond.R;

/**
 * Created by ZZG on 2017/10/31.
 */

public class myAsyncTask_pg extends Activity {
    private ProgressBar pg;
    myAsync mTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_asynctask_pg);
        pg=findViewById(R.id.pg);
         mTask=new myAsync();
        mTask.execute();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mTask!=null&&mTask.getStatus()==AsyncTask.Status.RUNNING){
            mTask.cancel(true);//cancel方法只是将对应的AsyncTask标记为取消状态，并不能直接结束这个线程
        }
    }

    class myAsync extends AsyncTask<Void,Integer,Void>{

        @Override
        protected Void doInBackground(Void... params) {
            //模拟进度栏的更新
         for(int i=0;i<100;i++){
             if (isCancelled()){
                 break;
             }
             publishProgress(i);
             try {
                 Thread.sleep(300);
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (isCancelled()){
                return;
            }
            pg.setProgress(values[0]);
        }
    }
}
