package cn.xbatis.plus;

import cn.xbatis.plus.helper.XbatisHelper;
import cn.xbatis.plus.interceptor.XbatisInterceptor;
import cn.xbatis.plus.properties.PlusScanProperties;
import org.springframework.context.annotation.Import;

@Import(value = {XbatisHelper.class, PlusScanProperties.class, XbatisInterceptor.class})
public class PlusScanConfig {
}
