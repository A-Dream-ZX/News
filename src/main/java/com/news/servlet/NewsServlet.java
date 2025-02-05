package com.news.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.news.dao.CommentDao;
import com.news.dao.NewsDao;
import com.news.dao.TopicDao;
import com.news.entity.Comment;
import com.news.entity.News;
import com.news.entity.NewsDetail;
import com.news.entity.Result;
import com.news.entity.Topic;

/**
 * 新闻的servlet Servlet implementation class NewsServlet
 */
@WebServlet("/NewsServlet")
public class NewsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    // 每页显示的新闻条数
    private static final int pageSize = 8;
    // 当前新闻类型ID
    private static int ntid = -1;

    /**
     * 处理新闻相关的HTTP请求
     *
     * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse
     * response) doGet(){} doPost(){doGet();}
     */
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        // 根据action参数决定具体操作
        if ("news".equals(action)) {
            printNewsToIndex(request, response);
        } else if ("detail".equals(action)) {
            doNewsDetail(request, response);
        } else if ("doComment".equals(action)) {
            doComment(request, response);
        } else if ("doCommentAJAX".equals(action)) {
            doCommentAJAX(request, response);
        } else {
            // 如果action参数无效，返回错误
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action parameter");
        }
    }

    /**
     * 处理AJAX评论提交请求
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @throws ServletException 如果Servlet操作失败
     * @throws IOException      如果IO操作失败
     */
    private void doCommentAJAX(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("ctid") != null) {
            PrintWriter out = response.getWriter();
            Result result = new Result();
            // 拿到评论数据
            int cnid;
            try {
                cnid = Integer.parseInt(request.getParameter("ctid"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid ctid parameter: " + request.getParameter("ctid"));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ctid parameter");
                return;
            }
            String cauthor = request.getParameter("cauthor");
            String cip = getIp(request);
            String ccontent = request.getParameter("ccontent");

            CommentDao commentDao = new CommentDao();
            Comment comment = new Comment(cnid, ccontent, cip, cauthor);
            int n = commentDao.addComment(comment);
            List<Comment> comlist = commentDao.findCommentByNews(cnid);
            result.setDatas(comlist);
            result.setMsg(n + "");
            String json = JSON.toJSONString(result);

            out.print(json);
            out.flush();
            out.close();
        } else {
            response.sendRedirect(request.getContextPath() + "/NewsServlet?action=news");
        }
    }

    /**
     * 处理评论提交请求，并重定向到新闻详情页面
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @throws ServletException 如果Servlet操作失败
     * @throws IOException      如果IO操作失败
     */
    protected void doComment(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("ctid") != null) {
            CommentDao commentDao = new CommentDao();
            // 拿到评论数据
            int cnid;
            try {
                cnid = Integer.parseInt(request.getParameter("ctid"));
            } catch (NumberFormatException e) {
                System.err.println("Invalid ctid parameter: " + request.getParameter("ctid"));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ctid parameter");
                return;
            }
            String cauthor = request.getParameter("cauthor");
            String cip = getIp(request);
            String ccontent = request.getParameter("ccontent");
            Comment comment = new Comment(cnid, ccontent, cip, cauthor);
            // 添加数据
            commentDao.addComment(comment);
            // 上下文
            request.getRequestDispatcher("/NewsServlet?action=detail&nid=" + cnid).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/NewsServlet?action=news");
        }
    }


    /**
     * 处理新闻详情请求，包括获取新闻详情和相关评论
     *
     * @param request  用于获取请求参数和设置属性的HttpServletRequest对象
     * @param response 用于发送响应的HttpServletResponse对象
     * @throws ServletException 如果Servlet操作失败
     * @throws IOException      如果输入/输出操作失败
     */
    protected void doNewsDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 检查nid参数是否存在
        if (request.getParameter("nid") != null) {
            int nid;
            try {
                // 将nid参数转换为整数
                nid = Integer.parseInt(request.getParameter("nid"));
            } catch (NumberFormatException e) {
                // 如果转换失败，打印错误信息并发送错误响应
                System.err.println("Invalid nid parameter: " + request.getParameter("nid"));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid nid parameter");
                return;
            }
            // 创建NewsDao和CommentDao实例
            NewsDao newsDao = new NewsDao();
            CommentDao commentDao = new CommentDao();
            // 查找新闻详情
            NewsDetail newsDetail = newsDao.findNewsById(nid);
            // 查找评论所有信息
            List<Comment> commentsList = commentDao.findCommentByNews(nid);
            // 查找全部news
            List<News> listAllNews = newsDao.findAllNews();
            // 将新闻详情、评论列表和全部新闻设置为请求属性
            request.setAttribute("newsDetail", newsDetail);
            request.setAttribute("commentsList", commentsList);
            request.setAttribute("listAllNews", listAllNews);
            // 转发请求到新闻阅读页面
            request.getRequestDispatcher("/news_read_ajax.jsp").forward(request, response);
        } else {
            // 如果nid参数不存在，重定向到新闻列表页面
            response.sendRedirect(request.getContextPath() + "/NewsServlet?action=news");
        }
    }

