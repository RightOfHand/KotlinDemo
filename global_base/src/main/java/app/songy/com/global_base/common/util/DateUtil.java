package app.songy.com.global_base.common.util;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *Description:
 *creator: song
 *Date: 2018/6/11 下午5:41
 */
public class DateUtil {
    public static final Calendar calendar = Calendar.getInstance();

    public static SimpleDateFormat DATE_FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.getDefault());

    public static SimpleDateFormat DATE_FULL_CHINESE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分",Locale.getDefault());

    public static SimpleDateFormat DATE_CHINESE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日",Locale.getDefault());

    public static SimpleDateFormat DATE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());

    public static SimpleDateFormat DATE_MONTHDAY_FORMAT = new SimpleDateFormat("MM-dd",Locale.getDefault());

    public static SimpleDateFormat DATE_MONTH_FORMAT = new SimpleDateFormat("yyyy-MM",Locale.getDefault());

    public static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("HH:mm",Locale.getDefault());

    public static SimpleDateFormat DATE_MS_FORMAT = new SimpleDateFormat("HH:mm:ss",Locale.getDefault());
    public static SimpleDateFormat DATE__WEEK_HALF_FULL_FORMAT = new SimpleDateFormat("yyyy-MM-dd E HH:mm",Locale.CHINA);

    public static SimpleDateFormat DATE_YM_ZH_FORMAT = new SimpleDateFormat("yyyy年MM月",Locale.getDefault());
    public static final SimpleDateFormat dateFormat14 = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
    public static final SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
    public static final SimpleDateFormat dateFormat9 = new SimpleDateFormat("HH:mm", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail = new SimpleDateFormat("M-d 周# HH:mm", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail2 = new SimpleDateFormat("yyyy-MM-dd 周#", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail4 = new SimpleDateFormat("yyyy年M月d日 周# HH:mm", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail5 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail6 = new SimpleDateFormat("MM月dd日 HH:mm", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail7 = new SimpleDateFormat("yyyy/MM/dd", Locale.CHINA);
    public static final SimpleDateFormat dateFormat_detail8 = new SimpleDateFormat("yyyy-MM-dd 周# HH:mm", Locale.CHINA);
    public static final long ONE_DAY_MILLISECONDS = 3600 * 1000 * 24;
    public static final long FULL_MONTH_MILLISECONDS = ONE_DAY_MILLISECONDS * 30;

    public static String formatDateString(String dateStr, SimpleDateFormat newFormat) {
        try {
            final Date parse = dateFormat14.parse(dateStr);
            final String format = newFormat.format(parse);
            if(format.contains("#")){
                String weekStr = getWeekStr(parse);
                return format.replace("#", weekStr);
            }
            return format;
        } catch (Exception e) {
            return "";
        }
    }

    public static Date parseDate(String dateStr) {
        try {
            return dateFormat14.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public static String formatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            return dateFormat.format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 根据毫秒数得到显示的日期时间字符串
     */
    public static String millSecond2String(long milliseconds) {
        return DateFormat.format("yyyy-MM-dd kk:mm:ss", milliseconds).toString();
    }

    public static Date getCustomTime(int interval) {
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parseDate(dateFormat14.format(date)));
        calendar.add(Calendar.DAY_OF_MONTH, +interval);//+interval 今天的时间加interval天
        date = calendar.getTime();
        return date;
    }
    @NonNull
    private static String getWeekStr(Date date) {
        calendar.setTime(date);
        final int week = calendar.get(Calendar.DAY_OF_WEEK);
        String weekStr = "";
        switch (week) {
            case Calendar.SUNDAY:
                weekStr = "日";
                break;
            case Calendar.MONDAY:
                weekStr = "一";
                break;
            case Calendar.TUESDAY:
                weekStr = "二";
                break;
            case Calendar.WEDNESDAY:
                weekStr = "三";
                break;
            case Calendar.THURSDAY:
                weekStr = "四";
                break;
            case Calendar.FRIDAY:
                weekStr = "五";
                break;
            case Calendar.SATURDAY:
                weekStr = "六";
                break;
        }
        return weekStr;
    }

    public static String formatServerTime(@NonNull Date date) {
        return dateFormat14.format(date);
    }

    public static String formatServerTime(String dateStr,SimpleDateFormat inputFormat) {
        try {
            return formatServerTime(inputFormat.parse(dateStr));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 获取星期数0-6
     *
     * @return
     */
    public static int getWeek() {
        calendar.setTime(new Date());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期列表，截至日前的一个星期
     */
    public static ArrayList<String> getWeekList() {
        ArrayList<String> weekStr = new ArrayList<>();
        int curWeek = getWeek();
        for (int i = 0; i < 7; i++) {
            curWeek++;
            if (curWeek > 7) {
                curWeek %= 7;
            }
            switch (curWeek) {
                case Calendar.SUNDAY:
                    weekStr.add("周日");
                    break;
                case Calendar.MONDAY:
                    weekStr.add("周一");
                    break;
                case Calendar.TUESDAY:
                    weekStr.add("周二");
                    break;
                case Calendar.WEDNESDAY:
                    weekStr.add("周三");
                    break;
                case Calendar.THURSDAY:
                    weekStr.add("周四");
                    break;
                case Calendar.FRIDAY:
                    weekStr.add("周五");
                    break;
                case Calendar.SATURDAY:
                    weekStr.add("周六");
                    break;
            }
        }
        return weekStr;
    }

    /**
     * 根据传入的时间，得出距离传入时间的中文表示
     * 例如：
     * 今天 -> HH:mm
     * 昨天 -> 昨天
     * 三天前 -> 3天前
     * 五个月前 ->5个月前
     * 四年前 ->4年前
     *
     * @param dateStr 指定的时间
     * @return 中文表示的时间距离
     */
    public static String getHumanityDateString(String dateStr) {
        try {
            Date date = dateFormat14.parse(dateStr);
            return getHumanityDateString(date);
        } catch (Exception e) {
            return "";
        }
    }
    public static String getTransferDateString(String dateString){
        try {
            Date date = dateFormat14.parse(dateString);
            return getTransferDate(date);
        } catch (ParseException e) {
            return "";
        }
    }
    /**
     * 根据传入的时间，得出距离传入时间的中文表示
     * 例如：
     * 今天 -> MM-DD
     * 昨天 -> 昨天 MM-DD
     * 今年非今非昨 -> XX月XX日 XX：XX
     * 非今年 ->20XX年XX月XX日 XX：XX
     *
     * @param date 指定的时间
     * @return 中文表示的时间距离
     */

    public static String getTransferDate(Date date){
        if (isToday(date)) {
            return "今天";
        }

        int months = monthAgo(date);
        if (months == 0) {
            if (daysAgo(date)<=1){
                return "昨天";
            }else {
                return formatDate(date,"MM月dd日");
            }
        }
        if (months >= 1 && months < 12) {
            return formatDate(date,"MM月dd日");
        }
        int years = yearsAgo(date);
        if (years >= 1) {
            return formatDate(date,"yyyy年MM月dd日");
        }
        return "未来";
    }
    /**
     * 根据传入的时间，得出距离传入时间的中文表示
     * 例如：
     * 今天 -> HH:mm
     * 昨天 -> 昨天
     * 三天前 -> 3天前
     * 五个月前 ->5个月前
     * 四年前 ->4年前
     *
     * @param date 指定的时间
     * @return 中文表示的时间距离
     */
    public static String getHumanityDateString(Date date) {
        if(isJustNow(date)){
            return "刚刚";
        }
        if (isToday(date)) {
            return formatDate(date, "HH:mm");
        }
        if (isToday(new Date(System.currentTimeMillis() - ONE_DAY_MILLISECONDS))) {
            return "昨天";
        }
        int months = monthAgo(date);
        if (months == 0) {
            return daysAgo(date) + 1 + "天前";
        }
        if (months >= 1 && months < 12) {
            return months + "个月前";
        }
        int years = yearsAgo(date);
        if (years >= 1) {
            return years + "年前";
        }
        return "未来";
    }

    private static boolean isJustNow(Date date) {
        return System.currentTimeMillis() - date.getTime() <= 60*1000*10;  //10分钟
    }

    private static int daysAgo(Date date) {
        long current = System.currentTimeMillis();
        long diff = current - date.getTime();
        return (int) (diff / ONE_DAY_MILLISECONDS);
    }

    public static int yearsAgo(Date date) {
        return monthAgo(date) / 12;
    }


    public static int monthAgo(Date date) {
        long current = System.currentTimeMillis();
        long diff = current - date.getTime();
        return (int) (diff / FULL_MONTH_MILLISECONDS);  //多少个月
    }

    /**
     * 判断一个日期是否是今天
     *
     * @param date 指定的日期
     * @return 是否是今天
     */
    public static boolean isToday(Date date) {
        //获取今天的最先时刻、最后时刻
        TimeInfo todayInfo = getTimeInfo(new Date(), TimeInfoScope.DAY);
        long time = date.getTime();
        return time >= todayInfo.startTime && time < todayInfo.endTime;
    }

    /**
     * 获取指定日期的TimeInfo
     *
     * @param date 指定日期
     * @return timeInfo
     */
    public static DateUtil.TimeInfo getTimeInfo(Date date, TimeInfoScope scope) {
        calendar.setTimeInMillis(date.getTime());
        TimeInfo timeInfoDay = new TimeInfo();
        TimeInfo timeInfoMonth = new TimeInfo();
        switch (scope) {
            case DAY:
                //获取天的最先时刻
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                timeInfoDay.startTime = calendar.getTimeInMillis();

                //获取天的最后时刻
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                calendar.set(Calendar.MILLISECOND, 999);
                timeInfoDay.endTime = calendar.getTimeInMillis();
            case MONTH:
                //获取月的最先时刻
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                timeInfoMonth.startTime = calendar.getTimeInMillis();

                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
                timeInfoMonth.endTime = calendar.getTimeInMillis();
                break;
        }
        switch (scope) {
            case DAY:
                return timeInfoDay;
            case MONTH:
                return timeInfoMonth;
            default:
                return null;
        }

    }

    /**
     * 一段时间的开始时间和结束时间的封装
     */
    public static class TimeInfo {
        public long startTime; //开始时间
        public long endTime;  //结束时间
    }

    public enum TimeInfoScope {
        DAY, MONTH
    }


    public static Date parse(String date) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        return parse(date, DATE_FULL_FORMAT);
    }

    public static Date parse(String date, SimpleDateFormat format) {
        if (TextUtils.isEmpty(date)) {
            return null;
        }
        try {
            return format.parse(date + "");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String format(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FULL_FORMAT.format(date);
    }

    public static String format(Date date, SimpleDateFormat format) {
        if (date == null) {
            return "";
        }
        return format.format(date);
    }
    public static String formatWithWeeK(Date date, SimpleDateFormat format) {
        if (date == null) {
            return "";
        }
        String formatStr =  format.format(date);

        if(formatStr.contains("#")){
            String weekStr = getWeekStr(date);
            return formatStr.replace("#", weekStr);
        }
        return formatStr;
    }


    public static String formatChinese(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_FULL_CHINESE_FORMAT.format(date);
    }

    public static String getChineseDate(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_CHINESE_FORMAT.format(date);
    }

    public static String getChineseDateMonth(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_YM_ZH_FORMAT.format(date);
    }


    /**
     * 获取日期 yyyy-MM-dd
     * */
    public static String getDate(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_DATE_FORMAT.format(date);
    }

    public static String getMonth(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_MONTH_FORMAT.format(date);
    }

    public static String getTime(Date date) {
        if (date == null) {
            return "";
        }
        return DATE_TIME_FORMAT.format(date);
    }

    public static int get(Date date, int field) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        if (date == null) {
            date = new Date();
        }
        d.setTime(date);
        return d.get(field);
    }


    public static Date updateDate(Date date, int year, int monthOfYear, int dayOfMonth) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        if (date == null) {
            date = new Date();
        }
        d.setTime(date);
        d.set(Calendar.YEAR, year);
        d.set(Calendar.MONTH, monthOfYear);
        d.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return d.getTime();
    }


    public static Date updateDate(Date date, int hourOfDay, int minute) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        if (date == null) {
            date = new Date();
        }
        d.setTime(date);
        d.set(Calendar.HOUR_OF_DAY, hourOfDay);
        d.set(Calendar.MINUTE, minute);
        d.set(Calendar.SECOND, 0);
        return d.getTime();
    }

    public static Date updateDate(Date date, int year, int monthOfYear, int dayOfMonth, int hourOfDay, int minute) {
        Calendar d = Calendar.getInstance(Locale.CHINA);
        if (date == null) {
            date = new Date();
        }

        d.setTime(date);
        d.set(Calendar.YEAR, year);
        d.set(Calendar.MONTH, monthOfYear);
        d.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        d.set(Calendar.HOUR_OF_DAY, hourOfDay);
        d.set(Calendar.MINUTE, minute);
        d.set(Calendar.SECOND, 0);
        return d.getTime();
    }

    /**
     * todo "yyyy年MM月"转"yyyy-MM-dd"
     */
    public static String updateDateStr(String dateStr) {
        return getDate(parse(dateStr, DateUtil.DATE_YM_ZH_FORMAT));
    }

    /**
     * TODO "yyyy-MM"转成"yyyyMM"
     * */
    public static String updateDateToString(String str) {
        String[] arr = str.split("-");
        return arr[0] + arr[1];
    }


    /***
     * 日期月份减一个月
     *
     * @param datetime 日期(2014-11)
     * @return 2014-10
     */
    public static String addMonth(String datetime) {
        SimpleDateFormat sdf = DATE_MONTH_FORMAT;
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, +1);
        date = cl.getTime();
        return sdf.format(date);
    }


    /***
     * 日期月份减一个月
     *
     * @return 2014-10
     */
    public static Date addSecond(Date date) {
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.SECOND, +1);
        date = cl.getTime();
        return date;
    }

    public static String subMonth(String datetime) {
        SimpleDateFormat sdf = DATE_MONTH_FORMAT;
        Date date = null;
        try {
            date = sdf.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, -1);
        date = cl.getTime();
        return sdf.format(date);
    }

    public static String subMonth(SimpleDateFormat format, String datetime) {
        Date date = null;
        try {
            date = format.parse(datetime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cl = Calendar.getInstance();
        cl.setTime(date);
        cl.add(Calendar.MONTH, -1);
        date = cl.getTime();
        return format.format(date);
    }

    public static boolean after(String server, String after) {
        if (server == null || server.length() != 7) {
            server = getLocalDate();
        }

        if (after == null || after.length() != 7) {
            after = getLocalDate();
        }
        int server_year = Integer.valueOf(server.substring(0, 4));
        int server_month = Integer.valueOf(server.substring(5));

        int after_year = Integer.valueOf(after.substring(0, 4));
        int after_month = Integer.valueOf(after.substring(5));
        boolean needAdd = true;
        if (server_year <= after_year && after_month >= server_month) {
            needAdd = false;
        }
        return needAdd;
    }

    /**
     * 服务器给时间为空时取本地时间
     * */
    private static String getLocalDate() {
        Calendar calendar = Calendar.getInstance();
        return DateUtil.format(calendar.getTime(), DateUtil.DATE_MONTH_FORMAT);
    }

    /**
     * "yyyy-MM-dd E HH:mm"格式的时间文本 转成long时间
     * @param dateStr
     * @return
     */
    public static long convertWeekDate2Long(String dateStr) {

        try {
            Date date = DateUtil.parse(dateStr, DateUtil.DATE__WEEK_HALF_FULL_FORMAT);
            return date.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return System.currentTimeMillis();
        }

    }

}
