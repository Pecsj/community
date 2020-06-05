package com.csj.mapper;

import com.csj.domain.Article;
import com.csj.domain.CommentLike;
import com.csj.domain.Notice;
import com.csj.domain.dto.ArticleComment;
import com.csj.domain.dto.MyComment;
import org.springframework.transaction.annotation.Transactional;

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
     * 根据用户id查找文章
     * @return
     */
    public List<Article> findById(Integer id);

    /**
     * 插入文章
     */
    public void insertArticle(Article article);


    /**
     * 根据文章id查询
     * @return
     */
    Article findByAid(Integer id);

    /**
     * 查询用户是否喜欢此文章
     * @param uid
     * @param aid
     * @return
     */
    Integer isLike(Integer uid, Integer aid);

    /**
     * 文章喜欢数+1
     * @param aid
     */
    void updateLike(int aid);

    /**
     * 文章阅读数+1
     * @param aid
     */
    void updateLookCount(int aid);

    /**
     * 更新文章内容
     * @param article
     */
    void updateArticle(Article article);

    /**
     * 获取热门文章
     * @return
     */
    List<Article> findHotList();

    /**
     * 获取评论信息
     * @param aid
     * @return
     */
    List<ArticleComment> findCommentById(int aid);

    /**
     * 获取当前用户喜欢的评论列表
     * @param id
     * @return
     */
    List<CommentLike> findCommentIsLike(Integer id);

    /**
     * 添加评论
     * @param comment
     */
    void insertComment(ArticleComment comment);

    /**
     * 添加喜欢评论
     * @param commentLike
     * @return
     */
    Integer insertCommentLike(CommentLike commentLike);

    /**
     * 评论喜欢数+1
     * @param cid
     */
    void updateCommentLike(Integer cid);

    /**
     * 文章评论数+1
     * @param aid
     */
    void updateCommentCount(Integer aid);

    /**
     * 根据文章名称查询
     * @param title
     * @return
     */
    List<Article> findByTitle(String title);

    /**
     * 删除文章
     * @param aid
     */
    @Transactional
    void deleteArticle(String aid);

    /**
     * 获取某文章下的评论
     * @return
     * @param aid
     */
    @Transactional
    List<Integer> getCid(String aid);

    /**
     * 删除评论
     * @param aid
     */
    @Transactional
    void deleteComment(String aid);

    /**
     * 删除文章喜欢数
     * @param aid
     */
    @Transactional
    void deleteLikeArticle(String aid);

    /**
     * 删除文章评论喜欢数
     * @param cids
     */
    @Transactional
    void deleteLikeComment(List<Integer> cids);

    /**
     * 查询我的回复列表
     * @param userId
     * @return
     */
    List<MyComment> findMyComment(int userId);

    /**
     * 添加通知公告
     * @param notice
     * @return
     */
    Integer insertNotice(Notice notice);

    /**
     * 查询最新通知公告
     * @return
     */
    Notice findLastNotice();
}
