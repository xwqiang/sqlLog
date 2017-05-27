package com.kuyun.sqlLog.filter;

import com.alibaba.druid.filter.AutoLoad;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.google.auto.service.AutoService;

/**
 * https://github.com/alibaba/druid/issues/1197
 * Created by xuwuqiang on 2017/5/27.
 */
@AutoService(value = Filter.class)
@AutoLoad
public class LogFilter extends FilterEventAdapter {

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        System.out.println(printSql(sql, statement));
        super.statementExecuteAfter(statement, sql, result);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        System.out.println(printSql(sql, statement));
        super.statementExecuteQueryAfter(statement, sql, resultSet);
    }

    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        System.out.println(printSql(sql, statement));
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
    }

    private String printSql(String sql, StatementProxy statementProxy) {
        for (int index = 0; index < statementProxy.getParametersSize(); index++) {
            sql = sql.replaceFirst("\\?", statementProxy.getParameter(index).getValue().toString());
        }
        return sql;
    }
}
