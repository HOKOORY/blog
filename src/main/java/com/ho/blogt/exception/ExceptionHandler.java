package com.ho.blogt.exception;

import com.ho.blogt.entity.Response;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
@Slf4j
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Response handleException(HttpServletRequest request, Exception ex) {
        Response response;
        log.error("exception error:{}", ex);
        response = new Response(ErrorCodeAndMsg.NETWORK_ERROR.getCode(),
                ErrorCodeAndMsg.NETWORK_ERROR.getMsg());
        return response;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(HttpException.class)
    @ResponseBody
    public Response handleHttpException(HttpServletRequest request, HttpException ex) {
        Response response;
        log.error("HttpException code:{},msg:{}", ex.getResponse().getCode(), ex.getResponse().getMsg());
        response = new Response(ex.getResponse().getCode(), ex.getResponse().getMsg());
        return response;
    }
}
