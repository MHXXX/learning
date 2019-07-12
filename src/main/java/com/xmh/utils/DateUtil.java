package main.java.com.xmh.utils;


import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class DateUtil {

    private static DateFormatSymbols dateformatSymbols = new DateFormatSymbols(Locale.getDefault());

    static {
        dateformatSymbols.setShortWeekdays(new String[]{"", "周日", "周一", "周二", "周三", "周四", "周五", "周六"});
    }

    /**
     * 英文简写（默认）如：2010
     */
    public static String FORMAT_YYYY = "yyyy";

    /**
     * 英文简写（默认）如：2010
     */
    public static String FORMAT_YYYY_CN = "yyyy年";
    /**
     * 英文简写（默认）如：2010-12-31 24
     */
    public static String FORMAT_YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    /**
     * 英文简写（默认）如：12-31 24
     */
    public static String FORMAT_MM_DD_HH = "MM-dd HH";
    /**
     * 英文简写（默认）如：31 24
     */
    public static String FORMAT_DD_HH = "dd HH";
    /**
     * 英文简写（默认）如：24
     */
    public static String FORMAT_HH = "HH";
    /**
     * 星期的简写 周一 周二 周三 周四等
     */
    public static String FORMAT_DAY_OF_WEEK_SORT_CN = "E";

    /**
     * 英文简写（默认）如：2010-12
     */
    public static String FORMAT_YYYY_MM = "yyyy-MM";

    /**
     * 英文简写（默认）如：2010-12
     */
    public static String FORMAT_YYYY_MM_CN = "yyyy年MM月";
    /**
     * 英文简写（默认）如：12-31
     */
    public static String FORMAT_MM_DD = "MM-dd";
    /**
     * 英文简写带星期（默认）如：12-31(周三)
     */
    public static String FORMAT_MM_DD_E = "MM-dd(E)";
    /**
     * 英文简写（默认）如：31
     */
    public static String FORMAT_DD = "dd";
    /**
     * 英文简写（默认）如：2010-12-01
     */
    public static String FORMAT_SHORT = "yyyy-MM-dd";

    /**
     * 英文全称  如：2010-12-01 23:15
     */
    public static String FORMAT_YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm:ss";
    /**
     * 英文全称  如：2010-12-01 23:15:06
     */
    public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

    /**
     * 精确到毫秒的完整时间    如：2010-12-01 23:15:06.999
     */
    public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 中文简写  如：2010年12月01日
     */
    public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
    /**
     * 中文简写  如：2010年12月01日(周日)
     */
    public static String FORMAT_SHORT_CN_WEEK = "yyyy年MM月dd日(E)";
    public static String FORMAT_SHORT_WEEK = "yyyy-MM-dd(E)";


    public static String FORMAT_ONLY_WEEK = "E";

    /**
     * 中文全称  如：2010年12月01日  23时15分06秒
     */
    public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

    /**
     * 精确到毫秒的完整中文时间   如：2010年12月01日  23时15分06秒999毫秒
     */
    public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

    public static final int FIRST_DAY_OF_WEEK = Calendar.MONDAY; // 中国周一是一周的第一天
    public static String FORMAT_HHMMSS = "HH:mm:ss";
    public static String FORMAT_HHMMSSS = "HH:mm:ss.S";
    public static String FORMAT_HHMM = "HH:mm";

    /**
     * 获取上周同期
     *
     * @param date
     * @return 上周同天
     */
    public static Date getLastWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_MONTH, -1);
        return calendar.getTime();

    }

    /**
     * 获取上月同期
     *
     * @param date 当日
     * @return
     */
    public static Date getLastMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        return calendar.getTime();

    }

    /**
     * 获取下月同期
     *
     * @param date 当日
     * @return
     */
    public static Date getNextMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, 1);
        return calendar.getTime();

    }

    /**
     * 获取去年同期
     *
     * @param date 当日
     * @return
     */
    public static Date getLastYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取昨日日期
     *
     * @param date 当日
     * @return
     */
    public static Date getYesterday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        return calendar.getTime();
    }

    /**
     * 获取明天日期
     *
     * @param date 当日
     * @return
     */
    public static Date getTorromow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 获取下一小时
     *
     * @param date 日期
     * @return
     */
    public static Date getNextHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        return calendar.getTime();
    }

    /**
     * 获取前一小时
     *
     * @param date 日期
     * @return
     */
    public static Date getPreviousHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        return calendar.getTime();
    }

    /**
     * 判断 yesterDay 是否是 today 的昨天
     *
     * @param today     今天日期
     * @param yesterDay 昨天日期
     * @return
     */
    public static boolean isYesterday(Date today, Date yesterDay) {
        return isSameDay(getYesterday(today), yesterDay);
    }

    /**
     * 判断 lastWeekDay 是否是 today 的上周同期
     *
     * @param today       今天日期
     * @param lastWeekDay 上周同期日期
     * @return
     */
    public static boolean isLastWeekDay(Date today, Date lastWeekDay) {
        return isSameDay(getLastWeek(today), lastWeekDay);
    }

    /**
     * 判断 lastMonthDay 是否是 today 的上月同期
     *
     * @param today        今天日期
     * @param lastMonthDay 上月同期
     * @return
     */
    public static boolean isLastMonthDay(Date today, Date lastMonthDay) {
        return isSameDay(getLastMonth(today), lastMonthDay);
    }

    /**
     * 判断 lastYearDay 是否是 today 的去年同期
     *
     * @param today       今天日期
     * @param lastYearDay 去年同期日期
     * @return
     */
    public static boolean isLastYearDay(Date today, Date lastYearDay) {
        return isSameDay(getLastYear(today), lastYearDay);
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isSameHour(Date date1, Date date2) {
        if (!isSameDay(date1, date2)) {
            return false;
        }
        if (date1.getHours() != date2.getHours()) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个日期是否是同一天
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (!isSameMonth(date1, date2)) {
            return false;
        }
        if (date1.getDate() != date2.getDate()) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个日期是否是同一周
     *
     * @return
     */
    public static boolean isSameWeek(Date week, Date otherWeek) {
        return isSameDay(getMondayOfWeek(week), getMondayOfWeek(otherWeek));
    }

    /**
     * 判断两个日期是否是同一月
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isSameMonth(Date date1, Date date2) {
        if (date1.getYear() != date2.getYear()) {
            return false;
        }
        if (date1.getMonth() != date2.getMonth()) {
            return false;
        }
        return true;
    }

    /***
     * 判断日期2是否是日期1的上周
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLastWeek(Date date1, Date date2) {
        return isSameWeek(getLastWeek(date1), date2);
    }

    /***
     /***
     * 判断日期2是否是日期1的上月
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLastMonth(Date date1, Date date2) {
        return isSameMonth(getLastMonth(date1), date2);
    }

    /***
     * 判断日期2是否是日期1的去年同月
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isLastYearMonth(Date date1, Date date2) {
        return isSameMonth(getLastYear(date1), date2);
    }

    /**
     * 判断两个日期是否是同一季度
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isSameSeason(Date date1, Date date2) {
        return getSeason(date1) == getSeason(date2);
    }

    /**
     * 判断 日期2 是否是 日期1的 上一季度
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isLastSeason(Date date1, Date date2) {
        return getSeason(date1) == getSeason(date2) + 1;
    }

    /**
     * 判断两个日期是否是同一年
     *
     * @param date1 日期1
     * @param date2 日期2
     * @return
     */
    public static boolean isSameYear(Date date1, Date date2) {
        if (date1.getYear() != date2.getYear()) {
            return false;
        }
        return true;
    }

    /**
     * 将HH:mm解析成整点对象
     *
     * @param HMStr
     * @param before true 往前取整  false 往后取整
     * @return
     * @throws ParseException 解析异常
     */
    public static Date parseHMDate2NowTime(String HMStr, boolean before) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        Date HMTime = parse(FORMAT_HHMM, HMStr);
        calendar.setTime(HMTime);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int milliSecond = calendar.get(Calendar.MILLISECOND);
        if (before) {
            hour = hour;
            minute = 0;
            second = 0;
            milliSecond = 0;
        } else {
            hour = hour;
            minute = 59;
            second = 59;
            milliSecond = 999;
        }
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        calendar.set(Calendar.MILLISECOND, milliSecond);
        return calendar.getTime();
    }

    /**
     * 将日期设置为整点
     *
     * @param date   需要设置的日期
     * @param HMTime 小时分钟信息
     * @return
     */
    public static Date setDayHMS(Date date, Date HMTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, HMTime.getHours());
        calendar.set(Calendar.MINUTE, HMTime.getMinutes());
        calendar.set(Calendar.SECOND, HMTime.getSeconds());
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();

    }

    /**
     * 将日期设置为对应的时分信息
     *
     * @param date 需要设置的日期
     * @return
     * @throws ParseException
     */
    public static Date setDayHM(Date date, String HMTimeStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//HH:mm:ss.S
        Date HMTime = null;
        try {
            HMTime = parse(FORMAT_HHMM, HMTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar hmCalendar = Calendar.getInstance();
        hmCalendar.setTime(HMTime);//HH:mm:ss.S

        calendar.set(Calendar.HOUR_OF_DAY, hmCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, hmCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 将日期设置为对应的时分秒
     *
     * @param date 需要设置的日期
     * @return
     * @throws ParseException
     */
    public static Date setDayHMS(Date date, String HMSTimeStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//HH:mm:ss.S
        Date HMSTime = null;
        try {
            HMSTime = parse(FORMAT_HHMMSS, HMSTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar hmsCalendar = Calendar.getInstance();
        hmsCalendar.setTime(HMSTime);//HH:mm:ss.S

        calendar.set(Calendar.HOUR_OF_DAY, hmsCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, hmsCalendar.get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, hmsCalendar.get(Calendar.SECOND));

        return calendar.getTime();
    }

    /**
     * 将日期设置为对应的时分秒
     *
     * @param date 需要设置的日期
     * @return
     * @throws ParseException
     */
    public static Date setDayH(Date date, String HTimeStr) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);//HH:mm:ss.S
        Date HTime = null;
        try {
            HTime = parse(FORMAT_HH, HTimeStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar hCalendar = Calendar.getInstance();
        hCalendar.setTime(HTime);//HH:mm:ss.S

        calendar.set(Calendar.HOUR_OF_DAY, hCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 获取时间戳
     */
    public static String getTimeString() {
        Calendar calendar = Calendar.getInstance();
        return format(FORMAT_FULL, calendar.getTime());
    }

    /**
     * 获取日期年份
     *
     * @param date 日期
     * @return
     */
    public static int getYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 功能描述：返回月
     *
     * @param date Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日
     *
     * @param date Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 功能描述：返回小时
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinuteOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
    }

    /**
     * 返回秒钟
     *
     * @param date Date 日期
     * @return 返回秒钟
     */
    public static int getSecond(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.SECOND);
    }

    /**
     * 功能描述：返回毫秒
     *
     * @param date 日期
     * @return 返回毫
     */
    public static int getMillis(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(calendar.MILLISECOND);
    }


    /**
     * 取得当天日期是周几
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        int day_of_week = c.get(Calendar.DAY_OF_WEEK);
        day_of_week -= 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        return day_of_week;
    }

    /**
     * 获取一个月 周一 到 周日 的个数 用count存储
     *
     * @param date
     * @return 下标0 代表周一的天数 下表1代表周二的天数 依次类推
     */
    public static int[] getCountDayOfWeekInMonth(Date date) {
        int[] count = new int[7];
        Date firstDate = getFirstDateOfMonth(date);
        int dayOfWeek = getDayOfWeek(firstDate);
        int dayOfMonth = getPassDayOfMonth(date);
        for (int i = 0; i < dayOfMonth; i++) {
            count[dayOfWeek - 1]++;
            dayOfWeek++;
            if (dayOfWeek > 7) {
                dayOfWeek = 1;
            }
        }
        return count;
    }


    /**
     * 取得一月的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_MONTH);
        return week_of_year;
    }

    /**
     * 取得一年的第几周
     *
     * @param date
     * @return
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int week_of_year = c.get(Calendar.WEEK_OF_YEAR);
        return week_of_year;
    }


    /**
     * 根据日期取得对应周周一日期
     *
     * @param date
     * @return
     */
    public static Date getMondayOfWeek(Date date) {
        Calendar monday = Calendar.getInstance();
        monday.setTime(date);
        monday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        monday.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return monday.getTime();
    }

    /**
     * 根据日期取得对应周周日日期
     *
     * @param date
     * @return
     */
    public static Date getSundayOfWeek(Date date) {
        Calendar sunday = Calendar.getInstance();
        sunday.setTime(date);
        sunday.setFirstDayOfWeek(FIRST_DAY_OF_WEEK);
        sunday.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return sunday.getTime();
    }

    /**
     * 根据日期取得对应月的第一个相同周几
     *
     * @param date
     * @return
     */
    public static Date getFirstSametWeekOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        date = setDayMinTime(date);
        c.setTime(date);
        int weekIndex = c.get(Calendar.DAY_OF_WEEK);
        c.set(Calendar.DAY_OF_MONTH, 1);
        while (true) {
            if (weekIndex == c.get(Calendar.DAY_OF_WEEK)) {
                break;
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        return c.getTime();
    }

    /**
     * 取得月的剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfMonth(Date date) {
        int dayOfMonth = getDayOfMonth(date);
        int day = getPassDayOfMonth(date);
        return dayOfMonth - day;
    }

    /**
     * 取得月已经过的天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfMonth(Date date) {
        Date monthStart = setDayMinTime(getFirstDateOfMonth(new Date()));
        if (monthStart.after(date)) {
            return getDayOfMonth(date);
        }

        Date monthEnd = setDayMaxTime(getLastDateOfMonth(new Date()));
        if (monthEnd.before(date)) {
            return 0;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        return c.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * 取得月天数
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 取得月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 取得季度第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfSeason(Date date) {
        return getFirstDateOfMonth(getSeasonDate(date)[0]);
    }

    /**
     * 取得季度最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfSeason(Date date) {
        return getLastDateOfMonth(getSeasonDate(date)[2]);
    }

    /**
     * 取得季度天数
     *
     * @param date
     * @return
     */
    public static int getDayOfSeason(Date date) {
        int day = 0;
        Date[] seasonDates = getSeasonDate(date);
        for (Date date2 : seasonDates) {
            day += getDayOfMonth(date2);
        }
        return day;
    }

    /**
     * 取得季度剩余天数
     *
     * @param date
     * @return
     */
    public static int getRemainDayOfSeason(Date date) {
        return getDayOfSeason(date) - getPassDayOfSeason(date);
    }

    /**
     * 取得季度已过天数
     *
     * @param date
     * @return
     */
    public static int getPassDayOfSeason(Date date) {
        int day = 0;

        Date[] seasonDates = getSeasonDate(date);

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);

        if (month == Calendar.JANUARY || month == Calendar.APRIL
            || month == Calendar.JULY || month == Calendar.OCTOBER) {// 季度第一个月
            day = getPassDayOfMonth(seasonDates[0]);
        } else if (month == Calendar.FEBRUARY || month == Calendar.MAY
                   || month == Calendar.AUGUST || month == Calendar.NOVEMBER) {// 季度第二个月
            day = getDayOfMonth(seasonDates[0])
                  + getPassDayOfMonth(seasonDates[1]);
        } else if (month == Calendar.MARCH || month == Calendar.JUNE
                   || month == Calendar.SEPTEMBER || month == Calendar.DECEMBER) {// 季度第三个月
            day = getDayOfMonth(seasonDates[0]) + getDayOfMonth(seasonDates[1])
                  + getPassDayOfMonth(seasonDates[2]);
        }
        return day;
    }

    /**
     * 取得季度月
     *
     * @param date
     * @return
     */
    public static Date[] getSeasonDate(Date date) {
        Date[] season = new Date[3];

        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int nSeason = getSeason(date);
        if (nSeason == 1) {// 第一季度
            c.set(Calendar.MONTH, Calendar.JANUARY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.FEBRUARY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MARCH);
            season[2] = c.getTime();
        } else if (nSeason == 2) {// 第二季度
            c.set(Calendar.MONTH, Calendar.APRIL);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.MAY);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.JUNE);
            season[2] = c.getTime();
        } else if (nSeason == 3) {// 第三季度
            c.set(Calendar.MONTH, Calendar.JULY);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.AUGUST);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.SEPTEMBER);
            season[2] = c.getTime();
        } else if (nSeason == 4) {// 第四季度
            c.set(Calendar.MONTH, Calendar.OCTOBER);
            season[0] = c.getTime();
            c.set(Calendar.MONTH, Calendar.NOVEMBER);
            season[1] = c.getTime();
            c.set(Calendar.MONTH, Calendar.DECEMBER);
            season[2] = c.getTime();
        }
        return season;
    }

    /**
     * 1 第一季度 2 第二季度 3 第三季度 4 第四季度
     *
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    /**
     * 取得年第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDateOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMinimum(Calendar.DAY_OF_YEAR));
        return setDayMinTime(c.getTime());
    }

    /**
     * 取得年最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDateOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        return setDayMaxTime(c.getTime());
    }

    /**
     * String转Date
     * 支持：2010-12-01 23:15:06.999
     * 2010-12-01 23:15:06
     * 2010-12-01 23:15
     * 2010-12-01
     *
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date dateFormat(String dateStr) throws ParseException {
        if (dateStr.length() == 23) {
            return parse(FORMAT_FULL, dateStr);
        }
        if (dateStr.length() == 19) {
            return DateUtil.parse(FORMAT_LONG, dateStr);
        } else if (dateStr.length() == 16) {
            return DateUtil.parse(FORMAT_YYYY_MM_DD_HH_MM, dateStr);
        } else if (dateStr.length() == 10) {
            return DateUtil.parse(FORMAT_SHORT, dateStr);
        } else {
            return null;
        }
    }

    /**
     * 设置一天的最大值
     *
     * @param date
     * @return
     */
    public static Date setDayMaxTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, c.getActualMaximum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMaximum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMaximum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
        return c.getTime();
    }

    /**
     * 设置一天的最小值
     *
     * @param date
     * @return
     */
    public static Date setDayMinTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, c.getActualMinimum(Calendar.HOUR_OF_DAY));
        c.set(Calendar.MINUTE, c.getActualMinimum(Calendar.MINUTE));
        c.set(Calendar.SECOND, c.getActualMinimum(Calendar.SECOND));
        c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
        return c.getTime();
    }

    /**
     * 当前日期加n毫秒
     *
     * @param date
     * @param num
     * @return
     */
    public static Date dateAddMillisecond(Date date, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MILLISECOND, num);
        return c.getTime();
    }

    /**
     * 获取两个时间点之间的天
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    public static List<Date> getDaysBetweenStartDateAndEndDate(Date startDate, Date endDate) {
        List<Date> dates = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        while (c.getTime().before(endDate)) {
            dates.add(c.getTime());
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        dates.add(endDate);
        return dates;
    }

    /**
     * 将字符串解析成时间，如果无法解析返回null
     *
     * @param sdf
     * @param dateStr
     * @return
     */
    public static Date parseDate(SimpleDateFormat sdf, String dateStr) {
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    public static Date maxDate(Date date1, Date date2) {
        if (date1.before(date2)) {
            return date2;
        } else {
            return date1;
        }
    }

    public static Date minDate(Date date1, Date date2) {
        if (date1.before(date2)) {
            return date1;
        } else {
            return date2;
        }
    }

    public static Date UTC2Local(Date date) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        String utcDate = sdf1.format(date);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utcZone);
        Date result = null;
        try {
            result = sdf.parse(utcDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date local2UTC(Date localDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
        TimeZone utcZone = TimeZone.getTimeZone("UTC");
        sdf.setTimeZone(utcZone);
        String utcTime = sdf.format(localDate);
        Date result = null;
        try {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
            result = sdf1.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Date> getMonthDyas(Date date) {
        Date monthStart = getFirstDateOfMonth(date);
        Date monthEnd = getLastDateOfMonth(date);
        Date currentDate = new Date();
        monthEnd = monthEnd.before(currentDate) ? monthEnd : currentDate;
        List<Date> dates = getDaysBetweenStartDateAndEndDate(monthStart, monthEnd);
        return dates;
    }

    public static String format(String dateFormatStr, Date date) {
        Locale locale = Locale.getDefault();
        SimpleDateFormat sdf = null;
        if (locale != null && locale == Locale.ENGLISH) {
            sdf = new SimpleDateFormat(dateFormatStr, locale);
        } else {
            sdf = new SimpleDateFormat(dateFormatStr, dateformatSymbols);
        }

        return sdf.format(date);
    }

    public static Date parse(String dateFormatStr, String dateStr) throws ParseException {
        Locale locale = Locale.getDefault();
        SimpleDateFormat sdf;
        if (locale != null && locale == Locale.ENGLISH) {
            sdf = new SimpleDateFormat(dateFormatStr, locale);
        } else {
            sdf = new SimpleDateFormat(dateFormatStr, dateformatSymbols);
        }
        return sdf.parse(dateStr);
    }
}
