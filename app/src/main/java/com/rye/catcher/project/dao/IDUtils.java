package com.rye.catcher.project.dao;

/**
 * Created by well on 15/3/16.
 *
 */
public class IDUtils {
    /**
     * id类型
     */
    public enum IDType {
        TYPE_USER,
        TYPE_GROUP,
        TYPE_APP
    }

    public static final String USER_PREFIX = "u__";
    public static final String GROUP_PREFIX = "g__";
    public static final String APP_PREFIX_OLD = "a__APP^";
    public static final String APP_PREFIX = "a__";
    /**
     *
     * @param code - code
     */
    public static boolean isFullId(String  code){
        if (code.startsWith(USER_PREFIX)) {
          return true;
        } else if (code.startsWith(GROUP_PREFIX)) {
          return true;
        } else if (code.startsWith(APP_PREFIX)) {
           return true;
        }
        return  false;
    }
    /**
     *
     * @param code - code
     * @param type - 1:user, 2:group, 3:app
     */
    public static String getFullId(String code, IDType type) {
        if (code.startsWith(USER_PREFIX)) {
            code = code.substring(USER_PREFIX.length());
            return getFullId(code, IDType.TYPE_USER);
        } else if (code.startsWith(GROUP_PREFIX)) {
            code = code.substring(GROUP_PREFIX.length());
            return getFullId(code, IDType.TYPE_GROUP);
        }else if (code.startsWith(APP_PREFIX_OLD)) {
            code = code.substring(APP_PREFIX.length());
            return getFullId(code, IDType.TYPE_APP);
        }else if (code.startsWith(APP_PREFIX)) {
            code = code.substring(APP_PREFIX.length());
            return getFullId(code, IDType.TYPE_APP);
        }
        String result = "";

        if (IDType.TYPE_USER == type) {
            result =  USER_PREFIX + code;
        } else if ( IDType.TYPE_GROUP == type) {
            result =  GROUP_PREFIX + code;
        } else if ( IDType.TYPE_APP == type) {
            result =  APP_PREFIX + code;
        }
        return result;
    }


    /**
     * 获取id
     */
    public static String getId(String fullId) {
        String code;
        if (fullId.startsWith(USER_PREFIX)) {
            code = fullId.substring(USER_PREFIX.length());
        } else if (fullId.startsWith(GROUP_PREFIX)) {
            code = fullId.substring(GROUP_PREFIX.length());
        } else if (fullId.startsWith(APP_PREFIX_OLD)) {
            code = fullId.substring(APP_PREFIX_OLD.length());
        } else if (fullId.startsWith(APP_PREFIX)) {
            code = fullId.substring(APP_PREFIX.length());
        } else {
	        code = fullId;
        }
        return code;
    }
    /**
     * 获取id类型
     */
    public static IDType getType(String fullId) {
        IDType type = null;
        if (fullId.startsWith(USER_PREFIX)) {
            type =  IDType.TYPE_USER;
        } else if (fullId.startsWith(GROUP_PREFIX)) {
            type =  IDType.TYPE_GROUP;
        }else if (fullId.startsWith(APP_PREFIX)) {
            type =  IDType.TYPE_APP;
        }
        return type;
    }

    /**
     *
     * @param idType
     * @return
     */
    public static int toCommonType(IDType idType) {
        if (idType == IDType.TYPE_USER) {
            return CommTypeUtils.USER;
        } else if (idType == IDType.TYPE_GROUP) {
            return CommTypeUtils.GROUP;
        } else if (idType == IDType.TYPE_APP) {
            return CommTypeUtils.APP;
        }

        return CommTypeUtils.NULL;
    }
}
