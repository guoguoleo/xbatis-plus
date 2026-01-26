package cn.xbatis.plus.utils;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.reflection.DefaultReflectorFactory;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PluginUtil {


    /**
     * 获取对象元数据信息
     *
     * @param boundSql 参数
     * @return 元数据信息
     */
    public static MetaObject getMetaObject(BoundSql boundSql) {
        return MetaObject.forObject(boundSql,
                SystemMetaObject.DEFAULT_OBJECT_FACTORY,
                SystemMetaObject.DEFAULT_OBJECT_WRAPPER_FACTORY,
                new DefaultReflectorFactory());
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


}
