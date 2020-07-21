package com.liu.javaproject.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.javaproject.mapper.BGoodsStockDailyStatisticsMapper;
import com.liu.javaproject.model.common.*;
import com.liu.javaproject.model.daily.BGoodsStockDailyStatistics;
import com.liu.javaproject.model.daily.BGoodsStockDailySum;
import com.liu.javaproject.model.dto.BGoodsStockDailyStatisticsDTO;
import com.liu.javaproject.model.enums.StockRecordTypeEnum;
import com.liu.javaproject.service.FileCommonService;
import com.liu.javaproject.service.IBGoodsStockDailyStatisticsService;
import com.liu.javaproject.utils.DateUtil;
import com.liu.javaproject.utils.SortListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 商品库存每日变更统计
 */
@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class BGoodsStockDailyStatisticsServiceImpl extends ServiceImpl<BGoodsStockDailyStatisticsMapper, BGoodsStockDailyStatistics> implements IBGoodsStockDailyStatisticsService
{
    @Value("${fileAccessUrlPrefix}")
    private String fileAccessUrlPrefix;

    @Value("${localPathPrefix}")
    private String localPathPrefix;

    @Autowired
    FileCommonService<BGoodsStockDailyStatisticsDTO> fileCommonService;

    @Autowired
    BGoodsStockDailyStatisticsMapper goodsStockDailyStatisticsMapper;

    /**
     * 每日商品库存变动统计入库
     * statisticsDay: 统计日
     * startTime: 统计数据的起始时间点
     */
    @Override
    public void addGoodsStockDailyStatistics(Date statisticsDay, Date startTime)
    {
        //获取统计数据结束时间点，即统计日的23:59:59
        Date endTime = DateUtil.addDate(statisticsDay, 0,0,1,0,0,-1,0);

        //上一个统计成功日
        Date lastDailyDate = DateUtil.addDate(startTime,0,0,-1,0,0,0,0);
        //获取商品表中所有商品的期初数量，即上一次统计的期末值
        List<BGoodsStockDailyStatistics> lastDailyStatisticsList = goodsStockDailyStatisticsMapper.selectGoodsDailyStatisticsByDate(lastDailyDate);
        if (lastDailyStatisticsList == null || lastDailyStatisticsList.isEmpty())
        {
            log.warn("[warn: BGoodsStockDailyStatisticsServiceImpl.addGoodsStockDailyStatistics()],商品表为空");
            return;
        }

        //从仓库变更记录表中，获取指定日期范围内商品变动数量
        //入库数量
        List<BGoodsStockDailySum> goodsInSumList = goodsStockDailyStatisticsMapper.selectGoodsInOutSumByDate(startTime, endTime, StockRecordTypeEnum.STOCK_IN.getKey());
        //出库数量
        List<BGoodsStockDailySum> goodsOutSumList = goodsStockDailyStatisticsMapper.selectGoodsInOutSumByDate(startTime, endTime, StockRecordTypeEnum.STOCK_OUT.getKey());
        //销售数量
        //todo 销售数量
        //调整数量
        List<BGoodsStockDailySum> goodsAdjustSumList = goodsStockDailyStatisticsMapper.selectGoodsInOutSumByDate(startTime, endTime, StockRecordTypeEnum.STOCK_UPDATE.getKey());

        Map<String, Long> goodsInSumMap = Collections.emptyMap();
        Map<String, Long> goodsOutSumMap = Collections.emptyMap();
        Map<String, Long> goodsAdjustSumMap = Collections.emptyMap();
        //将统计数据转换成Map, key:goodsId, value:统计数量
        if (goodsInSumList != null)
        {
            goodsInSumMap = goodsInSumList.stream()
                    .collect(Collectors.toMap(
                            goodsInSum -> goodsInSum.getGoodsId(),
                            goodsInSum -> goodsInSum.getStockRecordCount()
                    ));
        }

        if (goodsOutSumList != null)
        {
            goodsOutSumMap = goodsOutSumList.stream()
                    .collect(Collectors.toMap(
                            goodsOutSum -> goodsOutSum.getGoodsId(),
                            goodsOutSum -> goodsOutSum.getStockRecordCount()
                    ));
        }

        if (goodsAdjustSumList != null)
        {
            goodsAdjustSumMap = goodsAdjustSumList.stream()
                    .collect(Collectors.toMap(
                            goodsAdjustSum -> goodsAdjustSum.getGoodsId(),
                            goodsAdjustSum -> goodsAdjustSum.getStockRecordCount()
                    ));
        }

        //当前统计日的数据
        Date currentDate = DateUtil.getChinaTime();
        List<BGoodsStockDailyStatistics> nowStatisticsList = new ArrayList<>(lastDailyStatisticsList.size());
        for (BGoodsStockDailyStatistics lastDailyStatistics : lastDailyStatisticsList)
        {
            String goodId = lastDailyStatistics.getGoodsId();
            //期初库存为上一次期末库存
            Long beginStock = lastDailyStatistics.getEndStock() == null ? 0L : lastDailyStatistics.getEndStock();
            //入库数量
            Long receiveCount = goodsInSumMap.get(goodId) == null ? 0L : goodsInSumMap.get(goodId);
            //出库数量
            Long returnCount = goodsOutSumMap.get(goodId) == null ? 0L : goodsOutSumMap.get(goodId);
            //todo 销售数量
            Long saleCount = 0L;
            //调整数量
            Long adjustCount = goodsAdjustSumMap.get(goodId) == null ? 0L : goodsAdjustSumMap.get(goodId);
            //期末库存
            Long endStock = beginStock + receiveCount - returnCount - saleCount + adjustCount;
            //期初价值，上一次期末价值
            BigDecimal beginValue = lastDailyStatistics.getEndValue() == null ? BigDecimal.ZERO : lastDailyStatistics.getEndValue();
            //期末价值：期初价值 + 进货量*进货价 + (调整-销售)*上一次移动平均成本价（为0则根据当前进货取）
            BigDecimal endValue = null;
            if (beginValue.equals(BigDecimal.ZERO))
            {
                //上一轮库存为0
            }
            else
            {
                //上一轮有剩余库存

            }

            lastDailyStatistics.setPkno(null).setCtime(currentDate).setMtime(currentDate)
                    .setCuser(GoodsConstants.USER_SYSTEM).setMuser(GoodsConstants.USER_SYSTEM)
                    .setStatisticsDate(statisticsDay).setBeginStock(beginStock).setBeginValue(beginValue)
                    .setReceiveCount(receiveCount).setReturnCount(returnCount).setSaleCount(saleCount)
                    .setAdjustCount(adjustCount).setEndStock(endStock).setEndValue(endValue);
            nowStatisticsList.add(lastDailyStatistics);
        }
        saveBatch(nowStatisticsList);
    }

    /**
     * 查找指定时间之前(包含指定日期)，统计成功的日期
     *
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public Date getLastSuccessDate(Date date)
    {
        return goodsStockDailyStatisticsMapper.selectLastSuccessDate(date);
    }

    /**
     * 查找商品库存变动每日统计信息
     *
     * @param commonQuery
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ResponseHashResult getGoodsStockDailyStatisticsByCond(CommonQuery commonQuery)
    {
        if (commonQuery == null)
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }
        commonQuery.setDefaultPageParam();
        String condList = commonQuery.getCondList();
        String sortList = commonQuery.getSortList();

        Map<String, Object> queryMap = new HashMap<>(0);
        if (!StringUtils.isEmpty(condList))
        {
            queryMap = JSON.parseObject(condList, HashMap.class);
        }
        if (!StringUtils.isEmpty(sortList))
        {
            sortList = SortListUtil.parseSortList(sortList);
        }
        queryMap.put("sortList", sortList);

        PageHelper.startPage(commonQuery.getPageNo(), commonQuery.getPageSize());
        List<BGoodsStockDailyStatisticsDTO> goodsStockDailyStatisticsList = goodsStockDailyStatisticsMapper.selectGoodsStockDailyStatisticsByCond(queryMap);
        PageInfo<BGoodsStockDailyStatisticsDTO> pageInfo = new PageInfo<>(goodsStockDailyStatisticsList);

        Map<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("totalNum", pageInfo.getTotal());
        resultMap.put("totalPage", pageInfo.getPages());
        resultMap.put("pageNo", pageInfo.getPageNum());
        resultMap.put("pageSize", pageInfo.getPageSize());
        resultMap.put("list", goodsStockDailyStatisticsList);

        return new ResponseHashResult(ResponseStatus.responseSuccess(), resultMap);
    }

    /**
     * 导出商品库存变动每日统计信息
     *
     * @param commonQuery
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ResponseHashResult exportGoodsStockDailyStatisticsByCond(CommonQuery commonQuery)
    {
        if (commonQuery == null)
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        String condList = commonQuery.getCondList();
        String sortList = commonQuery.getSortList();

        Map<String, Object> queryMap = new HashMap<>(0);
        if (!StringUtils.isEmpty(condList))
        {
            queryMap = JSON.parseObject(condList, HashMap.class);
        }
        if (!StringUtils.isEmpty(sortList))
        {
            sortList = SortListUtil.parseSortList(sortList);
        }
        queryMap.put("sortList", sortList);

        List<BGoodsStockDailyStatisticsDTO> goodsStockDailyStatisticsDTOList = goodsStockDailyStatisticsMapper.selectGoodsStockDailyStatisticsByCond(queryMap);

        ResponseHashResult responseHashResult = fileCommonService.exportFile(BGoodsStockDailyStatisticsDTO.class, goodsStockDailyStatisticsDTOList, GoodsConstants.EXPORT_GOODS_STOCK_DAILY_STATISTICS_FILE_NAME, fileAccessUrlPrefix, localPathPrefix);
        return responseHashResult;
    }

    /**
     * 手动触发库存每日统计
     *
     * @return
     */
    @Override
    public ResponseHashResult startGoodsStockDailyStatistics(Date statisticsDate)
    {
        log.info("[info: BGoodsStockDailyStatisticsServiceImpl.startGoodsStockDailyStatistics()], 库存统计任务开始执行, 统计日期:{}", statisticsDate);

        //当天0点0分0秒
        Date statisticsDay = DateUtil.formatHhMmSsOfDate(statisticsDate);

        if (statisticsDay.compareTo(DateUtil.formatHhMmSsOfDate(DateUtil.getChinaTime())) >= 0)
        {
            log.info("[info: BGoodsStockDailyStatisticsServiceImpl.startGoodsStockDailyStatistics()], 今天及之后的数据无法统计");
            return new ResponseHashResult(new ResponseStatus(10001, "今天及之后的数据无法统计", false));
        }

        Date startTime;
        //获取上次统计成功日期
        Date lastSuccessDate = getLastSuccessDate(statisticsDay);
        if (lastSuccessDate == null)
        {
            //系统刚启用
            startTime = statisticsDay;
        }
        else if (lastSuccessDate.compareTo(statisticsDay) >= 0)
        {
            //已经统计成功，不需要再次统计
            log.info("[info: BGoodsStockDailyStatisticsServiceImpl.startGoodsStockDailyStatistics()], 当前日期已经统计过, 当前时间:{}", DateUtil.getChinaTime());
            return new ResponseHashResult(ResponseStatus.responseSuccess());
        }
        else
        {
            //统计起始时间是上次统计成功后的下一天0点0分0秒
            startTime = DateUtil.addDate(lastSuccessDate, 0, 0, 1, 0, 0, 0, 0);
        }

        log.info("[info: BGoodsStockDailyStatisticsServiceImpl.startGoodsStockDailyStatistics()], 开始统计每日库存变动，当前时间:{}", DateUtil.getChinaTime());
        addGoodsStockDailyStatistics(statisticsDay, startTime);
        log.info("[info: BGoodsStockDailyStatisticsServiceImpl.startGoodsStockDailyStatistics()], 每日库存变动统计入库完成, 当前时间:{}", DateUtil.getChinaTime());

        return new ResponseHashResult(ResponseStatus.responseSuccess());
    }
}
