package com.rye.catcher.project.dao;

/**
 * Created by jinyunyang on 15/3/5.
 */
public class DaoConstant {
    /**
     * 回车符
     */
    public static final String STR_ENTER = "\r\n";

    /**
     * 主键项
     */
    public static final String KEY_ID = "_PK_";

    /**
     * 返回信息标签
     */
    public static final String RTN_MSG = "_MSG_";
    /**
     * 成功信息
     */
    public static final String RTN_MSG_OK = "OK,";
    /**
     * 警告信息
     */
    public static final String RTN_MSG_WARN = "WARN,";
    /**
     * 失败信息
     */
    public static final String RTN_MSG_ERROR = "ERROR,";
    /**
     * 登录信息
     */
    public static final String RTN_MSG_LOGIN = "LOGIN,";
    /**
     * 执行时间
     */
    public static final String RTN_TIME = "_TIME_";

    /**
     * list包装标签
     */
    public static final String RTN_DATA = "_DATA_";


    public static final String PARAM_SELECT = "_SELECT_";
    /**
     * 参数：查询表，支持多个
     */
    public static final String PARAM_TABLE = "_TABLE_";
    /**
     * 参数：过滤条件
     */
    public static final String PARAM_WHERE = "_WHERE_";
    /**
     * 参数：排序设置
     */
    public static final String PARAM_ORDER = "_ORDER_";
    /**
     * 参数：分组设置
     */
    public static final String PARAM_GROUP = "_GROUP_";
    /**
     * 参数：获取记录行数
     */
    public static final String PARAM_ROWNUM = "_ROWNUM_";
    /**
     * 参数：设置prepare sql变量信息
     */
    public static final String PARAM_PRE_VALUES = "_PREVALUES_";


    /**
     * 每页显示数据量
     */
    public static final String PAGE_SHOWNUM = "SHOWNUM";
    /**
     * 当前页
     */
    public static final String PAGE_NOWPAGE = "NOWPAGE";
    /**
     * 数据总量
     */
    public static final String PAGE_ALLNUM = "ALLNUM";
    /**
     * 总页数
     */
    public static final String PAGE_PAGES = "PAGES";
    /**
     * 排序
     */
    public static final String PAGE_ORDER = "ORDER";

    public static final String YES = "1";
    public static final String NO = "0";

    public static final int YES_INT = 1;
    public static final int NO_INT = 2;
    public static final int THREE = 3;
    /**
     * 红点事件
     */
    public static final String BROADCAST_RED_FLAG_EVENT = "red_falg_event";

    /**
     * 错误或失败提醒的广播
     */
    public static final String BROADCAST_ERROR_TIP = "BROADCAST_ERROR_TIP";

    /**
     * 错误或失败保存的文本
     */
    public static final String KEY_ERROR_TIP_JSON = "KEY_ERROR_TIP_JSON";

    public static final int DISCONNECTION_CODE = 1;
    public static final int CONNECTION_CODE = 2;
    public static final int CONNECTION_LOGIN = 3;
}
