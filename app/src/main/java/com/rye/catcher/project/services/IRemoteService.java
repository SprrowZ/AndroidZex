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
import com.rye.catcher.activity.PersonBean;
import com.rye.catcher.beans.AIDLBean;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2018/12/3.
 *
 * @author Zzg
 */
public class IRemoteService extends Service {
    ArrayList<PersonBean> persons;


    /**
     * 当客户端绑定到该服务时会执行
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //一绑定就传过来一个列表，这是用来传递自定义实体类
//        persons=new ArrayList<PersonBean>();
//        return iBinder;
        //调用服务器方法
        return mBinder;
    }

    /**
     * ADD------>
     */
    private final Binder mBinder=new IDemoAIDL.Stub() {
     @Override
     public int add(int num1, int num) throws RemoteException {
         AIDLBean bean=new AIDLBean();
         bean.setIntParam1(num1);
         bean.setIntParam2(num);
         Log.d("ZzgAidl", "传入参数为： "+num+"、"+num1+";"+"AIDLBean:"+bean.toString());
         EventBus.getDefault().postSticky(bean);
         return num1+num;
     }
 };
    /**
     *基本类型传递
     */
//    private final Binder iBinder=new IMyAidlInterface.Stub() {
//        @Override
//        public List<String> basicTypes(byte aByte, int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, char aChar, String aString, List<String> aList) throws RemoteException {
//            return null;
//        }
//    };
    /**
     * 自定义实体类传递
     */
      private final Binder iBinder=new IMyAidlInterface.Stub() {
        @Override
        public List<PersonBean> add(PersonBean person) throws RemoteException {
            //传过来就加到persons列表中去
            persons.add(person);
            Log.i("zzg", "add: "+person.toString());
            return persons;
        }
    };
}
