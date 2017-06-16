package com.kuyun.sqlLog.filter;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by xuwuqiang on 2017/6/13.
 */
public class Test {

    public static void main(String[] args) {


        Map<String,Object> map = new HashMap();
        map.put("aaa","2323");
        map.put("aabba",new Object());
        System.out.printf("valueof %s\n",String.valueOf(map));

        Class<CharSequence> klass = CharSequence.class;
        System.out.println(klass.isAssignableFrom(String.class));
        Object array = Array.newInstance(String.class,1);
        Array.set(array,0,"test");
        System.out.println(array);

        System.out.println(array.getClass().getComponentType());

        Locale locale = new Locale("en","CC");
        Locale locale1 = Locale.getDefault();
        System.out.println(locale);
        System.out.println(locale1);


    }

}
