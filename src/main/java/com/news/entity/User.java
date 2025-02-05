package com.news.entity;
/*
 * 用户 实体类
 * @author
 */
public class User { 
	
	/**
	 * 用户ID，唯一标识一个用户
	 */
	private int uid;

	/**
	 * 用户名，用于登录和显示
	 */
	private String uname;

	/**
	 * 用户密码，用于登录验证
	 */
	private String upwd;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public void setUpwd(String upwd) {
		this.upwd = upwd;
	}

	public String getUname() {
		return uname;
	}

	public String getUpwd() {
		return upwd;
	}
}
