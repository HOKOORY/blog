package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author 63408
 */
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {
    /**
     * 通过筛选条件获取文章列表
     *
     * @param userId    那个用户的文章
     * @param tagId     这个是那个标签的文章
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return List<Article>
     */
    List<Article> getArticleList(@Param("userId") long userId, @Param("tagId") long tagId,
                                 @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 获取文章详细信息
     *
     * @param articleId 文章ID
     * @return Article
     */
    Article getArticleDetail(@Param("articleId") long articleId);

    /**
     * 增加一个赞
     *
     * @param articleId 文章ID
     * @return int
     */
    int addLike(@Param("articleId") long articleId);

    /**
     * 增加一个阅读量
     *
     * @param articleId 文章ID
     * @return int
     */
    int addRead(@Param("articleId") long articleId);

    long insertArticle(@Param("article") Article article);
}
