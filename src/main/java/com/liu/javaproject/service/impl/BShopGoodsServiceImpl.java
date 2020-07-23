package com.liu.javaproject.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liu.javaproject.mapper.BGoodsMapper;
import com.liu.javaproject.mapper.BGoodsTypeMapper;
import com.liu.javaproject.model.BGoods;
import com.liu.javaproject.model.BGoodsType;
import com.liu.javaproject.model.common.*;
import com.liu.javaproject.model.dto.BGoodsDTO;
import com.liu.javaproject.service.FileCommonService;
import com.liu.javaproject.service.IBShopGoodsService;
import com.liu.javaproject.utils.CurrentUtil;
import com.liu.javaproject.utils.DateUtil;
import com.liu.javaproject.utils.QueryWrapperUtil;
import com.liu.javaproject.utils.SortListUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author lijiaxin
 * @since 2020-05-21
 */
@Slf4j
@Service
@Transactional(rollbackFor = {Exception.class})
public class BShopGoodsServiceImpl extends ServiceImpl<BGoodsMapper, BGoods> implements IBShopGoodsService
{

    @Value("${fileAccessUrlPrefix}")
    private String fileAccessUrlPrefix;

    @Value("${localPathPrefix}")
    private String localPathPrefix;

    @Value("${goodsImgAccessUrlPrefix}")
    private String goodsImgAccessUrlPrefix;

    @Autowired
    private FileCommonService fileCommonService;

    /*@Autowired
    FileCommonService<BGoodsDTO> fileCommonService;*/

    @Autowired
    BGoodsMapper bGoodsMapper;

    @Autowired
    BGoodsTypeMapper bGoodsTypeMapper;

