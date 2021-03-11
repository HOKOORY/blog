package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.ArticleLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @auther: ho
 * @Date: 2021/3/7 - 03 - 07 - 13:53
 * @Description: com.ho.blogt.mapper
 * @Version: 1.0
 */
@Mapper
public interface ArticleLikeMapper extends BaseMapper<ArticleLike> {
    /**
     * 获取某个文章的点赞用户列表
     *
     * @param articleId 文章ID
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return 返回一个列表
     */
    List<ArticleLike> getArticleLikeList(@Param("articleId") long articleId,
                                         @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 获取某个用户的点赞文章列表
     *
     * @param userId    用户ID
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return 返回一个列表
     */
    List<ArticleLike> getUserLikeArticleList(@Param("userId") long userId,
                                             @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 获取用户有没有点这个文章赞
     *
     * @param userId    用户ID
     * @param articleId 文章ID
     * @return ArticleLike对象
     */
    ArticleLike getArticleLikeByUser(@Param("userId") long userId, @Param("articleId") long articleId);

    /**
     * 插入一条用户点赞文章的数据
     * @param articleLike 包装成javabean 传入
     * @return 数据ID
     */
    int insertArticleLike(@Param("articleLike") ArticleLike articleLike);
}
