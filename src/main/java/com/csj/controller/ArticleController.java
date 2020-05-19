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

import javax.servlet.http.HttpServletRequest;

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
    public ModelAndView lookArticle(int aid,
                                    HttpServletRequest request){
        Article article = articleService.getArticle(aid);
        User user = userService.findById(article.getCreator());
        ModelAndView mv = new ModelAndView();
        boolean islike = articleService.isLike(user.getId(),article.getId());//用户是否喜欢该文章
        articleService.addLook(aid);//阅读数+1
        //该用户是否可编辑
        User loginUser = (User) request.getSession().getAttribute("user");
        mv.addObject("isEditor",false);
        if (loginUser.getId()==user.getId()){
            mv.addObject("isEditor",true);
        }
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
            articleService.addLike(aid);
            return "true";
        }
    }

    @RequestMapping("/editArticle")
    public ModelAndView doEdit(Integer aid){
        Article article = articleService.getArticle(aid);
        ModelAndView mv =new ModelAndView();
        mv.addObject("article",article);
        mv.setViewName("editArticle");
        return mv;
    }

}
