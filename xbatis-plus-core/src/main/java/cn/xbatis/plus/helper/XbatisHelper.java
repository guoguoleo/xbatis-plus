package cn.xbatis.plus.helper;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class XbatisHelper {

    @Bean
    public MapperScannerConfigurer helpMapperScanner() {
        MapperScannerConfigurer scanner = new MapperScannerConfigurer();
        // 核心：指定 Mapper 接口所在的包路径（支持多个包，用逗号分隔）
        scanner.setBasePackage("cn.xbatis.plus.helper");
        return scanner;
    }


}
