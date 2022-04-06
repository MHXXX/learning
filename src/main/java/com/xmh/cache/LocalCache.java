package com.xmh.cache;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author 谢明辉
 * @date 2021/9/9
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface LocalCache {

    /**
     * 指定 cache 数据的 key 的 SPEL 表达式
     */
    String expression();

    /**
     * 指定 cache 的过期时间单位
     */
    TimeUnit timeunit() default TimeUnit.MINUTES;

    /**
     * 指定 cache 的过期时间
     */
    long duration() default -1;

    /**
     * 调用时进行删除操作
     */
    boolean delete() default false;

    /** 使用正则匹配删除 */
    boolean deleteAll() default false;
}
