package com.csj.controller;

import com.csj.domain.User;
import com.csj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/register")
    public String getRegister(){
        return "register";
    }

    /**
     * 接收表单注册用户
     * @return
     */
    @PostMapping("/register")
    public String register(User user,
                           HttpServletRequest request){
        //存入数据库
        user.setToken(UUID.randomUUID().toString());
        userService.insertUser(user);
        System.out.println(user);
        //存入session
        request.getSession().setAttribute("user",user);
        System.out.println("用户存入session");
        return "redirect:index";
    }



}
