package cn.xbatis.plus.interceptor;

import cn.xbatis.core.mybatis.mapper.intercept.MethodInterceptor;
import org.apache.ibatis.plugin.Interceptor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XbatisInterceptor {


    private final List<MethodInterceptor> interceptorList = new CopyOnWriteArrayList<>();


    public void addInterceptor(MethodInterceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public List<MethodInterceptor> getInterceptorList() {
        return interceptorList;
    }
}
