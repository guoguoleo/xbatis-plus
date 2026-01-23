package cn.xbatis.plus;

import cn.hutool.core.util.IdUtil;
import cn.xbatis.core.XbatisGlobalConfig;
import cn.xbatis.core.incrementer.Generator;
import cn.xbatis.core.incrementer.GeneratorFactory;
import cn.xbatis.plus.constants.IdGeneratorConstant;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;

import java.io.Serializable;

public class GlobalGenerator {

    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            //region 加载自定义id
            GeneratorFactory.register(IdGeneratorConstant.snow, new SnowIdHelper());
            GeneratorFactory.register(IdGeneratorConstant.uuid, new UuidHelper());
            GeneratorFactory.register(IdGeneratorConstant.simpleUuid, new SimpleUuidGenerator());
            //endregion
            //插入数据监听
            XbatisGlobalConfig.setGlobalOnInsertListener(this::setGlobalOnInsertListener);
            //更新数据监听
            XbatisGlobalConfig.setGlobalOnUpdateListener(this::setGlobalOnUpdateListener);
        };
    }

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


    private static class SnowIdHelper implements Generator<Serializable> {

        @Override
        public Serializable nextId(Class<?> entity) {
            return IdUtil.getSnowflakeNextId();
        }
    }

    private static class UuidHelper implements Generator<Serializable> {

        @Override
        public Serializable nextId(Class<?> entity) {
            return IdUtil.fastUUID();
        }
    }

    private static class SimpleUuidGenerator implements Generator<Serializable> {

        @Override
        public Serializable nextId(Class<?> entity) {
            return IdUtil.fastSimpleUUID();
        }
    }


}
