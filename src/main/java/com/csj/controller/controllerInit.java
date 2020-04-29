package com.csj.controller;

import com.csj.domain.User;
import com.csj.service.IUserService;
import com.csj.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@SessionAttributes("value")
public class controllerInit {
    @Autowired
    private IUserService service;

    @RequestMapping("/data")
    public String hello(String name, Model model){
        model.addAttribute("name",name);
        List<User> users = service.findAll();
        System.out.println(users);
        return "success";
    }

    @RequestMapping("/index")
    public String testGithub(){
        return "index";
    }

    //测试存入一个session
    @RequestMapping("/add")
    public String add(ModelMap modelMap){
        modelMap.addAttribute("value","csj");
        return "success";
    }

    @RequestMapping("/show")
    public String show(@SessionAttribute(value = "value",required = false) String value){
        System.out.println(value);
        return "success";
    }


}
