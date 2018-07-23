package com.example.myappsecond.Utils;

/**
 * Created by jinyunyang on 15/3/5.
 */
public class Constant {
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

    public static final String REFRESH_ALL = "REFRESH_ALL";

    public static final String REFRESH_List = "REFRESH_List";

    public static final String REFRESH_WEBVIEW = "REFRESH_WEBVIEW";

    public static final String REFRESH_PAGE = "REFRESH_PAGE";

    public static final String REFRESH_CURRENT = "REFRESH_CURRENT";

    /**
     * 错误或失败提醒的广播
     */
    public static final String BROADCAST_ERROR_TIP = "BROADCAST_ERROR_TIP";

    /**
     * 错误或失败保存的文本
     */
    public static final String KEY_ERROR_TIP_JSON = "KEY_ERROR_TIP_JSON";

    /*
    * 日程中的关闭loading的广播
    * */
    public static final String CAL_CHANGE_VIEW_LOADING_CLOSE = "CAL_CHANGE_VIEW_LOADING_CLOSE";

    public static final String RERESH_ALL = "RERESH_ALL";


    //下面的参数已经配置到gradle中
    /**
     * 华为沙盒相关测试环境参数
     */
//    public static final String SANDBOX_IP="114.255.225.50";
//    public static final String SANDBOX_INNER_IP="122.40.130.116";
//    public static final int SANDBOX_PORT=1301;
//    public static final String SANDBOX_WHITELIST="122.40.130.74;122.18.157.193;122.19.141.46";
    /**
     * 华为沙盒相关生产环境参数（正式版本）
     */
//    public static final String SANDBOX_IP="emm.icbc.com.cn";
//    public static final String SANDBOX_INNER_IP="10.248.66.77";
//    public static final int SANDBOX_PORT=443;
//    public static final String SANDBOX_WHITELIST="140.206.54.50;10.248.113.15;122.19.141.46";
    /**
     * 华为沙盒参数（公共）
     */
    public static final String AnyOfficePackageName="com.icbc.emm";


}
