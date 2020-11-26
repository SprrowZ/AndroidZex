package com.rye.catcher.project.cprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Create by rye
 * at 2020-09-27
 *
 * @description: Database 管理类,操作数据库
 */
public class DBManager {
    private static final String TAG = "DBManager";
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    private static DBManager dbManager;

    public static DBManager getInstance(Context context) {
        if (dbManager == null) {
            synchronized (DBManager.class) {
                if (dbManager == null) {
                    dbManager = new DBManager(new WeakReference<>(context));
                }
            }
        }
        return dbManager;
    }


    private DBManager(WeakReference<Context> wrf) {
        dbHelper = new DBHelper(wrf.get());
        database = dbHelper.getWritableDatabase();
    }

    public void insert(ContentValues contentValues) {
        long rowId = database.insert(DBHelper.TABLE_PGC, null, contentValues);
        Log.e(TAG, "insertBySqlite data ,rowId" + rowId);
    }

    public void insertBySQL(List<String> ugcNames) {
        String insertWithBatch = "INSERT INTO " + DBHelper.TABLE_PGC + " (" +
                DBHelper.TABLE_PGC_NAME + ") " + "VALUES ";
        for (String ugcName : ugcNames) {
            insertWithBatch = insertWithBatch + "('" + ugcName + "')," + "\n";
        }
        insertWithBatch = insertWithBatch.substring(0, insertWithBatch.lastIndexOf(",")) + ";";
        Log.e(TAG, "insert by execSQL ,sql: \n" + insertWithBatch);
        database.execSQL(insertWithBatch);
    }


    public Cursor queryAll(String tableName) {
        String simpleQuery = " SELECT * FROM " + tableName;
        Cursor cursor = database.rawQuery(simpleQuery, null);
        return cursor;
    }

    public boolean updatePartData(String ugcName, int updateSize) { //更新这些前 $size个数据 并更新最后一条
        String updateSql = "UPDATE " + DBHelper.TABLE_PGC + " SET " + DBHelper.TABLE_PGC_NAME + " = " +
                " '" + ugcName + "'" + " WHERE " + DBHelper.TABLE_PGC_ID + " < " + updateSize + " AND " +
                DBHelper.TABLE_PGC_COVER + "== NULL" + ";";
        Log.e(TAG, "update pgcName :" + updateSql);
        try {
            database.execSQL(updateSql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean deleteAllData() {//假删除，只删除前一百条数据
        String deleteSql = "DELETE FROM " + DBHelper.TABLE_PGC + " WHERE " + DBHelper.TABLE_PGC_ID + " < 100";
        Log.e(TAG, "delete top100:" + deleteSql);
        try {
            database.execSQL(deleteSql);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * LIKE --GLOB 《模糊搜索 保护condition 的ugc item
     * @param condition
     * @return
     */
    public Cursor selectByLike(String condition) {
        String select = "SELECT " + DBHelper.TABLE_PGC_NAME + " FROM " + DBHelper.TABLE_PGC +" WHERE " +
                DBHelper.TABLE_PGC_NAME + " LIKE " +" '%" +condition +" %'";
       return database.rawQuery(select,null);
    }

    /**
     * GLOB 模糊搜索，使用方式和LIKE一样，只是GLOB大小写敏感；符号 % --> * | _ --> ?
     * @param condition
     * @return
     */
    public Cursor selectByGlob(String condition){
      return null;
    }






}
