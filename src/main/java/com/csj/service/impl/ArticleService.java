package com.csj.service.impl;

import com.csj.domain.Article;
import com.csj.domain.CommentLike;
import com.csj.domain.dto.ArticleComment;
import com.csj.domain.dto.ListArticle;
import com.csj.domain.dto.MyComment;
import com.csj.mapper.ArticleMapper;
import com.csj.mapper.ListArticleMapper;
import com.csj.service.IArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ArticleService implements IArticleService {
    @Autowired
    private ArticleMapper mapper;
    @Autowired
    private ListArticleMapper listMapper;

    @Override
    public void insert(Article article) {
        //时间戳 创建时间
        long gmt = System.currentTimeMillis();
        article.setGmtCreat(gmt);

        //用户
        mapper.insertArticle(article);

    }

    /**
     * 查询所有文章，一对一关联用户(使用pageHelper分页)
     * @return
     */
    @Override
    public PageInfo<ListArticle> findListArticle(int pageNumber,int count) {
        PageHelper.startPage(pageNumber,count);
        List<ListArticle> listArticles = listMapper.findAll();
        for (ListArticle listArticle : listArticles) {
            if (listArticle.getDescription().length()>30){
                listArticle.setDescription(listArticle.getDescription().substring(0,29)+"......");//截取文章内容前十个字
            }
        }
        PageInfo<ListArticle> pageInfo = new PageInfo<>(listArticles);
        return pageInfo;
    }

    /**
     * 搜索文章，一对一关联用户(使用pageHelper分页)
     * @return
     */
    @Override
    public PageInfo<ListArticle> findSearchArticle(int pageNumber, int count, String title) {
        title = "%"+title+"%";
        PageHelper.startPage(pageNumber,count);
        List<ListArticle> listArticles = listMapper.findSearchArticle(title);
        for (ListArticle listArticle : listArticles) {
            if (listArticle.getDescription().length()>30){
                listArticle.setDescription(listArticle.getDescription().substring(0,29)+"......");//截取文章内容前十个字
            }
        }
        PageInfo<ListArticle> pageInfo = new PageInfo<>(listArticles);
        return pageInfo;
    }

    /**
     * 根据用户返回文章页面
     * @param id
     * @param pageNumber
     * @param count
     * @return
     */
    @Override
    public PageInfo<Article> getMyArticlePage(int id,int pageNumber,int count) {
        PageHelper.startPage(pageNumber,count);
        List<Article> articleList = mapper.findById(id);
        for (Article article : articleList) {
            if (article.getDescription().length()>30){
                article.setDescription(article.getDescription().substring(0,29)+"......");//截取文章内容前十个字
            }
        }
        PageInfo<Article> pageInfo = new PageInfo<>(articleList);
        return pageInfo;
    }

    /**
     * 根据文章id查找文章
     * @return
     */
    public Article getArticle(int id){
        Article article = mapper.findByAid(id);
        return article;
    }

    @Override
    public boolean isLike(Integer uid, Integer aid) {
        Integer count = mapper.isLike(uid, aid);
        return count!=null&&count>0?true:false;
    }

    @Override
    public void addLike(int aid) {
        mapper.updateLike(aid);
    }

    @Override
    public void addLook(int aid) {
        mapper.updateLookCount(aid);
    }

    /**
     * 更新文章内容
     * @param article
     */
    @Override
    public void updateArticle(Article article) {
        mapper.updateArticle(article);
    }

    /**
     * 获取热门文章信息
     * @return
     */
    @Override
    public List<Article> getHotList() {
        return mapper.findHotList();
    }

    /**
     * 查询评论列表
     * @param aid
     */
    @Override
    public PageInfo<ArticleComment> getCommentList(int aid, int uid) {
        PageHelper.startPage(1,20);
        List<ArticleComment> commentList = mapper.findCommentById(aid);
        //查询当前用户喜欢列表
        List<CommentLike> commentIsLike = mapper.findCommentIsLike(uid);
        for (CommentLike commentLike : commentIsLike) {
            int cid = commentLike.getCid();
            for (ArticleComment comment : commentList) {
                if(comment.getCid()==cid){
                    comment.setIsLike(true);
                }
            }
        }

        PageInfo<ArticleComment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }

    /**
     * 添加评论
     * @param comment
     */
    @Override
    public void saveComment(ArticleComment comment) {
        mapper.insertComment(comment);
    }

    /**
     * 喜欢评论,添加映射
     * @param commentLike
     * @return
     */
    @Override
    public Integer likeComment(CommentLike commentLike) {
        return mapper.insertCommentLike(commentLike);
    }

    /**
     * 评论喜欢数+1
     * @param cid
     */
    @Override
    public void addCommentLike(Integer cid) {
        mapper.updateCommentLike(cid);
    }

    /**
     * 文章评论数+1
     * @param aid
     */
    @Override
    public void addCommentCount(Integer aid) {
        mapper.updateCommentCount(aid);
    }

    /**
     * 搜索帖子
     * @param title
     * @return
     */
    @Override
    public List<Article> searchArticle(String title) {
        title = "%"+title+"%";
        return mapper.findByTitle(title);
    }

    /**
     * 删除帖子
     * @param aid
     * @return
     */
    @Override
    @Transactional
    public boolean deleteArticle(String aid) {
        try{
            //删除文章
            mapper.deleteArticle(aid);

            //删除喜欢文章
            //找出文章下的评论id并删除喜欢评论
            List<Integer> cidList = mapper.getCid(aid);
            mapper.deleteLikeComment(cidList);

            //删除评论
            mapper.deleteComment(aid);

            //删除喜欢文章
            mapper.deleteLikeArticle(aid);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获得我的回复分页列表
     * @param pageNumber
     * @param pageCount
     * @param userId
     * @return
     */
    @Override
    public PageInfo<MyComment> getMyComment(int pageNumber, int pageCount, int userId) {
        PageHelper.startPage(pageNumber,pageCount);
        List<MyComment> commentList = mapper.findMyComment(userId);
        //长度截取
        for (MyComment comment : commentList) {
            int length = comment.getDescription().length();
            if(length>30){
                comment.setDescription(comment.getDescription().substring(0,30)+"......");
            }
        }
        PageInfo<MyComment> pageInfo = new PageInfo<>(commentList);
        return pageInfo;
    }


}
