package com.news.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.news.dao.UserDao;
import com.news.entity.User;

/**
 * 用户Servlet实现类，负责处理用户相关的操作，包括登录和登出。
 */
@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 处理HTTP请求，包括GET和POST方法。
	 * 该方法首先设置响应内容类型和请求编码，然后根据action参数确定具体的操作。
	 *
	 * @param request  HttpServletRequest对象，包含客户端请求信息
	 * @param response HttpServletResponse对象，用于向客户端发送响应
	 * @throws ServletException 如果Servlet遇到困难
	 * @throws IOException      如果发生输入输出异常
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");

		if ("doLogin".equals(action)) {
			doLogin(request, response);
		} else if ("doLogout".equals(action)) {
			doLogout(request, response);
		}
	}

	/**
	 * 处理用户登出操作。
	 * 该方法会销毁当前用户的会话，并重定向到新闻页面。
	 *
	 * @param request  HttpServletRequest对象，包含客户端请求信息
	 * @param response HttpServletResponse对象，用于向客户端发送响应
	 * @throws IOException 如果发生输入输出异常
	 */
	private void doLogout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/NewsServlet?action=news");
	}

	/**
	 * 处理用户登录操作。
	 * 该方法会验证用户名和密码，如果验证成功则将用户信息存储在会话中并重定向到新闻页面，否则将登录失败信息返回给客户端。
	 *
	 * @param request  HttpServletRequest对象，包含客户端请求信息
	 * @param response HttpServletResponse对象，用于向客户端发送响应
	 * @throws IOException      如果发生输入输出异常
	 * @throws ServletException 如果Servlet遇到困难
	 */
	private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		UserDao userDao = new UserDao();

		String username = request.getParameter("uname");
		String password = request.getParameter("upwd");

		User userLogin = userDao.login(username, password);

		if (userLogin != null) {
			HttpSession session = request.getSession(); // 获取session
			session.setAttribute("loginOk", userLogin); // 存储session
			response.sendRedirect(request.getContextPath() + "/NewsServlet?action=news");
		} else {
			request.setAttribute("loginInfo", "登录失败");
			request.getRequestDispatcher("/NewsServlet?action=news").forward(request, response);
		}
	}
}
