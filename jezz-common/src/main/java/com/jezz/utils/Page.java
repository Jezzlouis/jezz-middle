package com.jezz.utils;


import java.io.Serializable;

/**
 * 分页对象
 */
public class Page<T> implements Serializable {

    private static final long serialVersionUID = 6479259084734311813L;
    private int pageCount;
    private int pageNum;
    private int total;
    private int size;
    private T items;

    public Page() {
    }

    private Page(int total, int pageNum, int pageSize, T items) {
        if (pageSize <= 0) pageSize = 10;
        this.total = total;
        this.pageNum = pageNum;
        this.size = pageSize;
        this.pageCount = (total + pageSize - 1) / pageSize;
        this.items = items;
    }

    public T getItems() {
        return items;
    }

    public void setItems(T items) {
        this.items = items;
    }

    public Page(int total, int size, int pageNum) {
        this.total = total;
        this.size = size;
        this.pageNum = pageNum;
        this.pageCount = (total + size - 1) / size;
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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static <T> Page<T> create(int total, int pageNum, int pageSize, T t) {
        return new Page<T>(total, pageNum, pageSize, t);
    }
}
