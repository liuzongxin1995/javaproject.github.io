package com.liu.javaproject.controller;


import com.liu.javaproject.model.BGoods;
import com.liu.javaproject.model.common.*;
import com.liu.javaproject.model.common.ResponseStatus;
import com.liu.javaproject.service.IBShopGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author lijiaxin
 * @since 2020-05-21
 */
@Slf4j
@RestController
@RequestMapping("/goods")
@Api(value = "门店可经营商品操作接口", description = "门店可经营商品操作接口")
public class BShopGoodsController
{

    @Autowired
    private IBShopGoodsService goodsService;

    @PostMapping("/getAllGoods")
    @ApiOperation(value = "查询商品", notes = "lijiaxin")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    )
    public ResponseHashResult getGoodsByCond(@RequestBody CommonQuery commonQuery)
    {
        try
        {
            return goodsService.getGoodsByCond(commonQuery);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.getGoodsByCond()], 商品查询失败", e);
            return new ResponseHashResult(ResponseStatus.responseFail());
        }
    }

    @GetMapping("/getGoodsById")
    @ApiOperation(value = "查询商品详情", notes = "lijiaxin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string"),
            @ApiImplicitParam(name = "goodsId", value = "商品编码", required = true, paramType = "query", dataType = "String")
    })
    public ResponseHashResult getGoodsInfoById(@RequestParam("goodsId") String goodsId)
    {
        try
        {
            return goodsService.getGoodsInfoById(goodsId);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.getGoodsInfoById()], 商品详细信息查询失败", e);
            return new ResponseHashResult(ResponseStatus.responseFail());
        }
    }

    @PostMapping("/updateGoods")
    @ApiOperation(value = "修改商品", notes = "lijiaxin")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    )
    public ResponseStringResult updateGoods(@RequestBody BGoods goods)
    {
        try
        {
            return goodsService.updateGoods(goods);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.updateGoods()], 商品更新失败", e);
            return new ResponseStringResult(ResponseStatus.responseFail());
        }
    }

    @PostMapping("/addGoods")
    @ApiOperation(value = "新增商品", notes = "lijiaxin")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    )
    public ResponseStringResult addGoods(@RequestBody BGoods goods)
    {
        try
        {
            return goodsService.addGoods(goods);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.addGoods()], 商品添加失败", e);
            return new ResponseStringResult(ResponseStatus.responseFail());
        }
    }

    @PostMapping("/removeGoodsById")
    @ApiOperation(value = "下架商品", notes = "lijiaxin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    })
    public ResponseStringResult removeGoodsById(@RequestBody BGoods goods)
    {
        try
        {
            return goodsService.removeGoodsById(goods);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.deleteGoods()], 商品下架失败", e);
            return new ResponseStringResult(ResponseStatus.responseFail());
        }
    }

    @PostMapping("/exportAllGoods")
    @ApiOperation(value = "导出商品", notes = "lijiaxin")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    )
    public ResponseHashResult exportGoods(@RequestBody CommonQuery commonQuery)
    {
        try
        {
            return goodsService.exportGoods(commonQuery);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.exportAllGoods()], 导出商品失败", e);
            return new ResponseHashResult(new ResponseStatus(ResultCode.FAIL));
        }
    }

    @PostMapping("/importExcel")
    @ApiOperation(value = "excel导入", notes = "lijiaxin")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "需要导入的excel文件", required = true, paramType = "form", dataType = "file"),
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    })
    public ResponseStringResult importExcel(@RequestPart(value = "file") MultipartFile file)
    {
        try
        {
            return goodsService.importExcel(file);
        }
        catch (Exception e)
        {
            log.error("[error: BShopGoodsController.importExcel()], 导入商品失败", e);
            return new ResponseStringResult(new ResponseStatus(ResultCode.FAIL));
        }
    }

    @PostMapping("/getAllGoodsWithoutPrice")
    @ApiOperation(value = "查询所有未设置价格的商品", notes = "lijiaxin")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "Token", value = "token",  required = true, paramType = "header", dataType = "string")
    )
    public ResponseHashResult getGoodsWithoutPriceByCond(@RequestBody CommonQuery commonQuery)
    {
        try
        {
            return goodsService.getGoodsWithoutPriceByCond(commonQuery);
        }
        catch (Exception e)
        {
            log.error("[error: BGoodsPriceController.getGoodsWithoutPriceByCond()], 查询所有未设置价格的商品失败", e);
            return new ResponseHashResult(ResponseStatus.responseFail());
        }
    }
}
