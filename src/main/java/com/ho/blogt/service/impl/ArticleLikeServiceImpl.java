package com.ho.blogt.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.ho.blogt.entity.ArticleLike;
import com.ho.blogt.enums.ErrorCodeAndMsg;
import com.ho.blogt.exception.HttpException;
import com.ho.blogt.mapper.ArticleLikeMapper;
import com.ho.blogt.mapper.ArticleMapper;
import com.ho.blogt.service.ArticleLikeService;
import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;
import com.ho.blogt.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Auther: ho
 * @Date: 2021/3/7 - 03 - 07 - 13:56
 * @Description: com.ho.blogt.service.impl
 * @Version: 1.0
 */
@Service
public class ArticleLikeServiceImpl implements ArticleLikeService {
    @Autowired
    ArticleLikeMapper articleLikeMapper;
    @Autowired
    ArticleMapper articleMapper;

    @Override
    public PageResult getArticleLikeList(long articleId, Date startTime, Date endTime, PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int limit = pageRequest.getLimit();
        PageHelper.startPage(page, limit);
        List<ArticleLike> list = articleLikeMapper.getArticleLikeList(articleId, startTime, endTime);
        return PageUtils.getPageResult(new PageInfo<ArticleLike>(list));
    }

    @Override
    public PageResult getUserLikeArticleList(long userId, Date startTime, Date endTime, PageRequest pageRequest) {
        int page = pageRequest.getPage();
        int limit = pageRequest.getLimit();
        PageHelper.startPage(page, limit);
        List<ArticleLike> list = articleLikeMapper.getUserLikeArticleList(userId, startTime, endTime);
        return PageUtils.getPageResult(new PageInfo<ArticleLike>(list));
    }

    @Override
    public boolean isLikeArticle(long userId, long articleId) {
        ArticleLike articleLike = articleLikeMapper.getArticleLikeByUser(userId, articleId);
        if (null == articleLike) {
            return false;
        }
        return true;
    }

    @Override
    public int insertArticleLike(long userId, long articleId) {
        if (this.isLikeArticle(userId, articleId)) {
            throw new HttpException(ErrorCodeAndMsg.USER_LIKED);
        }
        int res = articleMapper.addLike(articleId);
        if (0 == res) {
            throw new HttpException(ErrorCodeAndMsg.DATABASE_ERROR);
        }
        ArticleLike articleLike = new ArticleLike();
        articleLike.setUserId(userId);
        articleLike.setArticleId(articleId);
        articleLike.setCreateTime(new Date());
        articleLike.setUpdateTime(new Date());
        return articleLikeMapper.insertArticleLike(articleLike);
    }

}
