package cn.xbatis.plus.supports;

import cn.hutool.log.Log;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.mapping.BoundSql;

public class JSqlParseSupports {

    private final Log log = Log.get(JSqlParseSupports.class);

    public JSqlParseSupports() {
    }

    public String parseStatement(BoundSql boundSql) {
        try {
            String sql = boundSql.getSql();
            Statement statement = CCJSqlParserUtil.parse(sql);

            if (statement instanceof Insert insert) {
                this.execute(insert, boundSql);
            } else if (statement instanceof PlainSelect plainSelect) {
                this.execute(plainSelect, boundSql);
            } else if (statement instanceof Update update) {
                this.execute(update, boundSql);
            } else if (statement instanceof Delete delete) {
                this.execute(delete, boundSql);
            }
            return statement.toString();
        } catch (JSQLParserException e) {
            log.error("解析SQL异常", e);
            throw new RuntimeException("发生异常:" + e.getMessage(), e);
        }

    }



    /**
     * 处理select
     */
    protected void execute(Insert insert, BoundSql boundSql) {
        throw new UnsupportedOperationException();
    }

    protected void execute(Delete delete, BoundSql boundSql) {
        throw new UnsupportedOperationException();
    }

    protected void execute(Update update, BoundSql boundSql) {
        throw new UnsupportedOperationException();
    }

    protected void execute(PlainSelect plainSelect, BoundSql boundSql) {
        throw new UnsupportedOperationException();
    }


}
