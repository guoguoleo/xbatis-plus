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

            GeneratorFactory.register(IdGeneratorConstant.snow, new SnowIdHelper());
            GeneratorFactory.register(IdGeneratorConstant.uuid, new UuidHelper());
            GeneratorFactory.register(IdGeneratorConstant.simpleUuid, new SimpleUuidGenerator());


            XbatisGlobalConfig.setGlobalOnInsertListener(this::setGlobalOnInsertListener);

            XbatisGlobalConfig.setGlobalOnUpdateListener(this::setGlobalOnUpdateListener);
        };
    }

    public void setGlobalOnInsertListener(Object o) {
    }

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
