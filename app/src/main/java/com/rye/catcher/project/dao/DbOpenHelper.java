/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rye.catcher.project.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.Old_JsonUtils;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.rye.catcher.R;
/**
 * 数据库实例
 */
public class DbOpenHelper extends SQLiteOpenHelper {
    /**
     * db数据库实例容器：
     * 1.只有在登出的时候才关闭数据库，其它情况不再进行数据库的关闭操作；
     * 2.取得数据库实例逻辑：
     *      key为数据库名称，如果容器存在实例，直接返回，否则，创建实例，放入容器；
     */
    private static final Map<String, DbOpenHelper> dbMap = new HashMap<>();

    /**
     * 取得自己数据库的实例
     */
    public static DbOpenHelper getInstance() {
        DbOpenHelper helper = dbMap.get(getUserDatabaseName());
        if (helper != null) {
            return helper;
        }

        synchronized (TAG) {
            helper = dbMap.get(getUserDatabaseName());
            if(helper == null) {
                String dbName = getUserDatabaseName();
                if (TextUtils.isEmpty(dbName)) {
                    return null;
                }
                helper = new DbOpenHelper(dbName);
                dbMap.put(dbName, helper);

                Log.d(TAG, "创建数据库实例：" + dbName);
            }
        }

        return helper;
    }

    /**
     * 关闭所有的数据库实例
     */
    public static void closeDbs() {
        Log.d(TAG, "数据库实例关闭前：" + dbMap.size());
        for (DbOpenHelper helper : dbMap.values()) {
            helper.close();
        }
        dbMap.clear();
        Log.d(TAG, "数据库实例关闭后：" + dbMap.size());
    }

    private static final String TAG = "DbOpenHelper";
    private static final int DATABASE_VERSION = 1;
//    private static DbOpenHelper instance;

    private static Map<String, TableDef> tables = null;

    private DbOpenHelper(String dbName) {
        super(RyeCatcherApp.getContext(), dbName, null, DATABASE_VERSION);
        this.createTables(this.getWritableDatabase());
    }

