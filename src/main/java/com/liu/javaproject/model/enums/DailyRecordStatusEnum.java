package com.liu.javaproject.model.enums;

/**
 * 日结状态
 */
public enum DailyRecordStatusEnum
{
    SUCCESS("0"),
    RUNNING("1"),
    FAIL("9");

    private String state;

    DailyRecordStatusEnum(String state)
    {
        this.state = state;
    }

    public String getState()
    {
        return state;
    }
}
