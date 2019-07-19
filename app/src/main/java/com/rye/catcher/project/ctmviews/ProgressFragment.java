package com.rye.catcher.project.ctmviews;

import android.os.AsyncTask;
import android.util.Log;

import com.rye.catcher.R;
import com.rye.catcher.BaseFragment;

import butterknife.BindView;

/**
 * Created by ZZG on 2018/8/12.
 */
public class ProgressFragment extends BaseFragment {
    private static  final  String TAG="ProgressFragment";


    @BindView(R.id.progress1)
    HorizontalProgress mProgress1;
    @BindView(R.id.progress2)
    HorizontalProgress mProgress2;
    @BindView(R.id.round1)
    RoundProgress mRound1;
    @Override
    protected int getLayoutResId() {
        return R.layout.progress_test;
    }

    @Override
    protected void initData() {
        new AsyncTaskEx().execute(mProgress1.getProgress(),mProgress2.getProgress(),mRound1.getProgress());
    }

    //内存泄露
    private   class  AsyncTaskEx extends AsyncTask<Integer,Integer,Void>{

        @Override
        protected Void doInBackground(Integer... integers) {
            int progressNow=integers[0];
            int progressNow2=integers[1];
            int progressNow3=integers[2];
            for (int i=0;i<101-progressNow;i++){
                publishProgress(progressNow+i,progressNow2+i,progressNow3+i);
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //是运行在主线程中的
            return null;
        }

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "onPreExecute: i will start....");
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            mProgress1.setProgress(values[0]);
            mProgress2.setProgress(values[1]);
            mRound1.setProgress(values[2]);
        }
    }
}
