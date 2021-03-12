package com.ho.blogt.service;

import com.ho.blogt.entity.Article;
import com.ho.blogt.utils.PageRequest;
import com.ho.blogt.utils.PageResult;

import java.util.Date;

public interface ArticleService {
    /**
     * 获取首页文章推荐
     *
     * @return List<Article>
     */
    PageResult getArticleListForIndex();

    /**
     * 通过筛选条件获取文章列表
     *
     * @param userId    那个用户的文章
     * @param tagId     这个是那个标签的文章
     * @param startTime 筛选条件，开始时间  开始时间和结束时间必须同时传，不然不生效
     * @param endTime   筛选条件，结束时间
     * @return List<Article>
     */
    PageResult getArticleList(long userId, long tagId, Date startTime, Date endTime, PageRequest pageRequest);

    /**
     * 获取文章详细信息
     *
     * @param articleId 文章ID
     * @param userId    用户ID 用于判断用户是否有点赞该文章
     * @return Article
     */
    Article getArticleDetail(long articleId, long userId, String deviceId, String ip);

    /**
     * 发布文章
     *
     * @param userId     用户ID
     * @param title      标题
     * @param tagsId     文章所拥有的标签
     * @param context    内容，富文本
     * @param isShow     是否让大众可看 1显示  2不显示
     * @param canComment 是否能评论 1可以评论  2不可以评论
     * @return
     */
    long insertArticle(long userId, String title, long[] tagsId, String context, int isShow, int canComment);

    /**
     * 评论文章
     *
     * @param articleId 文章ID
     * @param userId    用户ID
     * @param parentId  父节点ID
     * @param context   内容
     * @return
     */
    long commentArticle(long articleId, long userId, long parentId, String context);
}
