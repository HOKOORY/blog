package com.ho.blogt.utils;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    /**
     * 当前页码
     */
    private int currentPage;
    /**
     * 每页数量
     */
    private int limit;
    /**
     * 记录总数
     */
    private long totalSize;
    /**
     * 页码总数
     */
    private int totalPages;
    /**
     * 数据模型
     */
    private List<?> content;
}
