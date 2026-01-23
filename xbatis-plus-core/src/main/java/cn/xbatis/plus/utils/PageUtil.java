package cn.xbatis.plus.utils;

import cn.xbatis.plus.dto.PageDto;

public class PageUtil {

    public static<T> void startPage(PageDto<T> pageDto){
        pageDto.setSize(pageDto.getPageSize());
        pageDto.setNumber(pageDto.getPageNumber());
    }
}
