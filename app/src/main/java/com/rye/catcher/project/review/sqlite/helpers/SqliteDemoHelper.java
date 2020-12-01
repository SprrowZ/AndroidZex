package com.rye.catcher.project.review.sqlite.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Create by rye
 * at 2020-10-10
 *
 * @description:
 */
public class SqliteDemoHelper {
    public static List<String> getBaseActions(){
       List<String> datas = new ArrayList<>();
       datas.add("插入假数据");
       datas.add("查询所有数据");
       datas.add("更新数据");
       datas.add("删除所有数据");
        return datas;
    }
}
