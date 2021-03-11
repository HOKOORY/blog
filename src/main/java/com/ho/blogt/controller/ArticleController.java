package com.ho.blogt.controller;

import com.ho.blogt.entity.Response;
import com.ho.blogt.entity.User;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.service.ArticleLikeService;
import com.ho.blogt.service.ArticleService;
import com.ho.blogt.service.TokenService;
import com.ho.blogt.utils.Constant;
import com.ho.blogt.utils.IpUtil;
import com.ho.blogt.utils.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @Autowired
    TokenService tokenService;
    @Autowired
    ArticleLikeService articleLikeService;

    /**
     * 获取首页的文章
     *
     * @return
     */
    @GetMapping("/articleListForIndex")
    public Response articleListForIndex() {
        return new Response(articleService.getArticleListForIndex());
    }

    /**
     * 文章列表
     *
     * @param tagId     筛选条件，通过标签筛选
     * @param startTime 筛选条件，通过时间范围筛选 startTime和endTime必须同时传
     * @param endTime   筛选条件，通过时间范围筛选
     * @return
     */
    @GetMapping("/articleList")
    public Response getArticleList(@RequestParam(value = "tagId", required = false, defaultValue = Constant.DEFAULT_VALUE) long tagId,
                                   @RequestParam(value = "startTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String startTime,
                                   @RequestParam(value = "endTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String endTime,
                                   PageRequest pageRequest) {
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
        return new Response(articleService.getArticleList(0, tagId, startDateTime, endDateTime, pageRequest));
    }

    /**
     * 获取用户文章列表
     *
     * @param tagId     筛选条件，通过标签筛选
     * @param startTime 筛选条件，通过时间范围筛选 startTime和endTime必须同时传
     * @param endTime   筛选条件，通过时间范围筛选
     * @param token     必传的头信息，通过这个参数获取用户信息，登录接口可获取该参数
     * @return
     */
    @GetMapping("/articleListByUserId")
    public Response getArticleListByUserId(@RequestParam(value = "tagId", required = false, defaultValue = Constant.DEFAULT_VALUE) long tagId,
                                           @RequestParam(value = "startTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String startTime,
                                           @RequestParam(value = "endTime", required = false, defaultValue = Constant.DEFAULT_VALUE) String endTime,
                                           PageRequest pageRequest,
                                           @RequestHeader(name = "token") String token) {
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
        return new Response(articleService.getArticleList(user.getId(), tagId, startDateTime, endDateTime, pageRequest));
    }

    /**
     * 文章点赞列表 关联用户表
     *
     * @param token
     * @param articleId
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping("/articleLikeList")
    public Response getArticleLikeList(@RequestHeader(name = "token", defaultValue = Constant.DEFAULT_VALUE) String token,
                                       @RequestParam(value = "articleId", required = true) long articleId,
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
        return new Response(articleLikeService.getArticleLikeList(articleId, startDateTime, endDateTime, pageRequest));
    }

    @GetMapping("/articleDetail")
    public Response getArticleDetail(@RequestHeader(name = "token", required = false, defaultValue = Constant.DEFAULT_VALUE) String token,
                                     @RequestParam(value = "articleId", required = true) long articleId,
                                     @RequestParam(value = "deviceId", required = true) String deviceId,
                                     HttpServletRequest request) {
        User user = (User) tokenService.getToken(token);
        long userId = 0;
        if (null != user) {
            userId = user.getId();
        }
        String ip = IpUtil.getIpAddr(request);
        return new Response(articleService.getArticleDetail(articleId, userId, deviceId, ip));
    }


    @PostMapping("/releaseArticle")
    public Response releaseArticle(@RequestHeader(name = "token", defaultValue = Constant.DEFAULT_VALUE) String token,
                                   @RequestParam(value = "title") String title, @RequestParam(value = "tagsId[]") long[] tagsId,
                                   @RequestParam(value = "context") String context,@RequestParam(value = "isShow") int isShow,
                                   @RequestParam(value = "canComment") int canComment) {
        User user = (User) tokenService.getToken(token);
        if (null == user) {
            // 抛出未登录异常
            throw new HttpException(ErrorCodeAndMsg.USER_NOT_LOGIN);
        }
        return new Response(articleService.insertArticle(user.getId(), title,tagsId,context,isShow,canComment));
    }
}
