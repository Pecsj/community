package com.csj.service;

import com.csj.domain.Article;

public interface IArticleService {
    /**
     * 发布新文章
     * @param article
     */
    void insert(Article article);

}
