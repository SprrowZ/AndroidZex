package com.rye.catcher.utils;
import com.rye.catcher.utils.ExtraUtil.Bean;
import com.rye.base.common.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * JSon工具类
 *
 * @author Jerry Li
 */
public class Old_JsonUtils {

    /**
     * 私有构建体方法JsonUtils
     */
    private Old_JsonUtils() {
    }

    /**
     * 将JSONObject转为Bean
     *
     * @param jsObject JSON对象
     * @return Bean实体
     */
    public static Bean toBean(JSONObject jsObject) {
        Bean bean = new Bean();
        try {
            for (Iterator<?> i = jsObject.keys(); i.hasNext(); ) {
                String key = (String) i.next();
                Object value = jsObject.get(key);
                if (value instanceof JSONArray) {
                    JSONArray tmpArray = (JSONArray) value;
                    List<Object> list = new ArrayList<>(tmpArray.length());
                    for (int index = 0, len = tmpArray.length(); index < len; index++) {
                        Object data = tmpArray.get(index);
                        if (data instanceof JSONObject) {
                            list.add(toBean((JSONObject) data));
                        } else {
                            list.add(data);
                        }
                    }
                    value = list;
                } else if (value instanceof JSONObject) {
                    value = toBean(((JSONObject) value));
                } else if (JSONObject.NULL.equals(value)) {
                    value = "";
                }
                bean.set(key, value);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return bean;
    }

//    public static EMAppDef toEmApp(JSONObject jsObject) {
//        EMAppDef bean = new EMAppDef();
//        try {
//            for (Iterator<?> i = jsObject.keys(); i.hasNext(); ) {
//                String key = (String) i.next();
//                Object value = jsObject.get(key);
//                if (value instanceof JSONArray) {
//                    JSONArray tmpArray = (JSONArray) value;
//                    List<Object> list = new ArrayList<>(tmpArray.length());
//                    for (int index = 0, len = tmpArray.length(); index < len; index++) {
//                        Object data = tmpArray.get(index);
//                        if (data instanceof JSONObject) {
//                            list.add(toBean((JSONObject) data));
//                        } else {
//                            list.add(data);
//                        }
//                    }
//                    value = list;
//                } else if (value instanceof JSONObject) {
//                    value = toBean(((JSONObject) value));
//                } else if (JSONObject.NULL.equals(value)) {
//                    value = "";
//                }
//                bean.set(key, value);
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e.getMessage(), e);
//        }
//        return bean;
//    }

    /**
     * 将JSONObject转为Bean
     *
     * @param str JSON串
     * @return Bean实体
     */
    public static Bean toBean(String str) {
        Bean bean = null;
        if (str != null && str.length() > 0) {
            try {
                JSONObject jsObject = new JSONObject(str);
                bean = toBean(jsObject);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage() + "\r\n" + str, e);
            }
        }
        if (bean == null) {
            return new Bean();
        } else {
            return bean;
        }
    }

