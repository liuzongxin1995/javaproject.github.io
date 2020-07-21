package com.liu.javaproject.model.enums;

/**
 * 文件类型枚举
 */
public enum FileTypeEnum {
    // 头像
    AVATAR("1", "avatar"),

    //图标
    ICON("2", "icon"),

    //广告
    AD("3", "ad"),

    //商品图标
    GOODSICON("4", "goodsIcon"),

    //巡检设备图片
    DEVICEINSPECTION("5", "deviceinspection"),

    //维修申请附件
    MAINTAINAPPLY("6","maintain"),

    //员工证件
    EMPLOYEEPAPERS("7","employeePapers"),

    //员工证件
    LEADPICTURE("8","leadpicture"),

    //员工证件
    COMPLAINT("9","complaint"),

    //盘存附件
    MONTN_SETTLEMENT("10","month_settlement"),

    //其他
    OTHERS("other","others");

    private String key;

    private String value;

    FileTypeEnum(String key, String value) {
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
            for (FileTypeEnum ase : FileTypeEnum.values()) {
                if (ase.getKey().equals(key)) {
                    return ase.getValue();
                }
            }
        }
        return null;
    }
}
