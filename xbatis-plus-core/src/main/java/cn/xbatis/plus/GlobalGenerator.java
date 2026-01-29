package cn.xbatis.plus;

import cn.hutool.core.util.IdUtil;
import cn.xbatis.core.XbatisGlobalConfig;
import cn.xbatis.core.incrementer.Generator;
import cn.xbatis.core.incrementer.GeneratorFactory;
import cn.xbatis.plus.constants.IdGeneratorConstant;
import cn.xbatis.plus.helper.ModifyListenerHelper;
import cn.xbatis.plus.interceptor.XbatisInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.io.Serializable;

public class GlobalGenerator {

    @Autowired
    private ModifyListenerHelper modifyListenerHelper;

    @Autowired
    private PlusScanProperties plusScanProperties;

    @Autowired
    private XbatisInterceptor xbatisInterceptor;

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {


            configuration.addInterceptor(xbatisInterceptor);

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


            XbatisGlobalConfig.enableInterceptOfficialMapperMethod();

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
