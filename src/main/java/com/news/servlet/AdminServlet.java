package com.news.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.news.dao.NewsDao;
import com.news.entity.News;
import com.news.entity.NewsDetail;
import com.news.entity.Result;
import com.news.entity.Topic;
import com.news.utils.PageBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.alibaba.fastjson.JSON;

import com.news.dao.CommentDao;
import com.news.dao.TopicDao;
import com.news.entity.Comment;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/AdminServlet")
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 处理AdminServlet的所有请求
	 *
	 * @param request  HttpServletRequest对象，用于获取请求信息
	 * @param response HttpServletResponse对象，用于发送响应信息
	 * @throws ServletException 如果Servlet操作失败
	 * @throws IOException      如果发生I/O错误
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		String action = request.getParameter("action");

		// 根据请求参数中的action值，决定执行相应的操作
		if ("adminIndex".equals(action)) {
			doAdminLogin(request, response);
		} else if ("addTopic".equals(action)) {
			doAddTopic(request, response);
		} else if ("toAddNews".equals(action)) {
			toAddNews(request, response);
		} else if ("doAddNews".equals(action)) {
			doAddNews(request, response);
		} else if ("toUpNews".equals(action)) {
			toUpAddNews(request, response);
		} else if ("doUpNews".equals(action)) {
			doUpNews(request, response);
		} else if ("doDeleteNews".equals(action)) {
			doDeleteNews(request, response);
		} else if ("toUpTopic".equals(action)) {
			toUpTopic(request, response);
		} else if ("toModify".equals(action)) {
			toModify(request, response);
		} else if ("doUpTopic".equals(action)) {
			doUpTopic(request, response);
		} else if ("doDeleteTopic".equals(action)) {
			doDeleteTopic(request, response);
		} else if ("doDeleteComment".equals(action)) {
			doDeleteComment(request, response);
		}
	}

	/**
	 * 处理删除评论的操作
	 *
	 * @param request  HttpServletRequest对象，用于获取请求信息
	 * @param response HttpServletResponse对象，用于发送响应信息
	 * @throws IOException      如果发生I/O错误
	 * @throws ServletException 如果Servlet操作失败
	 */
	private void doDeleteComment(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if (request.getParameter("newsId")!=null) {
			Result result=new Result();
			CommentDao commentDao=new CommentDao();
			PrintWriter out=response.getWriter();

			int cid = Integer.parseInt(request.getParameter("comId"));
			int nid = Integer.parseInt(request.getParameter("newsId"));

			int row=0;

			row=commentDao.deleteComment(cid);
			List<Comment> commentList = commentDao.findCommentByNews(nid);

			result.setDatas(commentList);
			result.setMsg(row+"");

			String json=JSON.toJSONString(result);
			out.print(json);
			out.flush();
			out.close();
		}
	}

	/**
	 * 处理删除主题的操作
	 *
	 * @param request  HttpServletRequest对象，用于获取请求信息
	 * @param response HttpServletResponse对象，用于发送响应信息
	 * @throws ServletException 如果Servlet操作失败
	 * @throws IOException      如果发生I/O错误
	 */
	private void doDeleteTopic(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		TopicDao topicDao = new TopicDao();
		int del = 0;
		if (request.getParameter("tid") != null) {
			int tid = Integer.parseInt(request.getParameter("tid"));
			del = topicDao.deleteTopic(tid);

		}
		request.setAttribute("del", del);
		request.getRequestDispatcher("/AdminServlet?action=toUpTopic").forward(request, response);

	}


	/**
	 * 执行主题更新操作
	 *
	 * @param request 用于获取请求信息的HttpServletRequest对象
	 * @param response 用于发送响应信息的HttpServletResponse对象
	 * @throws IOException 如果发生I/O错误
	 */
	private void doUpTopic(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    TopicDao topicDao = new TopicDao();
	    int row = 0;
	    if (request.getParameterValues("topic") != null) {
	        String[] topics = request.getParameterValues("topic");
	        Topic topic = new Topic(Integer.parseInt(topics[0]), topics[1]);
	        row = topicDao.updateTopic(topic);
	    }
	    response.sendRedirect(request.getContextPath() + "/AdminServlet?action=toUpTopic");
	}

	/**
	 * 跳转到主题修改页面
	 *
	 * @param request 用于获取请求信息的HttpServletRequest对象
	 * @param response 用于发送响应信息的HttpServletResponse对象
	 * @throws ServletException 如果Servlet操作出错
	 * @throws IOException 如果发生I/O错误
	 */
	private void toModify(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    if (request.getParameter("tid") != null) {
	        int tid = Integer.parseInt(request.getParameter("tid"));
	        String tname = request.getParameter("tname");
	        request.setAttribute("tid", tid);
	        request.setAttribute("tname", tname);
	        request.getRequestDispatcher("/newspages/topic_modify.jsp").forward(request, response);
	    } else {
	        response.sendRedirect(request.getContextPath() + "/AdminServlet?action=adminIndex");
	    }
	}

	/**
	 * 跳转到主题列表页面
	 *
	 * @param request 用于获取请求信息的HttpServletRequest对象
	 * @param response 用于发送响应信息的HttpServletResponse对象
	 * @throws IOException 如果发生I/O错误
	 * @throws ServletException 如果Servlet操作出错
	 */
	private void toUpTopic(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, ServletException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    TopicDao topicDao = new TopicDao();
	    List<Topic> topicList = topicDao.findAllTopic();
	    request.setAttribute("topicList", topicList);
	    request.getRequestDispatcher("/newspages/topic_list.jsp").forward(request, response);
	}

	/**
	 * 执行删除新闻操作
	 *
	 * @param request 用于获取请求信息的HttpServletRequest对象
	 * @param response 用于发送响应信息的HttpServletResponse对象
	 * @throws IOException 如果发生I/O错误
	 */
	private void doDeleteNews(HttpServletRequest request, HttpServletResponse response) throws IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    int row = 0;
	    if (request.getParameter("nid") != null) {
	        int nid = Integer.parseInt(request.getParameter("nid"));
	        NewsDao newsDao = new NewsDao();
	        row = newsDao.deleteNews(nid);
	    }
	    response.sendRedirect(request.getContextPath() + "/AdminServlet?action=adminIndex");
	}

	/**
	 * 修改新闻
	 *
	 * @param request 用于获取请求信息的HttpServletRequest对象
	 * @param response 用于发送响应信息的HttpServletResponse对象
	 * @throws ServletException 如果Servlet操作出错
	 * @throws IOException 如果发生I/O错误
	 */
	private void doUpNews(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html;charset=UTF-8");
	    int row = 0;
	    DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
	    ServletFileUpload upload = new ServletFileUpload(dfiFactory);
	    // 设置文件上传的参数
	    upload.setHeaderEncoding("UTF-8");
	    // 设置文件上传的最大值
	    upload.setFileSizeMax(1024 * 1024 * 100);
	    try {
	        List<FileItem> list = upload.parseRequest(request);
	        News news = new News();
	        Iterator<FileItem> iterator = list.iterator();
	        while (iterator.hasNext()) {
	            FileItem item = iterator.next();
	            if (item.isFormField()) { // 判断当前FileForm的类别：1.普通文本框 2.file文件域
	                String value = item.getString("UTF-8");
	                if (item.getFieldName().equals("nid")) {
	                    news.setNid(Integer.parseInt(value));
	                } else if (item.getFieldName().equals("ntid")) {
	                    news.setNtid(Integer.parseInt(value));
	                } else if (item.getFieldName().equals("ntitle")) {
	                    news.setNtitle(value);
	                } else if (item.getFieldName().equals("nauthor")) {
	                    news.setNauthor(value);
	                } else if (item.getFieldName().equals("nsummary")) {
	                    news.setNsummary(value);
	                } else if (item.getFieldName().equals("ncontent")) {
	                    news.setNcontent(value);
	                } else if (item.getFieldName().equals("npicpath")) {
	                    news.setNpicpath(value);
	                }
	            } else {
	                if (!"".equals(item.getName())) {
	                    // 获取upload在tomcat下的绝对路径
	                    String uploadfilePath = request.getServletContext().getRealPath("upload");
	                    // 创建目录
	                    File uploadDir = new File(uploadfilePath);
	                    if (!uploadDir.exists()) {
	                        uploadDir.mkdirs();
	                    }

	                    // 获取原来图片的文件路径
	                    File oldFile = new File(uploadfilePath, news.getNpicpath());
	                    if (news.getNpicpath() != null && !"".equals(news.getNpicpath())) {
	                        if (oldFile.exists()) {
	                            oldFile.delete();
	                        }
	                    }

	                    // 获取upload的文件目录下的指定文件名的文件
	                    File targetFile = new File(uploadfilePath, item.getName());
	                    item.write(targetFile);
	                    news.setNpicpath(item.getName());
	                    System.out.println("上传完成");
	                }
	            }
	        }
	        // 添加到数据库
	        NewsDao newsDao = new NewsDao();
	        row = newsDao.updateNews(news);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    response.sendRedirect(request.getContextPath() + "/AdminServlet?action=adminIndex");
	}

	/**
	 * 处理新闻更新请求
	 *
	 * 此方法负责响应带有更新新闻需求的请求它从请求中提取新闻ID，
	 * 根据ID从数据库中获取新闻详细信息和相关评论，然后将这些信息
	 * 存储在请求属性中，并将请求转发到新闻修改页面如果请求中没有
	 * 提供新闻ID，则重定向到管理员索引页面
	 *
	 * @param request 用于获取请求信息和参数的HttpServletRequest对象
	 * @param response 用于向客户端发送响应的HttpServletResponse对象
	 * @throws ServletException 如果Servlet操作失败
	 * @throws IOException 如果输入/输出操作失败
	 */
	private void toUpAddNews(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		if (request.getParameter("nid") != null) {
			TopicDao topicDao = new TopicDao();
			NewsDao newsDao = new NewsDao();
			CommentDao commentDao = new CommentDao();
			// 查全部主题题
			List<Topic> topicList = topicDao.findAllTopic();
			// 当前主题的id
			int nid = Integer.parseInt(request.getParameter("nid"));
			// 通过nid查询新闻信息 和 评论
			NewsDetail news = newsDao.findNewsById(nid);
			List<Comment> commentList = commentDao.findCommentByNews(nid);

			request.setAttribute("news", news);
			request.setAttribute("commentList", commentList);
			request.setAttribute("topicList", topicList);
			request.getRequestDispatcher("/newspages/news_modify.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/AdminServlet?action=adminIndex");
		}
	}


/**
 * 处理添加新闻请求
 *
 * @param request 用于获取请求信息的HttpServletRequest对象
 * @param response 用于发送响应信息的HttpServletResponse对象
 * @throws IOException 如果发生I/O异常
 * @throws ServletException 如果Servlet遇到异常
 */
private void doAddNews(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    int n = 0;
    DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
    ServletFileUpload upload = new ServletFileUpload(dfiFactory);
    // 设置文件上传的参数
    upload.setHeaderEncoding("UTF-8");
    // 设置文件上传的最大值
    upload.setFileSizeMax(1024 * 1024 * 100);

    try {
        List<FileItem> list = upload.parseRequest(request);
        News news = new News();
        Iterator<FileItem> iterator = list.iterator();
        while (iterator.hasNext()) {
            FileItem item = iterator.next();
            if (item.isFormField()) { // 判断当前FileForm的类别：1.普通文本框 2.file文件域
                String value = item.getString("UTF-8");
                if (item.getFieldName().equals("ntid")) {
                    news.setNtid(Integer.parseInt(value));
                } else if (item.getFieldName().equals("ntitle")) {
                    news.setNtitle(value);
                } else if (item.getFieldName().equals("nauthor")) {
                    news.setNauthor(value);
                } else if (item.getFieldName().equals("nsummary")) {
                    news.setNsummary(value);
                } else if (item.getFieldName().equals("ncontent")) {
                    news.setNcontent(value);
                }
            } else {
                // 获取upload在tomcat下的绝对路径
                String uploadfilePath = request.getServletContext().getRealPath("upload");
                // 获取upload的文件目录下的指定文件名的文件
                File targetFile = new File(uploadfilePath, item.getName());
                if (targetFile.toString().equals(uploadfilePath)) {
                    System.out.println("没有上传文件");
                } else {
                    System.out.println(uploadfilePath);
                    item.write(targetFile);
                    news.setNpicpath(item.getName());
                    System.out.println("upload OK");
                }
            }
        }
        // 添加到数据库
        NewsDao newsDao = new NewsDao();
        n = newsDao.addNews(news);
        request.setAttribute("addOK", n);
    } catch (Exception e) {
        e.printStackTrace();
    }
    request.getRequestDispatcher("/AdminServlet?action=adminIndex").forward(request, response);
}

/**
 * 加载添加新闻页面及其所需数据
 *
 * @param request 用于获取请求信息的HttpServletRequest对象
 * @param response 用于发送响应信息的HttpServletResponse对象
 * @throws ServletException 如果Servlet遇到异常
 * @throws IOException 如果发生I/O异常
 */
private void toAddNews(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    TopicDao topicDao = new TopicDao();
    List<Topic> topicList = topicDao.findAllTopic();
    request.setAttribute("topicList", topicList);
    request.getRequestDispatcher("/newspages/news_add.jsp").forward(request, response);
}

/**
 * 处理添加主题请求
 *
 * @param request 用于获取请求信息的HttpServletRequest对象
 * @param response 用于发送响应信息的HttpServletResponse对象
 * @throws IOException 如果发生I/O异常
 * @throws ServletException 如果Servlet遇到异常
 */
private void doAddTopic(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    int n = 0;
    if (request.getParameterValues("tname") != null) {
        TopicDao topicDao = new TopicDao();
        Topic topic = new Topic();
        List<Topic> topics = topicDao.findAllTopic();
        String[] tnames = request.getParameterValues("tname");
        String tname = tnames[0];
        for (int i = 0; i < topics.size(); i++) {
            if (tname.equals(topics.get(i).getTname())) {
                request.setAttribute("addError", n);
                request.getRequestDispatcher("/newspages/topic_add.jsp").forward(request, response);
                return;
            }
        }
        topic.setTname(tname);
        n = topicDao.addTopic(topic);
    }
    request.setAttribute("addOK", n);
    request.getRequestDispatcher("/newspages/topic_add.jsp").forward(request, response);
}

/**
 * 处理管理员登录请求并加载管理员页面
 *
 * @param request 用于获取请求信息的HttpServletRequest对象
 * @param response 用于发送响应信息的HttpServletResponse对象
 * @throws IOException 如果发生I/O异常
 * @throws ServletException 如果Servlet遇到异常
 */
private void doAdminLogin(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/html;charset=UTF-8");
    TopicDao topicDao = new TopicDao();
    PageBean pageBean = new PageBean((request.getParameter("currentPage") == null) ? 1
            : (Integer.parseInt(request.getParameter("currentPage"))));
    //
    List<Topic> topicList = topicDao.findClassTopicAndNews(pageBean.getCurrentPage(), pageBean.getEveryPage());
    // 总记录数
    int totalCount = topicDao.getTopicCount();
    pageBean.setTotalCount(totalCount);

    request.setAttribute("topicList", topicList);
    request.setAttribute("pageBean", pageBean);
    request.getRequestDispatcher("/newspages/admin.jsp").forward(request, response);
}

}
