package com.sc.zhenli.conf;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiangj on 2016/8/19.
 */
public class ThreadSession {
    public static ThreadLocal<Integer> SESSION = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {return -1;}
    };

    public static ThreadLocal<Map<String,Object>> SESSION_PARAM = new ThreadLocal<Map<String,Object>>() {
        @Override
        protected Map<String,Object> initialValue() {return new HashMap<String,Object>();}
    };

    public static ThreadLocal<Map<String,Object>> SESSION_PROPERTY = new ThreadLocal<Map<String,Object>>() {
        @Override
        protected Map<String,Object> initialValue() {return new HashMap<String,Object>();}
    };
}
