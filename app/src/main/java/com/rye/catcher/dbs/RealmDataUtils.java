package com.rye.catcher.dbs;

import com.rye.catcher.utils.FileUtils;

import java.util.List;

import io.realm.DynamicRealmObject;
import io.realm.Realm;
import io.realm.RealmResults;

public class RealmDataUtils {
    private static final  String REALM_LOG="REALM_LOG:";
    //存在内存中
    private static PersonDao personDao;
    public static RealmDataUtils getInstance(){

        return SingtonInstance.INSTANCE;
    }
    private static class SingtonInstance{
        private static  final  RealmDataUtils INSTANCE=new RealmDataUtils();
    }

    public PersonDao getPersonDb(){
        if (personDao ==null){
            personDao =new PersonDao();
        }
        return personDao;
    }

    /**
     * person表操作
     */
    public class PersonDao {
        /**
         * 插入操作----------之1
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

        //插入操作---------之2
        private void doInsertOrUpdate2(){

            Realm mRealm=Realm.getDefaultInstance();
            try{
                mRealm.beginTransaction();
                //存表
                Person person=mRealm.createObject(Person.class);
                person.setId((long) 777);
                person.setJob("doWhat...");
                mRealm.commitTransaction();
            }catch (Exception e){
                mRealm.cancelTransaction();
                FileUtils.writeUserLog(REALM_LOG+e.toString());
            }finally {
                mRealm.close();
            }
        }
        //插入操作---------之3
        //使用事务块
        private void doInsertOrUpdate3(Person person){
            if (person==null) return;
            Realm mRealm=Realm.getDefaultInstance();
            mRealm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                 realm.copyToRealm(person);
                }
            });
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
        public List<Person>  find( ){

            return null;
       }


    }
}