    /**
     * json数组转成Bean List
     *
     * @param str json字符串 如 [{
     *            'ID':'ZHENGWEN','NAME':'正文','VALUE':1},{'ID':'FUJIAN','NAME':'附件','VALUE':'3'}
     *            ]
     * @return Bean List
     */
    public static List<Bean> toBeanList(String str) {
        List<Bean> beanList;
        if (str != null && str.length() > 0) {
            try {
                JSONArray jsObject = new JSONArray(str);
                int len = jsObject.length();
                beanList = new ArrayList<>(len);
                for (int index = 0; index < len; index++) {
                    beanList.add(toBean(jsObject.getJSONObject(index)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            beanList = new ArrayList<>();
        }
        return beanList;
    }

//    public static List<EMAppDef> toEmAppList(String str) {
//        List<EMAppDef> beanList;
//        if (str != null && str.length() > 0) {
//            try {
//                JSONArray jsObject = new JSONArray(str);
//                int len = jsObject.length();
//                beanList = new ArrayList<>(len);
//                for (int index = 0; index < len; index++) {
//                    beanList.add(toEmApp(jsObject.getJSONObject(index)));
//                }
//            } catch (Exception e) {
//                throw new RuntimeException(e.getMessage(), e);
//            }
//        } else {
//            beanList = new ArrayList<>();
//        }
//        return beanList;
//    }


    /**
     * 将JSONObject转为Bean
     *
     * @param jsObject JSON Array
     * @return Bean实体
     **/
    public static List<Bean> toBeanList(JSONArray jsObject) {
        List<Bean> beanList;
        if (jsObject != null) {
            try {
                int len = jsObject.length();
                beanList = new ArrayList<>(len);
                for (int index = 0; index < len; index++) {
                    beanList.add(toBean(jsObject.getJSONObject(index)));
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        } else {
            beanList = new ArrayList<>();
        }
        return beanList;
    }

    /**
     * map转化为json字符串，支持嵌套
     *
     * @param map map对象
     * @return json字符串
     */
    public static String toJson(Map<?, ?> map) {
        return toJson(map, false);
    }

    /**
     * map转化为json字符串，支持嵌套
     *
     * @param maps map对象列表，支持多个map合并成一个json对象串
     * @return json字符串
     */
    public static String mapsToJson(Map<?, ?>... maps) {
        StringBuilder sb = new StringBuilder("{");
        for (Map<?, ?> map : maps) {
            sb.append(toJson(map, false, false, false)).append(",");
        }
        int len = sb.length(); //去除最后一个逗号
        if (len > 1) {
            sb.setLength(len - 1);
        }
        sb.append("}");
        return sb.toString();
    }

    /**
     * map转化为json字符串，支持嵌套，缺省忽略空值，包含大括号
     *
     * @param map        map对象
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @return json字符串
     */
    public static String toJson(Map<?, ?> map, boolean formatFlag) {
        return toJson(map, formatFlag, false);
    }

    /**
     * map转化为json字符串，支持嵌套，缺省包含大括号
     *
     * @param map        map对象
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @param emptyFlag  是否忽略空值，如果空字符串，或者null，自动忽略
     * @return json字符串
     */
    public static String toJson(Map<?, ?> map, boolean formatFlag, boolean emptyFlag) {
        return toJson(map, formatFlag, emptyFlag, true);
    }

    /**
     * map转化为json字符串，支持嵌套
     *
     * @param map        map对象
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @param emptyFlag  是否忽略空值，如果空字符串，或者null，自动忽略
     * @param withBrace  是否在起止位置包含大括号
     * @return json字符串
     */
    public static String toJson(Map<?, ?> map, boolean formatFlag, boolean emptyFlag, boolean withBrace) {
        String sep = formatFlag ? Constant.STR_ENTER : "";
        StringBuilder sb = new StringBuilder();
        if (withBrace) {
            sb.append("{");
        }
        for (Object key : map.keySet()) {
            Object value = map.get(key);
            StringBuilder sbLine = new StringBuilder();
            sbLine.append(sep).append("\"").append(key).append("\":");
            if (value != null) {
                if (key.equals(Constant.KEY_ID)) {
                    sbLine.append("\"").append(value.toString()).append("\"");
                } else if ((value instanceof String) || value.getClass().isPrimitive()) {
                    String var = value.toString();
                    if (var.length() == 0) { //值为空
                        if (emptyFlag) { //有忽略标志则不输出此项
                            sbLine.setLength(0);
                        } else {
                            sbLine.append("\"\"");
                        }
                    } else {
                        sbLine.append("\"").append(encode(value.toString())).append("\"");
                    }
                } else if (value instanceof List) {
                    sbLine.append(sep).append(toJson((List<?>) value, formatFlag, emptyFlag));
                } else if (value instanceof Map) {
                    sbLine.append(sep).append(toJson((Map<?, ?>) value, formatFlag, emptyFlag));
                } else {
                    sbLine.append("\"").append(encode(value.toString())).append("\"");
                }
            } else {
                if (emptyFlag) { //有忽略标志则不输出此项
                    sbLine.setLength(0);
                } else {
                    sbLine.append("\"\"");
                }
            }
            if (sbLine.length() > 0) { //合并到总体输出中
                sb.append(sbLine).append(",");
            }
        } //end for
        int len = sb.length(); //去除最后一个逗号
        if (len > 1) {
            sb.setLength(len - 1);
        }
        sb.append(sep);
        if (withBrace) {
            sb.append("}");
        }
        return sb.toString();
    }

    /**
     * list转化为json字符串，支持嵌套
     *
     * @param list list对象
     * @return json字符串
     */
    public static String toJson(List<?> list) {
        return toJson(list, false);
    }

    /**
     * list转化为json字符串，支持嵌套，缺省不压缩空值
     *
     * @param list       list对象
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @return json字符串
     */
    public static String toJson(List<?> list, boolean formatFlag) {
        return toJson(list, formatFlag, false);
    }

    /**
     * list转化为json字符串，支持嵌套
     *
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @param emptyFlag  是否忽略空值，如果空字符串，或者null，自动忽略
     * @param list       list对象
     * @return json字符串
     */
    public static String toJson(List<?> list, boolean formatFlag, boolean emptyFlag) {
        String sep = formatFlag ? Constant.STR_ENTER : "";
        StringBuilder sb = new StringBuilder("[");
        for (Object bean : list) {
            if (bean != null) {
                if (bean instanceof Map) {
                    sb.append(sep).append(toJson((Map<?, ?>) bean, formatFlag, emptyFlag)).append(",");
                } else if (bean instanceof List) {
                    sb.append(sep).append(toJson((List<?>) bean, formatFlag, emptyFlag)).append(",");
                } else {
                    sb.append(sep).append("\"").append(bean).append("\",");
                }
            }
        }
        int len = sb.length();
        if (len > 1) {
            sb.setLength(len - 1);
        }
        sb.append(sep).append("]");
        return sb.toString();
    }

    /**
     * map转化为json字符串，支持嵌套
     *
     * @param obj        需要被转换的对象
     * @param formatFlag 格式互标志，true则格式json串，带回车信息
     * @return json字符串
     */
    public static String toJson(Object obj, boolean formatFlag) {
        if (obj == null) {
            return "";
        } else if (obj instanceof List<?>) {
            return toJson((List<?>) obj, formatFlag);
        } else if (obj instanceof Map<?, ?>) {
            return toJson((Map<?, ?>) obj, formatFlag);
        } else {
            throw new RuntimeException("wrong json ojbect type");
        }
    }

    /**
     * 对双引号回车等特殊字符进行处理
     *
     * @param s 字符串
     * @return 字符串
     */
    public static String encode(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            switch (c) {
                case '\"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '/':
                    sb.append("\\/");
                    break;
                case '\b':      //退格
                    sb.append("\\b");
                    break;
                case '\f':      //走纸换页
                    sb.append("\\f");
                    break;
                case '\n':
                    sb.append("\\n"); //换行
                    break;
                case '\r':      //回车
                    sb.append("\\r");
                    break;
                case '\t':      //横向跳格
                    sb.append("\\t");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Bean对象转换为JSONObject对象
     *
     * @param bean Bean对象
     * @return JSONObject对象
     */
    public static JSONObject toJSONObject(Bean bean) {
        return new JSONObject(bean);
    }

    public static JSONObject toJSONObject(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return new JSONObject();
        }
    }

    /**
     * Bean集合 转换为 JSONArray对象
     *
     * @param collection Bean集合
     * @return JSONArray对象
     */
    public static JSONArray toJSONArray(Collection<Bean> collection) {
        JSONArray jsonArray = new JSONArray();
        for (Bean bean : collection) {
            jsonArray.put(toJSONObject(bean));
        }
        return jsonArray;
    }

    public static JSONArray toJSONArray(String json) {
        try {
            return new JSONArray(json);
        } catch (JSONException e) {
            return new JSONArray();
        }
    }

    public static Bean getBean(List<Bean> beanList, String id, String value) {
        for (int i = 0; i < beanList.size(); i++) {
            if (beanList.get(i).getStr(id).equals(value)) {
                return beanList.get(i);
            }
        }
        return new Bean();
    }

    public static Boolean isJson(String str) {
        if (str != null && str.length() > 0) {
            try {
                JSONObject jsObject = new JSONObject(str);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }
}
