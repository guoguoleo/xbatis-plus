package cn.xbatis.plus.utils;

import cn.xbatis.plus.dto.PagerDto;

public class PageUtil {

    public static<T> void startPage(PagerDto<T> pagerDto){
        pagerDto.setSize(pagerDto.getPageSize());
        pagerDto.setNumber(pagerDto.getPageNumber());
    }
}
