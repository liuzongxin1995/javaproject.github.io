package com.liu.javaproject.utils;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigDecimalUtil {

    private static final String AMOUNT_REGEX = "^\\d+\\.\\d{2}$";
    private static final String AMOUNT_REGEX2 = "^\\d+$";
    private static final String AMOUNT_REGEX3 = "^\\d+\\.\\d$";

    /**
     * 处理重量类型大数据
     *
     * @param num
     * @return
     */
    public static BigDecimal getVolumn(BigDecimal num) {
        BigDecimal num1 = new BigDecimal(0.000);
        if (num == null) {
            return num1.setScale(3, BigDecimal.ROUND_HALF_UP);
        } else {
            return num.setScale(3, BigDecimal.ROUND_HALF_UP);
        }
    }

    /**
     * 处理金额类型大数据
     *
     * @param num
     * @return
     */
    public static BigDecimal getMoney(BigDecimal num) {
        if (num == null) {
            return BigDecimal.valueOf(0.00).setScale(2);
        } else {
            return num.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }


    /**
     * Object转BigDecimal类型-王雷-2018年5月14日09:56:26
     *
     * @param value 要转的object类型
     * @return 转成的BigDecimal类型数据
     */
    public static BigDecimal getBigDecimal(Object value) {
        BigDecimal ret = null;
        if (value != null) {
            if (value instanceof BigDecimal) {
                ret = (BigDecimal) value;
            } else if (value instanceof String) {
                ret = new BigDecimal((String) value);
            } else if (value instanceof BigInteger) {
                ret = new BigDecimal((BigInteger) value);
            } else if (value instanceof Number) {
                ret = new BigDecimal(((Number) value).doubleValue());
            } else {
                throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass() + " into a BigDecimal.");
            }
        }
        return ret;
    }

    public static boolean isIllegalAmount(BigDecimal amount){
        return amount == null || amount.compareTo(BigDecimal.ZERO) <= 0;
    }

    public static boolean isIllegalMoney(BigDecimal money){
        if(isIllegalAmount(money)){
            return true;
        }
        String amountStr = money.toString();
        if (amountStr.matches(AMOUNT_REGEX2)) {
            amountStr += ".00";
        } else if (amountStr.matches(AMOUNT_REGEX3)) {
            amountStr += "0";
        }
        return !amountStr.matches(AMOUNT_REGEX);
    }

}
