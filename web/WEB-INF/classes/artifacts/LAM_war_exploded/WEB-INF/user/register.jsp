<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/fileinput.min.css" />
<script src="<%=request.getContextPath()%>/js/fileinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/fileinput-zh.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />

<!--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/9/20
  Time: 14:03
  To change this template use File | Settings | File Templates.
-->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
		<meta charset="utf-8" />
    <title>注册</title>
</head>
<body>
	<!--导航栏-->
	<%@include file="../common/header.jsp"%>

	<!--表单-->
	<div class="container container-small">
		<h1>注册</h1>
		<h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<form enctype="multipart/form-data" action="<%=request.getContextPath() %>/user/registerServlet?m=register" method="post">
			<div class="form-group">
				<label>用户名</label>
				<input name="userName" class="form-control" type="text" 
					value="${requestScope.userName}" placeholder="字母数字组合 3-8位">
			</div>
			<div class="form-group">
				<label>密码</label>
				<input name="password" class="form-control" type="password"
      		 placeholder="字母数字组合 6-10位">
			</div>
			<div class="form-group">
				<label>邮箱</label>
				<input name="email" class="form-control" type="text" 
					value="${requestScope.email}" placeholder="xxx@example.com">
			</div>
			<div class="form-group">
				<label>头像</label>
				<input name="userPicture" class="file" type="file" >
			</div>
			<div class="form-group">
				<button class="btn btn-primary btn-block" type="submit">注册</button>
			</div>
			<div class="form-group">
					注册LAM即代表您同意<a href="#">LAM条款</a>
				</div>
		</form>
		
	</div>

	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
</body>
</html>