    /**
     * 查询商品
     *
     * @param commonQuery
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ResponseHashResult getGoodsByCond(CommonQuery commonQuery)
    {
        if (commonQuery == null)
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        commonQuery.setDefaultPageParam(commonQuery);
        Integer pageNo = commonQuery.getPageNo();
        Integer pageSize = commonQuery.getPageSize();
        String condList = commonQuery.getCondList();
        String sortList = commonQuery.getSortList();
        Page<BGoods> goodsQueryPage =new Page<>(pageNo, pageSize);

        if (!StringUtils.isEmpty(sortList))
        {
            sortList = sortList.replace("firstCategoryName", "firstCategoryId")
                    .replace("secondCategoryName", "secondCategoryId")
                    .replace("thirdCategoryName", "thirdCategoryId")
                    .replace("goodsStateInfo", "goodsState");
        }
        QueryWrapper<BGoods> queryWrapper = QueryWrapperUtil.fillQueryWrapper(condList, sortList);
        IPage<BGoods> goodsPage = page(goodsQueryPage, queryWrapper);

        List<BGoods> goodsList = goodsPage.getRecords();
        List<BGoodsDTO> goodsDTOList = Collections.emptyList();

        if (null != goodsList && !goodsList.isEmpty())
        {
            goodsDTOList = getGoodsDTOList(goodsList);
        }

        Map<String, Object> resultMap = new HashMap<>(0);
        resultMap.put("totalNum", goodsPage.getTotal());
        resultMap.put("totalPage", goodsPage.getPages());
        resultMap.put("list", goodsDTOList);
        return new ResponseHashResult(ResponseStatus.responseSuccess(), resultMap);
    }

    /**
     * 查询商品详情
     *
     * @param goodsId
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ResponseHashResult getGoodsInfoById(String goodsId)
    {
        if (StringUtils.isEmpty(goodsId))
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }
        BGoodsDTO goodsInfo = bGoodsMapper.selectGoodsInfoById(goodsId);
        Map<String, Object> resultMap = new HashMap<>(1);
        resultMap.put("list", Collections.singletonList(goodsInfo));
        return new ResponseHashResult(ResponseStatus.responseSuccess(), resultMap);
    }

    /**
     * 修改商品
     *
     * @param goods
     * @return
     */
    @Override
    public ResponseStringResult updateGoods(BGoods goods)
    {
        if (goods == null || goods.getGoodsId() == null)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        if (goods.getGoodsShelfLife() != null && goods.getGoodsShelfLife().longValue() < 0)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.GOODS_SHELF_LIFE_INVALID));
        }

        if (goods.getGoodsWeight() != null && goods.getGoodsWeight().doubleValue() < 0)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.GOODS_WEIGHT_INVALID));
        }

        if (goods.getTaxRate() != null && goods.getTaxRate().doubleValue() < 0)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.GOODS_TAX_RATE_INVALID));
        }

        goods.setMtime(DateUtil.getChinaTime()).setMuser(CurrentUtil.getCurrentUserId());

        if (update(goods, new LambdaQueryWrapper<BGoods>().eq(BGoods::getGoodsId, goods.getGoodsId())))
        {
            //修改成功
            return new ResponseStringResult(ResponseStatus.responseSuccess());
        }
        else
        {
            return new ResponseStringResult(ResponseStatus.responseFail());
        }
    }

    /**
     * 新增商品
     *
     * @param goods
     * @return
     */
    @Override
    public ResponseStringResult addGoods(BGoods goods)
    {
        ResponseStringResult responseResult = new ResponseStringResult();

        if (!validateGoods(goods, responseResult))
        {
            return responseResult;
        }

        Date currentTime = DateUtil.getChinaTime();
        goods.setMtime(currentTime).setCtime(currentTime);
        goods.setCuser(CurrentUtil.getCurrentUserId()).setMuser(CurrentUtil.getCurrentUserId());

        if (save(goods))
        {
            responseResult.setStatus(ResponseStatus.responseSuccess());
        }
        else
        {
            responseResult.setStatus(ResponseStatus.responseFail());
        }

        return responseResult;

    }

    /**
     * 下架商品
     *
     * @param goods
     * @return
     */
    @Override
    public ResponseStringResult removeGoodsById(BGoods goods)
    {
        if (goods == null || goods.getPkno() == null || goods.getGoodsState() == null)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }
        String goodsState = goods.getGoodsState();
        if (!BosConstants.IS_ENABLED_YES.equals(goodsState) && !BosConstants.IS_ENABLED_NO.equals(goodsState))
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.GOODS_STATE_INVALID));
        }
        BGoods goodsDeleted = new BGoods();
        goodsDeleted.setPkno(goods.getPkno());
        goodsDeleted.setGoodsState(goodsState);
        goodsDeleted.setMtime(DateUtil.getChinaTime()).setMuser(CurrentUtil.getCurrentUserId());
        if (updateById(goodsDeleted))
        {
            return new ResponseStringResult(ResponseStatus.responseSuccess());
        }
        else
        {
            return new ResponseStringResult(ResponseStatus.responseFail());
        }
    }

    /**
     * 导出商品
     *
     * @param commonQuery
     * @return
     */
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    @Override
    public ResponseHashResult exportGoods(CommonQuery commonQuery)
    {
        if (commonQuery == null)
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        String condList = commonQuery.getCondList();
        String sortList = commonQuery.getSortList();
        if (!StringUtils.isEmpty(sortList))
        {
            sortList = sortList.replace("firstCategoryName", "firstCategoryId")
                    .replace("secondCategoryName", "secondCategoryId")
                    .replace("thirdCategoryName", "thirdCategoryId")
                    .replace("goodsStateInfo", "goodsState");
        }
        QueryWrapper<BGoods> queryWrapper = QueryWrapperUtil.fillQueryWrapper(condList, sortList);
        List<BGoods> goodsList = list(queryWrapper);

        List<BGoodsDTO> goodsDTOList = Collections.emptyList();

        if (null != goodsList && !goodsList.isEmpty())
        {
            goodsDTOList = getGoodsDTOList(goodsList);
        }

        ResponseHashResult responseResult = fileCommonService.exportFile(BGoodsDTO.class, goodsDTOList, GoodsConstants.EXPORT_GOODS_FILE_NAME, fileAccessUrlPrefix, localPathPrefix);
        return responseResult;
    }

    /**
     * 封装商品详细信息
     * @param goodsList
     * @return
     */
    private List<BGoodsDTO> getGoodsDTOList(List<BGoods> goodsList)
    {
        List<Map<String, String>> categoryNames = bGoodsMapper.selectGoodsType(goodsList);
        Map<String, String> categoryMap = categoryNames.stream().collect(Collectors.toMap(p -> p.get("categoryId"), p -> p.get("categoryName")));
        List<BGoodsDTO> goodsDTOList = new ArrayList<>(0);
        for (BGoods goods : goodsList)
        {
            BGoodsDTO goodsDTO = new BGoodsDTO();
            BeanUtils.copyProperties(goods, goodsDTO);
            if (!StringUtils.isEmpty(goodsDTO.getGoodsImg()))
            {
                goodsDTO.setGoodsImg(goodsImgAccessUrlPrefix + goodsDTO.getGoodsImg());
            }
            if (!StringUtils.isEmpty(goodsDTO.getFirstCategoryId()))
            {
                goodsDTO.setFirstCategoryName(categoryMap.get(goodsDTO.getFirstCategoryId()));
            }
            if (!StringUtils.isEmpty(goodsDTO.getSecondCategoryId()))
            {
                goodsDTO.setSecondCategoryName(categoryMap.get(goodsDTO.getSecondCategoryId()));
            }
            if (!StringUtils.isEmpty(goodsDTO.getThirdCategoryId()))
            {
                goodsDTO.setThirdCategoryName(categoryMap.get(goodsDTO.getThirdCategoryId()));
            }
            goodsDTO.setGoodsStateInfo(BosConstants.IS_ENABLED_YES.equals(goodsDTO.getGoodsState()) ? GoodsConstants.GOODS_STATE_ENABLE : GoodsConstants.GOODS_STATE_DISABLE);
            goodsDTO.setIsKeyGoodsInfo(BosConstants.IS_KEY_GOODS_YES.equals(goodsDTO.getIsKeyGoods()) ? GoodsConstants.IS_KEY_GOODS_YES : GoodsConstants.IS_KEY_GOODS_NO);
            goodsDTOList.add(goodsDTO);
        }
        return goodsDTOList;
    }

    /**
     * 导入excel
     *
     * @param file
     * @return
     */
    @Override
    public ResponseStringResult importExcel(MultipartFile file)
    {
        if (file == null)
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.ILLEGALINPUT));
        }

        String fileName = file.getOriginalFilename();

        if (!checkIfExcel(fileName))
        {
            return new ResponseStringResult(new ResponseStatus( 10001, "不是excel文件！",false));
        }

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);

        List<BGoods> goodsList = null;

        try (InputStream inputStream = file.getInputStream())
        {
            goodsList = ExcelImportUtil.importExcel(inputStream, BGoods.class, params);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsServiceImpl.importExcel()] failed to import excel！", e);
            return new ResponseStringResult(new ResponseStatus(ResultCode.FAIL));
        }

        if (goodsList == null || goodsList.isEmpty())
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.FILE_INVALID_DATE));
        }

        //excel中goodsId集合，去掉goodsId为空和重复的
        Set<String> goodsIdSet = goodsList.stream().filter(goods -> !StringUtils.isEmpty(goods.getGoodsId())).map(BGoods::getGoodsId).collect(Collectors.toSet());
        Map<String, Long> goodsId2PknoInDbMap = Collections.emptyMap();

        if (goodsIdSet != null && !goodsIdSet.isEmpty())
        {
            //查询数据库中所有对应excel中skuId的商品
            List<BGoods> goodsListInDb = list(new LambdaQueryWrapper<BGoods>().in(BGoods::getGoodsId, goodsIdSet));
            if (goodsListInDb != null)
            {
                //将数据库中的商品数据List转换成map集合，key：skuId，value：pkno
                goodsId2PknoInDbMap = goodsListInDb.stream().collect(Collectors.toMap(BGoods::getGoodsId, BGoods::getPkno, (v1,v2)->v1));
            }
        }

        //新增商品集合
        Map<String, BGoods> saveMap = new HashMap<>(0);
        //修改商品集合
        Map<String, BGoods> updateMap = new HashMap<>(0);

        Date recordTime = DateUtil.getChinaTime();
        String userId = CurrentUtil.getCurrentUserId();

        for (BGoods goods : goodsList)
        {
            if (StringUtils.isEmpty(goods.getGoodsId()))
            {
                //goodsId为空的数据不予处理
                continue;
            }
            goods.setCuser(userId).setCtime(recordTime)
                    .setMuser(userId).setMtime(recordTime);
            String goodsId = goods.getGoodsId();
            if (goodsId2PknoInDbMap.get(goodsId) == null)
            {
                //数据库中不存在当前goodsId对应的数据，新增操作
                saveMap.put(goodsId, goods);
            }
            else
            {
                //数据库中存在当前goodsId对应的数据，修改操作
                goods.setPkno(goodsId2PknoInDbMap.get(goodsId));
                updateMap.put(goodsId, goods);
            }
        }

        if (saveBatch(saveMap.values()) && (updateMap.isEmpty() || updateBatchById(updateMap.values())))
        {
            log.info("[info: BShopGoodsServiceImpl.importExcel()], 导入商品成功，新增{}条数据，修改{}条数据", saveMap.size(), updateMap.size());
            return new ResponseStringResult(new ResponseStatus(ResultCode.SUCCESS));
        }
        else
        {
            return new ResponseStringResult(new ResponseStatus(ResultCode.FAIL));
        }
    }

    /**
     * 查询未设置价格的商品
     *
     * @param commonQuery
     * @return
     */
    @Override
    public ResponseHashResult getGoodsWithoutPriceByCond(CommonQuery commonQuery)
    {
        if (commonQuery == null)
        {
            return new ResponseHashResult(new ResponseStatus(ResultCode.INVALID_PARAM));
        }

        commonQuery.setDefaultPageParam(commonQuery);
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
        List<BGoodsDTO> goodsInfoList = bGoodsMapper.selectGoodsInfoWithoutPrice(queryMap);
        PageInfo<BGoodsDTO> pageInfo = new PageInfo<>(goodsInfoList);

        Map<String, Object> resultMap = new HashMap<>(5);
        resultMap.put("totalNum", pageInfo.getTotal());
        resultMap.put("totalPage", pageInfo.getPages());
        resultMap.put("pageNo", pageInfo.getPageNum());
        resultMap.put("pageSize", pageInfo.getPageSize());
        resultMap.put("list", goodsInfoList);

        return new ResponseHashResult(new ResponseStatus(ResultCode.SUCCESS), resultMap);
    }

    /**
     * 校验商品信息
     * @param goods
     * @return
     */
    private boolean validateGoods(BGoods goods, ResponseResult responseResult)
    {
        /*
        校验条件
         */
        if (goods == null
                || StringUtils.isEmpty(goods.getGoodsId())
                || StringUtils.isEmpty(goods.getGoodsName())
                || StringUtils.isEmpty(goods.getGoodsBarCode())
        )
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.INVALID_PARAM));
            return false;
        }

        if (StringUtils.isEmpty(goods.getFirstCategoryId())
                || StringUtils.isEmpty(goods.getSecondCategoryId())
                || StringUtils.isEmpty(goods.getThirdCategoryId()))
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.GOODS_TYPE_IS_NULL));
            return false;
        }

        if (goods.getGoodsShelfLife() != null && goods.getGoodsShelfLife().longValue() < 0)
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.GOODS_SHELF_LIFE_INVALID));
            return false;
        }

        if (goods.getGoodsWeight() != null && goods.getGoodsWeight().doubleValue() < 0)
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.GOODS_WEIGHT_INVALID));
            return false;
        }

        if (goods.getTaxRate() != null && goods.getTaxRate().doubleValue() < 0)
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.GOODS_TAX_RATE_INVALID));
            return false;
        }

        //查询商品ID是否已添加过
        LambdaQueryWrapper<BGoods> lqr = new LambdaQueryWrapper<BGoods>();
        lqr.eq(BGoods::getGoodsId, goods.getGoodsId());
        BGoods dbGoods = getOne(lqr);

        if (dbGoods != null)
        {
            responseResult.setStatus(new ResponseStatus(ResultCode.GOODS_ID_EXIST));
            return false;
        }

        //查询商品类型是否存在
        LambdaQueryWrapper<BGoodsType> typeQueryWrapper = new LambdaQueryWrapper<>();
        typeQueryWrapper.in(BGoodsType::getCategoryId, Arrays.asList(goods.getFirstCategoryId(), goods.getSecondCategoryId(), goods.getThirdCategoryId()));
        typeQueryWrapper.eq(BGoodsType::getEnabled, BosConstants.IS_ENABLED_YES);
        List<BGoodsType> goodsTypeList = bGoodsTypeMapper.selectList(typeQueryWrapper);
        if (goodsTypeList == null || goodsTypeList.isEmpty() || goodsTypeList.size() < 3)
        {
            responseResult.setStatus(new ResponseStatus((ResultCode.GOODS_TYPE_NOT_EXIST)));
            return false;
        }
        return true;
    }

    /**
     * 判断是否excel文件
     * @param fileName
     * @return
     */
    private boolean checkIfExcel(String fileName)
    {
        return fileName.endsWith(GoodsConstants.EXCEL_TYPE_XLS_SUFFIX) || fileName.endsWith(GoodsConstants.EXCEL_TYPE_XLSX_SUFFIX);
    }
}
