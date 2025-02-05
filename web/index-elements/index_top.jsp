<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">



<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="pragma" content="no-cache"/>
    <meta http-equiv="cache-control" content="no-cache"/>
    <title>新闻中心</title>
    <link href="CSS/main.css" rel="stylesheet" type="text/css"/>


</head>
<body onload="focusOnLogin()">
<div class="navbar">
    <div class="container">
        <div class="navbar-header">
            <a
                    href="${pageContext.request.contextPath}/NewsServlet?action=news">新闻网站</a>
        </div>
        <div class="hidden-xs">
                <c:if test="${sessionScope.loginOk==null}">
                    <form action="${pageContext.request.contextPath}/UserServlet?action=doLogin" method="post" onsubmit="return check()">
                        <label for="uname">账号：</label>
                        <input type="text" id="uname" name="uname" value="" class="login_input"/>
                        <label for="upwd">密码：</label>
                        <input type="password" id="upwd" name="upwd" value="" class="login_input"/>
                        <input type="submit" class="login_sub" value="登录"/>
                        <label id="error" style="color:red; margin-left: 10px;">${loginInfo}</label>
                    </form>
                </c:if>
                <c:if test="${sessionScope.loginOk!=null}">
                    <span>欢迎您： ${sessionScope.loginOk.uname}</span>
                    <a href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex">控制台</a>
                    <a href="${pageContext.request.contextPath}//UserServlet?action=doLogout">退出</a>
                </c:if>
        </div>
    </div>
</div>
</body>
</html>


