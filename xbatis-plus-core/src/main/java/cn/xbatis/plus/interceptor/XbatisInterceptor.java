package cn.xbatis.plus.interceptor;

import org.apache.ibatis.plugin.Interceptor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class XbatisInterceptor {


    private final List<Interceptor> interceptorList = new CopyOnWriteArrayList<>();


    public void addInterceptor(Interceptor interceptor) {
        interceptorList.add(interceptor);
    }

    public List<Interceptor> getInterceptorList() {
        return interceptorList;
    }
}
