package com.liu.javaproject.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Map;

/**
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ResponseHashResult extends ResponseResult{

    private Map<String,Object> data;
    public ResponseHashResult() {}
    public ResponseHashResult(ResponseStatus status) {
        this.status = status;
    }
    public ResponseHashResult(ResponseStatus status, Map<String,Object> data) {
        this.data = data;
        this.status = status;
    }
}
