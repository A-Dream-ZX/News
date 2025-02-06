package com.news.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * 文件上传Servlet
 * 处理客户端上传的文件，并保存到服务器的指定目录
 */
@WebServlet("/UploadFileServlet")
public class UploadFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * 文件上传处理方法
     *
     * @param request  HttpServletRequest对象，用于获取上传的文件和请求信息
     * @param response HttpServletResponse对象，用于向客户端发送响应
     * @throws ServletException 如果Servlet操作出错
     * @throws IOException      如果IO操作出错
     */
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // 创建DiskFileItemFactory工厂
        DiskFileItemFactory dfiFactory = new DiskFileItemFactory();
        // 创建ServletFileUpload对象，用于解析请求中的文件
        ServletFileUpload upload = new ServletFileUpload(dfiFactory);
        // 设置文件上传的参数
        upload.setHeaderEncoding("UTF-8");
        // 设置文件上传的最大值
        upload.setFileSizeMax(1024 * 1024 * 100);

        try {
            // 解析请求，获取上传的文件项
            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    // 获取upload在tomcat下的绝对路径
                    String uploadFilePath = request.getServletContext().getRealPath("upload");
                    System.out.println(uploadFilePath);

                    // 检查并创建目标目录
                    File uploadDir = new File(uploadFilePath);
                    if (!uploadDir.exists()) {
                        uploadDir.mkdirs();
                    }

                    // 生成唯一文件名，防止文件重名
                    String fileName = generateUniqueFileName(uploadFilePath, item.getName());
                    File targetFile = new File(uploadDir, fileName);

                    // 写入文件到指定路径
                    item.write(targetFile);
                    // 将文件名保存到session，以供后续使用
                    request.getSession().setAttribute("uploadFileTest", fileName);
                    System.out.println("upload OK");
                }
            }
        } catch (FileUploadException e) {
            response.getWriter().println("文件上传失败: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("文件上传失败: " + e.getMessage());
            e.printStackTrace();
        }

        // 重定向到显示上传文件的页面
        response.sendRedirect("showUnloadFile.jsp");
    }

    /**
     * 生成唯一的文件名，以防止文件重名
     *
     * @param directory    文件上传目录的绝对路径
     * @param originalName 原始文件名
     * @return 唯一的文件名
     */
    private String generateUniqueFileName(String directory, String originalName) {
        File file = new File(directory, originalName);
        int count = 1;
        while (file.exists()) {
            String baseName = originalName.substring(0, originalName.lastIndexOf('.'));
            String extension = originalName.substring(originalName.lastIndexOf('.'));
            file = new File(directory, baseName + "_" + count++ + extension);
        }
        return file.getName();
    }
}
