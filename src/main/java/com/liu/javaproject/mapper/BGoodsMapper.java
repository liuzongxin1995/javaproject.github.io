package com.liu.javaproject.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.javaproject.model.BGoods;
import com.liu.javaproject.model.dto.BGoodsDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息表 Mapper 接口
 * </p>
 *
 * @author Admin
 */
@Mapper
@Repository
public interface BGoodsMapper extends BaseMapper<BGoods>
{
    /**
     * 查询单个商品详细信息
     * @param goodsId
     * @return
     */
    BGoodsDTO selectGoodsInfoById(@Param("goodsId") String goodsId);

    /**
     * 查询商品种类名称
     * @param goodsList
     * @return
     */
    List<Map<String, String>> selectGoodsType(List<BGoods> goodsList);

    /**
     * 查询商品信息
     * @param queryMap
     * @return
     */
    List<BGoodsDTO> selectGoodsInfoByQuery(HashMap<String, String> queryMap);

    /**
     * 查询商品详细信息
     * @param bGoods
     * @return
     */
    BGoodsDTO selectGoodsInfo(BGoods bGoods);

    /**
     * 查询没有设置价格的商品信息
     * @param queryMap
     * @return
     */
    List<BGoodsDTO> selectGoodsInfoWithoutPrice(Map<String, Object> queryMap);
}

