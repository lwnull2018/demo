package com.jack.chen.telegram.bot.model.enumeration;

/**
 * 活动状态枚举类型
 * Created by lwnull2018@gmail.com ON 2020/10/20.
 */
public enum StatusEnum {

    DOING(0, "进行中"),
    DONE(1, "已完成"),
    UNKNOW(-1, "未知状态类型");

    private int val;
    private String desc;

    StatusEnum() {

    }

    StatusEnum(int val, String desc) {
        this.val = val;
        this.desc = desc;
    }

    public int getVal() {
        return val;
    }

    public String getDesc() {
        return desc;
    }
}
