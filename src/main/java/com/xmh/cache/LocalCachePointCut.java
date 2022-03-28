package com.xmh.cache;

import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/3/24
 */

public class LocalCachePointCut extends StaticMethodMatcherPointcut {

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        Set<LocalCache> annotations = AnnotatedElementUtils.findAllMergedAnnotations(method, LocalCache.class);
        return !annotations.isEmpty();
    }
}
