package com.csj.service.impl;

import com.csj.domain.Article;
import com.csj.mapper.ArticleMapper;
import com.csj.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleMapper mapper;

    @Override
    public void insert(Article article) {
        //时间戳 创建时间
        long gmt = System.currentTimeMillis();
        article.setGmtCreat(gmt);

        //用户
        mapper.insertArticle(article);

    }
}
