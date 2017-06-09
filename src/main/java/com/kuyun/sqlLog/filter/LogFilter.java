package com.kuyun.sqlLog.filter;

import com.alibaba.druid.filter.AutoLoad;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.google.auto.service.AutoService;
import com.kuyun.sqlLog.util.SqlJoiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://github.com/alibaba/druid/issues/1197
 * Created by xuwuqiang on 2017/5/27.
 */

@AutoService(value = Filter.class)
@AutoLoad
public class LogFilter extends FilterEventAdapter {

    private Logger LOG = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        LOG.info(SqlJoiner.printSql(sql, statement));
        super.statementExecuteAfter(statement, sql, result);
    }

    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        LOG.debug(SqlJoiner.printSql(sql, statement));
        super.statementExecuteQueryAfter(statement, sql, resultSet);
    }

    protected void statementExecuteUpdateAfter(StatementProxy statement, String sql, int updateCount) {
        LOG.info(SqlJoiner.printSql(sql, statement));
        super.statementExecuteUpdateAfter(statement, sql, updateCount);
    }

}
