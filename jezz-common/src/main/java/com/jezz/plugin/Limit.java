/**
 *
 */
package com.jezz.plugin;

import java.io.Serializable;

public class Limit implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 2864524359649764964L;
    public static final Limit LIMIT_ONE = buildLimit(1, 1);
    private int size;
    private int pageId;
    private int start;

    /**
     * 用于 页面&DB分页
     */
    public static Limit buildLimit(int pageId, int pageSize) {
        if (pageId <= 0) {
            pageId = 1;
        }
        if (pageSize <= 0) {
            // 默认20
            pageSize = 20;
        }
        Limit limit = new Limit();
        limit.pageId = pageId;
        limit.size = pageSize;
        limit.start = (pageId - 1) * pageSize;
        return limit;
    }

    private Limit(int pageId, int size) {
        this.pageId = pageId;
        this.size = size;
    }

    private Limit() {
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @return the size
     */
    public int getStart() {
        return start;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the pageId
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * @param pageId the pageId to set
     */
    public void setPageId(int pageId) {
        this.pageId = pageId;
    }
}