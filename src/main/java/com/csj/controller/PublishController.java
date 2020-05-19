package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.User;
import com.csj.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
@SessionAttributes("error")
public class PublishController {
    @Autowired
    private IArticleService service;

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String dopublish(Article article, HttpServletRequest request){
        //判断用户是否登录
        Object user = request.getSession().getAttribute("user");
        if (user==null){
            request.setAttribute("error","用户未登录");
            return "publish";
        }
        System.out.println(user);
        Integer id = ((User) user).getId();
        article.setCreator(id);
        //将文章存入数据库
        service.insert(article);
        return "redirect:myarticle";
    }

    @RequestMapping("/updateArticle")
    public String updateArticle(Article article){
        article.setGmtModified(System.currentTimeMillis());
        service.updateArticle(article);
        return "redirect:lookArticle?aid="+article.getId();
    }

}
