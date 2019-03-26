package com.xmh.utils;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;


/**
 * @author 谢明辉
 * @description 工具集
 * 判断字符串是否为空{@link #isEmpty(String)}
 * 判断数组是否为空{@link #isEmpty(Object[])}
 * 将一个集合平均分成N个集合{@link #averageAssign(List, int)}
 * 合并字符串{@link #stringMerge(Object...)}
 * 线程休息1秒{@link #sleep()}
 * 线程休息N毫秒{@link #sleep(long)}
 * 生成6位随机数{@link #random6()}
 * 验证手机号码{@link #validatePhoneNumber(String)}
 * 验证身份证号码{@link #validateIDNumber(String)}
 * 验证邮箱{@link #validateEmail(String)}
 * 获取32位UUID{@link #getUUID()}
 * 得到异常信息字符串{@link #getErrorInfo(Exception)}
 */

public class MyUtils {

    private static SecureRandom random = new SecureRandom();

    /** 匹配邮箱地址 */
    private static final Pattern MAIL_PATTERN = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
    /** 匹配手机号码 */
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^1[3|4|5|7|8][0-9]\\d{8}$");
    /** 匹配身份证号 */
    private static final Pattern ID_PATTERN = Pattern.compile("^\\d{6}(\\d{8}|\\d{11})[0-9a-zA-Z]$");


    /**
     * @param s 需要判断是否为空的字符串
     * @return boolean
     * @createDate 2018-11-15
     * @description 判断字符串是否为空
     */
    public static boolean isEmpty(String s) {
        return "".equals(s) || s == null;
    }

    /**
     * 判断数组是否为空
     *
     * @param objects 需要判断的数组
     * @return boolean
     * @createDate 2019-3-12
     */
    public static boolean isEmpty(Object[] objects) {
        return objects == null || objects.length == 0;
    }


    /**
     * @param source 源数据集合
     * @param n      份数
     * @return java.util.List<java.util.List> 均分后的集合
     * @createDate 2018-11-15
     * @description 将一个集合平均分成n个集合
     */
    public static <T> List<List<T>> averageAssign(List<T> source, int n) {
        List<List<T>> result = new ArrayList<List<T>>();
        //余数
        int remainder = source.size() % n;
        //商
        int number = source.size() / n;
        //偏移量
        int offset = 0;
        for (int i = 0; i < n; i++) {
            List<T> value = null;
            if (remainder > 0) {
                value = source.subList(i * number + offset, (i + 1) * number + offset + 1);
                remainder--;
                offset++;
            } else {
                value = source.subList(i * number + offset, (i + 1) * number + offset);
            }
            result.add(value);
        }
        return result;
    }

    /**
     * @param objects 需要合并的字符串
     * @return java.lang.String
     * @createDate 2018-11-15
     * @description 合并字符串
     */
    public static String stringMerge(Object... objects) {
        if (objects.length == 0) {
            return "";
        }
        if (objects.length == 1) {
            return objects[0].toString();
        }
        StringBuilder sb = new StringBuilder();
        for (Object object : objects) {
            sb.append(object);
        }
        return sb.toString();
    }

    /**
     * @createDate 2018-11-15
     * @description 线程休息一秒
     */
    public static void sleep() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param millis 传入的时间，毫秒级
     * @createDate 2018-11-15
     * @description 线程休息自定义时间
     */
    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * @createDate 2018-11-15
     * @description 生成6位随机数
     */
    public static int random6() {
        return Math.abs(random.nextInt(899999)) + 100000;
    }


    /**
     * @param mobile 手机号码
     * @return boolean
     * @createDate 2018-11-15
     * @description 验证手机号码
     */
    public static boolean validatePhoneNumber(String mobile) {
        return !isEmpty(mobile) && MOBILE_PATTERN.matcher(mobile).matches();
    }

    /**
     * @param id 身份证号
     * @return boolean
     * @createDate 2018-11-15
     * @description 验证身份证号码
     */
    public static boolean validateIDNumber(String id) {
        return isEmpty(id) && ID_PATTERN.matcher(id).matches();
    }

    /**
     * @param email 电子邮箱
     * @return boolean
     * @createDate 2018-11-15
     * @description 验证电子邮箱
     */
    public static boolean validateEmail(String email) {
        return isEmpty(email) && MAIL_PATTERN.matcher(email).matches();
    }


    /**
     * @return java.lang.String
     * @createDate 2018-11-15
     * @description 获取32位UUID
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().trim().replaceAll("-", "").toUpperCase();

    }

    /**
     * 将exception打印到String中
     *
     * @param e 异常
     * @return java.lang.String
     * @createDate 2019-3-12
     */
    public static String getErrorInfo(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String errorInfo = sw.toString();
        try {
            sw.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        pw.close();
        return errorInfo;
    }

}