    /**
     * 关闭当前数据库实例
     */
    public void close() {
        try {
            super.close();
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }

    /**
     * 根据表定义创建表结构
     * @param db - db object
     * @param tblDef - 表定义
     * @return - 是否进行了修改
     */
    public boolean createTableWithDefine(SQLiteDatabase db, TableDef tblDef ) {
        boolean modified;
        if (!tableIsExist(db, tblDef.getName())) {
            // 表不存在则创建表
            db.execSQL(tblDef.getCreateTblSql());
            modified = true;
        } else {
            // 表存在，则检查表中字段是否有不存在的字段。
            modified = createNotExistCols(db, tblDef);
        }

        if (modified) {
            //remove from cache
            tables.remove(tblDef.getName());
        }
        return modified;
    }


    /**
     * @param tblName 表名
     * @return 表结构定义对象
     */
    public  TableDef getTableDef(String tblName) {
        if(tables != null) {
            return tables.get(tblName);
        }
        synchronized (TAG) {
            if(tables == null) {
                loadTableDefFile(RyeCatcherApp.getContext());
            }
        }
        TableDef tableDef =  tables.get(tblName);

        //get from database
        if (null == tableDef) {
            List<String> colums = getColNames(this.getWritableDatabase(), tblName);
            if (null == colums || 0 == colums.size()) {
                return null;
            }
            tableDef = createTableDef(tblName, colums);
            //put in cache
            tables.put(tableDef.getName(), tableDef);
        }

        return tableDef;
    }


    public boolean tableIsExist(SQLiteDatabase db, String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from sqlite_master "
                    + " where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    public boolean indexIsExist(SQLiteDatabase db, String indexName) {
        boolean result = false;
        if (indexName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as  'count' from sqlite_master "
                    + " where type ='index' and name ='" + indexName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 销毁数据库数据
     */
    public void destory() {
        // 关闭数据库
        closeDbs();
        String fileName = this.getDatabaseName();
        // 取得数据库文件名
        File dbFile =  RyeCatcherApp.getContext().getDatabasePath(fileName);
        if(dbFile == null) {
            return;
        }

        // 文件列表
        File[] files = dbFile.getParentFile().listFiles();
        for(File file: files) {
//            if(file.getName().endsWith(".db") || file.getName().endsWith(".db-journal")) {
                boolean hasDeleted = file.delete();
            Log.d(TAG, "Delete File:" + file.getPath() + ", " + hasDeleted);
//            }
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // TODO: 2018/9/14 根据用户名和Token起数据库名字，这里暂且写死
    private static String getUserDatabaseName() {
//        String userCode = HxHelper.getInstance().getModel().getUserCode();
//        String Token = HxHelper.getInstance().getModel().getToken();
//        if (StringUtils.isNotEmpty(userCode) && StringUtils.isNotEmpty(Token)) {
//            String servName = ServiceContext.getHttpServer().replaceAll("\\W", "");
//            String dbFile = servName + userCode + "cochat.db";
////            EMLog.e("DB", "db file======:" + dbFile);
//            //默认路径  /data/data/com.*.*(package name)/
//            return dbFile;
//        }
//        return null;
        return "SprrowZ"+"zzg.db";
    }

    /**
     * 创建数据表
     * @param db 数据库
     */
    private void createTables(SQLiteDatabase db) {
        long beginTime = System.currentTimeMillis();
        try {
            getTableDef("test"); // 装载数据表定义
            // 初始化表定义， 在Sqlite数据库中创建相应的表。
            Iterator it = tables.keySet().iterator();
            while (it.hasNext()) {
                String tblName = (String) it.next();
                TableDef tblDef = (TableDef) tables.get(tblName);
                if (!tableIsExist(db, tblDef.getName())) {
                    // 表不存在则创建表
                    db.execSQL(tblDef.getCreateTblSql());
                } else {
                    // 表存在，则检查表中字段是否有不存在的字段。
                    createNotExistCols(db, tblDef);
                }

                //判断是否需要创建索引
                if (tblDef.isNeedIndex()) {
                    if (!indexIsExist(db,tblDef.getIndexName())) {
                        // 创建索引
                        db.execSQL(tblDef.getCreateIndexSql());
                    }
                }
                //如果需要创建虚拟表，就创建虚拟表，便于实现全文检索
                if (tblDef.getNeedVirtualTable()){
                    if (!tableIsExist(db, tblDef.getName()+TableDef._virtual)) {
                        // 表不存在则创建表
                        db.execSQL(tblDef.getCreateVirtualTableTblSql());
                        Log.i("sql====",tblDef.getCreateVirtualTableTblSql());

                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        }
        long result = (System.currentTimeMillis() - beginTime);
        Log.d(TAG, "---------------createTables:" + result);
    }

    /**
     * 创建不存在的字段
     * @param db 数据库
     * @param tblDef 表定义
     * @return 是否创建了columns
     */
    private boolean createNotExistCols(final SQLiteDatabase db, final TableDef tblDef) {
        List<String> cols = findNotExistCols(db, tblDef);
        if(cols.size() > 0) {
            try {
                for (String col : cols) {
                    StringBuilder sql = new StringBuilder();
                    sql.append("ALTER TABLE ").append(tblDef.getName());
                    sql.append(" add column ");
                    Bean colBean = tblDef.getCol(col);
                    sql.append(colBean.getStr("name"));
                    sql.append(" ").append(colBean.getStr("type"));
                    Log.d(TAG, "Add Columns:" + sql.toString());
                    db.execSQL(sql.toString());
                }
                //如果存在虚拟表，执行删除，创建，拷贝数据
                if (tblDef.getNeedVirtualTable()){
                    //删除虚拟表
                    db.execSQL("DROP TABLE "+tblDef.getName()+TableDef._virtual);
                    //创建虚拟表
                    db.execSQL(tblDef.getCreateVirtualTableTblSql());
                    Log.i("sql====",tblDef.getCreateVirtualTableTblSql());
                    //拷贝数据 insertBySqlite into 虚拟表 select * from 真实表
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            AnyDao dao=new AnyDao(tblDef.getName());
                            List<Bean> beanList=dao.finds(null);
                            for (Bean bean:beanList){
                                //符号化存储
                                dao.saveToVirtualTable(bean);
                            }
//不能使用导入表了，因为虚拟表要符号化数据
//                            db.execSQL("insertBySqlite into "+tblDef.getName()+TableDef._virtual+
//                                    " select * from "+tblDef.getName());
                        }
                    }).start();

                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage(), e);
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /**
     *
     * @param db
     * @param tblDef
     * @return
     */
    private List<String> findNotExistCols(SQLiteDatabase db, TableDef tblDef) {
        List<String> result = new ArrayList<String>();
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tblDef.getName() + " LIMIT 0"
                    , null);
            if(cursor != null) {
                String[] cols = tblDef.getColNames();
                if(cols != null) {
                    for(String colName: cols) {
                        if(cursor.getColumnIndex(colName) == -1) {
                            result.add(colName);
                        }
                    }
                }
            }
        } catch (Exception e) {
//            EMLog.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 创建表结构定义JSON文件
     *
     * @param context 安卓上下文对象
     */
    private static void loadTableDefFile(Context context) {
        tables = new ConcurrentHashMap<String, TableDef>();
        InputStream is = context.getResources().openRawResource(R.raw.table_define);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);
        }

        String jsonString = writer.toString();
        List<Bean> list = Old_JsonUtils.toBeanList(jsonString);

        // 初始化表定义
        for (Bean bean : list) {
            TableDef tblDef = new TableDef(bean);
            tables.put(tblDef.getName(), tblDef);
        }
    }

    /**
     * 获取指定表的所有栏目名称
     * @param db - db object
     * @param tableName - 表名
     * @return
     */
    public List<String> getColNames(SQLiteDatabase db, String tableName) {
        if (! tableIsExist(db, tableName)) {
            return null;
        }
        List<String> result = new ArrayList<String>();
        Cursor cursor = null;
        try {
            //查询一行
            cursor = db.rawQuery("SELECT * FROM " + tableName + " LIMIT 0"
                    , null);
            if(cursor != null) {
                String [] columnsArray = cursor.getColumnNames();
                for (String column : columnsArray) {
                    result.add(column);
                }
            }
        } catch (Exception e) {
//            EMLog.e(TAG,"checkColumnExists1..." + e.getMessage()) ;
        } finally {
            if (null != cursor && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return result;
    }


    public TableDef createTableDef(String tableName, List<String> columns) {
       /* *  表结构定义JSON字符串如下：
        *  {
        *      "table" : "users",
        *      "key": "USER_CODE",
        *      "cols": [{
        *          name : "USER_CODE",
        *          type : "TEXT"
        *      }, {
        *          name : "USER_NAME",
        *          type : "TEXT"
        *      }],
        *      "order": "USER_NAME desc"
        *  }
        **/
        Bean tableBean = new Bean();
        tableBean.set("table", tableName);
        tableBean.set("key", "_PK_");
        List<Bean> cols = new ArrayList<Bean>();
        for (String columnName: columns) {
            cols.add(new Bean().set("name", columnName).set("type", "TEXT"));
        }

        tableBean.set("cols", cols);
        TableDef tableDef = new TableDef(tableBean);
        if (tableName.endsWith("_virtual")){
            tableDef.setNeedVirtualTable("1");
        }
        return tableDef;
    }
}
