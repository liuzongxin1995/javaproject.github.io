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
public class ResponseObjectResult extends ResponseResult{

    private Object data;
    public ResponseObjectResult() {}
    public ResponseObjectResult(ResponseStatus status) {
        this.status = status;
    }
    public ResponseObjectResult(ResponseStatus status, Object data) {
        this.data = data;
        this.status = status;
    }
}
