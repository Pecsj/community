package com.csj.service.impl;

import com.csj.domain.Article;
import com.csj.domain.dto.ListArticle;
import com.csj.mapper.ArticleMapper;
import com.csj.mapper.ListArticleMapper;
import com.csj.service.IArticleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            if (listArticle.getDescription().length()>10){
                listArticle.setDescription(listArticle.getDescription().substring(0,9)+"......");//截取文章内容前十个字
            }
        }
        PageInfo<ListArticle> pageInfo = new PageInfo<>(listArticles);
        return pageInfo;
    }
}
