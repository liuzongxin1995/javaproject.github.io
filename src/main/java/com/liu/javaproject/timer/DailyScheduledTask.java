package com.liu.javaproject.timer;

import com.liu.javaproject.service.IBGoodsStockDailyStatisticsService;
import com.liu.javaproject.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 定时器
 */
@Slf4j
@Component
public class DailyScheduledTask
{
    @Autowired
    IBGoodsStockDailyStatisticsService goodsStockDailyStatisticsService;

//    @Autowired
//    IBGoodsStockDailyRecordService goodsStockDailyRecordService;

    /**
     * 商品库存每日变动统计定时任务
     * 每天00:30，1:30，2:30启动，如果统计已完成，不会重复统计
     * 统计昨天的库存变动
     */
    @Scheduled(cron = "0 30 0,1,2 * * ? ")
//    @Scheduled(cron = "0 0/1 * * * ?")
    public void goodsStockDailyStatisticsScheduledTask()
    {
        log.info("[info: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()], 库存统计定时任务开始执行, 当前时间:{}", DateUtil.getChinaTime());

        Date currentTime = DateUtil.getChinaTime();
        //当天0点0分0秒
        Date today = DateUtil.formatHhMmSsOfDate(currentTime);
        //前一天0点0分0秒
        Date yesterday = DateUtil.addDate(today,0,0,-1,0,0,0,0);

        try
        {
            //开始统计
//            goodsStockDailyRecordService.recordState(yesterday, DailyRecordStatusEnum.RUNNING);
            log.info("[info: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()], 定时任务开始统计每日库存变动，当前时间:{}", DateUtil.getChinaTime());
            goodsStockDailyStatisticsService.startGoodsStockDailyStatistics(yesterday);
            log.info("[info: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()], 每日库存变动统计入库完成, 当前时间:{}", DateUtil.getChinaTime());
//            goodsStockDailyRecordService.recordState(yesterday, DailyRecordStatusEnum.SUCCESS);
        }
        catch (Exception e)
        {
            log.error("[info: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()], 库存统计定时任务执行异常, 当前时间:{}", DateUtil.getChinaTime(), e);
//            try
//            {
//                log.warn("[warn: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()], 记录库存日结失败状态, 当前时间:{}", DateUtil.getChinaTime());
//                goodsStockDailyRecordService.recordState(yesterday, DailyRecordStatusEnum.FAIL);
//            }
//            catch (Exception e1)
//            {
//                log.error("[error: DailyScheduledTask.goodsStockDailyStatisticsScheduledTask()]，记录库存日结失败状态异常！, 当前时间:{}", DateUtil.getChinaTime(), e);
//            }
        }
    }
}
