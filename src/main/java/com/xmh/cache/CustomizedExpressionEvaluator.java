package com.xmh.cache;

import com.xmh.log.core.MyLogEvaluationContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.context.expression.CachedExpressionEvaluator;
import org.springframework.context.expression.MethodBasedEvaluationContext;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/3/24 8:53 PM
 */
public class CustomizedExpressionEvaluator extends CachedExpressionEvaluator {

    private final Map<ExpressionKey, Expression> expressionCache = new ConcurrentHashMap<>(64);
    private final Map<AnnotatedElementKey, Method> targetMethodCache = new ConcurrentHashMap<>();

    public String parseExpression(String expression, Class<?> targetClass, Method method, Object[] args) {
        EvaluationContext evaluationContext = createEvaluationContext(targetClass, method, args);
        AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
        return getExpression(this.expressionCache, annotatedElementKey, expression).getValue(evaluationContext, String.class);
    }

    public EvaluationContext createEvaluationContext(Class<?> targetClass, Method method, Object[] args) {
        Method targetMethod = getTargetMethod(targetClass, method);
        return new MethodBasedEvaluationContext(new Root(method, targetClass), targetMethod, args, getParameterNameDiscoverer());
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

    @Getter
    @Setter
    private static class Root {
        private String methodName;
        private Method method;
        private String target;
        private Class<?> targetClass;

        public Root(Method method, Class<?> targetClass) {
            this.method = method;
            this.targetClass = targetClass;
            this.methodName = method.getName();
            this.target = targetClass.getName();
        }

    }

}
