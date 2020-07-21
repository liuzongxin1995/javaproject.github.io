package com.liu.javaproject.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class UserThreadLocal {
    private UserThreadLocal(){

    }

    private static final Map<Thread,Map<String,Object>> LOCAL = new HashMap<>();

    public static void set(Map<String,Object> info ){
        if (info == null) {
            LOCAL.remove(Thread.currentThread());
        } else {
            LOCAL.put(Thread.currentThread(), info);
        }
    }

    public static Map<String,Object> get()
    {
        return LOCAL.get(Thread.currentThread());
    }

    public static Object get(String key){
        if(StringUtils.isEmpty(key))
        {
            return LOCAL.get(Thread.currentThread());
        }
        Map<String,Object> map = LOCAL.get(Thread.currentThread());
        if(map == null)
            return null;
        if(map.containsKey(key))
            return map.get(key);
        return null;
    }
}
