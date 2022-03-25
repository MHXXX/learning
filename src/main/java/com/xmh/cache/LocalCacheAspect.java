package com.xmh.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * .
 *
 * @author 谢明辉
 * @date 2021/9/9
 */

@Component
@Aspect
@Slf4j
public class LocalCacheAspect {

    private static final CustomizedExpressionEvaluator EVALUATOR = new CustomizedExpressionEvaluator();

    @Around("@annotation(localCache)")
    public Object around(ProceedingJoinPoint point, LocalCache localCache) throws Throwable {
        String key = getCacheKey(point, localCache.expression());
        ImmutablePair<Boolean, Object> cacheResult;
        try {
            cacheResult = CacheUtil.get(key);
            Boolean exists = cacheResult.left;
            if (exists) {
                return cacheResult.right;
            }
        } catch (RuntimeException e) {
            log.error("缓存调用方法:{}", point.toLongString());
            log.error("缓存出错", e);
        }

        Object o = point.proceed();
        CacheUtil.cache(key, o, localCache.timeunit(), localCache.duration());
        return o;
    }

    private String getCacheKey(ProceedingJoinPoint point, String expression) {
        Object[] args = point.getArgs();
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = point.getTarget().getClass();
        return EVALUATOR.parseExpression(expression, targetClass, method, args);
    }
}
