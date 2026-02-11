package cn.xbatis.plus;

import cn.hutool.core.util.IdUtil;
import cn.xbatis.core.XbatisGlobalConfig;
import cn.xbatis.core.incrementer.Generator;
import cn.xbatis.core.incrementer.GeneratorFactory;
import cn.xbatis.plus.constants.IdGeneratorConstant;
import cn.xbatis.plus.helper.ModifyListenerHelper;
import cn.xbatis.plus.helper.XbatisHelper;
import cn.xbatis.plus.interceptor.XbatisInterceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;
import java.io.Serializable;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(
        value = {SqlSessionFactory.class, SqlSessionFactoryBean.class},
        name = {
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration",
        }
)
@ConditionalOnSingleCandidate(DataSource.class)
@EnableConfigurationProperties(GlobalPlusProperties.class)
@Import(value = {XbatisHelper.class, XbatisInterceptor.class})
public class GlobalPlusAutoConfiguration implements InitializingBean {


    @Autowired
    private GlobalPlusProperties globalPlusProperties;

    @Autowired
    private XbatisInterceptor xbatisInterceptor;

    @Autowired
    private ModifyListenerHelper modifyListenerHelper;

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> {


            configuration.addInterceptor(xbatisInterceptor);

            XbatisGlobalConfig.setLogicDeleteSwitch(this.globalPlusProperties.getLogicDeleteSwitch());
            //region 加载自定义id
            GeneratorFactory.register(IdGeneratorConstant.snow, new SnowIdHelper());
            GeneratorFactory.register(IdGeneratorConstant.uuid, new UuidHelper());
            GeneratorFactory.register(IdGeneratorConstant.simpleUuid, new SimpleUuidGenerator());
            //endregion
            //插入数据监听
            XbatisGlobalConfig.setGlobalOnInsertListener(modifyListenerHelper::setGlobalOnInsertListener);
            //更新数据监听
            XbatisGlobalConfig.setGlobalOnUpdateListener(modifyListenerHelper::setGlobalOnUpdateListener);
            //逻辑删除监听
            XbatisGlobalConfig.setLogicDeleteInterceptor(modifyListenerHelper::setLogicDeleteInterceptor);

        };

    }

    @Override
    public void afterPropertiesSet() throws Exception {

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
