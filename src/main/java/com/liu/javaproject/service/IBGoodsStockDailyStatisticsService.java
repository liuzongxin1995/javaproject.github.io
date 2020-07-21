package com.liu.javaproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.javaproject.model.common.CommonQuery;
import com.liu.javaproject.model.common.ResponseHashResult;
import com.liu.javaproject.model.daily.BGoodsStockDailyStatistics;

import java.util.Date;

/**
 * 商品库存每日变动统计
 */
public interface IBGoodsStockDailyStatisticsService extends IService<BGoodsStockDailyStatistics>
{
    /**
     * 统计每日商品库存变动
     */
    void addGoodsStockDailyStatistics(Date statisticsDay, Date startTime);

    /**
     * 查找指定时间之前(包含指定日期)，日结成功的日期
     * @return
     */
    Date getLastSuccessDate(Date date);

    /**
     * 查找商品库存变动每日统计信息
     * @param commonQuery
     */
    ResponseHashResult getGoodsStockDailyStatisticsByCond(CommonQuery commonQuery);

    /**
     * 导出商品库存变动每日统计信息
     * @param commonQuery
     * @return
     */
    ResponseHashResult exportGoodsStockDailyStatisticsByCond(CommonQuery commonQuery);

    /**
     * 手动触发库存每日统计
     * @return
     */
    ResponseHashResult startGoodsStockDailyStatistics(Date statisticsDate);
}
