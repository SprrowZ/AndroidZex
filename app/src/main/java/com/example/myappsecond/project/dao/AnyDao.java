package com.example.myappsecond.project.dao;

/**
 * Created by chen on 2016/5/25 0025.
 * 传入表名即可操作对应表
 */
public class AnyDao extends BaseDao {
    private String tableName=null;
    public AnyDao(String tableName) {
        super();
        this.tableName=tableName;
    }
    @Override
    public String getTableName() {
        return tableName;
    }
}
