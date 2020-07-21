package com.liu.javaproject.exception;

import com.liu.javaproject.model.common.ResultCode;

/**
 * 自定义异常：封装了异常具体信息。
 * @author LiuR
 */
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = -7662423477409732089L;
    private ResultCode resultCode;
    public CustomException(ResultCode resultCode){
        this.resultCode=resultCode;
    }
    public ResultCode getResultCode(){
        return this.resultCode;
    }
}
