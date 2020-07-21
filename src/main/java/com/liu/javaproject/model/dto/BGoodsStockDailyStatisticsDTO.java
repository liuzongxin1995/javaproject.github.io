package com.liu.javaproject.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.liu.javaproject.model.daily.BGoodsStockDailyStatistics;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel("商品库存每日统计数据")
@Data
@EqualsAndHashCode
public class BGoodsStockDailyStatisticsDTO extends BGoodsStockDailyStatistics implements Serializable
{
    private static final long serialVersionUID = -5749115350691514692L;

    @ApiModelProperty("商品名称")
    @Excel(name = "商品名称", orderNum = "10")
    private String goodsName;

    @ApiModelProperty("商品条形码")
    @Excel(name = "商品条形码", orderNum = "15")
    private String goodsBarCode;

}
