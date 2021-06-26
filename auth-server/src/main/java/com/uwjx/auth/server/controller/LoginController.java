package com.uwjx.auth.server.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wanghuan
 * @link https://huan.uwjx.com
 * @date 2021/6/26 20:16
 */
@RestController
@Slf4j
@RequestMapping(value = "login")
public class LoginController {

    @PostMapping
    public String login(){
        log.warn("登录模拟");
        return "token";
    }
}
