package com.rye.catcher.project.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rye.catcher.project.SQLiteZ.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZZG on 2018/1/16.
 */

public class DBCartoonManage {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    public DBCartoonManage(Context context){
        helper=new DatabaseHelper(context);
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName,0,mFactory);
        //所以要确保context已初始化，我们可以把实例化DBManager的步骤放在Activity的onCreate里
        db=helper.getWritableDatabase();

    }

    /**
     * add persons
     */
public void add(List<Humans> persons) {
//采用事务处理，确保数据完整性
    db.beginTransaction();//开始事务
    try {
        for (Humans person : persons) {
            db.execSQL("INSERT INTO " + DatabaseHelper.PERSON_TABLE + " VALUES(null, ?, ?, ?)",
                    new Object[]{person.name, person.age, person.info});
            //带两个参数的execSQL()方法，采用占位符参数？，把参数值放在后面，顺序对应
            //一个参数的execSQL()方法中，用户输入特殊字符时需要转义
            //使用站维护有效区分了这种情况
        }
        db.setTransactionSuccessful();//设置事务成功完成

    } finally {
        db.endTransaction();//结束事务
    }

}
    public void updataAge(Humans person){
        ContentValues cv=new ContentValues();
        cv.put("age",person.age);
        db.update(DatabaseHelper.PERSON_TABLE,cv,"name = ?",new String[]{person.name});
    }
    public void deleteOldPerson(Humans person){

    db.delete(DatabaseHelper.PERSON_TABLE,"age >= ?",
           new String[]{String.valueOf(person.age)});
    }

    public List<Humans> query(){
        ArrayList<Humans> persons=new ArrayList<Humans>();
        Cursor c=queryTheCursor();
        while(c.moveToNext()){
            Humans person=new Humans();
            person._id=c.getInt(c.getColumnIndex("_id"));
            person.name=c.getString(c.getColumnIndex("name"));
            person.age=c.getInt(c.getColumnIndex("age"));
            person.info=c.getString(c.getColumnIndex("info"));
            persons.add(person);
        }
        c.close();
        return persons;
    }
 public Cursor queryTheCursor(){
        Cursor c=db.rawQuery("SELECT * FROM "+DatabaseHelper.PERSON_TABLE,null);
        return c;
 }

 public void closeDB(){
     db.close();
 }








}
