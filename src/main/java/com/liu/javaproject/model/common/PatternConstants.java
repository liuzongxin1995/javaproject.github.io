package com.liu.javaproject.model.common;

import java.util.regex.Pattern;

/**
 * @author wantianyu
 * @date 2020/6/4 16:07
 * 常用正则
 */
public class PatternConstants {
    //正整数(1-999999999)
    public static final Pattern POSITIVE_INT_NUMBER_1 = Pattern.compile("^[1-9]\\d{0,8}$");

    //保留两位小数
    public static final Pattern POSITIVE_DECIMAL_NUMBER_1 = Pattern.compile("\\d{1,8}\\.\\d{1,2}$|\\d{1,8}$");
}
