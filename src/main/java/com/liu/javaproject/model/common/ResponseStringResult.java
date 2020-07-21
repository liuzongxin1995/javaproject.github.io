package com.liu.javaproject.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ResponseStringResult extends ResponseResult{

    private String data;
    public ResponseStringResult() {}
    public ResponseStringResult(ResponseStatus status) {
        this.status = status;
    }
    public ResponseStringResult(ResponseStatus status, String data) {
        this.data = data;
        this.status = status;
    }
}
