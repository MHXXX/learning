package com.xmh.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author 谢明辉
 * @date 2021/9/9
 */
@Slf4j
public class CacheUtil {
    private static final ConcurrentHashMap<String, CacheInfo> CACHE = new ConcurrentHashMap<>();


    public static void cache(String key, Object o) throws Exception {
        cache(key, o, TimeUnit.MINUTES, 5L);
    }

    public static void cache(String key, Object o, TimeUnit timeUnit, long duration) throws Exception {
        cache(key, o, timeUnit, duration, null, false);
    }

    public static void cache(String key, Object o, TimeUnit timeUnit, long duration, Callable<?> callable, boolean updateExpireOnAccess) throws Exception {
        log.info("对数据进行缓存,key:{},缓存时长:{},单位:{}", key, duration, timeUnit.toString());
        if (o == null && callable == null) {
            throw new RuntimeException("数据和load方法不能都为空");
        }

        long expire = duration == -1 ? Long.MAX_VALUE : timeUnit.toMillis(duration) + System.currentTimeMillis();
        CacheInfo cacheInfo = new CacheInfo(key, o, timeUnit, duration, callable, updateExpireOnAccess, expire);
        CACHE.put(key, cacheInfo);
    }

    /**
     * @return <li>left : 是否存在</li><li>right : 值</li>
     */
    public static <T> ImmutablePair<Boolean, T> get(String key) {
        // key 不存在
        if (!CACHE.containsKey(key)) {
            return ImmutablePair.of(false, null);
        }

        CacheInfo cacheInfo = CACHE.get(key);
        Object o;
        try {
            o = cacheInfo.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ImmutablePair.of(true, o == null ? null : (T) o);
    }

    private static class CacheInfo {
        /** 缓存Loader */
        public final Callable<?> callable;
        /** 访问时是否刷新过期时间 */
        public final boolean updateExpireOnAccess;
        private final String key;
        /** 缓存时长的单位 */
        private final TimeUnit timeUnit;
        /** 缓存时长 */
        private final Long duration;
        /** 过期时间戳 */
        public Long expire;
        /** 缓存数据 */
        public Object data;

        public CacheInfo(String key, Object data, TimeUnit timeUnit, Long duration, Callable<?> callable, boolean updateExpireOnAccess, Long expire) {
            this.key = key;
            this.data = data;
            this.timeUnit = timeUnit;
            this.duration = duration;
            this.callable = callable;
            this.updateExpireOnAccess = updateExpireOnAccess;
            this.expire = expire;
        }

        public Object get() throws Exception {
            // 没过期
            if (System.currentTimeMillis() < expire) {
                //  数据为空 callable 不为空,则初始化
                if (data == null && callable != null) {
                    data = callable.call();
                }
                // 续期处理 duration 为 -1 不用续期
                if (updateExpireOnAccess && duration != -1) {
                    expire = System.currentTimeMillis() + timeUnit.toMillis(duration);
                }
                log.debug("缓存命中:{}", key);
                return data;
            }
            // 过期处理
            else {
                if (callable != null) {
                    log.debug("{} 缓存过期,调用 Loader 重新缓存", key);
                    data = callable.call();
                    expire = System.currentTimeMillis() + timeUnit.toMillis(duration);
                    return data;
                } else {
                    return null;
                }

            }
        }
    }
}
