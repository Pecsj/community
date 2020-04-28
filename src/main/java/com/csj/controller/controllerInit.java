package com.csj.controller;

import com.csj.domain.User;
import com.csj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class controllerInit {
    @Autowired
    private UserService service;

    @RequestMapping("/hello")
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

}
