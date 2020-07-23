package com.liu.javaproject.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 商品
 * </p>
 *
 * @author lijiaxin
 * @since 2020-05-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel("可经营商品表")
@TableName("b_goods")
public class BGoods implements Serializable
{

    private static final long serialVersionUID = 6490223017052010089L;

    /**
     * pkno
     */
    @ExcelIgnore
    @ApiModelProperty("主键")
    @TableId(value = "pkno", type = IdType.AUTO)
    private Long pkno;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    @TableField("ctime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "110")
    private Date ctime;

    /**
     * 修改时间
     */
    @ApiModelProperty("修改时间")
    @TableField("mtime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "修改时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "120")
    private Date mtime;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    @TableField("cuser")
    @Excel(name = "创建人", orderNum = "105")
    private String cuser;

    /**
     * 修改人
     */
    @ApiModelProperty("修改人")
    @TableField("muser")
    @Excel(name = "修改人", orderNum = "115")
    private String muser;

    /**
     * 商品编码
     */
    @ApiModelProperty("商品编号")
    @TableField("goods_id")
    @Excel(name = "商品编号", orderNum = "5")
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty("商品名称")
    @TableField("goods_name")
    @Excel(name = "商品名称", orderNum = "15")
    private String goodsName;

    /**
     * 条形码
     */
    @ApiModelProperty("条形码")
    @TableField("goods_bar_code")
    @Excel(name = "条形码", orderNum = "18")
    private String goodsBarCode;

    /**
     * 商品简称
     */
    @ApiModelProperty("商品简称")
    @TableField("goods_short_name")
    @Excel(name = "商品简称", orderNum = "20")
    private String goodsShortName;

    /**
     * 商品分类编码(大类)
     */
    @ApiModelProperty("商品分类编号(大类)")
    @TableField("first_category_id")
    @Excel(name = "商品分类编号(大类)", orderNum = "25")
    private String firstCategoryId;

    /**
     * 二级分类编码(中类)
     */
    @ApiModelProperty("二级分类编号(中类)")
    @TableField("second_category_id")
    @Excel(name = "二级分类编号(中类)", orderNum = "35")
    private String secondCategoryId;

    /**
     * 三级分类编码(小类)
     */
    @ApiModelProperty("三级分类编号(小类)")
    @TableField("third_category_id")
    @Excel(name = "三级分类编号(小类)", orderNum = "45")
    private String thirdCategoryId;

    /**
     * 商品单位
     */
    @ApiModelProperty("商品单位")
    @TableField("unit")
    @Excel(name = "商品单位", orderNum = "60")
    private String unit;

    /**
     * 商品规格
     */
    @ApiModelProperty("规格")
    @TableField("spec")
    @Excel(name = "规格", orderNum = "62")
    private String spec;

    /**
     * 图片地址
     */
    @ApiModelProperty("图片地址")
    @TableField("goods_img")
    @ExcelIgnore
    private String goodsImg;

    /**
     * 重量
     */
    @Excel(name = "重量", orderNum = "65")
    @ApiModelProperty("重量")
    @TableField("goods_weight")
    private BigDecimal goodsWeight;

    /**
     * 重量单位
     */
    @ApiModelProperty("重量单位")
    @TableField(exist = false)
    @ExcelIgnore
    private String weightUnit;

    /**
     * 税率
     */
    @ApiModelProperty("销项税")
    @TableField("tax_rate")
    @Excel(name = "销项税(%)", orderNum = "70", numFormat = "0.##")
    private BigDecimal taxRate;

    /**
     * 保质期(月)
     */
    @ApiModelProperty("保质期(月)")
    @TableField(exist = false)
    @ExcelIgnore
    private BigDecimal shelfLifeMonth;

    /**
     * 保质期(天)
     */
    @ApiModelProperty("保质期(天)")
    @TableField("goods_shelf_life")
    @Excel(name = "保质期(天)", orderNum = "75")
    private BigDecimal goodsShelfLife;

    /**
     * 备注
     */
    @ApiModelProperty("备注")
    @TableField("remark")
    @Excel(name = "备注", orderNum = "80")
    private String remark;

    /**
     * 商品状态 0-不可订货 | 1-可订货
     */
    @ApiModelProperty("商品状态 0-不可订货 | 1-可订货")
    @TableField("goods_state")
    private String goodsState;

    /**
     * 产地
     */
    @ApiModelProperty("产地")
    @TableField("goods_produce_place")
    @Excel(name = "产地", orderNum = "90")
    private String goodsProducePlace;

    /**
     * 厂家
     */
    @ApiModelProperty("厂家")
    @TableField("goods_produce_factory")
    @Excel(name = "厂家", orderNum = "95")
    private String goodsProduceFactory;

    /**
     * 是否为重点商品（0：非重点商品，1：重点商品）
     */
    @ApiModelProperty("是否为重点商品")
    @TableField("is_key_goods")
    @ExcelIgnore
    private String isKeyGoods;
    
    /**
     * 商品零售价
     */
    @ApiModelProperty("商品零售价")
    @TableField(exist = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal goodsSalePrice;
    
    /**
     * 商品上下架 0-上架|1-下架
     */
    @ApiModelProperty("商品上下架 0-上架|1-下架")
    @TableField("is_delete")
    private String isDelete;
}
