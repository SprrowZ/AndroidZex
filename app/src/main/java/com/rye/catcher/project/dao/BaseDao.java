package com.rye.catcher.project.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.catcher.utils.ExtraUtil.Lang;
import com.rye.catcher.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 数据操作基本方法
 * Created by jinyunyang on 15/3/9.
 */
public abstract class BaseDao {

    /**
     * true-表示使用虚拟表全文搜索
     * false-表示使用like进行搜索
     */
    private static final Boolean FULL_SEARCH = false;

    public BaseDao() {
    }

    private DbOpenHelper getDbOpenHelper() {
        return DbOpenHelper.getInstance();
    }

    /**
     * 抽象方法，需要子类实现此方法。
     *
     * @return 获取表名
     */
    public abstract String getTableName();

    /**
     * @return 表定义
     */
    public TableDef getTblDefine() {
        DbOpenHelper helper = getDbOpenHelper();
        if (helper == null) {
            return null;
        }
        TableDef def = helper.getTableDef(getTableName());
        if (def != null) {
            return def;
        }

        //get tableDefine From Database
        def = getTableDefFromDatabase(getTableName());
        if (def != null) {
            return def;
        }

        Log.e("DB", "Table not found:" + getTableName());
        return null;
    }

    /**
     * @param id 数据ID
     * @return 指定ID对应的数据Bean，没找到返回空
     */
    public Bean find(String id) {
        TableDef tblDef = getTblDefine();
        if (tblDef == null) {
            return null;
        }
        SqlBean sqlBean = new SqlBean();
        sqlBean.and(tblDef.getPK(), id);
        if (StringUtils.isEmpty(id)) {
            Log.e("basedao", "find: id不能为null");
            return null;
        }
        List<Bean> list = this.finds(sqlBean);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    /***
     * 全文检索，只有虚拟表可用(建议放到子线程中使用)
     * 有两种不同的实现 match虚拟表查询和
     *
     * @param value 查询所需关键词
     */
    public List<Bean> fullSearch(String value, int num) {
        FullSearchInter fullSearchInter = this instanceof FullSearchInter ? ((FullSearchInter) this) : null;
        if (fullSearchInter == null) {
            throw new UnsupportedOperationException("若您需要全局搜索，请实现FullSearchInter接口");
        }
        List<Bean> result;
        if (FULL_SEARCH) {
            result = fullSearchInter.searchByMatch(value, num);
        } else {
            result = fullSearchInter.searchByLike(value, num);
        }
        return result;
    }

    /***
     * 全文检索，只有虚拟表可用(建议放到子线程中使用)
     * 有两种不同的实现 match虚拟表查询和
     *
     * @param value 查询所需关键词
     */
    public List<Bean> fullSearch(String value) {
        return fullSearch(value, 0);
    }

    /**
     * 根据查询数量进行全文检索
     * 如果查询数量为0，则查询全部
     */
    public List<Bean> Match(String value, int count) {
        SqlBean sqlBean = new SqlBean();
        sqlBean.getWhere().append(" and ").append(getTableName() + TableDef._virtual).
                append(" MATCH " + "'" + MatchUtils.tokenizeBySmart(value) + "*'");
        if (count > 0) {
            sqlBean.limit(count);
        }

        List<Bean> list = match(sqlBean);

        return list;
    }

    /**
     * 执行sql语句
     *
     * @param sb
     * @return
     */
    protected List<Bean> rawQueryForResult(StringBuilder sb) {

        List<Bean> result = new ArrayList<>();
        TableDef tblDef = getTblDefine();
        if (tblDef == null) {
            return result;
        }

        // 取得数据库对象
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return result;
        }
        SQLiteDatabase db = dboh.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sb.toString(), null);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                Bean bean = new Bean();
                //遍历cursor的列，组装Bean
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String col = columnNames[i]; //第i列的名称
                    String value1 = cursor.getString(i); //第i列的值
                    bean.set(col, value1);
                }
                //设置_PK_
                if (bean.isNotEmpty(tblDef.getPK())) {
                    bean.setId(bean.getStr(tblDef.getPK()));
                }

                result.add(bean);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * 执行sql语句
     *
     * @param sb
     * @return
     */
    protected boolean execSQL(StringBuilder sb) {
        // 取得数据库对象
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return false;
        }
        SQLiteDatabase db = dboh.getReadableDatabase();
        try {
            db.execSQL(sb.toString());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
        }
    }


    /**
     * 根据查询数量进行全文检索
     * 如果查询数量为0，则查询全部
     */
    public List<Bean> match(SqlBean sql) {
        //查询虚拟表数据
        List<String> matchList = this.MatchFinds(sql);

        if (matchList.size() == 0) {
            return new ArrayList<Bean>();
        }

        String pk = getTblDefine().getPK();
        SqlBean sqlBean1 = new SqlBean();
        sqlBean1.andIn(pk, matchList);

        List<Bean> list = this.finds(sqlBean1);

        return list;
    }

    /**
     * @param queryBean 查询Bean
     * @return 查询结果
     * 私有方法，不对外开放
     */
    protected List<String> MatchFinds(SqlBean queryBean) {
        if (queryBean == null) {
            queryBean = new SqlBean();
        }

        List<String> result = new ArrayList<>();
        TableDef tblDef = getTblDefine();
        if (!tblDef.getNeedVirtualTable()) {
            //如果不是虚拟表，直接报错
            return result;
        }
        if (tblDef == null) {
            return result;
        }

        String[] cols;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_SELECT)) {
            cols = queryBean.getStr(DaoConstant.PARAM_SELECT).split(",");
        } else {
            cols = tblDef.getSelects();
        }
        String where = null;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_WHERE)) {
            where = " 1==1 " + queryBean.getWhere();
        }
        String orderby = null;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_ORDER)) {
            orderby = queryBean.getStr(DaoConstant.PARAM_ORDER);
        } else if (tblDef.existOrder()) {
            orderby = tblDef.getOrder();
        }

        String[] args = queryBean.getVarStrings();
        String limit = queryBean.getLimitClause();
        // 取得数据库对象
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return new ArrayList<>();
        }
        SQLiteDatabase db = dboh.getReadableDatabase();
        if (!db.isOpen()) {
            return new ArrayList<>();
        }
        Cursor cursor = null;
        try {
            cursor = db.query(false, (tblDef.getName() + TableDef._virtual), cols, where
                    , args, null, null, orderby, limit);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                Bean bean = new Bean();
                //遍历cursor的列，组装Bean
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    String col = columnNames[i]; //第i列的名称
                    String value = cursor.getString(i); //第i列的值
                    bean.set(col, value);
                }
                //设置_PK_
                if (bean.isNotEmpty(tblDef.getPK())) {
                    bean.setId(bean.getStr(tblDef.getPK()));
                }

                result.add(bean.getId());
            }
        } catch (Exception e) {
            return new ArrayList<>();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    /**
     * @param queryBean 查询Bean
     * @return 查询结果
     */
    public List<Bean> finds(SqlBean queryBean) {
        if (queryBean == null) {
            queryBean = new SqlBean();
        }

        List<Bean> result = new ArrayList<>();
        TableDef tblDef = getTblDefine();
        if (tblDef == null) {
            return result;
        }

        String[] cols;
        String cols_str;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_SELECT)) {
            cols = queryBean.getStr(DaoConstant.PARAM_SELECT).split(",");
            String s = Arrays.toString(cols);
            cols_str = s.substring(1, s.length() - 1);
        } else {
            cols = tblDef.getSelects();
            cols_str = "*";
        }
        String where = null;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_WHERE)) {
            where = " 1==1 " + queryBean.getWhere();
        }
        String orderby = null;
        if (queryBean.isNotEmpty(DaoConstant.PARAM_ORDER)) {
            orderby = queryBean.getStr(DaoConstant.PARAM_ORDER);
        } else if (tblDef.existOrder()) {
            orderby = tblDef.getOrder();
        }

        String[] args = queryBean.getVarStrings();
        String limit = queryBean.getLimitClause();
        if (true) {
            Log.e(BaseDao.this.getClass().getSimpleName() + "查询语句", "select " + cols_str + " from " + tblDef.getName() + " where " + where);
            Log.e(BaseDao.this.getClass().getSimpleName() + "查询参数", Arrays.toString(args) + "," + limit + "," + orderby);
        }
        // 取得数据库对象
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return result;
        }
        SQLiteDatabase db = dboh.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(false, tblDef.getName(), cols, where
                    , args, null, null, orderby, limit);
            String[] columnNames = cursor.getColumnNames();
            while (cursor.moveToNext()) {
                Bean bean = new Bean();
                //遍历cursor的列，组装Bean
                for (int i = 0; i < cursor.getColumnCount(); i++) {
                    try {
                        String col = columnNames[i]; //第i列的名称
                        String value = cursor.getString(i); //第i列的值
                        bean.set(col, value);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                //设置_PK_
                if (bean.isNotEmpty(tblDef.getPK())) {
                    bean.setId(bean.getStr(tblDef.getPK()));
                }

                result.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return result;
    }

    protected boolean beforeSave(Bean saveBean, Bean oldBean) {
        return true;
    }

    /**
     * 一般的批量保存方法
     */
    public void batchSave(List<Bean> list) {
        for (Bean saveBean : list) {
            save(saveBean);
        }
    }

    /**
     * 高效的批量保存方法
     * 如果有虚拟表，同时更新虚拟表
     */
    public void batchSaveEfficient(List<Bean> list) {
        Log.d("batchSave--start--", "size:" + list.size());
        long start = System.currentTimeMillis();
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();

        db.beginTransaction(); //开启事务
        try {
            TableDef tblDef = this.getTblDefine();


            for (Bean saveBean : list) {
                ContentValues values = new ContentValues();
                ContentValues values_v = null;
                if (tblDef.getNeedVirtualTable()) {
                    values_v = new ContentValues();
                }
                if (!tblDef.existCol(tblDef.getPK())) {
                    throw new RuntimeException("无效的主键");
                } else if (saveBean.isEmpty(tblDef.getPK())) { //如果没有主键，添加主键
                    saveBean.set(tblDef.getPK(), Lang.getUUID());
                }

                for (Object key : saveBean.keySet()) {
                    String colName = (String) key;
                    if (tblDef.existCol(colName)) {
                        values.put(colName, saveBean.getStr(colName));
                    }
                    //给虚拟表增加准备更新数据
                    if (values_v != null) {
                        if (tblDef.existCol(colName)) {//如果存在此字段，才去更新;
                            if (colName.equals(tblDef.getPK())) {//主键不被符号化
                                values_v.put(colName, saveBean.getStr(colName));
                            } else {
                                values_v.put(colName,
                                        MatchUtils.tokenize(saveBean.getStr(colName)));
                            }
                        }
                    }
                }

                db.replace(tblDef.getName(), null, values);
                //是否有虚拟表，如果有，则更新虚拟表
                if (tblDef.getNeedVirtualTable()) {
                    db.replace((tblDef.getName() + TableDef._virtual), null, values_v);
                }
            }
            db.setTransactionSuccessful(); //提交
        } catch (Exception e) {
            Log.e("batchSave", e.toString());
        } finally {
            db.endTransaction(); //关闭事务
        }

        long end = System.currentTimeMillis();
        Log.d("batchSave--end--", "time:" + (end - start) / 1000.0f + "s");
    }


    /**
     * 高效的保存方法:直接replace，不管之前是否有同一条数据；
     * 如果有虚拟表，同时更新虚拟表
     */
    public void replaceOne(Bean saveBean) {
        long start = System.currentTimeMillis();
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        db.beginTransaction(); //开启事务
        try {
            TableDef tblDef = this.getTblDefine();

            ContentValues values = new ContentValues();
            ContentValues values_v = null;
            if (tblDef.getNeedVirtualTable()) {
                values_v = new ContentValues();
            }
            if (!tblDef.existCol(tblDef.getPK())) {
                throw new RuntimeException("无效的主键");
            } else if (saveBean.isEmpty(tblDef.getPK())) { //如果没有主键，添加主键
                saveBean.set(tblDef.getPK(), Lang.getUUID());
            }

            for (Object key : saveBean.keySet()) {
                String colName = (String) key;
                if (tblDef.existCol(colName)) {
                    values.put(colName, saveBean.getStr(colName));
                }
                //给虚拟表增加准备更新数据
                if (values_v != null) {
                    if (tblDef.existCol(colName)) {//如果存在此字段，才去更新;
                        if (colName.equals(tblDef.getPK())) {//主键不被符号化
                            values_v.put(colName, saveBean.getStr(colName));
                        } else {
                            values_v.put(colName,
                                    MatchUtils.tokenize(saveBean.getStr(colName)));
                        }
                    }
                }
            }

            db.replace(tblDef.getName(), null, values);
            //是否有虚拟表，如果有，则更新虚拟表
            if (tblDef.getNeedVirtualTable()) {
                db.replace((tblDef.getName() + TableDef._virtual), null, values_v);
            }
            db.setTransactionSuccessful(); //提交
        } catch (Exception e) {
            Log.e("batchSave", e.toString());
        } finally {
            db.endTransaction(); //关闭事务
        }

        long end = System.currentTimeMillis();
        Log.d("batchSave--end--", "time:" + (end - start) / 1000.0f + "s");
    }

//    IDUtils.APP_PREFIX_OLD

    /**
     * 删除老数据
     */
    public void delete_old_data() {
        SqlBean sqlBean = new SqlBean();
        sqlBean.andLike("CHATTER", IDUtils.APP_PREFIX_OLD);
        delete(sqlBean);
    }

    /**
     * 保存数据
     *
     * @param saveBean 数据Bean
     */
    public void save(Bean saveBean) {
        DbOpenHelper dboh = getDbOpenHelper();
        if (dboh==null) {
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();

        ContentValues values = new ContentValues();
        TableDef tblDef = this.getTblDefine();

        ContentValues values_v = null;
        if (tblDef.getNeedVirtualTable()) {
            values_v = new ContentValues();
        }
        if (tblDef == null) {
            throw new RuntimeException("TableDef is null.");
        }
        if (!tblDef.existCol(tblDef.getPK())) {
            throw new RuntimeException("无效的主键");
        } else if (saveBean.isEmpty(tblDef.getPK())) { //如果没有主键，添加主键
            saveBean.set(tblDef.getPK(), Lang.getUUID());
        }

        for (Object key : saveBean.keySet()) {
            String colName = (String) key;
            if (tblDef.existCol(colName)) {
                values.put(colName, saveBean.getStr(colName));
                if (values_v != null) {
                    if (colName.equals(tblDef.getPK())) {//主键不被符号化
                        values_v.put(colName, saveBean.getStr(colName));
                    } else {
//                        values_v.put(colName,
//                                MatchUtils.tokenize(saveBean.getStr(colName)));
                        if (FULL_SEARCH) {//根据变量判断是否符号化
                            if (saveBean.getStr(colName).length() < 200) {//如果长度大于200，不符号化；否则有性能问题
                                values_v.put(colName,
                                        MatchUtils.tokenize(saveBean.getStr(colName)));
                            } else {
                                values_v.put(colName, saveBean.getStr(colName));
                            }

                        } else {
                            values_v.put(colName, saveBean.getStr(colName));
                        }
                    }
                }
            }
        }


        if (!db.isOpen()) { // 数据库没有连接，则退出
            return;
        }

        String where = tblDef.getPK() + " = ? ";
        String[] args = {saveBean.getStr(tblDef.getPK())};
        // 查询是否有老数据
        Cursor cursor = db.query(this.getTableName(), null, where, args, null, null, null);
        try {
            if (cursor.moveToNext()) { // 有数据，则更新
                Bean oldBean = toBean(cursor);
                boolean result = beforeSave(saveBean, oldBean);
                if (result) {
                    db.update(this.getTableName(), values, where, args);
                    //是否有虚拟表，如果有，则更新虚拟表
                    if (tblDef.getNeedVirtualTable()) {
                        db.update(tblDef.getName() + TableDef._virtual, values_v, where, args);
                    }
                }
            } else { // 无数据，则插入
                db.insert(tblDef.getName(), null, values);
                //是否有虚拟表，如果有，则更新虚拟表
                if (tblDef.getNeedVirtualTable()) {
                    db.insert(tblDef.getName() + TableDef._virtual, null, values_v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 修改数据
     *
     * @param saveBean 数据Bean
     */
    public void update(Bean saveBean) {
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        ContentValues values = new ContentValues();
        TableDef tblDef = this.getTblDefine();

        ContentValues values_v = null;
        if (tblDef.getNeedVirtualTable()) {
            values_v = new ContentValues();
        }
        if (tblDef == null) {
            throw new RuntimeException("TableDef is null.");
        }
        if (!tblDef.existCol(tblDef.getPK())) {
            throw new RuntimeException("无效的主键");
        } else if (saveBean.isEmpty(tblDef.getPK())) { //如果没有主键，添加主键
            saveBean.set(tblDef.getPK(), Lang.getUUID());
        }

        for (Object key : saveBean.keySet()) {
            String colName = (String) key;
            if (tblDef.existCol(colName)) {
                values.put(colName, saveBean.getStr(colName));
                if (values_v != null) {
                    if (colName.equals(tblDef.getPK())) {//主键不被符号化
                        values_v.put(colName, saveBean.getStr(colName));
                    } else {
//                        values_v.put(colName,
//                                MatchUtils.tokenize(saveBean.getStr(colName)));
                        if (FULL_SEARCH) {//根据变量判断是否符号化
                            if (saveBean.getStr(colName).length() < 200) {//如果长度大于200，不符号化；否则有性能问题
                                values_v.put(colName,
                                        MatchUtils.tokenize(saveBean.getStr(colName)));
                            } else {
                                values_v.put(colName, saveBean.getStr(colName));
                            }

                        } else {
                            values_v.put(colName, saveBean.getStr(colName));
                        }
                    }
                }
            }
        }


        if (!db.isOpen()) { // 数据库没有连接，则退出
            return;
        }
        String where = tblDef.getPK() + " = ? ";
        String[] args = {saveBean.getStr(tblDef.getPK())};
        try {
            db.update(this.getTableName(), values, where, args);
            //是否有虚拟表，如果有，则更新虚拟表
            if (tblDef.getNeedVirtualTable()) {
                db.update(tblDef.getName() + TableDef._virtual, values_v, where, args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 保存数据
     *
     * @param saveBean 数据Bean
     */
    public void saveToVirtualTable(Bean saveBean) {
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        TableDef tblDef = this.getTblDefine();

        ContentValues values_v = null;
        if (tblDef.getNeedVirtualTable()) {
            values_v = new ContentValues();
        }
        if (tblDef == null) {
            throw new RuntimeException("TableDef is null.");
        }
        if (!tblDef.existCol(tblDef.getPK())) {
            throw new RuntimeException("无效的主键");
        } else if (saveBean.isEmpty(tblDef.getPK())) { //如果没有主键，添加主键
            saveBean.set(tblDef.getPK(), Lang.getUUID());
        }

        for (Object key : saveBean.keySet()) {
            String colName = (String) key;
            if (tblDef.existCol(colName)) {
                if (values_v != null) {
                    if (colName.equals(tblDef.getPK())) {//主键不被符号化
                        values_v.put(colName, saveBean.getStr(colName));
                    } else {
                        values_v.put(colName,
                                MatchUtils.tokenize(saveBean.getStr(colName)));
                    }
                }
            }
        }

        if (!db.isOpen()) { // 数据库没有连接，则退出
            return;
        }

        String where = tblDef.getPK() + " = ? ";
        String[] args = {saveBean.getStr(tblDef.getPK())};
        // 查询是否有老数据
        Cursor cursor = db.query(this.getTableName(), null, where, args, null, null, null);
        try {
            if (cursor.moveToNext()) { // 有数据，则更新
                Bean oldBean = toBean(cursor);
                boolean result = beforeSave(saveBean, oldBean);
                if (result) {

                    //是否有虚拟表，如果有，则更新虚拟表
                    if (tblDef.getNeedVirtualTable()) {
                        db.update(tblDef.getName() + TableDef._virtual, values_v, where, args);
                    }
                }
            } else { // 无数据，则插入
                //是否有虚拟表，如果有，则更新虚拟表
                if (tblDef.getNeedVirtualTable()) {
                    db.insert(tblDef.getName() + TableDef._virtual, null, values_v);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    /**
     * 把cursor中的数据保存到Bean对象中
     *
     * @param cursor 数据Cursor对象
     * @return 单条记录的Bean对象
     */
    private Bean toBean(Cursor cursor) {
        Bean bean = new Bean();
        int count = cursor.getColumnCount();
        for (int i = 0; i < count; i++) {
            try {
                final String value = cursor.getString(i);//此处取大数据量的数据有问题，可能是存base64之类的gif太大，取不出来，导致崩溃;
                final String name = cursor.getColumnName(i);
                bean.set(name, value);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bean;
    }


    /**
     * 删除指定ID记录
     *
     * @param id 指定ID
     * @return 影响的行数。0表示未找到数据，-1表示数据库异常，1表示成功删除一条记录。
     */
    public int delete(String id) {
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return  -1;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        if (db.isOpen()) {
            TableDef tblDef = this.getTblDefine();
            if (tblDef == null) {
                return -1;
            }
            String where = tblDef.getPK() + " = ?";
            String[] args = {id};
            return db.delete(this.getTableName(), where, args);
        }

        return -1;
    }

    /**
     * 删除记录
     *
     * @param deleteQuery - 删除过滤
     */
    public int delete(SqlBean deleteQuery) {
        if (deleteQuery == null) {
            deleteQuery = new SqlBean();
        }
        String where = null;
        if (deleteQuery.isNotEmpty(DaoConstant.PARAM_WHERE)) {
            where = " 1==1 " + deleteQuery.getWhere();
        }

        String[] args = deleteQuery.getVarStrings();
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return  -1;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        if (db.isOpen()) {
            int a = db.delete(this.getTableName(), where, args);
            if (this.getTblDefine().getNeedVirtualTable()) {
                db.delete(this.getTblDefine().getName() + TableDef._virtual, where, args);
            }
            return a;
        } else {
            return -1;
        }
    }

    /**
     * 取得记录个数
     */
    public int count(SqlBean sqlBean) {
        if (sqlBean == null) {
            sqlBean = new SqlBean();
        }

        sqlBean.selects("count(*) as _COUNT_");
        List<Bean> list = finds(sqlBean);
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(0).getInt("_COUNT_");
        }
    }

    /**
     * 清除当前表里所有数据
     */
    public void clear() {
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        if (db.isOpen()) {
            db.delete(getTableName(), null, null);
            if (getTblDefine().getNeedVirtualTable()) {
                db.delete(getTableName() + TableDef._virtual, null, null);
            }
        }
    }

    private TableDef getTableDefFromDatabase(String table) {
        DbOpenHelper dboh = getDbOpenHelper();
        if(dboh==null){
            return null;
        }
        SQLiteDatabase db = dboh.getWritableDatabase();
        List<String> columns = dboh.getColNames(db, table);
        return dboh.createTableDef(table, columns);
    }

}
