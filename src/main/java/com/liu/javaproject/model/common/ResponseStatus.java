package com.liu.javaproject.model.common;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseStatus {

    private boolean success;
    private Integer code;
    private String message;
    private String newToken;

    private static volatile ResponseStatus responseSuccess;
    private static volatile ResponseStatus responseFail;

    public ResponseStatus() {
    }

    public static ResponseStatus responseSuccess(){

        if(responseSuccess == null){
            synchronized (ResponseStatus.class){
                if(responseSuccess == null){
                    responseSuccess = new ResponseStatus(ResultCode.SUCCESS);
                }
            }
        }

        return ResponseStatus.responseSuccess;
    }

    public static ResponseStatus responseFail(){

        if(responseFail == null){
            synchronized (ResponseStatus.class){
                if(responseFail == null){
                    responseFail = new ResponseStatus(ResultCode.FAIL);
                }
            }
        }

        return ResponseStatus.responseFail;
    }

    public static ResponseStatus responseInvalidParam(String msg){
        return new ResponseStatus(ResultCode.INVALID_PARAM.code,msg,false);
    }

    public ResponseStatus(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;

    }

   public ResponseStatus(ResultCode code,String newToken) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.newToken = newToken;
    }

    public ResponseStatus(Integer code,String message,boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
