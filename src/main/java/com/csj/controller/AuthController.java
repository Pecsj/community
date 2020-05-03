package com.csj.controller;

import com.csj.domain.AccessToken;
import com.csj.domain.GithubUser;
import com.csj.domain.User;
import com.csj.service.IUserService;
import com.csj.service.impl.UserService;
import com.csj.util.Convert;
import com.csj.util.OkHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
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
    public String github(){
        StringBuilder url = new StringBuilder("https://github.com/login/oauth/authorize");
        url.append("?client_id="+clientId);
        url.append("&redirect_uri="+redirectUrl);
        url.append("&state="+System.currentTimeMillis());
        url.append("&scope=user");
        System.out.println(url);
        return "redirect:"+url;
    }
    /**
     * 用code码发送access_token请求获取用户信息
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public String callBack(String code,
                           ModelMap modelMap,
                           HttpServletRequest request,
                           HttpServletResponse response) throws Exception{
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setCode(code);
        accessToken.setState("1");

        //获取token码
        String token = okHttp.getAccessToken(accessToken);
        //根据token 获取用户信息
        GithubUser guser = null;
        int count = 0;
        while (guser==null&&count<5){
            if(count==4){
                request.setAttribute("error","与github链接超时");
                return "index";
            }
            count++;
            Thread.sleep(1000);//有时会出现服务器过早关闭链接导致空指针异常(github延迟高)出现commitreset,和网络有关
            guser = okHttp.getUser(token);
        }

        //查看数据库中是否存在
        if(!userService.isExistByName(guser.getLogin())){
            //数据库不存在，存入数据库，设置用户随机token码用于cookie持久化登录
            guser.setToken();
            userService.insertGithubUser(guser);
        }

        //保存到session中持久化登录，将token保存到cookie中用作登录验证
        User user = Convert.gitToUser(guser);
        modelMap.addAttribute("user",user);//使用modelMap和Attributes注解存入session
        System.out.println("用户存入session");
        //数据库中存在，取出授权码
        user = userService.findByName(user.getName());
        Cookie cookie = new Cookie("token", user.getToken());
        //测试使用值5分钟失效
        cookie.setMaxAge(60*5);
        response.addCookie(cookie);

        return "index";
    }
}
