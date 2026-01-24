package cn.xbatis.plus;

import cn.hutool.core.util.IdUtil;
import cn.xbatis.core.XbatisGlobalConfig;
import cn.xbatis.core.incrementer.Generator;
import cn.xbatis.core.incrementer.GeneratorFactory;
import cn.xbatis.plus.constants.IdGeneratorConstant;
import cn.xbatis.plus.helper.ModifyListenerHelper;
import cn.xbatis.plus.properties.PlusScanProperties;
import jakarta.annotation.Resource;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class GlobalGenerator {

    @Resource
    private ModifyListenerHelper modifyListenerHelper;

    @Resource
    private PlusScanProperties plusScanProperties;

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {
            XbatisGlobalConfig.setLogicDeleteSwitch(this.plusScanProperties.getLogicDeleteSwitch());
            //region 加载自定义id
            GeneratorFactory.register(IdGeneratorConstant.snow, new SnowIdHelper());
            GeneratorFactory.register(IdGeneratorConstant.uuid, new UuidHelper());
            GeneratorFactory.register(IdGeneratorConstant.simpleUuid, new SimpleUuidGenerator());
            //endregion
            //插入数据监听
            XbatisGlobalConfig.setGlobalOnInsertListener(o -> modifyListenerHelper.setGlobalOnInsertListener(o));
            //更新数据监听
            XbatisGlobalConfig.setGlobalOnUpdateListener(o -> modifyListenerHelper.setGlobalOnUpdateListener(o));
            //逻辑删除监听
            XbatisGlobalConfig.setLogicDeleteInterceptor((clazz, update) ->
                    modifyListenerHelper.setLogicDeleteInterceptor(clazz, update));
        };
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
