package com.liu.javaproject.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * <p>
 * 员工卡号管理表
 * </p>
 *
 * @author admin
 * @since 2019-06-11
 */
@Data
@Accessors(chain = true)
public class BaseModel {

    @ApiModelProperty("主键")
    @TableId(value = "pkno", type = IdType.AUTO)
    private Long pkno;

    @ApiModelProperty("创建时间")
    @TableField("ctime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date ctime;

    @ApiModelProperty("修改时间")
    @TableField("mtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date mtime;

    @ApiModelProperty("创建人id")
    @TableField("cuser")
    private String cuser;

    @ApiModelProperty("修改人id")
    @TableField("muser")
    private String muser;

}
