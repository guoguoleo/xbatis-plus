package cn.xbatis.plus.interceptor;

import cn.xbatis.plus.supports.JSqlParseSupports;
import cn.xbatis.plus.utils.DataScopeUtil;
import cn.xbatis.plus.utils.PluginUtil;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.sql.SQLException;

/**
 * 数据权限拦截器
 * 自己创建一个拦截器，继承该拦截器
 * 继承该拦截器后，实现execute(PlainSelect plainSelect, BoundSql boundSql)
 */
public class DataScopeInnerInterceptor extends JSqlParseSupports implements InnerInterceptor {

    @Override
    public void beforeQuery(Executor executor, MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler<?> resultHandler, BoundSql boundSql) throws SQLException {
        if (DataScopeUtil.getDataPermission() == null) {
            return;
        }
        PluginUtil.XBoundSql xBoundSql = PluginUtil.xBoundSql(boundSql);
        xBoundSql.sql(this.parseStatement(boundSql));
    }

}
