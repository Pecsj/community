package com.csj.service;

import com.csj.domain.dto.GithubUser;
import com.csj.domain.User;
import com.csj.domain.dto.UpdateXxx;

import java.util.List;

public interface IUserService {
    List<User> findAll();
    void insertGithubUser(GithubUser guser);
    void insertUser(User user);
    User findByToken(String token);
    boolean isExistByName(String name);
    User findByName(String name);
    User findById(Integer id);
    User loginUser(String name, String password);
    boolean isExistFace(int id);
    void updateAge(Integer id,Integer age);
    void updateXxx(UpdateXxx updateXxx);
}
