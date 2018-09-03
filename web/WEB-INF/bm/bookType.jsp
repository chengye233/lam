<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/12
  Time: 20:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
		<meta charset="utf-8" />
    <title>修改类别</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

<div class="container">
		<h2>修改类别</h2>
    <h3>${requestScope.errorMessage}</h3>
		<div class="row">
			<!--左侧栏-->
			<div class="col-sm-3">
				<div class="list-group side-bar">
					<a class="list-group-item active" href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks">
						图书管理
					</a>
					<a class="list-group-item" href="<%=request.getContextPath()%>/bm/bsServlet?m=forward&p=registerBook">
						登记图书
					</a>
					<a class="list-group-item" href="<%=request.getContextPath()%>/bm/btServlet?m=getTypes">
						类别管理
					</a>
				</div>
			</div>
			
			<!--中间 表单-->
			<div class="col-sm-5">
				<form action="<%=request.getContextPath()%>/bm/btServlet?m=updateType" method="post">
					<input type="hidden" name="bookType" value="${requestScope.bookType.bookType}">
					<div class="form-group">
						<label>${requestScope.bookType.bookType}</label>
						<input class="form-control" type="text" name="typeName">
					</div>
					<div class="form-group">
						<button class="btn btn-primary btn-block" type="submit">修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>


<%--底栏--%>
<%@include file="../common/footer.jsp"%>
    
</body>
</html>
