package com.xmh.cache;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(LocalCacheAutoConfiguration.class)
public @interface EnableLocalCache {
}