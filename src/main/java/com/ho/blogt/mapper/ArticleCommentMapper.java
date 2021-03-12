package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.ArticleComment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleCommentMapper extends BaseMapper<ArticleComment> {
    long insertArticleComment(@Param("articleComment") ArticleComment articleComment);
}
