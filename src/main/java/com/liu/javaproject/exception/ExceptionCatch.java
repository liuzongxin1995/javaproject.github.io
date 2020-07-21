package com.liu.javaproject.exception;

import com.google.common.collect.ImmutableMap;
import com.liu.javaproject.model.common.ResponseResult;
import com.liu.javaproject.model.common.ResponseStatus;
import com.liu.javaproject.model.common.ResponseStringResult;
import com.liu.javaproject.model.common.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 统一异常捕获类
 */
@RestControllerAdvice  //控制器增强
public class ExceptionCatch {
    //日志
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);
    //使用EXCEPTIONS存放异常类型和错误代码的映射，ImmutableMap的特点的一旦创建不可改变，并且线程安全
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> Exceptions;
    private static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    /*
     * 异常处理器
     * 只要该类异常被捕获到就执行下面方法
     */
    @ExceptionHandler(CustomException.class)
    public ResponseResult handleCustomException(CustomException e){
        //打印日志
        LOGGER.error("catch exception:{}\r\nexception:",e.getMessage(),e);
        ResultCode resultCode = e.getResultCode();
        return new ResponseResult(new ResponseStatus(resultCode));
    }

    /*
     * 异常处理器
     * 只要该类异常被捕获到就执行下面方法
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseStringResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        //打印日志
        LOGGER.error("catch exception:{}\r\nexception:",e.getMessage(),e);
        return new ResponseStringResult(new ResponseStatus(1013,e.getBindingResult().getFieldError().getDefaultMessage(),false));
    }

    /*
     * 异常处理器
     * 只要该类异常被捕获到就执行下面方法
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(Exception e){
        //打印日志
        LOGGER.error("catch exception:{}\r\nexception:",e.getMessage(),e);
        if(Exceptions==null){
            Exceptions=builder.build();
        }
        ResultCode code = Exceptions.get(e.getClass());
        //Map中没有，则报系统的异常信息。
        if(code==null){
            return new ResponseResult(new ResponseStatus(ResultCode.SERVER_ERROR));
        }else{
            //Map中有，则报规定的异常信息。
            return new ResponseResult(new ResponseStatus(code));
        }
    }
    static{
        //Map中加入基础的异常类型判断。
        builder.put(HttpMessageNotReadableException.class, ResultCode.INVALID_PARAM);
    }
}
