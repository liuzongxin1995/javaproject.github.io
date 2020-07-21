package com.liu.javaproject.model.common;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "通用删除接口入参类")
public class CommonDelete {

    @ApiModelProperty(value = "主键json数组")
    private String pknos;//主键集合



}
