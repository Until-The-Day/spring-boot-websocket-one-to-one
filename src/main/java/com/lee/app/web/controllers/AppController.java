package com.lee.app.web.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class AppController {

    @RequestMapping(value = "/")
    public String app() {
        log.info("请求根目录");

        return "app";
    }

}
