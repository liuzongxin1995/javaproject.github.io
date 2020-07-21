package com.liu.javaproject.exception;


import com.liu.javaproject.model.common.ResultCode;

/**
 * 异常抛出类。
 */
public class ExceptionCast {
    public static void castException(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
