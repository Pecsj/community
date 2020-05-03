package com.csj.service;

import com.csj.domain.dto.GithubUser;
import com.csj.domain.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    void insertGithubUser(GithubUser guser);
    User findByToken(String token);
    boolean isExistByName(String name);
    User findByName(String name);
}
