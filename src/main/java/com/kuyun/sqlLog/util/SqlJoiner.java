package com.kuyun.sqlLog.util;

import com.alibaba.druid.proxy.jdbc.StatementProxy;
import java.util.Calendar;
import org.joda.time.LocalDateTime;

/**
 * Created by xuwuqiang on 2017/5/31.
 */
public class SqlJoiner {

    public static String printSql(String sql, StatementProxy statementProxy) {
        for (int index = 0; index < statementProxy.getParametersSize(); index++) {
            sql = sql.replaceFirst("\\?", quateIt(statementProxy.getParameter(index).getValue()));
        }
        return sql;
    }

    private static String quateIt(Object object) {
        if (object == null) {
            return "NULL";
        }
        if (needToQuate(object)) {
            if (isDateTimeLike(object.getClass())) {
                return "'" + LocalDateTime.fromDateFields((java.util.Date) object).toString("yyyy-MM-dd HH:mm:ss")
                    + "'";
            }
            return "'" + object + "'";
        }
        return object.toString();
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

    /**
     * java.lang.Boolean#TYPE
     * java.lang.Character#TYPE
     * java.lang.Byte#TYPE
     * java.lang.Short#TYPE
     * java.lang.Integer#TYPE
     * java.lang.Long#TYPE
     * java.lang.Float#TYPE
     * java.lang.Double#TYPE
     * java.lang.Void#TYPE
     */
    private static Boolean isPrimitive(Class<?> klass) {
        if (klass == null) {
            throw new NullPointerException("kclass is NULL");
        }
        if (klass == int.class
            || klass == Integer.class
            || klass == boolean.class
            || klass == Boolean.class
            || klass == byte.class
            || klass == short.class
            || klass == long.class
            || klass == Long.class
            || klass == float.class
            || klass == Float.class
            || klass == double.class
            || klass == Double.class) {
            return true;
        }
        return klass.isPrimitive();
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
