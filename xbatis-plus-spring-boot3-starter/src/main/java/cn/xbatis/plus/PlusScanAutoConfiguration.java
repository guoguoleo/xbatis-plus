package cn.xbatis.plus;

import cn.xbatis.plus.helper.XbatisHelper;
import cn.xbatis.plus.interceptor.XbatisInterceptor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import(value = {XbatisHelper.class, XbatisInterceptor.class, PlusScanProperties.class})
public class PlusScanAutoConfiguration {
}
