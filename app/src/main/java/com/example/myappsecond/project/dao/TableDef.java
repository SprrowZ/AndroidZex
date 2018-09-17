package com.example.myappsecond.project.dao;



import com.example.myappsecond.utils.ExtraUtil.Bean;
import com.example.myappsecond.utils.JsonUtils;
import com.example.myappsecond.utils.StringUtils;

import java.util.List;

/**
 * 表结构定义类
 * Created by jinyunyang on 15/3/9.
 */
public class TableDef {
    private final static String SELECTS = "_selects_";
    private Bean defBean = new Bean();
    private Bean colsDef = new Bean();
    /**
     * 虚拟表 表名关键字
     * */
    public static final String _virtual="_virtual";
    private Bean indexBean;

    /**
     *  表结构定义JSON字符串如下：
     *  {
     *      "table" : "users",
     *      "key": "USER_CODE",
     *      "virtualtable":"0",
     *      "cols": [{
     *          name : "USER_CODE",
     *          type : "TEXT"
     *      }, {
     *          name : "USER_NAME",
     *          type : "TEXT"
     *      }],
     *      "order": "USER_NAME desc"
     *  }
     * @param defJson 表结构定义JSON字符串。
     */
    public TableDef(String defJson) {
        this.defBean = JsonUtils.toBean(defJson);
        initCols();
    }

    private void initCols() {
        List<Bean> cols = this.defBean.getList("cols");
        for(Bean col: cols) {
            this.colsDef.put(col.getStr("name"), col);
        }
    }

    /**
     *
     * @param bean 表定义Bean
     */
    public TableDef(Bean bean) {
        this.defBean = bean;
        initCols();
    }

    /**
     *
     * @return 主键字段名称
     */
    public String getPK() {
        return defBean.getStr("key");
    }

    /**
     *
     * @return 是否存在默认排序字段
     */
    public boolean existOrder() {
        return defBean.isNotEmpty("order");
    }

    /**
     *
     * @return 默认排序顺序
     */
    public String getOrder() {
        return defBean.getStr("order");
    }

    /**
     *
     * @return 表名
     */
    public String getName() {
        return this.defBean.getStr("table");
    }

    /**
     * @return 是否需要创建虚拟表
     * */
    public boolean getNeedVirtualTable(){
        return false;
//        return StringUtils.isNotEmpty(this.defBean.getStr("fts"));
    }
    /**
     * 虚拟表fts3 or fts4;
     * */
    public String getVirtualTableUsing(){
        return this.defBean.getStr("fts");
    }

    /**
     * @param _virtual 0 不是虚拟表 1 是虚拟表
     * */
    public void setNeedVirtualTable(String _virtual){
         this.defBean.set("virtualtable",_virtual);
    }
    /**
     *
     * @return selects select语句中使用的字段名称
     */
    public String[] getSelects() {
        if(defBean.isNotEmpty(SELECTS)) {
            return (String[]) defBean.get(SELECTS);
        }
        String[] colNames = getColNames();
        defBean.set(SELECTS, colNames);

        return colNames;
    }

    public String[] getColNames() {
        List<Bean> cols = defBean.getList("cols");
        String[] colNames = new String[cols.size()];
        for(int i=0; i< cols.size(); i++) {
            Bean col = cols.get(i);
            colNames[i] = col.getStr("name");
        }
        return colNames;
    }

    public Bean getCol(String colName) {
        return colsDef.getBean(colName);
    }

    public boolean existCol(String colName) {
        return colsDef.containsKey(colName);
    }
    /**
     * fts3 or fts4
     * @return 创建虚拟表结构的SQL语句
     */
    public String getCreateVirtualTableTblSql() {
        StringBuilder sql = new StringBuilder("CREATE VIRTUAL TABLE ");
        //虚拟表名字为:[真实表名字_virtual]
        sql.append(this.getName()+TableDef._virtual);
        sql.append(" USING "+ getVirtualTableUsing()+" ");
        List<Bean> cols = defBean.getList("cols");
        sql.append("(");
        String key = this.getPK();
        String type="";
        for(Bean col: cols) {
            String name = col.getStr("name");
            sql.append(name).append(" ").append(col.getStr("type"));
            if(name.equals(key)) {
                sql.append(" PRIMARY KEY");
                type=col.getStr("type");
            }
            sql.append(",");
        }

        sql.append(",");
        if(cols.size() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }

        sql.append(");");
        return sql.toString();
    }
    /**
     * @return 创建表结构的SQL语句
     */
    public String getCreateTblSql() {
        StringBuilder sql = new StringBuilder("CREATE TABLE ");
        sql.append(this.getName());
        List<Bean> cols = defBean.getList("cols");
        sql.append("(");
        String key = this.getPK();
        String type="";
        for(Bean col: cols) {
            String name = col.getStr("name");
            sql.append(name).append(" ").append(col.getStr("type"));
            if(name.equals(key)) {
                sql.append(" PRIMARY KEY");
                type=col.getStr("type");
            }
            sql.append(",");
        }


        if(cols.size() > 0) {
            sql.deleteCharAt(sql.length() - 1);
        }

        sql.append(");");
        return sql.toString();
    }


    /**
     * @return 是否需要创建索引
     * */
    public boolean isNeedIndex(){
        String value = this.defBean.getStr("indexs");
        if (StringUtils.isNotEmpty(value)) {
            indexBean = JsonUtils.toBean(value);
            return true;
        }
        return false;
    }


    public String getCreateIndexSql() throws Exception {

        StringBuilder sql = new StringBuilder("CREATE INDEX ");
        sql.append(getIndexName());
        sql.append(" ON ");
        sql.append(this.getName());
        sql.append(" ( ");
        sql.append( getIndexColsStr() );
        sql.append(" );");

        return sql.toString();
    }

    public String getIndexColsStr() {
        return indexBean.getStr("columns");
    }

    public String getIndexName() {
        return indexBean.getStr("name");
    }

}
