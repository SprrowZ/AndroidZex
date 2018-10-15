package com.rye.catcher.project.dao;

import android.content.Context;

import com.rye.catcher.utils.ExtraUtil.Bean;


/**
 * 保存系统常用的一些KEY_VALUE值。例如：好友通讯录的更新时间等
 * Created by jinyunyang on 15/3/13.
 */
public class KeyValueDao extends BaseDao {
    public KeyValueDao(Context context) {
        super();
    }

    @Override
    public String getTableName() {
        return "sy_key_value";
    }

    /**
     *
     * @param key 数据主键
     * @param value 数据值
     */
    public void save(String key, String value) {
        Bean user = new Bean();
        user.set("ITEM_KEY", key);
        user.set("ITEM_VALUE", value);
        this.save(user);
    }

    /**
     *
     * @param key 数据主键
     * @return 数据值
     */
    public String getValue(String key) {
        Bean bean = this.find(key);
        if(bean == null) {
            return "";
        }

        return bean.getStr("ITEM_VALUE");
    }
}
