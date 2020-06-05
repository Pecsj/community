package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.Notice;
import com.csj.domain.User;
import com.csj.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@SessionAttributes("error")
public class PublishController {
    @Autowired
    private IArticleService service;

    /**
     * 跳转到发布页面
     * @return
     */
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    /**
     * 发布新帖子
     * @param article
     * @param request
     * @return
     */
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

    /**
     * 更新保存帖子
     * @param article
     * @return
     */
    @RequestMapping("/updateArticle")
    public String updateArticle(Article article){
        article.setGmtModified(System.currentTimeMillis());
        service.updateArticle(article);
        return "redirect:lookArticle?aid="+article.getId();
    }

    /**
     * 发布通知公告
     * @return
     */
    @RequestMapping("/publishNotice")
    @ResponseBody
    public Object publishNotice(Notice notice){
        //存入数据库,
        service.addNotice(notice);
        return notice;
    }

    /**
     * 更新通知公告
     * @return
     */
    @RequestMapping("/updateNotice")
    @ResponseBody
    public Object updateNotice(){
        Notice notice = service.getLastNotice();
        return notice;
    }

}
