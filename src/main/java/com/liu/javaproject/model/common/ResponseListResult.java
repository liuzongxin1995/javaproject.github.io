package com.liu.javaproject.model.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @author Admin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class ResponseListResult extends ResponseResult{

    private List data;
    public ResponseListResult() {}
    public ResponseListResult(ResponseStatus status) {
        this.status = status;
    }
    public ResponseListResult(ResponseStatus status, List data) {
        this.data = data;
        this.status = status;
    }
}
