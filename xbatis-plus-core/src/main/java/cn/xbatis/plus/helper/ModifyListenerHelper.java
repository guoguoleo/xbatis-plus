package cn.xbatis.plus.helper;

import cn.xbatis.core.sql.executor.BaseUpdate;

public class ModifyListenerHelper {

    /**
     * 全局插入数据监听
     * @param o 实体对象
     */
    public void setGlobalOnInsertListener(Object o) {
    }

    /**
     * 全局更新数据监听
     * @param o 实体对象
     */
    public void setGlobalOnUpdateListener(Object o) {
    }

    public void setLogicDeleteInterceptor(Class<?> clazz, BaseUpdate<?> update){
    }

}
