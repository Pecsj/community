package com.csj.controller;

import com.csj.domain.User;
import com.csj.domain.dto.ListArticle;
import com.csj.domain.dto.UpdateXxx;
import com.csj.service.IArticleService;
import com.csj.service.IBaidu;
import com.csj.service.IUserService;
import com.csj.service.impl.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@SessionAttributes("value")
public class controllerInit {
    @Autowired
    private IUserService userservice;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IBaidu baidu;

    @Value("${page.pageCount}")
    private Integer pageCount;

    @RequestMapping("/data")
    public String hello(String name, Model model){
        model.addAttribute("name",name);
        List<User> users = userservice.findAll();
        System.out.println(users);
        return "success";
    }

    @RequestMapping({"/","/index"})
    public String init(HttpServletRequest request,
                             Integer pageNumber
                             ){
        Object sessionUser = request.getSession().getAttribute("user");
        if(sessionUser==null){
            //去cookie中查找用户是否持久化登录
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (Cookie cookie : cookies) {
                    if("token".equals(cookie.getName())){
                        //根据cookie中的token授权码获取用户
                        User user = null;
                        user = userservice.findByToken(cookie.getValue());
                        request.getSession().setAttribute("user",user);
                    }
                }
            }
        }

        //获取文章列表
        if(pageNumber==null){
            pageNumber=1;
        }
        PageInfo<ListArticle> pageInfo = articleService.findListArticle(pageNumber, pageCount);
        request.setAttribute("pageInfo",pageInfo);
        return "index";
    }

    /**
     * 跳转到个人详情页面
     * @return
     */
    @GetMapping("/myinfo")
    public String myInfo(boolean update,
                         HttpServletRequest request){
        if (update){
            int id = ((User)request.getSession().getAttribute("user")).getId();
            User user = userservice.findById(id);
            request.getSession().setAttribute("user",user);
        }
        return "myinfo";
    }

    @GetMapping("/update")
    @ResponseBody
    public String updateXxx(String key,String value,
                            HttpServletRequest request){
        int id = ((User)request.getSession().getAttribute("user")).getId();
        int age=-1;
        if("age".equals(key)){
            age = Integer.parseInt(value);
            userservice.updateAge(id,age);
            return "ok";
        }
        UpdateXxx updateXxx = new UpdateXxx(id,key,value);
        userservice.updateXxx(updateXxx);
        return "ok";
    }


}
