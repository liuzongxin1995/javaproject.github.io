package com.liu.javaproject.model.common;

import lombok.Data;

/**
 * @Author: LiuR
 * @Date: 2019/11/8 15:29
 */
@Data
public class ResponseResult {
    protected ResponseStatus status;
    ResponseResult(){}
    public ResponseResult(ResponseStatus status){
        this.status=status;
    }
}
