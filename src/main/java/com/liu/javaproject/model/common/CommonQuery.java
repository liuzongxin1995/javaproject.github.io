package com.liu.javaproject.model.common;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.liu.javaproject.utils.SortListUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Data
public class CommonQuery {

    @ApiModelProperty("第几页")
    private Integer pageNo;

    @ApiModelProperty("每页几条")
    private Integer pageSize;

    @ApiModelProperty("条件列表")
    private String condList;

    @ApiModelProperty("排序列表")
    private String sortList;

    public void setDefaultPageParam(CommonQuery query){
        if(query != null){
            if (query.getPageNo() == null || query.getPageNo() < 1) {
                this.pageNo = 1;
            }
            if (query.getPageSize() == null || query.getPageSize() < 1) {
                this.pageSize = 10;
            }

        }
    }

    public static void initCommonQuery(CommonQuery query) {
        if (query == null) {
            query = new CommonQuery();
        }
        Integer pageNo = query.getPageNo();
        if (pageNo == null || pageNo < 1) {
            query.setPageNo(1);
        }
        Integer pageSize = query.getPageSize();
        if (pageSize == null || pageSize < 1) {
            query.setPageSize(10);
        }
    }

    public Map<String, Object> conditionToMap() {
        HashMap<String, Object> map = Maps.newHashMap();
        if (!StringUtils.isEmpty(condList)) {
            map = JSON.parseObject(condList, HashMap.class);
        }
        if (!StringUtils.isEmpty(sortList)) {
            sortList = SortListUtil.parseSortList(sortList);
        }
        map.put("sortList", sortList);
        return map;
    }


}
