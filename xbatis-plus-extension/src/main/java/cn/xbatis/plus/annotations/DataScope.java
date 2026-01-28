package cn.xbatis.plus.annotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataScope {

    /**
     * 数据权限表标识，可用于多个数据权限标识
     */
    String tab() default "";

}
