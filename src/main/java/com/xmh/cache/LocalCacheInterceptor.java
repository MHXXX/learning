package com.xmh.cache;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.aop.framework.AopProxyUtils;

import java.lang.reflect.Method;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/3/28 10:13 AM
 */

@Slf4j
public class LocalCacheInterceptor implements MethodInterceptor {

    private final CustomizedExpressionEvaluator EVALUATOR = new CustomizedExpressionEvaluator();

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        Class<?> targetClass = invocation.getThis() == null ? null : AopProxyUtils.ultimateTargetClass(invocation.getThis());
        LocalCache localCache = method.getAnnotation(LocalCache.class);
        String expression = localCache.expression();
        Object[] args = invocation.getArguments();
        String key;
        Object o;
        try {
            key = EVALUATOR.parseExpression(expression, targetClass, method, args);
        } catch (Exception e) {
            log.error("获取缓存 key 失败", e);
            return invocation.proceed();
        }

        // 删除缓存
        if (localCache.delete()) {
            o = invocation.proceed();
            CacheUtil.delete(key);
            return o;
        }

        // 读取缓存或写入缓存
        ImmutablePair<Boolean, Object> cacheResult;
        try {
            cacheResult = CacheUtil.get(key);
            Boolean exists = cacheResult.left;
            if (exists) {
                return cacheResult.right;
            }
        } catch (RuntimeException e) {
            log.error("缓存调用方法:{}", invocation.getMethod().toGenericString());
            log.error("缓存出错", e);
        }

        o = invocation.proceed();
        CacheUtil.cache(key, o, localCache.timeunit(), localCache.duration());
        return o;

    }
}
