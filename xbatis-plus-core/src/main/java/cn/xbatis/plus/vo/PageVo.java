package cn.xbatis.plus.vo;

import cn.xbatis.core.mybatis.mapper.context.Pager;

import java.util.List;

/**
 * 分页返回类
 *
 * @param <T>
 */
public class PageVo<T> {

    private long total;

    private List<T> list;

    public PageVo() {
    }

    public PageVo(Pager<T> page) {
        this.list = page.getResults();
        this.total = page.getTotal();
    }


    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
