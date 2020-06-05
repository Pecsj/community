package com.csj.controller;

import com.csj.domain.User;
import com.csj.domain.dto.UpdateXxx;
import com.csj.service.IBaidu;
import com.csj.service.IUserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
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
                           HttpServletRequest request,
                           HttpServletResponse response){
        //调用百度AI添加用户
        System.out.println(user.getFace());
        //判断百度云人脸库是否已经存在该用户
        int id = baidu.faceSearch(user.getFace());
        if(id < 0){
            if(!userService.isExistByName(user.getName())){
                //存入数据库
                user.setToken(UUID.randomUUID().toString());
                userService.insertUser(user);
                //存入session和百度人脸库
                request.getSession().setAttribute("user",user);
                System.out.println("用户存入session");
                Cookie token = new Cookie("token",user.getToken());
                //测试值五分钟
                token.setMaxAge(60*60*24*7);
                response.addCookie(token);
                baidu.addFace(user.getId(),user.getName(), user.getFace());
                return "redirect:index";
            }else{
                //数据库有重名
                System.out.println("数据库有重名");
                return "register";
            }
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
    @ResponseBody
    public Object login (HttpServletRequest request,
                       HttpServletResponse response,
                       String name,
                       String password) throws IOException, ServletException {
        response.setContentType("text/html; charset=utf-8");

        String image = request.getParameter("baseImg");
        System.out.println("image="+image);
        int id = -1;
        User user = null;
        if(image!=null){
            //调用百度AI
            id = baidu.faceSearch(image);
            if(id<0){
                //调用失败
                return "baiduError";
            }
            user = userService.findById(id);
        }else{
            //普通登录
            user = userService.loginUser(name, password);
            if (user == null){
                return "loginError";
            }
        }
        System.out.println(user);
        //将查询的用户放到session和cookie中
        request.getSession().setAttribute("user",user);
        Cookie token = new Cookie("token",user.getToken());
        //测试值五分钟
        token.setMaxAge(60*60*24*7);
        response.addCookie(token);
        return "ok";
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateXxx(String key,String value,
                            HttpServletRequest request){
        int id = ((User)request.getSession().getAttribute("user")).getId();

        UpdateXxx updateXxx = new UpdateXxx(id,key,value);
        userService.updateXxx(updateXxx);
        return "ok";
    }

    /**
     * 添加人脸
     * @return
     */
    @RequestMapping("/addFace")
    @ResponseBody
    public Object addFace(String img,HttpServletRequest request){
        User user = (User)request.getSession().getAttribute("user");
        user.setFace(img);
        //添加到人脸库
        JSONObject res = baidu.addFace(user.getId(), user.getName(), img);
        //更新数据库
        int i = 0;
        if("SUCCESS".equals(res.getString("error_msg"))){
            i = userService.insertFace(user);
        }

        if(i>0){
            return '1';
        }else return '0';
    }


}
