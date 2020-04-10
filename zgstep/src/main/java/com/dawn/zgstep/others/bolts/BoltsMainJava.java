package com.dawn.zgstep.others.bolts;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.dawn.zgstep.R;

import java.util.concurrent.Callable;

import bolts.Task;


public class BoltsMainJava extends Activity {

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolts_main);
        Task.callInBackground(new Callable<Boolean>() {
        @Override
        public Boolean call() throws Exception {
            Log.d("zzg","---");
            return true;
        }
        });
    }





}
