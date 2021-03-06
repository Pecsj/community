package com.csj.controller;

import com.csj.domain.AccessToken;
import com.csj.domain.dto.GithubUser;
import com.csj.domain.User;
import com.csj.service.IUserService;
import com.csj.service.impl.UserService;
import com.csj.util.Convert;
import com.csj.util.OkHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@SessionAttributes("user")
public class AuthController {
    @Autowired
    OkHttp okHttp = new OkHttp();
    @Autowired
    IUserService userService = new UserService();

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.url}")
    private String redirectUrl;

    @GetMapping("/github")
    public ModelAndView github(){
        StringBuilder url = new StringBuilder("https://github.com/login/oauth/authorize");
        url.append("?client_id="+clientId);
        url.append("&redirect_uri="+redirectUrl);
        url.append("&state="+System.currentTimeMillis());
        url.append("&scope=user");
        System.out.println(url);
        ModelAndView modelAndView = new ModelAndView("redirect:"+url);
        return modelAndView;
    }
    /**
     * 用code码发送access_token请求获取用户信息
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public ModelAndView callBack(String code,
                           ModelMap modelMap,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception{
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setCode(code);
        accessToken.setState("1");
        ModelAndView modelAndView = new ModelAndView("redirect:/index");

        //获取token码
        String token = okHttp.getAccessToken(accessToken);
        //根据token 获取用户信息,获取失败重复获取5次
        GithubUser guser = null;
        int count = 0;
        while (guser==null&&count<5){
            if(count==4){
                request.setAttribute("error","与github链接超时");
                return modelAndView;
            }
            count++;
            guser = okHttp.getUser(token);
        }

        //查看数据库中是否存在
        if(!userService.isExistByName(guser.getLogin())){
            //数据库不存在，存入数据库，设置用户随机token码用于cookie持久化登录
            guser.setToken();
            userService.insertGithubUser(guser);
        }
        User user = Convert.gitToUser(guser);
        //数据库中存在，取出授权码//保存到session中持久化登录，将token保存到cookie中用作登录验证
        user = userService.findByName(user.getName());
        request.getSession().setAttribute("user",user);
        System.out.println("用户存入session"+user);
        Cookie cookie = new Cookie("token", user.getToken());
        //测试使用值5分钟失效
        cookie.setMaxAge(60*60*24*7);
        response.addCookie(cookie);
        return modelAndView;
    }
}
