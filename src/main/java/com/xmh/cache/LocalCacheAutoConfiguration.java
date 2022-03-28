package com.xmh.cache;

import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;

/**
 * .
 *
 * @author 谢明辉
 * @create 2022/3/28 10:10 AM
 */
public class LocalCacheAutoConfiguration {

    @Bean
    public LocalCachePointCut localCachePointCut() {
        return new LocalCachePointCut();
    }

    @Bean
    LocalCacheInterceptor localCacheInterceptor() {
        return new LocalCacheInterceptor();
    }

    @Bean
    public DefaultPointcutAdvisor localCachePointcutAdvisor(LocalCachePointCut pointCut, LocalCacheInterceptor interceptor) {
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(interceptor);
        advisor.setPointcut(pointCut);
        return advisor;
    }

    @PostConstruct
    public void init() {
        System.out.println("******************");
        System.out.println("****MY LOG !!!****");
        System.out.println("******************");
    }
}
