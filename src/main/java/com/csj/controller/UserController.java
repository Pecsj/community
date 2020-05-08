package com.csj.controller;

import com.csj.domain.User;
import com.csj.service.IBaidu;
import com.csj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IBaidu baidu;

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
                           String base64Img,
                           HttpServletRequest request){
        //调用百度AI添加用户
        System.out.println(base64Img);
        //判断百度云人脸库是否已经存在该用户
        int id = baidu.faceSearch(base64Img);
        if(id < 0){
            //存入数据库
            user.setToken(UUID.randomUUID().toString());
            userService.insertUser(user);
            //存入session
            request.getSession().setAttribute("user",user);
            System.out.println("用户存入session");
            baidu.addFace(user.getId(),user.getName(), base64Img);
            return "redirect:index";
        }else{
            //人脸库已经存在
            request.setAttribute("error","人脸库中已存在该用户");
            return "register";
        }
    }

    /**
     * 注销退出用户登录
     * @return
     */
    @GetMapping("/loginout")
    public String loginOut(HttpServletRequest request,
                           HttpServletResponse response){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if ("token".equals(cookie.getName())){
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        request.getSession().removeAttribute("user");
        return "redirect:index";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    /**
     * 用户登录
     * @return
     */
    @PostMapping("/login")
    public void login (HttpServletRequest request,
                        HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=utf-8");

        String image = request.getParameter("baseImg");
        System.out.println(image);
        //调用百度AI
        int id = baidu.faceSearch(image);
        if(id<0){
            //调用失败
            response.getWriter().write("false");//写入到返回结果中
            return;
        }
        //调用成功查询数据库对应id用户
        User user = userService.findById(id);
        System.out.println(user);
        //将查询的用户放到session和cookie中
        request.getSession().setAttribute("user",user);
        Cookie token = new Cookie("token",user.getToken());
        response.addCookie(token);
        response.getWriter().write("ok");//写入到返回结果中
    }
}
