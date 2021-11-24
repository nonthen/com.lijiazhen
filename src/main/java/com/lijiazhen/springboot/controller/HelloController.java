package com.lijiazhen.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;


import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @RequestMapping("/sayHello")
    public String sayHello(ModelMap modelMap) {
        Map<String, String> user = new HashMap<>();
        user.put("userid", "9678");
        user.put("username", "xiaoli");
        user.put("userage", "22");
        modelMap.put("user", user);
        return "hello";
    }
}
