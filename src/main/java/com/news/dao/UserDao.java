package com.news.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.news.entity.User;
import com.news.utils.JDBCUtil;

/**
 * UserDao类负责与数据库交互，处理用户相关的数据操作
 */
public class UserDao {
    // JDBCUtil实例用于数据库连接和资源关闭
	private final JDBCUtil jdbcUtil = new JDBCUtil();

	/**
	 * 登录方法，根据用户名和密码查询用户信息
	 *
	 * @param username 用户名，用于查询用户信息
	 * @param password 密码，用于验证用户身份
	 * @return 通过返回user对象来表示登录是否成功，如果用户不存在或密码错误，则返回null
	 */
	public User login(String username,String password) {
		// SQL查询语句，用于根据用户名和密码查询用户信息
		String sql = "SELECT * from news_users where uname=? and upwd=?";
		// 获取数据库连接
		Connection con = jdbcUtil.getConnection();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user=null;
		try {
			// 准备SQL语句
			stmt = con.prepareStatement(sql);
			// 设置SQL语句参数
			stmt.setString(1, username);
			stmt.setString(2, password);
			// 执行查询
			rs = stmt.executeQuery();
			// 处理查询结果，如果找到匹配的用户信息，则创建User对象并填充信息
			if (rs.next()) {
				user=new User();
				user.setUid(rs.getInt("usid"));
				user.setUname(rs.getString("uname"));
				user.setUpwd(rs.getString("upwd"));
			}

		} catch (SQLException e) {
			// 打印异常信息
			e.printStackTrace();
		} finally {
			// 关闭数据库连接和资源
			jdbcUtil.closeDB(con, stmt, rs);
		}
		// 返回用户对象
		return user;
	}
}
