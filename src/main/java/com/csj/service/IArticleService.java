package com.csj.service;

import com.csj.domain.Article;
import com.csj.domain.dto.ListArticle;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IArticleService {
    /**
     * 发布新文章
     * @param article
     */
    void insert(Article article);

    PageInfo<ListArticle> findListArticle(int pageNumber,int count);

}
