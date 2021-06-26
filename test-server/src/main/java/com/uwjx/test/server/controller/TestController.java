package com.uwjx.test.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 19:44
 */
@RestController
@Slf4j
@RequestMapping(value = "test")
public class TestController {

    @GetMapping
    public String get(@RequestParam("key") String key) {
        log.warn("测试业务接口 : {}" , key);
        return "测试业务接口返回:" + key;
    }
}
