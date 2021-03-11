package com.ho.blogt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author 63408
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Article implements Serializable {
    private static final long serialVersionUID = 8027771578122401824L;
    private long id;
    private long userId;
    private String title;
    private int isShow;
    private int canComment;
    private long likeSum;
    private long readSum;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private List<ArticleTag> tags;
    private int isLike;
    private ArticleContext context;
}
