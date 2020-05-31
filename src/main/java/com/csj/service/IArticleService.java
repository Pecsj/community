package com.csj.service;

import com.csj.domain.Article;
import com.csj.domain.CommentLike;
import com.csj.domain.dto.ListArticle;
import com.csj.domain.dto.MyComment;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface IArticleService {
    /**
     * 发布新文章
     * @param article
     */
    void insert(Article article);

    PageInfo<ListArticle> findListArticle(int pageNumber,int count);

    PageInfo<Article> getMyArticlePage(int id,int pageNumber,int count);

    Article getArticle(int aid);

    boolean isLike(Integer uid, Integer aid);

    void addLike(int aid);

    void addLook(int aid);

    void updateArticle(Article article);

    List<Article> getHotList();

    PageInfo<MyComment> getCommentList(int aid,int uid);

    void saveComment(MyComment comment);

    Integer likeComment(CommentLike commentLike);

    void addCommentLike(Integer cid);

    void addCommentCount(Integer aid);
}
