package com.liu.javaproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.javaproject.model.daily.BGoodsStockDailyStatistics;
import com.liu.javaproject.model.daily.BGoodsStockDailySum;
import com.liu.javaproject.model.dto.BGoodsStockDailyStatisticsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface BGoodsStockDailyStatisticsMapper extends BaseMapper<BGoodsStockDailyStatistics>
{
    /**
     * 获取指定日期范围内，指定操作类型，商品库存变动数量之和
     * @param startTime
     * @param endTime
     * @return
     */
    List<BGoodsStockDailySum> selectGoodsInOutSumByDate(@Param("startTime") Date startTime, @Param("endTime") Date endTime, @Param("type") Integer type);

    /**
     * 获取指定日结天的日结数据
     * @param day
     * @return
     */
    List<BGoodsStockDailyStatistics> selectGoodsDailyStatisticsByDate(@Param("day") Date day);

    /**
     * 查找指定时间之前(包含指定日期)，日结成功的日期
     * @return
     */
    Date selectLastSuccessDate(@Param("date") Date date);

    /**
     * 查询日结数据
     * @param queryMap
     * @return
     */
    List<BGoodsStockDailyStatisticsDTO> selectGoodsStockDailyStatisticsByCond(Map<String, Object> queryMap);
}
