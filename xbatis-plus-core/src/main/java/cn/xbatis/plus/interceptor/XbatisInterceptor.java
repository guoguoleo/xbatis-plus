package cn.xbatis.plus.interceptor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Intercepts(
        {
                @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
                @Signature(type = StatementHandler.class, method = "getBoundSql", args = {}),
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        }
)
public class XbatisInterceptor implements Interceptor {

    private final List<InnerInterceptor> interceptors = new CopyOnWriteArrayList<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        Object[] args = invocation.getArgs();
        if (target instanceof Executor) {
            Executor executor = (Executor) target;
            Object parameter = args[1];
            boolean isUpdate = args.length == 2;
            MappedStatement ms = (MappedStatement) args[0];

            if (!isUpdate && ms.getSqlCommandType() == SqlCommandType.SELECT) {
                RowBounds rowBounds = (RowBounds) args[2];
                ResultHandler<?> resultHandler = (ResultHandler<?>) args[3];
                BoundSql boundSql;
                if (args.length == 4) {
                    boundSql = ms.getBoundSql(parameter);
                } else {
                    boundSql = (BoundSql) args[5];
                }

                for (InnerInterceptor interceptor : this.interceptors) {
                    if (!interceptor.willDoQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql)) {
                        return Collections.emptyList();
                    }
                    interceptor.beforeQuery(executor, ms, parameter, rowBounds, resultHandler, boundSql);
                }

                CacheKey cacheKey = executor.createCacheKey(ms, parameter, rowBounds, boundSql);
                return executor.query(ms, parameter, rowBounds, resultHandler, cacheKey, boundSql);


            } else if (isUpdate) {
                for (InnerInterceptor interceptor : interceptors) {
                    if (!interceptor.willDoUpdate(executor, ms, parameter)) {
                        return -1;
                    }
                    interceptor.beforeUpdate(executor, ms, parameter);
                }
            }

        } else if (target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler) target;
            if (null == args) {
                for (InnerInterceptor interceptor : this.interceptors) {
                    interceptor.beforeGetBoundSql(statementHandler);
                }
            } else {
                Connection connections = (Connection) args[0];
                Integer transactionTimeout = (Integer) args[1];
                for (InnerInterceptor interceptor : this.interceptors) {
                    interceptor.beforePrepare(statementHandler, connections, transactionTimeout);
                }
            }


        } else {
            invocation.proceed();
        }

        return invocation.proceed();
    }


    public void addInnerInterceptor(InnerInterceptor innerInterceptor) {
        this.interceptors.add(innerInterceptor);
    }

    public List<InnerInterceptor> getInterceptors() {
        return Collections.unmodifiableList(this.interceptors);
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }
}
