package com.csj.mapper;

import com.csj.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface UserMapper {

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll();
}
