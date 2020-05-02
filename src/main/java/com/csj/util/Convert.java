package com.csj.util;

import com.csj.domain.GithubUser;
import com.csj.domain.User;

/**
 * 转换器
 */
public class Convert {
    /**
     * githubUser转user
     * @return
     */
    public static User gitToUser(GithubUser guser){
        User user = new User();
        user.setName(guser.getLogin());
        user.setPassword(guser.getId());
        user.setToken(guser.getToken());
        user.setAvatarUrl(guser.getAvatar_url());
        return user;
    }

}
