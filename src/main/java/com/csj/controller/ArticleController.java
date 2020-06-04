package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.CommentLike;
import com.csj.domain.User;
import com.csj.domain.dto.ArticleComment;
import com.csj.domain.dto.ListArticle;
import com.csj.service.IArticleService;
import com.csj.service.IUserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${page.pageCount}")
    private Integer pageCount;

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

        //查询评论详情
        PageInfo<ArticleComment> commentList = articleService.getCommentList(aid,loginUser.getId());

        mv.addObject("commentList",commentList);
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

    @RequestMapping("/saveComment")
    public String saveComment(String aid,String contain,HttpServletRequest request){
        //添加评论
        ArticleComment comment = new ArticleComment();
        comment.setAid(Integer.parseInt(aid));
        User user = (User) request.getSession().getAttribute("user");
        comment.setUid(user.getId());
        comment.setContain(contain);
        articleService.saveComment(comment);
        //文章评论数+1
        articleService.addCommentCount(comment.getAid());
        return "redirect:lookArticle?aid="+aid;
    }

    /**
     * 添加评论喜欢
     * @param commentLike
     * @param aid
     * @return
     */
    @RequestMapping("/doCommentLike")
    @ResponseBody
    public String doCommentLike(CommentLike commentLike,Integer aid){
        //喜欢评论
        Integer rows = articleService.likeComment(commentLike);
        if (rows==null||rows<=0){
            return "false";
        }else {
            //评论喜欢数+1
            articleService.addCommentLike(commentLike.getCid());
            return "true";
        }
    }

    /**
     * 跳转到主页
     * @return
     */
    @RequestMapping("/searchArticle")
    public ModelAndView searchArticle(String title,Integer pageNumber){
        ModelAndView mv = new ModelAndView();
        //获取文章列表
        if(pageNumber==null){
            pageNumber=1;
        }
        PageInfo<ListArticle> pageInfo = articleService.findSearchArticle(pageNumber, pageCount,title);
        mv.addObject("search","yes");
        mv.addObject("title",title);
        mv.addObject("pageInfo",pageInfo);
        mv.setViewName("index");
        return mv;
    }

    /**
     * 删除文章
     * @return
     */
    @RequestMapping("/deleteArticle")
    public String deleteArticle(String aid){
        boolean flag = articleService.deleteArticle(aid);
        if(flag){
            return "redirect:index";
        }else{
            return "redirect:index?error=删除失败";
        }
    }



}
