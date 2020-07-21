package com.liu.javaproject.model.enums;

/**
 * 删除标识枚举 未删除-0  已删除-1
 */
public enum DeleteStatusEnum {

    // 未删除 - 0
    IS_NOT_DELETED("0", "未删除"),
    // 已删除 - 1
    IS_DELETED("1", "已删除");


    private String key;
    private String value;

    DeleteStatusEnum(String key, String value) {
        this.key = key;
        this.setValue(value);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
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
            for (DeleteStatusEnum ase : DeleteStatusEnum.values()) {
                if (ase.getKey().equals(key)) {
                    return ase.getValue();
                }
            }
        }
        return null;
    }
}
