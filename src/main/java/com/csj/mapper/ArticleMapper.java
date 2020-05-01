package com.csj.mapper;

import com.csj.domain.Article;

import java.util.List;

public interface ArticleMapper {

    /**
     * 查询所有文章
     * @return
     */
    public List<Article> findAll();

    /**
     * 查询文章数目
     * @return
     */
    public int findCount();

    /**
     * 根据用户名查找文章
     * @return
     */
    public List<Article> findByUsername(String username);

    /**
     * 插入文章
     */
    public void insertArticle(Article article);


}