package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.User;
import com.csj.domain.dto.ListArticle;
import com.csj.service.IArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MyListController {

    @Autowired
    private IArticleService articleService;
    //默认查询个数
    @Value("${page.pageCount}")
    private Integer pageCount;

    @RequestMapping("/myarticle")
    public String getMyArticlePage(HttpServletRequest request,
                                   Integer pageNumber){
        User user = (User)request.getSession().getAttribute("user");
        //获取文章列表
        if(pageNumber==null){
            pageNumber=1;
        }
        PageInfo<Article> pageInfo = articleService.getMyArticlePage(user.getId(),pageNumber, pageCount);
        request.setAttribute("pageInfo",pageInfo);
        return "myarticle";
    }

    @RequestMapping("/mymessage")
    public String getMyMsgPage(){
        return "mymessage";
    }

    @RequestMapping("/myresponse")
    public String getMyResponsePage(){
        return "myresponse";
    }

}
