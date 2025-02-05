package com.news.entity;

import java.util.ArrayList;
import java.util.List;

/*
 * Topic类代表一个主题实体
 * 它包含了主题的标识符和名称，以及与该主题相关的新闻列表
 * @author [作者名]
 */
public class Topic {
    // 主题标识符
    private int tid;
    // 主题名称
    private String tname;

    // 与当前主题相关的新闻列表
    private List<News> newsList = new ArrayList<News>();


    public Topic(int tid, String tname) {
        this.tid = tid;
        this.tname = tname;
    }

    public Topic() {
        super();
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public Topic(int tid, String tname, List<News> newsList) {
        super();
        this.tid = tid;
        this.tname = tname;
        this.newsList = newsList;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

}
