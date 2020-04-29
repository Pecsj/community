package com.csj;

import com.csj.domain.User;
import com.csj.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    public UserMapper userMapper;

    @Test
    void contextLoads() {
        User user = userMapper.findByName("bbb");
        if (user!=null){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
    }

}
