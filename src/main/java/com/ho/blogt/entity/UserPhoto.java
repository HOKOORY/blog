package com.ho.blogt.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther: ho
 * @Date: 2021/3/7 - 03 - 07 - 12:57
 * @Description: com.ho.blogt.entity
 * @Version: 1.0
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserPhoto implements Serializable {
    private static final long serialVersionUID = 210531478283063610L;
    private long id;
    private long userId;
    private long imageId;
    private int status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
