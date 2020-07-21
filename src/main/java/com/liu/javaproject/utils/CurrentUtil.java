package com.liu.javaproject.utils;

import com.liu.javaproject.config.UserThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 通用工具类
 */
@Slf4j
public class CurrentUtil {
    /**
     * 获取当前登录人的userId
     * 如果获取失败，默认使用system
     * @return
     */
    public static String getCurrentUserId() {
        log.error("===>CurrentUtil :" + Thread.currentThread().getName());
        String userId = "system";
        try {
            if (UserThreadLocal.get("userId") != null) {
                userId = (String) UserThreadLocal.get("userId");
            }
            return userId;
        } catch (Exception e) {
            return userId;
        }
    }

    /**
     * @Author ChenHu
     * @Date 2019/11/8-10:28
     * @Description 获取当前用户信息（数据来源自token解析）
     * AuthInterceptor.preHandle() 方法存入
     * @Return
     **/
    public static Map<String,Object> getCurrentUser() {
        return UserThreadLocal.get();
    }


    /**
     * 获取当前登录人的token
     *
     * @return
     */
    public static String getCurrentToken() {
        String token = "";
        try {
            if (UserThreadLocal.get("token") != null) {
                token = (String) UserThreadLocal.get("token");
            }
            return token;
        } catch (Exception e) {
            return token;
        }
    }
}
