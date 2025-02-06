package com.news.entity;

/**
 * NewsDetail类继承自News类，用于表示新闻的详细信息
 * 它添加了一个新的属性来存储新闻的来源名称
 */
public class NewsDetail extends News {
    /**
     * tname字段存储新闻来源的名称
     */
    private String tname;


    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

}
