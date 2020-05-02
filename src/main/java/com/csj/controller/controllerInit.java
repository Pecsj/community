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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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

    @RequestMapping("/")
    public String testGithub(HttpServletRequest request){
        //去cookie中查找用户是否持久化登录
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if("token".equals(cookie.getName())){
                    //根据cookie中的token授权码获取用户
                    User user = new User();
                    user = service.findByToken(cookie.getValue());
                    request.getSession().setAttribute("user",user);
                }
            }
        }
        return "index";
    }


}
