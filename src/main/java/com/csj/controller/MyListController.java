package com.csj.controller;

import com.csj.domain.Article;
import com.csj.domain.User;
import com.csj.domain.dto.ListArticle;
import com.csj.domain.dto.MyComment;
import com.csj.service.IArticleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 跳转到我的回复界面
     * @return
     */
    @RequestMapping("/myresponse")
    public ModelAndView getMyResponsePage(Integer pageNumber,HttpServletRequest request){
        ModelAndView mv = new ModelAndView();
        int userId = ((User)request.getSession().getAttribute("user")).getId();
        if(pageNumber==null){
            pageNumber=1;
        }
        //获取我的回复列表
        PageInfo<MyComment> myComment = articleService.getMyComment(pageNumber, pageCount,userId);
        mv.addObject("myComment",myComment);
        mv.setViewName("myresponse");
        return mv;
    }

    /**
     * 跳转到我的消息界面
     * @return
     */
    @RequestMapping("/mymessage")
    public String getMyMsgPage(){
        return "mymessage";
    }

}
