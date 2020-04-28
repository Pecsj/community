package com.csj.service.impl;

import com.csj.domain.GithubUser;
import com.csj.domain.User;
import com.csj.mapper.UserMapper;
import com.csj.service.IUserService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserService implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 查询所有用户
     * @return
     */
    public List<User> findAll(){
        List<User> users = userMapper.findAll();
        return users;
    }

    /**
     * 添加github用户
     */
    public void insertGithubUser(GithubUser guser){
        User user = new User();
        user.setName(guser.getLogin());//使用GitHub账户名当作用户名
        user.setPassword(guser.getId());//使用GitHub的id当作用户密码
        userMapper.insert(user);
    }

}
