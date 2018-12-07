package com.rye.catcher.project.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.rye.catcher.activity.IDemoAIDL;
import com.rye.catcher.activity.IMyAidlInterface;

import java.util.List;

/**
 * Created at 2018/12/3.
 *
 * @author Zzg
 */
public class IRemoteService extends Service {
    /**
     * 当客户端绑定到该服务时会执行
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /**
     * ADD------>
     */
    private final Binder mBinder=new IDemoAIDL.Stub() {
     @Override
     public int add(int num1, int num) throws RemoteException {
         Log.d("ZzgAidl", "add:....传入的两个参数为： "+num+"、"+num1);
         return num1+num;
     }
 };
    /**
     *基本类型传递
     */
    private final Binder iBinder=new IMyAidlInterface.Stub() {
        @Override
        public List<String> basicTypes(byte aByte, int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, char aChar, String aString, List<String> aList) throws RemoteException {
            return null;
        }
    };
}
