package com.example.myappsecond.project.sqlDemo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ZZG on 2018/1/16.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    //数据库版本号
    private static final int DATABASE_VERSION=1;
    //数据库名
    private static  final String DATABASE_NAME="firstDB.db";
    //数据表名
    public static final String TABLE_NAME="PersonTable";
    //前缀
    private static final String PREFIX="ZZG";



    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        //SQLiteOpenHelper的构造函数参数：
        //context:上下文环境
        //name： 数据库名字
        //factory：游标工厂(可选)
        //version:数据库模型版本号
    }
public DatabaseHelper(Context context){
        //数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
    super(context,DATABASE_NAME,null,DATABASE_VERSION);
    //CursorFactory设置为null,使用系统默认的工厂类

}

//继承SQLiteOpenHelper类，必须要覆写的三个方法：onCreate(),onUpgrade(),onOpen()
    @Override
    public void onCreate(SQLiteDatabase db) {
//调用时间：数据库第一次创建时onCreate()方法会被调用
        //onCreate方法有一个SQLiteDatabase对象作为参数，根据需要对这个对象填充表和初始化数据
        //这个方法中主要完成创建数据库后对数据库的操作
        Log.i("Create", "onCreate:Database ");
       //构建创建表的SQL语句(可以从SQLite Expert工具的DDL粘贴过来加进StringBuffer中)
        StringBuffer sBuffer=new StringBuffer();
        sBuffer.append("CREATE TABLE IF NOT EXISTS["+TABLE_NAME+"] (");
        sBuffer.append("[_id] INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,");
        sBuffer.append("[name] TEXT,");
        sBuffer.append("[age] INTEGER,");
        sBuffer.append("[info] TEXT)");
        //执行创建表的SQL语句
        db.execSQL(sBuffer.toString());
        //即便程序修改重新运行，只要数据库已经创建过，就不会再进入这个onCreate方法
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     //调用时间：如果DATABASE_VERSION值被修改为别的数，系统发现现有数据库版本不同，即会调用onUpgrade
        //onUpgrade方法的三个参数，一个SQLiteDatabase对象，一个旧的版本号，和一个新的版本号
        //这样就可以吧一个数据库从旧的模型转变到新的模型
        //这个方法中主要完成更改数据库版本的操作
        Log.i("onUpgrade", "onUpgrade: DatabaseHelper onUpgrade");
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
        //通过检查常量值来决定如何，升级时删除旧表，然后调用onCreate来创建新表
        //实际项目中不能这么做，正确的做法是在更新数据表结构时，还要考虑用户存放于数据库中的数据不丢失
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //每次打开数据库之后首先被执行
    }















}
