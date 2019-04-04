package com.jezz.plugin;


import com.jezz.utils.Page;

import java.io.Serializable;

public class PagePlugin<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private int pageCount; //总页数
    private int pageNum; //页码
    private int total; //总记录数
    private int pageSize; // 每页大小
    private T items;

    public PagePlugin() {
    }

    private PagePlugin(int total, int pageNum, int pageSize, T items) {
        if (pageSize <= 0) {
            pageSize = 10;
        }
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pageCount = (total + pageSize - 1) / pageSize;
        this.items = items;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public PagePlugin(int total, int pageSize, int pageNum) {
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pageCount = (total + pageSize - 1) / pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public static <T> PagePlugin<T> create(int total, int pageNum, int pageSize, T t) {
        return new PagePlugin<T>(total, pageNum, pageSize, t);
    }

    public static <T> PagePlugin<T> create(T t, Page page) {
        return create(page.getTotal(), page.getPageNum(), page.getPageCount(), t);
    }
}
