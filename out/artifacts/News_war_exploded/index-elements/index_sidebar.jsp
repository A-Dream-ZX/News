<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<%-- 左侧列表 --%>
<div class="sidebar">
<%--	<h1> <img src="Images/title_1.gif" alt="国内新闻" /> </h1>--%>
	<h1>国内新闻</h1>
	<div class="side_list">
		<ul>
			<!-- 国内新闻 -->
			<c:forEach var="news" items="${listAllNews}">
				<c:if test="${news.ntid == 1}">
					<li>
						<a href="NewsServlet?action=detail&nid=<c:out value='${news.nid}'/>">
							<c:choose>
								<c:when test="${fn:length(news.ntitle) > 11}">
									<c:out value="${fn:substring(news.ntitle, 0, 11)}..."/>
								</c:when>
								<c:otherwise>
									<c:out value="${news.ntitle}"/>
								</c:otherwise>
							</c:choose>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

<%--	<h1> <img src="Images/title_2.gif" alt="国际新闻" /> </h1>--%>
	<h1>国际新闻</h1>
	<div class="side_list">
		<ul>
			<!-- 国际新闻 -->
			<c:forEach var="news" items="${listAllNews}">
				<c:if test="${news.ntid == 2}">
					<li>
						<a href="NewsServlet?action=detail&nid=<c:out value='${news.nid}'/>">
							<c:choose>
								<c:when test="${fn:length(news.ntitle) > 11}">
									<c:out value="${fn:substring(news.ntitle, 0, 11)}..."/>
								</c:when>
								<c:otherwise>
									<c:out value="${news.ntitle}"/>
								</c:otherwise>
							</c:choose>
						</a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
	</div>

<%--	<h1> <img src="Images/title_3.gif" alt="娱乐新闻" /> </h1>--%>
<%--	<div class="side_list">--%>
<%--		<ul>--%>
<%--			<!-- 娱乐新闻 -->--%>
<%--			<c:forEach var="news" items="${listAllNews}">--%>
<%--				<c:if test="${news.ntid == 5}">--%>
<%--					<li>--%>
<%--						<a href="NewsServlet?action=detail&nid=<c:out value='${news.nid}'/>">--%>
<%--							<c:choose>--%>
<%--								<c:when test="${fn:length(news.ntitle) > 11}">--%>
<%--									<c:out value="${fn:substring(news.ntitle, 0, 11)}..."/>--%>
<%--								</c:when>--%>
<%--								<c:otherwise>--%>
<%--									<c:out value="${news.ntitle}"/>--%>
<%--								</c:otherwise>--%>
<%--							</c:choose>--%>
<%--						</a>--%>
<%--					</li>--%>
<%--				</c:if>--%>
<%--			</c:forEach>--%>
<%--		</ul>--%>
<%--	</div>--%>
</div>
