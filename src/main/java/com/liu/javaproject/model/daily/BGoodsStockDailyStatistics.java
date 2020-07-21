package com.liu.javaproject.model.daily;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 商品库存每日变更统计表
 */
@ApiModel(value = "商品库存每日变更统计表")
@Data
@EqualsAndHashCode
@Accessors(chain = true)
@TableName("b_goods_stock_daily_statistics")
public class BGoodsStockDailyStatistics implements Serializable
{
    private static final long serialVersionUID = -8434895768351833812L;

    @Excel(name = "主键", orderNum = "1")
    @ApiModelProperty("主键")
    @TableId(value = "pkno", type = IdType.AUTO)
    private Long pkno;

    @Excel(name = "创建时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "75")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("创建时间")
    private Date ctime;

    @Excel(name = "修改时间", format = "yyyy-MM-dd HH:mm:ss", orderNum = "85")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("修改时间")
    private Date mtime;

    @Excel(name = "创建人", orderNum = "70")
    @ApiModelProperty("创建人")
    private String cuser;

    @Excel(name = "修改人", orderNum = "80")
    @ApiModelProperty("修改人")
    private String muser;

    @Excel(name = "商品编码", orderNum = "5")
    @ApiModelProperty("商品编码")
    private String goodsId;

    @Excel(name = "统计日期", format = "yyyy-MM-dd HH:mm:ss", orderNum = "20")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty("统计日期")
    private Date statisticsDate;

    @Excel(name = "期初库存", orderNum = "25")
    @ApiModelProperty("期初库存")
    private Long beginStock;

    @Excel(name = "期初价值", orderNum = "30")
    @ApiModelProperty("期初价值")
    private BigDecimal beginValue;

    @Excel(name = "本日进货数", orderNum = "35")
    @ApiModelProperty("本日进货数")
    private Long receiveCount;

    @Excel(name = "本日退货数", orderNum = "40")
    @ApiModelProperty("本日退货数")
    private Long returnCount;

    @Excel(name = "本日销量", orderNum = "45")
    @ApiModelProperty("本日销量")
    private Long saleCount;

    @Excel(name = "本日调整", orderNum = "50")
    @ApiModelProperty("本日调整")
    private Long adjustCount;

    @Excel(name = "期末数量", orderNum = "55")
    @ApiModelProperty("期末数量")
    private Long endStock;

    @Excel(name = "期末价值", orderNum = "60")
    @ApiModelProperty("期末价值")
    private BigDecimal endValue;

    @Excel(name = "移动平均成本价", orderNum = "65")
    @ApiModelProperty("移动平均成本价")
    private BigDecimal transferAvgPrice;
}
