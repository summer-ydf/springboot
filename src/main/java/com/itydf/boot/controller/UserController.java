package com.itydf.boot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @GetMapping("index")
    @ResponseBody
    public String index(){
        System.out.println("交给Git管理了");
        System.out.println("代码修改001");
        System.out.println("代码修改002");
        return "Hello SpringData JPA";
    }
}
