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
        <version>1.0.1</version>
     </dependency>
   ```

3. mybatis配置,如下
   ```java
    @XbatisPojoCheckScan(basePackages = "com.**.**.entity")
    @MapperScans(value = {@MapperScan(basePackages = "com.**.**.dao")})
    @SpringBootConfiguration
    public class MybatisConfig {
    }
    ```
4. 继承ModifyListenerHelper，可以实现数据插入/更新/删除时候监听
   ```java
    @Component
    public class ModifyListener extends ModifyListenerHelper {
        /**
        * 全局插入数据监听
        * @param o 实体对象
        */
        public void setGlobalOnInsertListener(Object o) {
            //插入时处理逻辑
        }

        /**
         * 全局更新数据监听
         * @param o 实体对象
         */
        public void setGlobalOnUpdateListener(Object o) {
             //更新时处理逻辑
        }

         /**
         * 逻辑删除监听
         * @param clazz 实体类
         * @param update 更新对象
         */
        public void setLogicDeleteInterceptor(Class<?> clazz, BaseUpdate<?> update){
            //逻辑删除时处理逻辑
        }
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
      public class MybatisConfig {

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

