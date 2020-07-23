package com.liu.javaproject.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author mingc
 * @date 2020/5/22 11:43
 */
@Data
@ApiModel(value = "商品类别信息")
public class GoodsTypeDTO {
	/**
	 * 商品分类编号
	 */
	@ApiModelProperty("商品分类编号")
	@Pattern(regexp = "^\\d{2}|\\d{4}|\\d{6}$",message = "请按规定格式填写商品分类编码(大类编码为2位数字，中类编码为4位数字，小类编码为6位数字)！")
	private String categoryId;

	/**
	 * 商品分类名称
	 */
	@ApiModelProperty("商品分类名称")
	@Pattern(regexp = "^[a-zA-Z0-9_\\u4e00-\\u9fa5]*$",message = "商品名称格式错误，中文字母数字下划线。")
	private String categoryName;

	/**
	 *商品分类 1-一级分类（大类）|2-二级分类（中类）|3-三级分类（小类）
	 */
	@ApiModelProperty("商品分类")
	@Pattern(regexp = "^[1|2|3]{1}$",message = "商品分类格式错误！1-一级分类（大类）|2-二级分类（中类）|3-三级分类（小类）")
	private String category;

	/**
	 * 上级商品分类编号
	 */
	@ApiModelProperty(value = "上级商品分类编号")
	@Pattern(regexp = "^\\s{0}|\\d{2}|\\d{4}",message = "请按规定格式填写商品分类编码(大类：XX,中类：大类+XX,小类：中类+XX)！")
	private String parentCategoryId;

}
