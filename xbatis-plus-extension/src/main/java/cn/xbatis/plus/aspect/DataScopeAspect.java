package cn.xbatis.plus.aspect;

import cn.hutool.log.Log;
import cn.xbatis.plus.annotations.DataScope;
import cn.xbatis.plus.utils.DataScopeUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class DataScopeAspect {

    private final Log log = Log.get(DataScopeAspect.class);

    @Pointcut("@annotation(cn.xbatis.plus.annotations.DataScope)")
    public void pointcut() {

    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable{
        log.info("DataScopeAspect around");
        try{
            MethodSignature signature = (MethodSignature) point.getSignature();
            Method method = signature.getMethod();
            DataScopeUtil.setDataPermission(method.getAnnotation(DataScope.class));
            return point.proceed();
        }finally {
            DataScopeUtil.removeDataPermission();
        }
    }

}
