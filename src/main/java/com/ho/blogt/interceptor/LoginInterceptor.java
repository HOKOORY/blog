package com.ho.blogt.interceptor;


import com.ho.blogt.entity.User;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.service.impl.TokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    TokenServiceImpl tokenService;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 验证是否登录
        System.out.println("preHandle run!");
        httpServletResponse.setHeader("Content-type", "text/html;charset=UTF-8");
        String token = httpServletRequest.getHeader("token");
        if (token == null) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        User user = (User) tokenService.getToken(token);
        if (user == null) {
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        System.out.println("postHandle run!");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        System.out.println("afterCompletion run!");
    }
}