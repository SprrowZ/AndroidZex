package com.dawn.zgstep.others.threads;

import android.provider.ContactsContract;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by rye
 * at 2021/4/6
 *
 * @description:
 */
public class SynBean {
    private static final String TAG = "RYE";
    private final Object lock = new Object();
    public void methodA() {
        synchronized (this) {
            sout("methodA...start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sout("methodA...sleep over");
        }
    }

    public void methodB() {
        synchronized (this) {
            sout("methodB..start");
        }
    }

    public void methodC() {
        synchronized (lock) {
            sout("methodC...start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sout("methodC...sleep over");
        }
    }

    public void methodD() {
        synchronized (lock) {
            sout("methodD..start");
        }
    }


    public void methodE(){
        synchronized (SynBean.class){
            sout("methodE...start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sout("methodE...sleep over");
        }
    }

    public void methodF(){
        synchronized (SynBean.class){
            sout("methodF...start");
        }
    }


    private String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS");
        return sdf.format(date);
    }

    private void sout(String content) {
        System.out.println(getTime() +"   "+ content);
    }
}
