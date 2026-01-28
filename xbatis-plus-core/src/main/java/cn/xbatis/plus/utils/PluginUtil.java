package cn.xbatis.plus.utils;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.Configuration;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PluginUtil {


    /**
     * 获取对象元数据信息
     *
     * @param object 参数
     * @return 元数据信息
     */
    public static MetaObject getMetaObject(Object object) {
        return MetaObject.forObject(object,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
    }

    /**
     * 获得真正的处理对象,可能多层代理.
     */
    @SuppressWarnings("unchecked")
    public static <T> T realTarget(Object target) {
        if (Proxy.isProxyClass(target.getClass())) {
            Plugin plugin = (Plugin) Proxy.getInvocationHandler(target);
            MetaObject metaObject = getMetaObject(plugin);
            return realTarget(metaObject.getValue("target"));
        }
        return (T) target;
    }


    public static XBoundSql xBoundSql(BoundSql boundSql) {
        return new XBoundSql(boundSql);
    }


    /**
     * 构建BoundSql
     */
    public static class XBoundSql {

        private final MetaObject boundSql;
        private final BoundSql delegate;

        XBoundSql(BoundSql boundSql) {
            this.delegate = boundSql;
            this.boundSql = PluginUtil.getMetaObject(boundSql);
        }


        public String sql() {
            return delegate.getSql();
        }

        /**
         * 运行sql
         */
        public void sql(String sql) {
            boundSql.setValue("sql", sql);
        }

        public List<ParameterMapping> parameterMappings() {
            List<ParameterMapping> parameterMappings = delegate.getParameterMappings();
            return new ArrayList<>(parameterMappings);
        }

        public void parameterMappings(List<ParameterMapping> parameterMappings) {
            boundSql.setValue("parameterMappings", Collections.unmodifiableList(parameterMappings));
        }

        public Object parameterObject() {
            return get("parameterObject");
        }

        public Map<String, Object> additionalParameters() {
            return get("additionalParameters");
        }

        @SuppressWarnings("unchecked")
        private <T> T get(String property) {
            return (T) boundSql.getValue(property);
        }
    }



    //xbatis 请求
    public static class XStatementHandler {
        private final MetaObject statementHandler;

        XStatementHandler(MetaObject statementHandler) {
            this.statementHandler = statementHandler;
        }

        public ParameterHandler parameterHandler() {
            return get("parameterHandler");
        }

        public MappedStatement mappedStatement() {
            return get("mappedStatement");
        }

        public Executor executor() {
            return get("executor");
        }

        public XBoundSql xBoundSql() {
            return new XBoundSql(boundSql());
        }

        public BoundSql boundSql() {
            return get("boundSql");
        }

        public Configuration configuration() {
            return get("configuration");
        }

        @SuppressWarnings("unchecked")
        private <T> T get(String property) {
            return (T) statementHandler.getValue(property);
        }
    }


    public static XStatementHandler xStatementHandler(StatementHandler statementHandler) {
        statementHandler = realTarget(statementHandler);
        MetaObject object = getMetaObject(statementHandler);
        return new XStatementHandler(getMetaObject(object.getValue("delegate")));
    }

}
