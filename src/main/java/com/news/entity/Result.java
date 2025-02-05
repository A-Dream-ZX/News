package com.news.entity;

import java.util.List;

/**
 * 结果类，用于封装操作的结果。
 * 包含一个描述结果的消息和一个与操作结果相关的数据列表。
 */
public class Result {
    /**
     * 消息字段，用于存储描述操作结果的消息。
     */
    private String msg;

    /**
     * 数据字段，用于存储与操作结果相关的数据列表。
     */
    private List datas;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List getDatas() {
        return datas;
    }

    public void setDatas(List datas) {
        this.datas = datas;
    }

    public Result(String msg, List datas) {

        this.msg = msg;
        this.datas = datas;
    }

    public Result() {

    }


}
