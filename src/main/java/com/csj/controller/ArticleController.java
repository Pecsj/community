package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.User;
import com.csj.service.IArticleService;
import com.csj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 文章详情控制器
 */
@Controller
public class ArticleController {

    @Autowired
    private IArticleService articleService;
    @Autowired
    private IUserService userService;

    @RequestMapping("/lookArticle")
    @ResponseBody
    public ModelAndView lookArticle(int aid){
        Article article = articleService.getArticle(aid);
        User user = userService.findById(article.getCreator());
        ModelAndView mv = new ModelAndView();
        boolean islike = articleService.isLike(user.getId(),article.getId());//用户是否喜欢该文章
        System.err.println("用户是否喜欢"+islike);
        mv.addObject("isLike",islike);
        mv.addObject("user",user);
        mv.addObject("article",article);
        mv.setViewName("lookArticle");
        return mv;
    }

    @RequestMapping("/doLike")
    @ResponseBody
    public String doLike(int uid,int aid){
        Integer rows = userService.likeArticle(uid,aid);
        if (rows==null||rows<=0){
            return "false";
        }else {
            return "true";
        }
    }

}
