package com.ho.blogt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ho.blogt.entity.*;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.mapper.*;
import com.ho.blogt.service.ArticleService;
import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;
import com.ho.blogt.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    ArticleLikeMapper articleLikeMapper;
    @Autowired
    ArticleVisitorMapper articleVisitorMapper;
    @Autowired
    ArticleContextMapper articleContextMapper;
    @Autowired
    ArticleTagMapper articleTagMapper;

    @Override
    public PageResult getArticleListForIndex() {
        PageHelper.startPage(1, 10);
        List<Article> list = articleMapper.getArticleList(0, 0, null, null);
        return PageUtils.getPageResult(new PageInfo<Article>(list));
    }

    @Override
    public PageResult getArticleList(long userId, long tagId, Date startTime, Date endTime, PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int limit = pageRequest.getLimit();
        PageHelper.startPage(page, limit);
        List<Article> list = articleMapper.getArticleList(userId, tagId, startTime, endTime);
        return PageUtils.getPageResult(new PageInfo<Article>(list));
    }

    @Override
    public Article getArticleDetail(long articleId, long userId, String deviceId, String ip) {
        // 计算出当前时间前24小时的dateTime
        Date startDateTime;
        Date endDateTime = new Date();
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(endDateTime);
        rightNow.add(Calendar.HOUR_OF_DAY, -24);
        startDateTime = rightNow.getTime();
        List<ArticleVisitor> articleVisitorList =
                articleVisitorMapper.getArticleVisitorList(articleId, userId, startDateTime, endDateTime, ip, deviceId, 0, 1);
        if (articleVisitorList.size() == 0) {
            // 近24小时内未进行观看，则增加观看次数
            articleMapper.addRead(articleId);
        }
        ArticleVisitor articleVisitor = new ArticleVisitor();
        articleVisitor.setArticleId(articleId);
        articleVisitor.setCreateTime(new Date());
        articleVisitor.setUserId(userId);
        articleVisitor.setDeviceId(deviceId);
        articleVisitor.setIp(ip);
        articleVisitorMapper.insertArticleVisitor(articleVisitor);
        Article res = articleMapper.getArticleDetail(articleId);
        if (0 == userId) {
            res.setIsLike(0);
            return res;
        }
        ArticleLike like = articleLikeMapper.getArticleLikeByUser(userId, articleId);
        if (null == like) {
            res.setIsLike(0);
        } else {
            res.setIsLike(1);
        }
        return res;
    }

    @Override
    @Transactional(rollbackFor = {RuntimeException.class})
    public long insertArticle(long userId, String title, long[] tagsId, String context, int isShow,int canComment) {
        Article article = new Article();
        ArticleContext articleContext = new ArticleContext();
        ArticleTag articleTag = new ArticleTag();
        Date date = new Date();
        article.setUserId(userId);
        article.setIsShow(isShow);
        article.setCanComment(canComment);
        article.setTitle(title);
        article.setCreateTime(date);
        article.setUpdateTime(date);
        long articleId = articleMapper.insertArticle(article);
        if (articleId <= 0) {
            throw new HttpException(ErrorCodeAndMsg.DATABASE_ERROR);
        }
        articleContext.setArticleId(articleId);
        articleContext.setContext(context);
        articleContext.setCreateTime(date);
        articleContext.setUpdateTime(date);
        long articleContextId = articleContextMapper.insertContext(articleContext);
        if (articleContextId <= 0) {
            throw new HttpException(ErrorCodeAndMsg.DATABASE_ERROR);
        }
        for (long str : tagsId) {
            articleTag.setCreateTime(date);
            articleTag.setUpdateTime(date);
            articleTag.setArticleId(articleId);
            articleTag.setTagId(str);
            if (articleTagMapper.insertArticleTag(articleTag) <= 0) {
                throw new HttpException(ErrorCodeAndMsg.DATABASE_ERROR);
            }
        }
        return 0;
    }


}
