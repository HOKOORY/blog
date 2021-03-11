package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.ArticleVisitor;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArticleVisitorMapper extends BaseMapper<ArticleVisitor> {
    /**
     * 插入一条浏览数据
     *
     * @param articleVisitor visitor对象
     * @return 插入ID
     */
    int insertArticleVisitor(@Param("articleVisitor") ArticleVisitor articleVisitor);

    /**
     * 获取浏览数据ID
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @param offset    从那条数据开始  分页需要
     * @param rows      获取多少个     分页需要
     * @return
     */
    List<ArticleVisitor> getArticleVisitorList(@Param("articleId") long articleId, @Param("userId") long userId,
                                               @Param("startTime") Date startTime, @Param("endTime") Date endTime,
                                               @Param("ip") String ip, @Param("device_id") String device_id,
                                               @Param("offset") int offset, @Param("rows") int rows);
}
