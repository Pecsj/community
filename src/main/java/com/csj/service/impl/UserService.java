package com.csj.service.impl;

import com.csj.domain.dto.GithubUser;
import com.csj.domain.User;
import com.csj.mapper.UserMapper;
import com.csj.service.IUserService;
import com.csj.util.Convert;
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
        User user = Convert.gitToUser(guser);
        userMapper.insert(user);
    }

    /**
     * 添加用户
     * @param user
     */
    public void insertUser(User user){
        userMapper.insert(user);
    }

    /**
     * 根据token查询用户
     * @param token
     * @return
     */
    @Override
    public User findByToken(String token) {
        User user = userMapper.findByToken(token);
        return user;
    }

    /**
     * 查看是否有重名
     * @return
     */
    public boolean isExistByName(String name){
        User user = userMapper.findByName(name);
        if (user!=null){
            return true;
        }
        return false;
    }

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    @Override
    public User findByName(String name) {
        User user = userMapper.findByName(name);
        return user;
    }

    @Override
    public User findById(Integer id) {
        User user = userMapper.findById(id);
        return user;
    }

}
