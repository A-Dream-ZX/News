package com.news.entity;
/*
 * 新闻 实体类
 * @author  
 */

public class News {
	/**
	 * 文章ID
	 * 用于唯一标识一篇文章
	 */
	private int nid;

	/**
	 * 文章类型ID
	 * 用于标识文章属于哪个类型或分类
	 */
	private int ntid;

	/**
	 * 文章标题
	 * 用于显示文章的标题
	 */
	private String ntitle;

	/**
	 * 文章作者
	 * 用于显示文章的作者信息
	 */
	private String nauthor;

	/**
	 * 文章创建日期
	 * 用于记录文章的创建时间
	 */
	private String ncreatedate;

	/**
	 * 文章图片路径
	 * 用于存储文章相关的图片路径
	 */
	private String npicpath;

	/**
	 * 文章内容
	 * 用于存储文章的正文内容
	 */
	private String ncontent;

	/**
	 * 文章修改日期
	 * 用于记录文章的最近修改时间
	 */
	private String nmodifydate;

	/**
	 * 文章摘要
	 * 用于存储文章的简短摘要或概述
	 */
	private String nsummary;
	public int getNid() {
		return nid;
	}

	public void setNid(int nid) {
		this.nid = nid;
	}

	public int getNtid() {
		return ntid;
	}

	public void setNtid(int ntid) {
		this.ntid = ntid;
	}

	public String getNtitle() {
		return ntitle;
	}

	public void setNtitle(String ntitle) {
		this.ntitle = ntitle;
	}

	public String getNauthor() {
		return nauthor;
	}

	public void setNauthor(String nauthor) {
		this.nauthor = nauthor;
	}

	public String getNcreatedate() {
		return ncreatedate;
	}

	public void setNcreatedate(String ncreatedate) {
		this.ncreatedate = ncreatedate;
	}

	public String getNpicpath() {
		return npicpath;
	}

	public void setNpicpath(String npicpath) {
		this.npicpath = npicpath;
	}

	public String getNcontent() {
		return ncontent;
	}

	public void setNcontent(String ncontent) {
		this.ncontent = ncontent;
	}

	public String getNmodifydate() {
		return nmodifydate;
	}

	public void setNmodifydate(String nmodifydate) {
		this.nmodifydate = nmodifydate;
	}

	public String getNsummary() {
		return nsummary;
	}

	public void setNsummary(String nsummary) {
		this.nsummary = nsummary;
	}
}
