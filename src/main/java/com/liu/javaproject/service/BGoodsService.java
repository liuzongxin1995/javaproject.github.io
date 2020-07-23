package com.liu.javaproject.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.liu.javaproject.model.BGoods;
import com.liu.javaproject.model.common.CommonQuery;
import com.liu.javaproject.model.common.ResponseHashResult;
import com.liu.javaproject.model.common.ResponseStringResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BGoodsService extends IService<BGoods> {

	/**
	 * 添加商品
	 * @param bGoods
	 * @return
	 */
	ResponseStringResult addGoods(BGoods bGoods);

    /**
     * 上架记录
     * 
     * @param list
     * @return
     */
    public ResponseStringResult addAll(List<BGoods> list);
    
    /**
     * 下架记录
     * 
     * @param list
     * @return
     */
    public ResponseStringResult removeAll(List<String> list);
    
	/**
	 * 查询商品
	 * @param commonQuery
	 * @return
	 */
	ResponseHashResult getAllGoods(CommonQuery commonQuery);

	/**
	 * 查看商品详细信息
	 * @param bGoods
	 * @return
	 */
	ResponseHashResult getGoodsInfo(BGoods bGoods);

	/**
	 * 修改商品信息
	 * @param bGoods
	 * @return
	 */
	ResponseStringResult modifyGoods(BGoods bGoods);

	/**
	 * 下架商品(修改商品状态)
	 * @param bGoods
	 * @return
	 */
	ResponseStringResult deleteGoods(BGoods bGoods);

	/**
	 * 导出所有商品
	 * @param commonQuery
	 * @return
	 */
	ResponseHashResult exportAllGoods(CommonQuery commonQuery);

	/**
	 * 导入excel
	 * @param file
	 * @return
	 */
	ResponseStringResult importExcel(MultipartFile file);

	/**
	 * 批量导入excel
	 * @param files
	 * @return
	 */
	ResponseStringResult importExcelV2(MultipartFile[] files);
}
