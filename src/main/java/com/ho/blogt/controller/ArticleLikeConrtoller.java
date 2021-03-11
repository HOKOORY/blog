package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import com.ho.blogt.entity.User;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.service.ArticleLikeService;
import com.ho.blogt.service.TokenService;
import com.ho.blogt.utils.Constant;
import com.ho.blogt.utils.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("articleLike")
public class ArticleLikeConrtoller {
    @Autowired
    ArticleLikeService articleLikeService;
    @Autowired
    TokenService tokenService;

    /**
     * 我的文章的点赞文章列表
     *
     * @param token       token header传
     * @param startTime   筛选条件
     * @param endTime     筛选条件
     * @param pageRequest 分页 page/limit
     * @return
     */
    @GetMapping("myArticleLike")
    public Response getMyArticleLike(@RequestHeader(name = "token", required = false, defaultValue = Constant.DEFAULT_VALUE) String token,
                                     @RequestParam(value = "startTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String startTime,
                                     @RequestParam(value = "endTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String endTime,
                                     PageRequest pageRequest) {
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            // 抛出未登录异常
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        Date startDateTime = null;
        Date endDateTime = null;
        if (!Constant.DEFAULT_VALUE.equals(startTime) && !Constant.DEFAULT_VALUE.equals(endTime)) {
            try {
                startDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(startTime);
                endDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTime);
            } catch (ParseException e) {
                // 抛出时间格式不对的异常
                throw new HttpException(ErrorCodeAndMsg.DATEFORMAT_ERROR);
            }
        }
        return new Response(articleLikeService.getUserLikeArticleList(user.getId(), startDateTime, endDateTime, pageRequest));
    }

    /**
     * 用于判断是否点赞该文章 1是点赞了，0是没点赞
     *
     * @param token     token
     * @param articleId 文章ID
     * @return
     */
    @GetMapping("isLikeArticle")
    public Response isLikeArticle(@RequestHeader(name = "token", required = false, defaultValue = Constant.DEFAULT_VALUE) String token,
                                  @RequestParam(value = "articleId", required = true) long articleId) {
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            // 抛出未登录异常
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return new Response(articleLikeService.isLikeArticle(user.getId(), articleId) ? 1 : 0);
    }

    /**
     * 点赞文章
     *
     * @param token     token
     * @param articleId 文章ID
     * @return
     */
    @PostMapping("likeArticle")
    public Response LikeArticle(@RequestHeader(name = "token", required = false, defaultValue = Constant.DEFAULT_VALUE) String token,
                                @RequestParam(value = "articleId", required = true) long articleId) {
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            // 抛出未登录异常
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return new Response(articleLikeService.insertArticleLike(user.getId(), articleId));
    }
}
