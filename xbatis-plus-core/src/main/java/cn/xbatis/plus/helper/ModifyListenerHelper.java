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

    /**
     * 逻辑删除监听
     * @param clazz 实体类
     * @param update 更新对象
     */
    public void setLogicDeleteInterceptor(Class<?> clazz, BaseUpdate<?> update){
    }

}
