package cn.xbatis.plus.base;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.core.mybatis.mapper.context.Pager;
import cn.xbatis.db.annotations.Paging;

public interface BaseMapper<T> extends MybatisMapper<T> {

    @Paging
    Pager<T> page(Pager<T> pager);

}