// 打印news表信息到首页

    /**
     * 查询并打印news表的信息到首页，支持分页和分类查询
     *
     * @param request  用于获取请求参数和设置属性的HttpServletRequest对象
     * @param response 用于发送响应的HttpServletResponse对象
     * @throws ServletException 如果Servlet操作失败
     * @throws IOException      如果输入/输出操作失败
     */
    protected void printNewsToIndex(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 设置响应内容类型
        response.setContentType("text/html;charset=UTF-8");
        // 初始化变量
        int sizeNews = -1;
        TopicDao topicDao = new TopicDao();
        NewsDao newsDao = new NewsDao();
        List<News> listNews = null;

        // 查找全部news
        List<News> listAllNews = newsDao.findAllNews();
        // 查询topic表的全部数据
        List<Topic> listTopic = topicDao.findAllTopic();

        // 默认显示第1页（首页）
        int pageNo = 1;
        // 获取页面的当前页
        if (request.getParameter("pageNo") != null) {
            try {
                pageNo = Integer.parseInt(request.getParameter("pageNo"));
            } catch (NumberFormatException e) {
                // 如果转换失败，打印错误信息并发送错误响应
                System.err.println("Invalid pageNo parameter: " + request.getParameter("pageNo"));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid pageNo parameter");
                return;
            }
        }

        // 分类查询news数据
        int ntid = -1;
        if (request.getParameter("ntid") != null) {
            try {
                ntid = Integer.parseInt(request.getParameter("ntid"));
            } catch (NumberFormatException e) {
                // 如果转换失败，打印错误信息并发送错误响应
                System.err.println("Invalid ntid parameter: " + request.getParameter("ntid"));
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ntid parameter");
                return;
            }
        }
        // 判断是全部新闻信息分页，还是新闻信息分类后的分页
        String tid = request.getParameter("tid");
        if ("ntid".equals(tid) && ntid != -1) {
            if (pageNo > 0) {
                // 根据分类查询新闻数据
                listNews = newsDao.findClassNews(ntid, pageNo, pageSize);
                // 查询分类新闻的总条数
                sizeNews = newsDao.findClassNewsSize(ntid);
            } else {
                sizeNews = 0;
            }
        } else {
            ntid = -1;
            // 从当前页的首行查询pageSize条数据
            listNews = newsDao.findAllByPage(pageNo, pageSize);
            // 查询表的总条数
            sizeNews = newsDao.findNewsSize();
        }

        // 总页数（尾页）
        int allPages = (sizeNews % pageSize == 0) ? (sizeNews / pageSize) : (sizeNews / pageSize) + 1;

        // 上一页和下一页
        int prev = pageNo;
        int next = pageNo;
        // 判断是否有上下页和首页
        if (allPages <= 0) {
            prev = 0;
            next = 0;
            pageNo = 0;
        } else if (allPages == 1) {
            prev = 1;
            next = 1;
        } else if (allPages > 1 && pageNo >= 1 && pageNo < allPages) {
            if (pageNo == 1) {
                prev = 1;
            } else {
                prev--;
            }
            next++;
        } else if (pageNo >= allPages) {
            prev--;
            next = allPages;
        }

        // 将新闻列表、分页信息、主题列表和全部新闻设置为请求属性
        request.setAttribute("listNews", listNews);
        request.setAttribute("pageNo", pageNo);
        request.setAttribute("allPages", allPages);
        request.setAttribute("prev", prev);
        request.setAttribute("next", next);
        request.setAttribute("listTopic", listTopic);
        request.setAttribute("listAllNews", listAllNews);
        // 转发请求到首页
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

// 获取request的ip地址

    /**
     * 获取请求的IP地址，考虑了代理情况
     *
     * @param request 用于获取请求头信息的HttpServletRequest对象
     * @return 返回请求者的IP地址
     */
    public static String getIp(HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        String forwarded = request.getHeader("X-Forwarded-For");
        String realIp = request.getHeader("X-Real-IP");

        String ip = null;
        if (realIp == null) {
            if (forwarded == null) {
                ip = remoteAddr;
            } else {
                ip = remoteAddr + "/" + forwarded;
            }
        } else {
            if (realIp.equals(forwarded)) {
                ip = realIp;
            } else {
                ip = realIp + "/" + forwarded.replaceAll(", " + realIp, "");
            }
        }
        return ip;
    }


}
