package com.liu.javaproject.model.enums;

/**
 * @author wantianyu
 * @date 2020/6/11 15:26
 * 门店订货相关枚举
 */
public enum  ReplenishGoodsEnum {

    STATION_INFO("1001","测试站");

    /**
     * 参数编号
     */
    private String sCode;

    /**
     * 参数名称
     */
    private String sName;

    ReplenishGoodsEnum(String sCode, String sName){
        this.sCode = sCode;
        this.sName = sName;
    }

    public String getsCode() {
        return sCode;
    }

    public String getsName() {
        return sName;
    }
}
