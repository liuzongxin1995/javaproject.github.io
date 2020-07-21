package com.liu.javaproject.model.enums;

/**
 * 库存操作枚举
 */
public enum StockRecordTypeEnum {

    // 新增 - 1
    STOCK_ADD(1, "新增"),
    // 修改 - 2
    STOCK_UPDATE(2, "修改"),
    // 删除 - 3
    STOCK_DELETE(3, "删除"),

    // 出库 - 11
    STOCK_OUT(11, "出库"),
    // 入库 - 12
    STOCK_IN(12, "入库");

    private Integer key;
    private String value;

    StockRecordTypeEnum(Integer key, String value) {
        this.key = key;
        this.setValue(value);
    }

    public Integer getKey() {
        return key;
    }

    public void setKey(Integer key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static String getValueByKey(String key) {
        if (key != null) {
            for (StockRecordTypeEnum ase : StockRecordTypeEnum.values()) {
                if (ase.getKey().equals(key)) {
                    return ase.getValue();
                }
            }
        }
        return null;
    }
}
