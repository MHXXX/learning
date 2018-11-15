package com.xmh.utils;


import java.util.ArrayList;
import java.util.List;


/**
 * @author 谢明辉
 * @createDate 2018-11-15
 * @description
 * @see #isEmpty 判断字符串是否为空
 * @see #averageAssign 将一个集合平均分成n个集合
 */

public class MyUtils {

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
}
