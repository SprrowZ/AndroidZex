package com.dawn.zgstep.others.bolts;



import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.util.concurrent.Callable;

import bolts.Task;


public class BoltsMain  extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Task.callInBackground(new Callable<Boolean>() {    @Override
        public Boolean call() throws Exception {
            XLog.d(Thread.currentThread().getName() + " " + "call in background thread");        return true;
        }
        });
    }


}
