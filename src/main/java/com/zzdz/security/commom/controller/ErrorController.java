package com.zzdz.security.commom.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    //公共错误跳转
    @RequestMapping(value="autherror")
    public String autherror(int code) {
        return code == 1 ? "未登录" : "未授权";
    }

}
