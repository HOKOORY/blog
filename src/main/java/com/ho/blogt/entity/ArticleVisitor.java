package com.ho.blogt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 63408
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ArticleVisitor implements Serializable {
    private static final long serialVersionUID = -1318284598220470294L;
    private long id;
    private long userId;
    private long articleId;
    private String ip;
    private String deviceId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
