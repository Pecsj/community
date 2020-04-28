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

}
