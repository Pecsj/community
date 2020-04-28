package com.csj.domain;

import java.util.UUID;

/**
 * 封装由github返回的user对象
 */
public class GithubUser {
    private String id;
    private String login;
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setToken() {
        this.token = UUID.randomUUID().toString();
    }

    public String getToken() {
        return token;
    }
}
