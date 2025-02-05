package com.news.entity;

/*
 * 评论  实体类
 * @author 
 **/
public class Comment {
	
	/**
	 * 文章的唯一标识符
	 * 用于唯一标识一篇文章
	 */
	private int cid;

	/**
	 * 文章分类的标识符
	 * 用于标识文章所属的分类
	 */
	private int cnid;

	/**
	 * 文章的内容
	 * 存储文章的正文部分
	 */
	private String ccontent;

	/**
	 * 文章的创建日期
	 * 记录文章创建的时间
	 */
	private String cdate;

	/**
	 * 文章的IP地址
	 * 记录发表文章时的IP地址
	 */
	private String cip;

	/**
	 * 文章的作者
	 * 记录文章的作者信息
	 */
	private String cauthor;
	
	public int getCid() {
		return cid;
	}

	public void setCid(int cid) {
		this.cid = cid;
	}

	public int getCnid() {
		return cnid;
	}

	public void setCnid(int cnid) {
		this.cnid = cnid;
	}

	public String getCcontent() {
		return ccontent;
	}

	public void setCcontent(String ccontent) {
		this.ccontent = ccontent;
	}

	public String getCdate() {
		return cdate;
	}

	public void setCdate(String cdate) {
		this.cdate = cdate;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getCauthor() {
		return cauthor;
	}

	public void setCauthor(String cauthor) {
		this.cauthor = cauthor;
	}

	public Comment( int cnid, String ccontent, String cip, String cauthor) {
		super();
		this.cnid = cnid;
		this.ccontent = ccontent;
		this.cip = cip;
		this.cauthor = cauthor;
	}
	public Comment() {
		super();
	}

}
