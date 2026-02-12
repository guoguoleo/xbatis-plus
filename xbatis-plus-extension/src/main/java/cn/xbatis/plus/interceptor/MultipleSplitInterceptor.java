package cn.xbatis.plus.interceptor;

import cn.hutool.log.Log;
import cn.xbatis.datasource.routing.DataSourceHolder;
import cn.xbatis.plus.plugins.DynamicPlugins;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

@Intercepts({
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
public class MultipleSplitInterceptor implements Interceptor {

    @Autowired(required = false)
    private DynamicPlugins dynamicPlugins;


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if (StringUtils.isNotBlank(DataSourceHolder.getCurrent())) {
            return invocation.proceed();
        }

        if (this.dynamicPlugins == null){
            return invocation.proceed();
        }

        Object[] args = invocation.getArgs();
        MappedStatement ms = (MappedStatement) args[0];

        String key = this.dynamicPlugins.getKey(ms);
        if (StringUtils.isNotBlank(key)) {
            DataSourceHolder.add(key);
        }
        try {
            return invocation.proceed();
        } finally {
            DataSourceHolder.remove();
        }
    }


}
