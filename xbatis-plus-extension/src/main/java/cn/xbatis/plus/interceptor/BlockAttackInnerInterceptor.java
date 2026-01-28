package cn.xbatis.plus.interceptor;

import cn.hutool.core.lang.Assert;
import cn.hutool.log.Log;
import cn.xbatis.plus.supports.JSqlParseSupports;
import cn.xbatis.plus.utils.PluginUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ParenthesedExpressionList;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.update.Update;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;

import java.sql.Connection;

/**
 * 防止sql全表更新与删除
 */
public class BlockAttackInnerInterceptor extends JSqlParseSupports implements InnerInterceptor {

    private final Log log = Log.get(BlockAttackInnerInterceptor.class);

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtil.XStatementHandler handler = PluginUtil.xStatementHandler(sh);
        MappedStatement ms = handler.mappedStatement();
        SqlCommandType sct = ms.getSqlCommandType();
        if (sct == SqlCommandType.UPDATE || sct == SqlCommandType.DELETE) {
            BoundSql boundSql = handler.boundSql();
            this.parseStatement(boundSql);
        }
    }

    @Override
    protected void execute(Update update, BoundSql boundSql) {
        log.info("update sql: {}", update.toString());
        this.checkWhere(update.getWhere(), "禁止全局更新");
    }

    @Override
    protected void execute(Delete delete, BoundSql boundSql) {
        log.info("delete sql: {}", delete.toString());
        this.checkWhere(delete.getWhere(), "禁止全局删除");
    }

    protected void checkWhere(Expression where, String ex) {
        Assert.isFalse(this.fullMatch(where), ex);
    }


    private boolean fullMatch(Expression where) {
        if (where == null) {
            return true;
        }
        log.info(where.toString());
        if (where instanceof EqualsTo equalsTo) {
            return equalsTo.getLeftExpression().toString().equals(equalsTo.getRightExpression().toString());
        } else if (where instanceof NotEqualsTo notEqualsTo) {
            return !notEqualsTo.getLeftExpression().toString().equals(notEqualsTo.getRightExpression().toString());
        } else if (where instanceof OrExpression orExpression) {
            this.fullMatch(orExpression.getRightExpression());
        } else if (where instanceof AndExpression andExpression) {
            this.fullMatch(andExpression.getRightExpression());
        } else if (where instanceof ParenthesedExpressionList<?> parenthesedExpressionList) {
            this.fullMatch(parenthesedExpressionList.get(0));
        }

        return false;
    }


}
