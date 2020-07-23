package com.liu.javaproject.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;

/**
 * @author mingc
 * @date 2020/5/22 11:46
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("b_goods_type")
public class BGoodsType extends BaseModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ExcelIgnore
    @ApiModelProperty("主键")
    @TableId(value = "pkno", type = IdType.AUTO)
    private Long pkno;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "40")
    private Date ctime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "修改时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "50")
    private Date mtime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @Excel(name = "创建人", orderNum = "35")
    private String cuser;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    @Excel(name = "修改人", orderNum = "45")
    private String muser;

    /**
     * 商品分类编号
     */
    @ApiModelProperty(value = "商品分类编号")
    private String categoryId;

    /**
     * 商品分类名称
     */
    @ApiModelProperty(value = "商品分类名称")
    @Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]*$",message = "商品名称格式错误，请输入中文，字母，数字，下划线。")
    private String categoryName;

    /**
     * 上级商品分类编号
     */
    @ApiModelProperty(value = "上级商品分类编号")
    private String parentCategoryId;

    /**
     *商品分类 1-一级分类（大类）|2-二级分类（中类）|3-三级分类（小类）
     */
    @ApiModelProperty("商品分类")
    private String category;

    /**
     * 默认排序
     */
    @Excel(name = "默认排序")
    @ApiModelProperty("默认排序")
    private Integer defaultSeq;

    /**
     * 是否生效 1-生效|0-失效
     */
    @ExcelIgnore
    @ApiModelProperty("是否生效 1-生效|0-失效")
    private String enabled;

    /**
     * 平台 20-HOS
     */
    @ExcelIgnore
    @ApiModelProperty("平台 20-HOS")
    private String channel;

    @ExcelIgnore
    @TableField(exist = false)
    private String firstCategoryId;

    @ExcelIgnore
    @TableField(exist = false)
    private String firstCategoryName;

    @ExcelIgnore
    @TableField(exist = false)
    private String secondCategoryId;

    @ExcelIgnore
    @TableField(exist = false)
    private String secondCategoryName;

    @ExcelIgnore
    @TableField(exist = false)
    private String thirdCategoryId;

    @ExcelIgnore
    @TableField(exist = false)
    private String thirdCategoryName;

    /**
     *税率
     */
    @ApiModelProperty("税率")
    @TableField("tax_rate")
    @Excel(name = "税率", orderNum = "35")
    private String taxRate;

    /**
     * 税收分类编码
     */
    @ApiModelProperty("税收分类编码")
    @TableField("spbm")
    @Excel(name = "税收分类编码", orderNum = "40")
    private String spbm;

}
