package com.liu.javaproject.model.daily;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class BGoodsStockDailySum implements Serializable
{
    private static final long serialVersionUID = -6126898920158516108L;

    private String goodsId;

    private Long stockRecordCount;

}
