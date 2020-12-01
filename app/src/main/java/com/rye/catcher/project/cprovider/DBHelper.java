package com.rye.catcher.project.cprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Create by rye
 * at 2020-09-25
 *
 * @description: {@link CustomContentProvider} 工具类
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DB_NAME = "local.rye.db";
    private static final int DB_VERSION = 1;
    public static final String TABLE_PGC = "table_pgc";
    //UGC
    public static final String TABLE_PGC_ID = "_id";
    public static final String TABLE_PGC_NAME = "_name";
    public static final String TABLE_PGC_COVER = "_cover";
    public static final String TABLE_PGC_NATION = "_nation";
    public static final String TABLE_PGC_NUMBER = "_number";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    /**
     * [ _id, _name ,_cover]
     *
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_PGC + " ("
                + TABLE_PGC_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                + TABLE_PGC_NAME + " TEXT UNIQUE ON CONFLICT REPLACE , " //冲突时替换，所以新增的数据，可能因为此值冲突，导致数据被替换，而不是增加
                + TABLE_PGC_COVER + " TEXT,"
                + TABLE_PGC_NATION + " VARCHAR(50),"
                + TABLE_PGC_NUMBER + " LONG )";

        db.execSQL(createTable);


        db.execSQL("CREATE TABLE IF NOT EXISTS person(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                " name VARCHAR,info TEXT)");

        Log.e(TAG, "create table , sql:" + createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //升级暂且不管
    }
}
