package com.kuyun.sqlLog.util;

import com.alibaba.druid.proxy.jdbc.StatementProxy;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;

/**
 * Created by xuwuqiang on 2017/5/31.
 */
public class SqlJoiner {

    public static final Integer a = new Integer(12);

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
                return "'" + LocalDateTime.fromDateFields((Date) object).toString("yyyy-MM-dd HH:mm:ss") + "'";
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
    private static Boolean isPrimitive(Class<?> kclass) {
        if (kclass == null) {
            throw new NullPointerException("kclass is NULL");
        }
        if (kclass == int.class || kclass == Integer.class || kclass == boolean.class || kclass == Boolean.class
            || kclass == byte.class
            || kclass == short.class
            || kclass == long.class || kclass == Long.class || kclass == float.class || kclass == Float.class
            || kclass == double.class || kclass == Double.class) {
            return true;
        }
        return kclass.isPrimitive();
    }

    private static boolean isDateTimeLike(Class<?> klass) {
        return Calendar.class.isAssignableFrom(klass)
            || java.util.Date.class.isAssignableFrom(klass)
            || java.sql.Date.class.isAssignableFrom(klass)
            || java.sql.Time.class.isAssignableFrom(klass);
    }

    public static boolean isNumber(Class<?> klass) {
        return Number.class.isAssignableFrom(klass)
            || klass.isPrimitive() && !is(boolean.class,klass) && !is(char.class,klass);
    }

    public static boolean is(Class<?> type,Class<?> klass) {
        return null != type && klass == type;
    }

}
