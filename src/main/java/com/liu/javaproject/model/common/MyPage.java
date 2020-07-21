package com.liu.javaproject.model.common;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wantianyu
 * @date 2020/6/5 15:18
 * 分页对象
 */
@Data
public class MyPage<E> {
    /**
     * 总条数
     */
    private long totalNum;

    /**
     * 总条数
     */
    private int totalPage;

    /**
     * 本页数据
     */
    private List<E> list;

    public MyPage(PageInfo<E> pageInfo){
        this.totalNum = pageInfo.getTotal();
        this.totalPage = pageInfo.getPages();
        this.list = pageInfo.getList();
    }
}
