package com.liu.javaproject.model.common;


//操作的响应状态

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description = "返回状态")
public class ResponseStatus implements Serializable{

    private static final long serialVersionUID = -3435462186394872857L;

    @ApiModelProperty(value = "是否成功")
    private boolean success;//是否成功
    @ApiModelProperty(value = "返回码")
    private Integer code;//返回码，如：200
    @ApiModelProperty(value = "返回信息")
    private String message;//返回信息，如："操作成功"，"操作失败"，"权限不足"
    @ApiModelProperty(value = "返回的token")
    private String newToken;

    private static ResponseStatus responseSuccess;
    private static ResponseStatus responseFail;

    public ResponseStatus() {
    }

    public ResponseStatus(ResultCode code) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
    }

    public ResponseStatus(ResultCode code, String newToken) {
        this.success = code.success;
        this.code = code.code;
        this.message = code.message;
        this.newToken = newToken;
    }

    public ResponseStatus(Integer code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
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


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNewToken() {
        return newToken;
    }

    public void setNewToken(String newToken) {
        this.newToken = newToken;
    }

}
