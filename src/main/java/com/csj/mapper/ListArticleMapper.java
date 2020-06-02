package com.csj.mapper;

import com.csj.domain.dto.ListArticle;

import java.util.List;

public interface ListArticleMapper {
    /**
     * 查询所有文章列表，一对一关联用户
     * @return
     */
    List<ListArticle> findAll();

    /**
     * 根据标题查询文章
     * @return
     */
    List<ListArticle> findSearchArticle(String title);

}
