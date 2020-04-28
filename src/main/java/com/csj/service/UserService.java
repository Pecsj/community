package com.csj.service;

import com.csj.domain.User;
import com.csj.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public List<User> findAll(){
        List<User> users = userMapper.findAll();
        return users;
    }

}
