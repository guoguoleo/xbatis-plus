package cn.xbatis.plus.plugins;

import org.apache.ibatis.mapping.MappedStatement;

/**
 * 动态数据源组件
 */
public interface DynamicPlugins {

    /**
     * 获取数据库的key，对应spring.ds.routing下的数据源标识
     * @return 数据库标识
     */
    default String getKey(MappedStatement ms) {
        return "";
    }

}
