package cn.xbatis.plus.supports;

import cn.hutool.log.Log;
import cn.xbatis.core.sql.executor.Update;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import org.apache.ibatis.mapping.BoundSql;

public class JSqlParseSupports {

    private final Log log = Log.get(JSqlParseSupports.class);

    public Statement parseStatement(BoundSql boundSql) {
        try {
            String sql = boundSql.getSql();
            Statement statement = CCJSqlParserUtil.parse(sql);

            if (statement instanceof PlainSelect plainSelect) {
                execute(plainSelect, boundSql);
            }
            return statement;
        } catch (JSQLParserException e) {
            log.error("解析SQL异常", e);
            throw new RuntimeException("发生异常:" + e.getMessage(), e);
        }

    }

    /**
     * 处理select方法拦截
     */
    public void execute(PlainSelect plainSelect, BoundSql boundSql) {

    }


}
