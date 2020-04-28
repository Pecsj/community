package com.csj.controller;

import com.csj.domain.AccessToken;
import com.csj.domain.GithubUser;
import com.csj.domain.User;
import com.csj.service.IUserService;
import com.csj.service.impl.UserService;
import com.csj.util.OkHttp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
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

    /**
     * 用code码发送access_token请求获取用户信息
     * @param code
     * @return
     */
    @GetMapping("/callback")
    public String callBack(String code) throws Exception{
        AccessToken accessToken = new AccessToken();
        accessToken.setClient_id(clientId);
        accessToken.setClient_secret(clientSecret);
        accessToken.setRedirect_uri(redirectUrl);
        accessToken.setCode(code);
        accessToken.setState("1");

        //获取token码
        String token = okHttp.getAccessToken(accessToken);
        //根据token 获取用户信息
        Thread.sleep(1000);//有时会出现服务器过早关闭链接导致空指针异常(github延迟高)出现commitreset,和网络有关
        GithubUser user = okHttp.getUser(token);
        System.out.println(user.getId());
        System.out.println(user.getLogin());
        System.out.println(user.getToken());

        //将GitHubuser保存到数据库
        userService.insertGithubUser(user);
        return "index";
    }

}
