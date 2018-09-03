<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
<!--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/2
  Time: 16:42
  To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
		<meta charset="utf-8" />
    <title>登陆</title>
</head>
<body>
	<!--导航栏-->
	<%@include file="../common/header.jsp"%>

	<!--表单-->
	<div class="container container-small">
		<h1>登陆</h1>
		<h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<form action="<%=request.getContextPath() %>/user/registerServlet?m=login" method="post">
			<div class="form-group">
				<label>邮箱</label>
				<input name="email" class="form-control" type="text" value="${requestScope.email}">
			</div>
			
			<div class="form-group">
				<label>密码</label>
				<input name="password" class="form-control" type="password">
			</div>
			
			<div class="form-group">
				<button class="btn btn-primary btn-block" type="submit">登陆</button>
			</div>
			
			<div class="form-group">
				<a href="<%=request.getContextPath() %>/user/registerServlet?m=forward&p=password">忘记密码</a>
				<a class="a-right" href="<%=request.getContextPath() %>/user/registerServlet?m=forward&p=register">没有密码?注册</a>
			</div>
		</form>
		
	</div>

	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
</body>
</html>
