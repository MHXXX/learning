package com.xmh.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * .
 *
 * @author 谢明辉
 * @date 2022/1/21
 */
@RestController
public class MainController {

    @GetMapping("/test")
    public String test() {
        return "success";
    }

}
