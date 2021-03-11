package com.ho.blogt.utils;

import lombok.Data;

@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int page = Constant.PAGE;
    /**
     * 每页数量
     */
    private int limit = 2;
}
