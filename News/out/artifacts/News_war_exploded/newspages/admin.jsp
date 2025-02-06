<%@ page language="java" import="java.util.*,java.sql.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- 导入顶部页面元素 -->
<jsp:include page="console_element/top.jsp"/>
<script language="javascript">
    /**
     * 在删除新闻前弹出确认对话框
     * @returns {boolean} 如果用户确认删除，则返回true；否则返回false
     */
    function clickdel() {
        return confirm("删除请点击确认");
    }
</script>

<div id="main">
    <!-- 导入左侧页面元素 -->
    <jsp:include page="console_element/left.jsp"/>

    <div id="opt_area">
        <ul class="classlist">
            <!-- 遍历新闻列表 -->
            <c:forEach var="topic" items="${requestScope.topicList}">
                <li class='space'><strong>${topic.tname}</strong></li>
                <c:forEach var="news" items="${topic.newsList}">
                    <li>${news.ntitle}<span> 作者：${news.nauthor}&#160;&#160;&#160;&#160; <a
                            href="${pageContext.request.contextPath}/AdminServlet?action=toUpNews&nid=${news.nid}">修改</a> &#160;&#160;&#160;&#160; <a
                            href="${pageContext.request.contextPath}/AdminServlet?action=doDeleteNews&nid=${news.nid}" onclick='return clickdel()'>删除</a>
                    </span>
                    </li>
                </c:forEach>
            </c:forEach>
            <!-- 遍历新闻列表结束 -->

            <!-- 分页处理开始 -->
            <p align="right">
                当前页数:[${pageBean.currentPage}/${pageBean.totalPage} ]&nbsp;&nbsp;
                <a href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&currentPage=1">首页</a><a
                    href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&currentPage=${pageBean.prevPage}">&nbsp;&nbsp;上一页</a><a
                    href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&currentPage=${pageBean.nextPage}">&nbsp;&nbsp;下一页</a> <a
                    href="${pageContext.request.contextPath}/AdminServlet?action=adminIndex&currentPage=${pageBean.totalPage}">&nbsp;&nbsp;末页</a>
            </p>
        </ul>
    </div>


</div>
<!-- 导入底部页面元素 -->
<jsp:include page="console_element/bottom.jsp"/>
