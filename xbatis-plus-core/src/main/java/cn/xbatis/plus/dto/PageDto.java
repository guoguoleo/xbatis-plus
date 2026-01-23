package cn.xbatis.plus.dto;

import cn.xbatis.core.mybatis.mapper.context.Pager;

/**
 * 分页基类
 */
public class PageDto<T> extends Pager<T> {

    private int pageNumber = 1;

    private int pageSize = 10;


    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }
}
