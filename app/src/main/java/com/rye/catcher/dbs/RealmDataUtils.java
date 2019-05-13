package com.rye.catcher.dbs;

import android.util.Log;

import com.rye.catcher.utils.FileUtils;

import io.realm.Realm;

public class RealmDataUtils {
    private static final  String REALM_LOG="REALM_LOG:";
    //存在内存中
    private static PersonUtils personUtils;
    public RealmDataUtils getInstance(){

        return SingtonInstance.INSTANCE;
    }
    private static class SingtonInstance{
        private static  final  RealmDataUtils INSTANCE=new RealmDataUtils();
    }

    public PersonUtils getPersonDb(){
        if (personUtils==null){
            personUtils=new PersonUtils();
        }
        return personUtils;
    }

    /**
     * person表操作
     */
    public class PersonUtils{
        /**
         * 插入操作
         * @param person
         */
        public void doInsertOrUpdate(Person person){
            if (person==null) return;
            Realm mRealm=Realm.getDefaultInstance();
            try{
                mRealm.beginTransaction();
                //存表
                mRealm.copyToRealmOrUpdate(person);
                mRealm.commitTransaction();
            }catch (Exception e){
                mRealm.cancelTransaction();
                FileUtils.writeUserLog(REALM_LOG+e.toString());
            }finally {
                mRealm.close();
            }
        }
        public void doDelete(Person person){
            if (person==null) return;
            Realm mRealm=Realm.getDefaultInstance();
            try{
                mRealm.beginTransaction();
                Person deleteData=mRealm.where(Person.class).equalTo("id",person.getId()).findFirst();
               //很不智能啊。。。上面已经判空了
                assert deleteData != null;
                deleteData.deleteFromRealm();
                mRealm.commitTransaction();
            }catch (Exception e){
                mRealm.cancelTransaction();
                e.printStackTrace();
            }finally {
                mRealm.close();
            }
        }



    }
}
