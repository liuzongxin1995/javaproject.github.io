package com.liu.javaproject.model.common;

public enum ResultCode {

    /*-------------------公用返回状态-------------------*/
    SUCCESS(true,10000,"操作成功！"),
    FAIL(false,10001,"操作失败!"),
    ILLEGALINPUT(false,10015,"非法输入!"),

    /*-------------------认证部分-------------------*/
    INVALID_PARAM(false,30001,"非法参数！"),
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！"),

    /*-------------------文件部分-------------------*/
    FILE_UPLOAD_FAILED(false,30003,"文件上传失败！"),
    FILE_CANNOTCONTAINCOMMA(false,30004,"文件名中不能含有英文逗号！"),
    FILE_EXECUTABLEFILE_NOTALLOW(false,30005,"禁止上传可执行类文件！"),
    FILE_TYPE_NOT_SUPPORT (false,30006,"文件类型不支持！"),
    FILE_IS_TOO_LARGE (false,30007,"文件过大，不能超过200K！"),
    FILE_FORMAT_NOTALLOW(false,30008,"上传文件格式不允许！"),
    FILE_INVALID_DATE(false,30010,"导入文件没有有效数据！"),
    FILE_ERROR_DATE(false,30011,"导入文件存在错误数据！"),
    FILE_TYPE_NOT_EXCEL(false, 10001, "不是excel文件！"),

    /*-------------------供应商部分-------------------*/
    SUPPLIER_ID_EXIST(false,10103,"供应商ID已存在！"),
    SUPPLIER_NOT_EXIST(false,10103,"供应商不存在！"),

    /*-------------------商品部分-------------------*/
    GOODS_ID_EXIST(false,10103,"商品ID已存在！"),
    GOODS_ID_NOT_EXIST(false,10103,"商品ID不存在！"),
    GOODS_NAME_EXIST(false,10105,"商品名称已存在！"),
    GOODS_BARCODE_EXIST(false,10106,"商品条形码已存在！"),
    GOODS_SKU_EXIST(false,10106,"商品sku编码已存在！"),

    GOODS_ID_DELETE(false,10103,"商品ID不可用！"),
    GOODS_STATE_DELETE(false,10103,"商品已存在，商品状态不可用！"),
    GOODS_NOT_EXIST(false,10103,"商品不存在！"),

    GOODS_CAT_ID_NOT_EXIST(false,10107,"商品分类编码(大类)ID不存在！"),
    GOODS_SEC_CAT_ID_NOT_EXIST(false,10108,"二级分类编码(中类)ID不存在！"),
    GOODS_THR_CAT_ID_NOT_EXIST(false,10109,"三级分类编码(小类)ID不存在！"),

    GOODS_TYPE_ID_EXIST(false,10110,"商品类别ID已存在！"),
    GOODS_TYPE_ID_ERROR(false,10110,"请按规定格式填写商品分类编码(大类编码为2位数字，中类编码为4位数字，小类编码为6位数字)！"),
    GOODS_PARENT_TYPE_NOT_EXIST(false,10110,"上一级商品分类不存在！"),
    GOODS_PARENT_TYPE_ERROR(false,10110,"请按规定格式填写商品分类编码(大类：XX,中类：大类+XX,小类：中类+XX)！"),
    GOODS_TYPE_NAME_EXIST(false,10111,"商品分类名称已存在！"),
    GOODS_TYPE_NOT_EXIST(false,10112,"商品类别ID不存在！"),
    GOODS_TYPE_IS_USED(false,10113,"商品类别ID使用中，不可删除！"),
    GOODS_TYPE_DELETE(false,10122,"商品类别ID不可用！"),
    GOODS_PRICE_ACTIVE_TIME_INVALID(false,10123,"商品价格的有效时间错误！"),
    GOODS_PRICE_INVALID(false,10124,"商品价格非法！"),
    GOODS_TYPE_IS_NULL(false,10125,"商品类型不能为空！"),
    GOODS_SHELF_LIFE_INVALID(false,10127,"商品保质期非法！"),
    GOODS_WEIGHT_INVALID(false,10128,"商品重量非法！"),
    GOODS_TAX_RATE_INVALID(false,10129,"商品税率非法！"),
    GOODS_STATE_INVALID(false,10126,"商品状态非法！");

    boolean success;
    int code;
    String message;

    ResultCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public boolean success() {
        return success;
    }

    public int code() {
        return code;
    }

    public String message() {
        return message;
    }

}
