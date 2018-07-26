package com.example.myappsecond.utils;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jinyunyang on 15/3/6.
 */
public class StringUtils {

    /**
     * <p>Checks if a String is empty ("") or null.</p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is empty or null
     */
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * <p>Checks if a String is not empty ("") and not null.</p>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is not empty and not null
     */
    public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }

    /**
     * @param str 指定字符串
     * @return 自定字符串是否全是字母
     */
    public static boolean isAlpha(String str) {
        if (str == null) {
            return false;
        }
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            char codePoint = str.charAt(i);
            if (!('A' <= codePoint && codePoint <= 'Z') && !('a' <= codePoint && codePoint <= 'z')) {
                return false;
            }
        }
        return true;
    }

    public static String transHtml(String text) {
        return text.replace("&nbsp;", "\t").replace("&amp;", "&").replace("&quot;", "'").replace("&lt;", "<").replace("&gt;", ">");
    }

    /**
     * 通过param key 获取在URL中的value
     *
     * @param url - format key1=value1&key2=value2
     * @param key - key
     * @return
     */
    public static String getValueFromUrl(String url, String key) {
        String[] keyValueArray = url.split("&");
        for (String keyValue : keyValueArray) {
            String[] kv = keyValue.split("=");
            if (kv.length != 2) {
                continue;
            }
            String k = kv[0];
            String v = kv[1];

            if (key.equals(k)) {
                return v;
            }
        }
        return "";
    }

    public static int subStringDate(String date) {
        return Integer.parseInt(date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10));
    }

    /**
     * @param str 指定字符串
     * @return 自定字符串第一个字符是否是字母
     */
    public static boolean isFirstAlpha(String str) {
        if (str == null) {
            return false;
        }
        char codePoint = str.charAt(0);
        if (!('A' <= codePoint && codePoint <= 'Z') && !('a' <= codePoint && codePoint <= 'z')) {
            return false;
        }
        return true;
    }

    public static int getMailIconColor(String str) {
        if (str == null) {
            return 0;
        }
        char codePoint;
        if (StringUtils.isFirstAlpha(str)) {
            codePoint = str.substring(0, 1).toUpperCase().charAt(0);
        } else {
            codePoint = str.charAt(0);
        }
        int number = codePoint;
        return number % 10;
    }

//    public static boolean isOldVersion(String newVersion) {
//        // 1,比较版本号
//        try {
//            PackageInfo info = EChatApp.getInstance().getPackageManager().getPackageInfo(EChatApp.getInstance().getPackageName(), 0);
//            String localVersion = info.versionName;
//            String nums[] = newVersion.split("\\.");
//            String nums_local[] = localVersion.split("\\.");
//            boolean hasNewVer = false;
//
//            for (int a = 0; a < nums.length; a++) {
//                String num = nums[a];
//                String num_local = nums_local[a];
//                if (Integer.parseInt(num) < Integer.parseInt(num_local)) {
//                    break;
//                }
//                if (Integer.parseInt(num) > Integer.parseInt(num_local)) {
//                    hasNewVer = true;
//                    break;
//                }
//            }
//            return hasNewVer;
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }

    //去掉HTML标签
    public static String delHTMLTag(String htmlStr) {
        String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式
        String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式
        String regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式

        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); //过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); //过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); //过滤html标签

        return htmlStr.trim(); //返回文本字符串
    }

//    public static String ListToString(List<Object> list) {
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < list.size(); i++) {
//            Bean bean = JsonUtils.toBean(list.get(i).toString());
//            UserCalendarDao dao = new UserCalendarDao();
//            if (!dao.isVisible(bean.getStr("SSIC_ID"))) {
//                Bean userBean = new Bean();
//                userBean.set("TDEPT_NAME", bean.getStr("TDEPT_NAME"));
//                userBean.set("TDEPT_CODE", bean.getStr("TDEPT_CODE"));
//                userBean.set("USER_CODE", bean.getStr("USER_CODE"));
//                userBean.set("USER_NAME", bean.getStr("USER_NAME"));
//                userBean.set("SSIC_ID", bean.getStr("SSIC_ID"));
//                userBean.set("DEPT_CODE", bean.getStr("DEPT_CODE"));
//                userBean.set("DEPT_NAME", bean.getStr("DEPT_NAME"));
//                userBean.set("_PK_", bean.getStr("DATA_ID"));
//                userBean.set("STATUS_FLAG", "1");
//                userBean.set("TYPE", "3");
//                dao.save(userBean);
//            }
//            if (i == list.size() - 1) {
//                sb.append(bean.getStr("SSIC_ID"));
//            } else {
//                sb.append(bean.getStr("SSIC_ID"));
//                sb.append(",");
//            }
//        }
//        return sb.toString();
//    }

    public static ArrayList<String> stringToArray(String query) {
        String[] strings = query.split(",");
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < strings.length; i++) {
            arrayList.add(strings[i]);
        }
        return arrayList;
    }

    public static String arrayToString(ArrayList<String> strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.size(); i++) {
            if (i == strings.size() - 1) {
                sb.append(strings.get(i));
            } else {
                sb.append(strings.get(i));
                sb.append(",");
            }
        }
        return sb.toString();
    }

    // 判断一个字符串是否都为数字
    public static boolean isDigit(String strNum) {
        Pattern pattern = Pattern.compile("[0-9]{1,}");
        Matcher matcher = pattern.matcher(strNum);
        return matcher.matches();
    }

    public static int isChineseCharacter(String txt) {//判断字符串中的中文字符个数是不是超过两个
        int num = 0;
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        for (int i = 0; i < txt.length(); i++) {
            char ch = txt.charAt(0);
            Matcher m = p.matcher(String.valueOf(ch));
            if (m.matches()) {
                num++;
            }
        }
        return num;
    }

    public static String arrayToStringMail(String[] strings) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            if (i == strings.length - 1) {
                sb.append("'" + strings[i] + "'");
            } else {
                sb.append("'" + strings[i] + "'");
                sb.append(",");
            }
        }
        return sb.toString();
    }

    public static String removeIp(String url) {
        int num = 0;
        for (int i = 0; i < url.length(); i++) {
            char c = url.charAt(i);
            String str = String.valueOf(c);
            if (str.equals("/")) {
                num++;
                if (num == 3) {
                    return url.substring(i, url.length());
                }
            }
        }
        return "";
    }

    public static Map<String, String> toMap(String url) {
        Map<String, String> map = null;

        if (url != null && url.indexOf("&") > -1 && url.indexOf("=") > -1) {
            map = new HashMap<String, String>();

            String[] arrTemp = url.split("&");
            for (String str : arrTemp) {
                String[] qs = str.split("=");
                map.put(qs[0], qs[1]);
            }
        }
        return map;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     * @author lzf
     */
    private static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;
        strURL = strURL.trim();
        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                for (int i = 1; i < arrSplit.length; i++) {
                    strAllParam = arrSplit[i];
                }
            }
        }
        return strAllParam;
    }

    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     * @author lzf
     */
    public static Map<String, String> urlSplit(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();
        String[] arrSplit = null;
        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");
            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }
}
