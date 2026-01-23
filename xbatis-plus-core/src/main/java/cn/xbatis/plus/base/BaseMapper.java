package cn.xbatis.plus.base;

import cn.xbatis.core.mybatis.mapper.MybatisMapper;
import cn.xbatis.core.mybatis.mapper.context.Pager;
import cn.xbatis.db.annotations.Paging;
import cn.xbatis.plus.dto.PageDto;

public interface BaseMapper<T> extends MybatisMapper<T> {

    @Paging
    Pager<T> page(PageDto<T> pageDto);

}
