package com.rye.catcher.project.sqlDemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created at 2019/2/13.
 *
 * @author Zzg
 * @function:
 */
public class CartoonDao  {
    private static  final  String TAG="CartoonDao";
    private SQLiteOpenHelper helper;
    private SQLiteDatabase db;
    public CartoonDao(Context mContext){
        helper=new DatabaseHelper(mContext);
        db=helper.getWritableDatabase();
    }

    /**
     * 插入数据,第一种方法
     * @param cartoonList
     */
    public void insertData(List<Cartoon> cartoonList){
        db.beginTransaction();
        try {
            for (Cartoon cartoon:cartoonList){
                String insertSql= String.format("INSERT INTO %sVALUES(null,?,?,?,?,?,?,?,?,?) ",
                        DatabaseHelper.CARTOON_TABLE);
                db.execSQL(insertSql,new Object[]{cartoon.getValue(Cartoon.NAME),
                        cartoon.getValue(Cartoon.ACTORS),cartoon.getValue(Cartoon.LEAD),
                        cartoon.getValue(Cartoon.ISSUE_TIME),cartoon.getValue(Cartoon.DIRECTOR),
                        cartoon.getValue(Cartoon.IS_END),cartoon.getValue(Cartoon.NATIONALITY),
                        cartoon.getValue(Cartoon.DETAILS),cartoon.getValue(Cartoon.INSERT_TIME)});
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            Log.d(TAG, "insert: ");
        }finally {
            db.endTransaction();
        }
    }

    /**
     * 更新数据，方法一;还可以通过execSQL
     * @param cartoon
     */
    public void update(Cartoon cartoon){
        final String whereClause="ID=?";
        ContentValues values=new ContentValues();
        values.put(Cartoon.ID,(Integer)cartoon.getValue(Cartoon.ID));
        values.put(Cartoon.NAME, (String) cartoon.getValue(Cartoon.NAME));
        values.put(Cartoon.ACTORS, (String) cartoon.getValue(Cartoon.ACTORS));
        values.put(Cartoon.LEAD, (String) cartoon.getValue(Cartoon.LEAD));
        values.put(Cartoon.ISSUE_TIME,(String)cartoon.getValue(Cartoon.ISSUE_TIME));
        values.put(Cartoon.DIRECTOR,(String)cartoon.getValue(Cartoon.DIRECTOR));
        values.put(Cartoon.IS_END,(String)cartoon.getValue(Cartoon.IS_END));
        values.put(Cartoon.NATIONALITY,(String)cartoon.getValue(Cartoon.NATIONALITY));
        values.put(Cartoon.DETAILS,(String)cartoon.getValue(Cartoon.DETAILS));
        values.put(Cartoon.INSERT_TIME,(String)cartoon.getValue(Cartoon.INSERT_TIME));
        db.update(DatabaseHelper.CARTOON_TABLE,values,whereClause,
                new String[]{(String) cartoon.getValue(Cartoon.ID)});
    }

    public void query(){
        ArrayList<Cartoon> resultList=new ArrayList<>();
        Cursor cursor=queryTheCursor();
        while (cursor.moveToNext()){
            Cartoon cartoon=new Cartoon();
            
        }
    }

    public Cursor queryTheCursor(){
        String select="SELECT * FROM "+DatabaseHelper.CARTOON_TABLE+" WHERE ID > ? ";
        Cursor c=db.rawQuery(select,new String[]{"1"});
        return c;
    }

}
