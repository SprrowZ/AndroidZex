package com.example.myappsecond.project.ctmviews;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myappsecond.R;
import com.example.myappsecond.activity.fragment.BaseFragment;

/**
 * Created by ZZG on 2018/8/12.
 */
public class ProgressFragment extends BaseFragment {
    private static  final  String TAG="ProgressFragment";
    private View view;
    private HorizontalProgressbarWithProgress mProgress1;
    private HorizontalProgressbarWithProgress mProgress2;
    private RoundProgressbarWithProgress mRound1;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View views=inflater.inflate(R.layout.progress_test,container,false);
      this.view=views;
      return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mProgress1=view.findViewById(R.id.progress1);
        mProgress2=view.findViewById(R.id.progress2);
        mRound1=view.findViewById(R.id.round1);
        new AsyncTaskEx().execute(mProgress1.getProgress(),mProgress2.getProgress(),mRound1.getProgress());
    }

    class  AsyncTaskEx extends AsyncTask<Integer,Integer,Void>{

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
