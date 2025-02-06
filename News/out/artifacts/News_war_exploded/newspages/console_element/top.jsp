<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>新闻发布系统管理后台</title>
    <link href="${pageContext.request.contextPath}/CSS/admin.css" rel="stylesheet" type="text/css"/>
    <style>

    </style>
</head>
<body>
<header>
    <div id="welcome">欢迎使用新闻管理系统！</div>
</header>
<nav id="admin_bar">
    <div id="status">
        管理员： ${sessionScope.loginOk.uname} &#160;&#160;&#160;&#160;
        <a href="${pageContext.request.contextPath}/UserServlet?action=doLogout">注销</a>&nbsp;&#160;
        <a href="${pageContext.request.contextPath}/NewsServlet?action=news">返回</a>
    </div>
    <div id="messages">
        <c:if test="${requestScope.addOK > 0}">
            <h4>添加成功</h4>
        </c:if>
        <c:if test="${requestScope.del > 0}">
            <h4>删除成功</h4>
        </c:if>
        <c:if test="${requestScope.addError == 0}">
            <h4>标题不能重复</h4>
        </c:if>
    </div>
    <div id="channel"></div>
</nav>