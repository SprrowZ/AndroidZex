package com.rye.catcher.utils;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.text.TextUtils;

import com.rye.catcher.RyeCatcherApp;
import com.rye.catcher.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class DateUtils {
    private static final long INTERVAL_IN_MILLISECONDS = 30000L;

    public static final String FORMAT_DATETIME_MS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMAT_MOUTH = "yyyy年MM月dd日";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_DATE1 = "yyyyMMdd";

    public static final String FORMAT_DATETIME_VOTE = "yyyy/MM/dd HH:mm:ss";

    public static final String FORMAT_DATEHR = "yyyy-MM-dd HH:mm";

    public static final String FORMAT_DATE_YEAR = "yyyy-MM-dd";
    public static final String FORMAT_DATE_DONE = "yy-MM-dd";
    public static final String FORMAT_DATE_DONE1 = "mm:ss";
    public static final String FORMAT_DATE_VOTE = "yyyy.MM.dd";

    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_TIME_MS1 = "yyyyMMddHHmmssSSS";

    public static final String FORMAT_MIN_SCE = "mm:ss";
    public static final String FORMAT_MOUTH_SECOND = "yyyy年MM月dd日 HH:mm";
    public static final String FORMAT_MOUTH_WEEK = "yyyy年MM月dd日 EEEE";

    public static SimpleDateFormat format = new SimpleDateFormat(FORMAT_DATE_YEAR); //设置时间格式

    public static String getToday() {
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.FORMAT_DATE);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateStr = formatter.format(curDate);
        return dateStr;
    }

    public static String getToday1() {
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtils.FORMAT_DATE1);
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String dateStr = formatter.format(curDate);
        return dateStr;
    }

    /**
     * 是否是今年
     */
    public static boolean isThisYear(long paramLong) {
        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        calendar.setTime(new Date(paramLong));
        int year = calendar.get(Calendar.YEAR);
        return thisYear == year;
    }
    //-----------------------------聊天列表时间格式统一----------------------------------//
    public static String getConversationTime(Date paramDate) {
        String str;
        long l = paramDate.getTime();
        if (isToday(l)) {
            Calendar localCalendar = GregorianCalendar.getInstance();
            localCalendar.setTime(paramDate);
            int i = localCalendar.get(11);
            if (i > 17)
                str = "晚上 hh:mm";
            else if ((i >= 0) && (i <= 6))
                str = "凌晨 hh:mm";
            else if ((i > 11) && (i <= 17))
                str = "下午 hh:mm";
            else
                str = "上午 hh:mm";
        } else if (isYesterday(l)) {
            str = "昨天";
        } else if (isThisWeek(paramDate)) {
            str = getWeekStr(paramDate);
        } else if (!isThisYear(l)) {
            str = "y年M月d日";
        } else {
            str = "M月d日";
        }
        return new SimpleDateFormat(str, Locale.CHINA).format(paramDate);
    }
    //-----------------------------聊天详情时间格式统一----------------------------------//
    //请不要乱用时间格式,避免出现混乱
    public static String getChatDetailTime(Date paramDate) {
        String str;
        long l = paramDate.getTime();
        if (isToday(l)) {
            Calendar localCalendar = GregorianCalendar.getInstance();
            localCalendar.setTime(paramDate);
            int i = localCalendar.get(11);
            if (i > 17)
                str = "晚上 hh:mm";
            else if ((i >= 0) && (i <= 6))
                str = "凌晨 hh:mm";
            else if ((i > 11) && (i <= 17))
                str = "下午 hh:mm";
            else
                str = "上午 hh:mm";
        } else if (isYesterday(l)) {
            str = "昨天 HH:mm";
        } else if (isThisWeek(paramDate)) {
            str = getWeekDetail(paramDate);
        } else {
            str = "M月d日 HH:mm";
        }
        return new SimpleDateFormat(str, Locale.CHINA).format(paramDate);
    }
    /**
     * 根据一个日期，返回是星期几 小时:分钟 的字符串
     */
    public static String getWeekDetail(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return new SimpleDateFormat("EEEE").format(c.getTime())+" HH:mm";
    }

    public static boolean isCanBack(long millis) {
        long diff = (System.currentTimeMillis()) - millis;
        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        if (minutes < 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据当前时间判断所给日期属不属于今天
     * @param date
     * @return
     */
    public static boolean isToday(Date date) {
        Calendar cal1 = Calendar.getInstance();
        Date date1 = new Date(System.currentTimeMillis());
        cal1.setTime(date1);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        boolean isSameYear = cal1.get(Calendar.YEAR) == cal2
                .get(Calendar.YEAR);
        boolean isSameMonth = isSameYear
                && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
        boolean isSameDate = isSameMonth
                && cal1.get(Calendar.DAY_OF_MONTH) == cal2
                .get(Calendar.DAY_OF_MONTH);

        return isSameDate;
    }

    /**
     * 得到某个月的范围
      * @param date
     * @return
     */
    public static String[] getMonthRange(String date) {
        String start = date.substring(0, 8) + "01";
        String month = date.substring(5, 7);
        String end;
        if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            end = date.substring(0, 8) + "31";
        } else if (month.equals("02")) {
            int year = Integer.parseInt(date.substring(0, 4));
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                end = date.substring(0, 8) + "29";
            } else {
                end = date.substring(0, 8) + "28";
            }
        } else {
            end = date.substring(0, 8) + "30";
        }
        return new String[]{start, end};
    }

    public static int getEndOfMonth(String month, int year) {
        int end;
        if (month.equals("01") || month.equals("03") || month.equals("05") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            end = 31;
        } else if (month.equals("02")) {
            if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
                end = 29;
            } else {
                end = 28;
            }
        } else {
            end = 30;
        }
        return end;
    }

    /**
     * 得到本周周一和周日
     *
     * @return yyyy-MM-dd
     */
    public static String[] convertWeekByDate(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        //判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        //        if (1 == dayWeek) {
        //            cal.add(Calendar.DAY_OF_MONTH, -1);
        //        }
        System.out.println("要计算日期为:" + format.format(cal.getTime())); //输出要计算日期
        cal.setFirstDayOfWeek(Calendar.SUNDAY);//设置一个星期的第一天，一个星期的第一天是星期日
        int day = cal.get(Calendar.DAY_OF_WEEK);//获得当前日期是一个星期的第几天
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);//根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        String imptimeBegin = format.format(cal.getTime());
        System.out.println("所在周星期一的日期：" + imptimeBegin);
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = format.format(cal.getTime());
        System.out.println("所在周星期日的日期：" + imptimeEnd);
        String[] strings = {imptimeBegin, imptimeEnd};
        return strings;
    }

    /**
     * 得到本周周一
     *
     * @return yyyy-MM-dd
     */
    public static String getMonday(Date time, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 1);
        return format.format(cal.getTime());
    }

    /**
     * 得到本周周日
     *
     * @return yyyy-MM-dd
     */
    public static String getSunday(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 7);
        return format.format(cal.getTime());
    }

    /**
     * 按照一定格式返回本周周日
     * @param time
     * @param type
     * @return
     */
    public static String getSunday(Date time, String type) {
        SimpleDateFormat format = new SimpleDateFormat(type); //设置时间格式
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        cal.add(Calendar.DATE, -day_of_week + 7);
        return format.format(cal.getTime());
    }

    public static ArrayList<String> getDate(String date1, String date2) {
        ArrayList<String> list = new ArrayList<>();
        if (!list.contains(date1)) {
            list.add(date1);
        }
        if (!list.contains(date2)) {
            list.add(date2);
        }
        if (date1.equals(date2)) {
            System.out.println("两个日期相等!");
        }

        String tmp;
        if (date1.compareTo(date2) > 0) {  //确保 date1的日期不晚于date2
            tmp = date1;
            date1 = date2;
            date2 = tmp;
        }

        tmp = format.format(str2Date(date1).getTime() + 3600 * 24 * 1000);

        int num = 0;
        while (tmp.equals(date2)) {
            if (!list.contains(tmp)) {
                list.add(tmp);
            }
            System.out.println(tmp);
            num++;
            tmp = format.format(str2Date(tmp).getTime() + 3600 * 24 * 1000);
        }

        if (num == 0)
            System.out.println("两个日期相邻!");
        return list;
    }

    public static String formatString1(String date, String allDay) {
        try {
            if (StringUtils.isNotEmpty(date)) {
                if (allDay.equals("1")) {
                    return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8);
                }
                return date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8) + " " + date.substring(8, 10) + ":" + date.substring(10, 12);
            }
        } catch (Exception e) {

        }
        return "";
    }

    public static String formatString(String date, int flag) {
        try {
            if (StringUtils.isNotEmpty(date)) {
                if (date.length() > 12) {
                    return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + date.substring(11, 13) + date.substring(14, 16) + "00000";
                } else {
                    if (flag == 0) {
                        return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + "000000000";
                    } else if (flag == 1) {
                        return date.substring(0, 4) + date.substring(5, 7) + date.substring(8, 10) + "235959000";
                    }
                }
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }
        return "";
    }

    private static Date str2Date(String str) {
        if (str == null) return null;

        try {
            return format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }







    //获取两个时间相差的天数
    public static int daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days)) + 1;
    }









    public static boolean isThisWeek(Date date) {
        Calendar monday = new GregorianCalendar();
        monday.setTimeInMillis(System.currentTimeMillis());
        int day_of_week = monday.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        monday.add(Calendar.DATE, -day_of_week + 1);

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        int cha = calendar.get(Calendar.DAY_OF_YEAR) - monday.get(Calendar.DAY_OF_YEAR);
        if (cha > 0 && cha < 7) {
            return true;
        }
        return false;
    }

    public static boolean isToday(long paramLong) {
        TimeInfo localTimeInfo = getTodayStartAndEndTime();
        return ((paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo
                .getEndTime()));
    }

    public static boolean isYesterday(long paramLong) {
        TimeInfo localTimeInfo = getYesterdayStartAndEndTime();
        return ((paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo
                .getEndTime()));
    }

    public static boolean isSameDay(long start, long end) {
        Date startDay = new Date(start);
        Date endDay = new Date(end);
        return startDay.getYear() == endDay.getYear()
                && startDay.getMonth() == endDay.getMonth()
                && startDay.getDate() == endDay.getDate();
    }

    /**
     * 取得两个时间相差的毫秒数
     */
    public static long diff(String dateStr1, String dateStr2) {
        long diff = 0;
        Date date1 = stringToDate(dateStr1);
        Date date2 = stringToDate(dateStr2);

        if (date1 != null && date2 != null) {
            diff = Math.abs(date1.getTime() - date2.getTime());
        }

        return diff;
    }

    /**
     * 取得特定时间与当前时间之间相差的毫秒数
     */
    public static long diffCurrentTime(String dateStr) {
        long diff = 0;

        Date date = stringToDate(dateStr);
        if (date != null) {
            diff = Math.abs(date.getTime() - System.currentTimeMillis());
        }

        return diff;
    }

    /**
     * 其实用SimpleDateFormat的parseTime("~").getTime()也可
     * @param dateStr
     * @return
     */
    public static Date stringToDate(String dateStr) {
        if (dateStr == null) {
            return null;
        }
        int len = dateStr.length();
        if (len == 10) {
            return StringToDate(dateStr, FORMAT_DATE);
        } else if (len == 16) {
            return StringToDate(dateStr, DateUtils.FORMAT_DATEHR);
        } else if (len == 19) {
            return StringToDate(dateStr, DateUtils.FORMAT_DATETIME);
        } else if (len == 23) {
            return StringToDate(dateStr, DateUtils.FORMAT_DATETIME_MS);
        }
        return null;
    }


    /**
     * 取得特定时间之前的时间
     */
    public static Date getDateBefore(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        now.set(Calendar.HOUR, 8);
        return now.getTime();
    }

    public static Date StringToDate(String dateStr, String format) {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                format);
        Date localDate = null;
        try {
            localDate = localSimpleDateFormat.parse(dateStr);
        } catch (ParseException localParseException) {
            localParseException.printStackTrace();
        }
        return localDate;
    }

    public static String longToStr(long time, String format) {
        SimpleDateFormat dataFormat = new SimpleDateFormat(
                format);
        Date date = new Date();
        date.setTime(time);
        return dataFormat.format(date);
    }

    public static String getCurrentTime(String format) {
        if (TextUtils.isEmpty(format)) {
            format = FORMAT_DATETIME;
        }
        return longToStr(System.currentTimeMillis(), format);
    }


    public static String toTimeBySecond(int paramInt) {
        int i = paramInt / 60;
        int j = 0;
        if (i >= 60) {
            j = i / 60;
            i %= 60;
        }
        int k = paramInt % 60;
        return String.format("%02d:%02d", new Object[]{Integer.valueOf(i),
                Integer.valueOf(k)});
    }

    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(5, -1);
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(5, -1);
        localCalendar2.set(11, 23);
        localCalendar2.set(12, 59);
        localCalendar2.set(13, 59);
        localCalendar2.set(14, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();
        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }

    public static TimeInfo getTodayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.set(11, 0);
        localCalendar1.set(12, 0);
        localCalendar1.set(13, 0);
        localCalendar1.set(14, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss S");
        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.set(11, 23);
        localCalendar2.set(12, 59);
        localCalendar2.set(13, 59);
        localCalendar2.set(14, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();
        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }




    /**
     * 根据一个日期，返回是星期几的字符串
     *
     * @param date
     * @return
     */
    public static String getWeek(Date date) {
        // 再转换为时间
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // int hour=c.get(Calendar.DAY_OF_WEEK);
        // hour中存的就是星期几了，其范围 1~7
        // 1=星期日 7=星期六，其他类推
        return new SimpleDateFormat("EEEE").format(c.getTime());
    }

    public static String getWeekStr(Date date) {
        String str = "";
        str = getWeek(date);
        if ("1".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Sunday);
        } else if ("2".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Monday);
        } else if ("3".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Tuesday);
        } else if ("4".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Wednesday);
        } else if ("5".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Thursday);
        } else if ("6".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Friday);
        } else if ("7".equals(str)) {
            str = RyeCatcherApp.getInstance().getString(R.string.Saturday);
        }
        return str;
    }

    public static String getTimestampStr() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String timeAgo(Date date) {
        return timeAgo(date.getTime());
    }

    @SuppressLint("StringFormatInvalid")
    public static String timeAgo(long millis) {
        long diff = (new Date().getTime()) - millis;

        Resources r = RyeCatcherApp.getInstance().getBaseContext().getResources();

        String prefix = r.getString(R.string.time_ago_prefix);
        String suffix = r.getString(R.string.time_ago_suffix);

        double seconds = Math.abs(diff) / 1000;
        double minutes = seconds / 60;
        double hours = minutes / 60;
        double days = hours / 24;
        double years = days / 365;

        String words;

        if (seconds < 45) {
            words = r.getString(R.string.time_ago_seconds, Math.round(seconds));
        } else if (seconds < 90) {
            words = r.getString(R.string.time_ago_minute, 1);
        } else if (minutes < 45) {
            words = r.getString(R.string.time_ago_minutes, Math.round(minutes));
        } else if (minutes < 90) {
            words = r.getString(R.string.time_ago_hour, 1);
        } else if (hours < 24) {
            words = r.getString(R.string.time_ago_hours, Math.round(hours));
        } else if (hours < 42) {
            words = r.getString(R.string.time_ago_day, 1);
        } else if (days < 30) {
            words = r.getString(R.string.time_ago_days, Math.round(days));
        } else if (days < 45) {
            words = r.getString(R.string.time_ago_month, 1);
        } else if (days < 365) {
            words = r.getString(R.string.time_ago_months, Math.round(days / 30));
        } else if (years < 1.5) {
            words = r.getString(R.string.time_ago_year, 1);
        } else {
            words = r.getString(R.string.time_ago_years, Math.round(years));
        }

        StringBuilder sb = new StringBuilder();

        if (prefix != null && prefix.length() > 0) {
            sb.append(prefix);
        }

        sb.append(words);

        if (suffix != null && suffix.length() > 0) {
            sb.append(suffix);
        }

        return sb.toString().trim();
    }


    public static String getTime(Date date, SimpleDateFormat formatter) {
        return formatter.format(date);
    }

    /**
     * 得到当前时间
     * @param time
     * @param format
     * @return
     */
    public static String  getTime(long time,String format){
        SimpleDateFormat format1=new SimpleDateFormat(format);
        Date date=new Date(time);
        return format1.format(date);
    }
    /**
     * Adds the given amount to a {@code Calendar} field.
     *
     * @param field the {@code Calendar} field to modify.
     * @param value the amount to add to the field.
     * @throws IllegalArgumentException if {@code field} is {@code DST_OFFSET} or {@code
     *                                  ZONE_OFFSET}.
     */
    public static Date add(Date noteDate, int field, int value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(noteDate);
        calendar.add(field, value);
        Date noteDateplus30 = calendar.getTime();
        return noteDateplus30;
    }
    /**
     * 返回当月最后一天的日期
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getMaximum(Calendar.DATE));
        return calendar.getTime();
    }

    /**
     * 依据date获取该日期下的天
     */
    public static String getDay(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd");
        return format.format(date.getTime());
    }


}
