package com.liu.javaproject.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.liu.javaproject.model.BGoods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode
@ApiModel("商品信息")
public class BGoodsDTO extends BGoods implements Serializable
{
    private static final long serialVersionUID = 6693281552216801890L;

    @ApiModelProperty("商品状态描述")
    @Excel(name = "商品状态描述", orderNum = "86")
    private String goodsStateInfo;

    @ApiModelProperty("上下架标识")
    @Excel(name = "上下架标识", orderNum = "87")
    private String isDeleteText;

    @ApiModelProperty("是否为重点商品")
    @Excel(name = "是否为重点商品", orderNum = "85")
    private String isKeyGoodsInfo;

    @ApiModelProperty("商品分类名称(大类)")
    @Excel(name = "商品大类名称", orderNum = "30")
    private String firstCategoryName;

    @ApiModelProperty("二级分类名称(中类)")
    @Excel(name = "商品中类名称", orderNum = "40")
    private String secondCategoryName;

    @ApiModelProperty("三级分类名称(小类)")
    @Excel(name = "商品小类名称", orderNum = "50")
    private String thirdCategoryName;
}
