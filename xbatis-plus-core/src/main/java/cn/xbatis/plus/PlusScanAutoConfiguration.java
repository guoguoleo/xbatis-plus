package cn.xbatis.plus;

import cn.xbatis.plus.helper.XbatisHelper;
import cn.xbatis.plus.interceptor.XbatisInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@EnableConfigurationProperties(PlusScanProperties.class)
@Import(value = {XbatisHelper.class, XbatisInterceptor.class})
public class PlusScanAutoConfiguration {
}
