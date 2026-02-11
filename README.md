# xbatis-plus

#### 介绍

xbatis封装，向mybatisplus靠拢,仅支持springboot3版本

#### 软件架构

软件架构说明

#### 安装教程

1. maven install打包
2. 项目引入
   ```xml
     <dependency>
        <groupId>cn.xbatis.plus</groupId>
        <artifactId>xbatis-plus-spring-boot3-starter</artifactId>
        <version>1.0.0</version>
     </dependency>
   ```

3. 创建配置类继承GlobalPlusConfiguration,如下
   ```java
    @XbatisPojoCheckScan(basePackages = "com.**.**.entity")
    @MapperScans(value = {@MapperScan(basePackages = "com.**.**.dao")})
    @SpringBootConfiguration
    public class MybatisConfig extends GlobalPlusConfiguration {
    }
    ```

#### 使用说明

1. 拦截器
自定义拦截器需要继承 InnerInterceptor 接口
2. 全表防止更新与删除拦截器  BlockAttackInnerInterceptor
3. 权限拦截器  继承 DataScopeInnerInterceptor ，在protected void executePlainSelect(PlainSelect plainSelect, BoundSql boundSql, DataScope dataScope)
添加逻辑
4. 实现拦截器
   ```java
      @XbatisPojoCheckScan(basePackages = "com.**.**.entity")
      @MapperScans(value = {@MapperScan(basePackages = "com.**.**.dao")})
      @SpringBootConfiguration
      public class MybatisConfig extends GlobalPlusConfiguration {

          @Bean
          public XbatisInterceptor xbatisInterceptor() {
               XbatisInterceptor xbatisInterceptor = new XbatisInterceptor();
               xbatisInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
               xbatisInterceptor.addInnerInterceptor(new UserDataScopeInterceptor());
               return xbatisInterceptor;
          }
      }

   ```

#### 参与贡献

1.  鸣谢xbatis  https://gitee.com/xbatis/xbatis
2.  鸣谢hutool https://gitee.com/chinabugotech/hutool

