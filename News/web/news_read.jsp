<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!-- 引入顶部页面元素 -->
<jsp:include page="index-elements/index_top.jsp" />

<!-- 引入CSS样式和jQuery库 -->
<link href="CSS/read.css" rel="stylesheet" type="text/css" />
<script src="${pageContext.request.contextPath}/js/jquery-3.7.1.js"></script>

<!-- 初始化页面脚本 -->
<script type="text/javascript">
	$(document).ready(function() {
		/**
		 * 检查评论表单是否填写完整
		 * @returns {boolean} 返回true如果表单填写完整，否则返回false
		 */
		function checkComment() {
			var cauthor = $("#cauthor");
			var content = $("#ccontent");
			if (cauthor.val().trim() === "") {
				alert("用户名不能为空！！");
				return false;
			} else if (content.val().trim() === "") {
				alert("评论内容不能为空！！");
				return false;
			}
			return true;
		}

		// 提交评论的事件处理
		$("#doComment").click(function(event) {
			event.preventDefault();
			if (!checkComment()) {
				return;
			}

			// 发送评论数据到服务器
			$.ajax({
				url: "${pageContext.request.contextPath}/NewsServlet?action=doComment",
				type: "post",
				data: {
					ctid: $("#ctid").val(),
					cauthor: $("#cauthor").val(),
					cip: $("#cip").val(),
					ccontent: $("#ccontent").val()
				},
				dataType: "json",
				success: function(result) {
					if (result.msg === '0') {
						alert("评论失败！");
						return;
					}

					// 更新评论列表
					$("#commentTable").empty();
					$.each(result.datas, function(i, comment) {
						var rowHtml = $("<tr><td>留言人：</td><td>" + comment.cauthor + "</td><td>IP：</td><td>" + comment.cip + "</td><td>留言时间：</td><td>" + comment.cdate + "</td></tr><tr><td colspan='6'>" + comment.ccontent + "</td></tr><tr><td colspan='6'><hr/></td></tr>");
						$("#commentTable").append(rowHtml);
					});

					// 清空评论内容框
					$("#ccontent").val("");
				},
				error: function(req) {
					if (req.status !== 200) {
						alert("HTTP状态：" + req.status);
					}
				}
			});
		});
	});
</script>

<!-- 页面主体 -->
<div id="container">
	<!-- 引入侧边栏页面元素 -->
	<jsp:include page="index-elements/index_sidebar.jsp" />
	<div class="main">
		<div class="content">
			<!-- 显示新闻详情 -->
			<ul class="classlist">
				<table width="80%" align="center">
					<!-- 显示新闻标题 -->
					<tr width="100%">
						<td colspan="2" align="center">${newsDetail.ntitle}</td>
					</tr>
					<!-- 显示新闻作者、类型和发布时间 -->
					<tr>
						<td colspan="2">
							<hr />
						</td>
					</tr>
					<tr>
						<td align="center">作者:${newsDetail.nauthor}
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							类型： <a
									href="${pageContext.request.contextPath}/NewsServlet?action=news&tid=ntid&ntid=${newsDetail.ntid}">${newsDetail.tname}</a>
						</td>
						<td align="left">发布时间:${newsDetail.ncreatedate}</td>
					</tr>
					<!-- 显示新闻摘要 -->
					<tr>
						<td align="right"><strong>摘要：${newsDetail.nsummary}</strong></td>
					</tr>
					<tr>
						<td colspan="2" align="center"></td>
					</tr>
					<!-- 显示新闻内容 -->
					<tr>
						<td colspan="2">${newsDetail.ncontent}</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr />
						</td>
					</tr>
				</table>
			</ul>
			<!-- 显示评论列表 -->
			<ul class="classlist">
				<table width="80%" align="center" id="commentTable">
					<!-- 如果没有评论，则显示暂无评论 -->
					<c:if test="${fn:length(commentsList) == 0}">
						<td colspan="6">暂无评论！</td>
						<tr>
							<td colspan="6">
								<hr />
							</td>
						</tr>
					</c:if>
					<!-- 遍历并显示所有评论 -->
					<c:forEach var="comments" items="${commentsList}">
						<tr>
							<td>留言人：</td>
							<td>${comments.cauthor}</td>
							<td>IP：</td>
							<td>${comments.cip}</td>
							<td>留言时间：</td>
							<td>${comments.cdate}</td>
						</tr>
						<tr>
							<td colspan="6">${comments.ccontent}</td>
						</tr>
						<tr>
							<td colspan="6">
								<hr />
							</td>
						</tr>
					</c:forEach>
				</table>
			</ul>
			<!-- 评论表单 -->
			<ul class="classlist">
				<form>
					<input type="hidden" id="ctid" value="${newsDetail.nid}" />
					<table width="80%" align="center">
						<tr>
							<td>评 论</td>
						</tr>
						<tr>
							<td>用户名：</td>
							<td><input id="cauthor" name="cauthor" value="${sessionScope.loginOk.uname}" readonly="readonly" />
								IP：<input id="cip" name="cip" value="Ip地址" readonly="readonly" /></td>
						</tr>
						<tr>
							<td colspan="2"><textarea id="ccontent" name="ccontent" cols="70" rows="10"></textarea></td>
						</tr>
						<tr>
							<td colspan="2"><input id="doComment" value="发  表" type="button" /></td>
						</tr>
					</table>
				</form>
			</ul>
		</div>
	</div>
</div>
<!-- 清除请求属性 -->
<%
	request.removeAttribute("news_view");
	request.removeAttribute("comments_view");
%>
<!-- 引入底部页面元素 -->
<jsp:include page="index-elements/index_botton.jsp" />
