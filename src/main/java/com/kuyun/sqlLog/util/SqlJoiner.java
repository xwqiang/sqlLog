package com.kuyun.sqlLog.util;

import com.alibaba.druid.proxy.jdbc.StatementProxy;
import java.util.Calendar;
import org.joda.time.LocalDateTime;

/**
 * Created by xuwuqiang on 2017/5/31.
 */
public class SqlJoiner {

    public static String printSql(String sql, StatementProxy statementProxy) {

        String[] sqlArr = sql.split("[?]");
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (statementProxy.getParametersSize() > 0) {
            for (i = 0; i < statementProxy.getParametersSize(); i++) {
                sb.append(sqlArr[i]);
                String obj = quateIt(statementProxy.getParameter(i).getValue());
                sb.append(obj);
            }
        }
        for (; i < sqlArr.length; i++) {
            sb.append(sqlArr[i]);
        }
        return sb.toString();
    }

    private static String quateIt(Object object) {
        if (object == null) {
            return "NULL";
        }
        if (!needToQuate(object)) {
            return object.toString();
        }
        if (isDateTimeLike(object.getClass())) {
            return "'" + LocalDateTime.fromDateFields((java.util.Date) object).toString("yyyy-MM-dd HH:mm:ss")
                + "'";
        }
        return "'" + object + "'";
    }

    private static boolean needToQuate(Object object) {
        if (object == null) {
            return false;
        }
        boolean isPrimitive = isPrimitive(object.getClass());
        if (isPrimitive) {
            return false;
        }
        //Objects like String Date etc.
        return true;
    }

    private static Boolean isPrimitive(Class<?> klass) {
        if (klass == null) {
            throw new NullPointerException("kclass is NULL");
        }
        if (klass == int.class
            || klass == Integer.class
            || klass == boolean.class
            || klass == Boolean.class
            || klass == byte.class
            || klass == Byte.class
            || klass == short.class
            || klass == Short.class
            || klass == long.class
            || klass == Long.class
            || klass == float.class
            || klass == Float.class
            || klass == double.class
            || klass == Double.class) {
            return true;
        }
        return false;
    }

    private static boolean isDateTimeLike(Class<?> klass) {
        return Calendar.class.isAssignableFrom(klass)
            || java.util.Date.class.isAssignableFrom(klass)
            || java.sql.Date.class.isAssignableFrom(klass)
            || java.sql.Time.class.isAssignableFrom(klass);
    }

    public static boolean isNumber(Class<?> klass) {
        return Number.class.isAssignableFrom(klass)
            || klass.isPrimitive() && !is(boolean.class, klass) && !is(char.class, klass);
    }

    public static boolean is(Class<?> type, Class<?> klass) {
        return null != type && klass == type;
    }

}
