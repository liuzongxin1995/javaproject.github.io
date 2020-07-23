package com.liu.javaproject.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liu.javaproject.model.BGoodsType;
import com.liu.javaproject.model.dto.GoodsTypeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author mingc
 * @date 2020/5/22 12:14
 */
@Mapper
@Repository
public interface BGoodsTypeMapper extends BaseMapper<BGoodsType> {

    /**
     * 查询商品分类详细信息
     * @param queryMap
     * @return
     */
    List<BGoodsType> selectAll(HashMap<String, Object> queryMap);

    /**
     * 查询商品分类（分类ID,分类名称）
     * @param queryMap
     * @return
     */
    List<GoodsTypeDTO> selectAllInfo(HashMap<String, Object> queryMap);

    /**
     * 获取商品信息列表
     * @param queryMap
     * @return
     */
    List<BGoodsType> getGoodsList(HashMap<String, Object> queryMap);
}

