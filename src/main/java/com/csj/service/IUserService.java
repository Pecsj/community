package com.csj.service;

import com.csj.domain.GithubUser;
import com.csj.domain.User;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    void insertGithubUser(GithubUser guser);
}
