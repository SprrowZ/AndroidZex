package com.dawn.zgstep.others.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Create by rye
 * at 2020-10-13
 *
 * @description: SQLite测试类
 */
public class SQLiteTest extends SQLiteOpenHelper {
    private static final String TAG = "com.dawn.zgstep.db.SQLiteTest";
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "testrye.db";
    private SQLiteDatabase db;

    private static final String TABLE_ONE_NAME = "tableOne";

    public SQLiteTest(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createTable1(String tabName) {

        String createTable1 = "CREATE TABLE IF NOT EXISTS " + tabName + " (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT , " +
                "name VARCHAR(50) NOT NULL, address VARCHAR(50) )";
        println(createTable1);
        db.execSQL(createTable1);
    }

    private void println(String content) {
        Log.e(TAG, "SQL:" + content);
    }
}
