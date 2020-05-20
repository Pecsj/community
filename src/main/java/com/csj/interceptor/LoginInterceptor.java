package com.csj.interceptor;

import com.csj.domain.User;
import com.csj.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private IUserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object sessionUser = request.getSession().getAttribute("user");
        if(sessionUser==null){
            //去cookie中查找用户是否持久化登录
            Cookie[] cookies = request.getCookies();
            if(cookies!=null){
                for (Cookie cookie : cookies) {
                    if("token".equals(cookie.getName())){
                        //根据cookie中的token授权码获取用户
                        User user = null;
                        user = userService.findByToken(cookie.getValue());
                        request.getSession().setAttribute("user",user);
                        return true;
                    }
                }
            }
        }
        response.sendRedirect(request.getContextPath()+"/login");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
