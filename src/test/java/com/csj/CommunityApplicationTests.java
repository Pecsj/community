package com.csj;

import com.csj.domain.User;
import com.csj.domain.dto.ListArticle;
import com.csj.mapper.ListArticleMapper;
import com.csj.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CommunityApplicationTests {

    @Autowired
    private ListArticleMapper listMapper;

    @Test
    void contextLoads() {
        List<ListArticle> articles = listMapper.findAll();
        for (ListArticle article : articles) {
            System.out.println(article);
        }
    }

}
