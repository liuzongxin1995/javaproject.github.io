package com.liu.javaproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.javaproject.model.BGoods;
import com.liu.javaproject.model.common.CommonQuery;
import com.liu.javaproject.model.common.ResponseHashResult;
import com.liu.javaproject.model.common.ResponseStringResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author lijiaxin
 * @since 2020-05-21
 */
public interface IBShopGoodsService extends IService<BGoods>
{
    /**
     * 查询商品
     * @param commonQuery
     * @return
     */
    ResponseHashResult getGoodsByCond(CommonQuery commonQuery);

    /**
     * 查询商品详情
     * @param goodsId
     * @return
     */
    ResponseHashResult getGoodsInfoById(String goodsId);

    /**
     * 修改商品
     * @param goods
     * @return
     */
    ResponseStringResult updateGoods(BGoods goods);

    /**
     * 新增商品
     * @param goods
     * @return
     */
    ResponseStringResult addGoods(BGoods goods);

    /**
     * 下架商品
     * @param goods
     * @return
     */
    ResponseStringResult removeGoodsById(BGoods goods);

    /**
     * 导出商品
     * @param commonQuery
     * @return
     */
    ResponseHashResult exportGoods(CommonQuery commonQuery);

    /**
     * 导入excel
     * @param file
     * @return
     */
    ResponseStringResult importExcel(MultipartFile file);

    /**
     * 查询未设置价格的商品
     * @param commonQuery
     * @return
     */
    ResponseHashResult getGoodsWithoutPriceByCond(CommonQuery commonQuery);
}
