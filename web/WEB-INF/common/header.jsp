<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/header.css">

<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    	<div class="navbar navbar-default">
			<div class="container">
				<!--logo-->
				<div class="navbar-header">
					<a href="<%=request.getContextPath()%>/user/registerServlet?m=forward&p=login" class="navbar-brand"></a>
				</div>
				<div class="hidden-xs">
					<!--板块-->
					<ul class="nav navbar-nav">
						<li><a href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks">首页</a></li>
						<li><a href="<%=request.getContextPath()%>/user/ubServlet?m=getBorrowedRecords&userId=${sessionScope.user.userId}">还书</a></li>
					</ul>
					<!--用户个人信息管理 右浮动 -->
					<ul class="nav navbar-nav navbar-right">
						<li><a href="<%=request.getContextPath()%>/user/ubServlet?m=getUser&userId=${sessionScope.user.userId}">我的资料</a></li>
					</ul>
				</div>
			</div>
		</div>
 	</body>
</html>