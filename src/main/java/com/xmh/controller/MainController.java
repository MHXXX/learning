package com.xmh.controller;

import com.xmh.cache.LocalCache;
import com.xmh.log.core.MyLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/1/21
 */
@RestController
public class MainController {

    @GetMapping("/test/{num}")
    @MyLog(success = "'hello ' + #num")
    @LocalCache(expression = "'testKey_' + #num", duration = 10 ,timeunit = TimeUnit.SECONDS)
    public String test(@PathVariable("num") Integer num) {
        return "success " + num;
    }

}
