package com.csj.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@Component
public class ExceptionResolver {//implements HandlerExceptionResolver {

    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        ModelAndView mv = new ModelAndView();
        String msg = "系统错误，请联系管理员";
        if (e instanceof ErrorException){
            msg = ((ErrorException) e).getMsg();
        }
        mv.addObject("msg",msg);
        mv.setViewName("error");
        return mv;
    }
}
