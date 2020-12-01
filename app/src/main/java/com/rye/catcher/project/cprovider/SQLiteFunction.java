package com.rye.catcher.project.cprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.rye.catcher.agocode.beans.PgcBean;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Random;


/**
 * Create by rye
 * at 2020-09-25
 *
 * @description:
 */
public class SQLiteFunction {
    private static final String TAG = "SQLiteFunction";

    private static DBManager dbManager;

    public SQLiteFunction(Context context) {
        dbManager = DBManager.getInstance(context);
    }

//    public static void initTable(WeakReference<Context> wrf) {
//        Context context = wrf.get();
//        helper = new DBHelper(context);
//        helper.getReadableDatabase();
//    }

    private HashMap<String, String> getPgcData() {
        HashMap<String, String> pgcNames = new HashMap<>();
        pgcNames.put("命运石之门", "日本");
        pgcNames.put("虫师", "日本");
        pgcNames.put("银魂", "日本");
        pgcNames.put("灌篮高手", "日本");
        pgcNames.put("东京喰种", "日本");
        pgcNames.put("JOJO的奇妙冒险", "日本");
        pgcNames.put("鬼灭之刃", "日本");
        pgcNames.put("夏目友人帐", "日本");
        pgcNames.put("秦时明月", "中国");
        pgcNames.put("天行九歌", "中国");
        pgcNames.put("凡人修仙传", "中国");
        pgcNames.put("Re:从零开始的异世界生活", "日本");
        pgcNames.put("一人之下", "中国");
        pgcNames.put("镇魂街", "中国");
        pgcNames.put("罗小黑战记", "中国");
        pgcNames.put("雾山五行", "中国");
        pgcNames.put("侠肝义胆沈剑心", "中国");
        pgcNames.put("狐妖小红娘", "中国");
        return pgcNames;
    }


    public void insertFakeUgcData() {
        HashMap<String, String> originData = getPgcData();
        for (Map.Entry<String, String> entry : originData.entrySet()) {
             ContentValues content = new ContentValues();
             content.put(DBHelper.TABLE_PGC_NAME,entry.getKey());
             content.put(DBHelper.TABLE_PGC_NATION,entry.getValue());
             //随机生成编号
             int number = new Random().nextInt(1000);
             content.put(DBHelper.TABLE_PGC_NUMBER,number);
             dbManager.insert(content);
        }
    }

    public void insertFakeUgcDataByExecSQL() {
        List<String> ugcNames = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ugcNames.add("《鬼灭之刃》，编号:" + new Random().nextInt(1000));
        }
        dbManager.insertBySQL(ugcNames);
    }


    /**
     * 查询所有数据
     *
     * @return
     */
    public List<PgcBean> queryAllUgcData() {
        List<PgcBean> dataList = new ArrayList<>();
        Cursor cursor = dbManager.queryAll(DBHelper.TABLE_PGC);
        while (cursor.moveToNext()) {
            PgcBean pgcBean = new PgcBean();
            pgcBean.pgcId = cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_PGC_ID));
            pgcBean.pgcName = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_PGC_NAME));
            pgcBean.pgcName = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_PGC_NATION));
            pgcBean.pgcNumber = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_PGC_NUMBER));
            dataList.add(pgcBean);
            Log.i(TAG, "data:" + pgcBean.toString());
        }
        cursor.close();
        return dataList;
    }

    /**
     * 更新数据
     *
     * @param newUgcName
     * @param updateSize
     * @return
     */
    public boolean updateData(String newUgcName, int updateSize) { //更新前n条数据的name
        return dbManager.updatePartData(newUgcName, updateSize);
    }

    /**
     * 删除所有数据（实际上是一百条）
     *
     * @return
     */
    public boolean deleteAllData() {
        return dbManager.deleteAllData();
    }

     /**
     * 模糊查询  根据 _name
     *
     * @param ugcName
     * @return
     */
    public List<PgcBean> selectByLike(String ugcName) {
        List<PgcBean> result = new ArrayList<>();
        Cursor cursor = dbManager.selectByLike(ugcName);
        while (cursor.moveToNext()) {
            PgcBean pgcBean = new PgcBean();
            pgcBean.pgcId = cursor.getInt(cursor.getColumnIndex(DBHelper.TABLE_PGC_ID));
            pgcBean.pgcName = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_PGC_NAME));
            pgcBean.pgcNation = cursor.getString(cursor.getColumnIndex(DBHelper.TABLE_PGC_NATION));
            pgcBean.pgcNumber = cursor.getLong(cursor.getColumnIndex(DBHelper.TABLE_PGC_NUMBER));
            result.add(pgcBean);
        }
        cursor.close();
        return result;
    }


}
