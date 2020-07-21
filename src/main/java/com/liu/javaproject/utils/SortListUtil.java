package com.liu.javaproject.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SortListUtil {

    /**
     * 将排序条件的字段转为数据库中的字段
     *
     * @param sortList
     * @return
     */
    public static String parseSortList(String sortList) {

        List<String> list = new ArrayList<>();
        String sort = "";

        if (StringUtils.isNotEmpty(sortList)) {
            // 解析排序参数字符串 [["menuId","ASC"],["UnitType","DESC"]]
            JSONArray sortArray = JSONObject.parseArray(sortList);
            for (int i = 0; i < sortArray.size(); i++) {
                // 将驼峰属性转换为数据库下划线形式
                String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ((JSONArray) sortArray.get(i)).get(0).toString());
                Object order = ((JSONArray) sortArray.get(i)).get(1);
                if (order.equals("ASC") || order.equals("asc") || order.equals("DESC") || order.equals("desc")) {
                    sort = field + " " + order;
                    list.add(sort);
                }
            }
            sortList = Joiner.on(",").join(list.toArray());
        }

        return sortList;

    }

    /**
     * 将排序条件的字段转为数据库中的字段
     *
     * @param sortList, suffix（后缀，用于修正排序参数 isDeleteText - isDelete）
     * @return
     */
    public static String parseSortList(String sortList, String suffix) {

        List<String> list = new ArrayList<>();
        String sort = "";

        if (StringUtils.isNotEmpty(sortList)) {
            // 解析排序参数字符串 [["menuId","ASC"],["UnitType","DESC"]]
            JSONArray sortArray = JSONObject.parseArray(sortList);
            for (int i = 0; i < sortArray.size(); i++) {
                String oldField = ((JSONArray) sortArray.get(i)).get(0).toString();
                if (oldField.indexOf(suffix) != -1) {
                    oldField = oldField.substring(0, oldField.length() - suffix.length());
                }
                // 将驼峰属性转换为数据库下划线形式
                String field = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, oldField);
                Object order = ((JSONArray) sortArray.get(i)).get(1);
                if (order.equals("ASC") || order.equals("asc") || order.equals("DESC") || order.equals("desc")) {
                    sort = field + " " + order;
                    list.add(sort);
                }
            }
            sortList = Joiner.on(",").join(list.toArray());
        }

        return sortList;

    }

}
