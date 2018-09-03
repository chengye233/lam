<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
<!--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/2
  Time: 17:08
  To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
		<meta charset="utf-8" />
    <title>找回密码</title>
</head>
<body>
	<!--导航栏-->
	<%@include file="../common/header.jsp"%>
	<!--表单-->
	<div class="container container-small">
		<h1>找回密码</h1>
		<h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<form action="<%=request.getContextPath() %>/user/registerServlet?m=password" method="post">
			<div class="form-group">
				<label>请输入邮箱</label>
				<input name="email" class="form-control" type="text">
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-block">找回</button>
			</div>
		</form>
	</div>

	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
</body>
</html>
