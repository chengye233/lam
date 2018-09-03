<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/11
  Time: 15:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
		<meta charset="utf-8" />
    <title>图书类别管理</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

	<div class="container">
		<h2>图书类别管理</h2>
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
					<a class="list-group-item" href="<%=request.getContextPath()%>/bm/btServlet?m=forward&p=addType">
						添加类别
					</a>
				</div>
			</div>
			
			<!--中间 列表-->
			<div class="col-sm-8">
				<table class="table table-striped table-hover">
	    	<thead>
	    		<tr>
	    			<th>类别代码</th>
	    			<th>类别名</th>
	    			<th>编辑操作</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${requestScope.typeList}" var="type">
	        	<tr>
					<td>${type.bookType}</td>
					<td>${type.typeName}</td>
					<td><a href="<%=request.getContextPath()%>/bm/btServlet?m=getType&bookType=${type.bookType}">修改</a></td>
	        	</tr>
	        </c:forEach>	
	    	</tbody>
	    </table>
			</div>
			
		</div>
	</div>


	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
    
</body>
</html>
