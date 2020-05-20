package com.csj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/test")
    public String test1(ModelMap modelMap) throws Exception{
        modelMap.addAttribute("error","我是错误星系");

        return "publish";
    }

}
