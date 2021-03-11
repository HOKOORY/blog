package com.ho.blogt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ho.blogt.entity.ArticleContext;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleContextMapper extends BaseMapper<ArticleContext> {
    long insertContext(@Param("articleContext") ArticleContext articleContext);
}
