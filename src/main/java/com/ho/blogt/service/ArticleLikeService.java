package com.ho.blogt.service;

import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;

import java.util.Date;

/**
 * @Auther: ho
 * @Date: 2021/3/7 - 03 - 07 - 13:55
 * @Description: com.ho.blogt.service
 * @Version: 1.0
 */
public interface ArticleLikeService {
    /**
     * 获取某个文章的点赞用户列表
     *
     * @param articleId 文章ID
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return 返回一个列表
     */
    PageResult getArticleLikeList(long articleId, Date startTime, Date endTime, PageRequest pageRequest);

    /**
     * 获取某个用户的点赞文章列表
     *
     * @param userId    用户ID
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return 返回一个列表
     */
    PageResult getUserLikeArticleList(long userId, Date startTime, Date endTime, PageRequest pageRequest);

    /**
     * 获取用户有没有点这个文章赞
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     * @return ArticleLike对象
     */
    boolean isLikeArticle(long userId, long articleId);

    /**
     * 插入一条用户点赞文章的数据
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     * @return 数据ID
     */
    int insertArticleLike(long userId, long articleId);
}
