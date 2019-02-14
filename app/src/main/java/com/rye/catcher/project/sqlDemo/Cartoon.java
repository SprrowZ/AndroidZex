package com.rye.catcher.project.sqlDemo;

import java.sql.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created at 2019/2/13.
 *
 * @author Zzg
 * @function:
 */
public class Cartoon  extends ConcurrentHashMap<String,Object>{
    public static String ID="ID";
    public static String NAME="NAME";
    public static String ACTORS="ACTORS";
    public static String LEAD="LEAD";
    public static String ISSUE_TIME="ISSUE_TIME";
    public static String DIRECTOR="DIRECTOR";
    public static String IS_END="IS_END";
    public static String NATIONALITY="NATIONALITY";
    public static String DETAILS="DETAILS";
    public static String INSERT_TIME="INSERT_TIME";

    public Object getValue(String key){
        return super.get(key);
    }
    public void setValue(String key,Object value){
        if (value!=null){
            super.put(key,value);
        }else{
            super.put(key,"");
        }

    }

}
