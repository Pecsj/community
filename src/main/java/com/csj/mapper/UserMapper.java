package com.csj.mapper;

import com.csj.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();

    /**
     * 添加用户
     */
    void insert(User user);

    /**
     * 根据token查询用户
     * @param token
     * @return
     */
    User findByToken(String token);

    /**
     * 根据用户名查找用户
     * @param name
     * @return
     */
    User findByName(String name);

    /**
     * 根据用户id查询
     * @param id
     * @return
     */
    User findById(Integer id);
}
