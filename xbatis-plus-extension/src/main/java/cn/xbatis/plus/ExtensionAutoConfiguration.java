package cn.xbatis.plus;

import cn.xbatis.plus.aspect.DataScopeAspect;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootConfiguration
@Import(value = {DataScopeAspect.class})
public class ExtensionAutoConfiguration {
}
