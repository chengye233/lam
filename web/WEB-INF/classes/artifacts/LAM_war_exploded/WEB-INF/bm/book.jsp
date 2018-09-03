<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/fileinput.min.css" />
<script src="<%=request.getContextPath()%>/js/fileinput.min.js"></script>
<script src="<%=request.getContextPath()%>/js/fileinput-zh.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/6
  Time: 20:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
	<meta charset="utf-8" />
    <title>修改图书信息</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

	<div class="container">
		<h2>修改图书</h2>
    <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<div class="row">
			<!--左侧栏 具体图书管理 图片-->
			<div class="col-sm-2">
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
			<div class="col-sm-7">
				<form enctype="multipart/form-data" action="<%=request.getContextPath()%>/bm/bkServlet?m=updateBooks" method="post" onsubmit="setType()">
					<input type="hidden" name="bookId" value="${requestScope.book.bookId}">
          <input type="hidden" name="keyWord" value="${requestScope.keyWord}">
          <div class="form-group">
						<c:if test="${requestScope.book.useState==1}">
	            <label>使用</label>
	            <input class="form-control" type="radio" name="useState" checked="checked" value="1">
	            <label>损坏</label>
	            <input class="form-control" type="radio" name="useState" value="0">
	        	</c:if>
	        	<c:if test="${requestScope.book.useState==0}">
            	<label>使用</label>
            	<input class="form-control" type="radio" name="useState" value="1">
            	<label>损坏</label>
	            <input class="form-control" type="radio" name="useState" checked="checked" value="0">
            </c:if>
					</div>
					<div class="form-group">
						<c:if test="${requestScope.book.bookState==1}">
	            	<label>已借</label>
	            	<input class="form-control" type="radio" name="bookState" checked="checked" value="1">
	            	<label>未借</label>
	            	<input class="form-control" type="radio" name="bookState" value="0">
	        	</c:if>
	        	<c:if test="${requestScope.book.bookState==0}">
	            	<label>已借</label>
	            	<input class="form-control" type="radio" name="bookState" value="1">
	            	<label>未借</label>
	            	<input class="form-control" type="radio" name="bookState" checked="checked" value="0">
            </c:if>
					</div>
					
					<div class="form-group">
						<button class="btn btn-primary btn-block">修改</button>
					</div>
				</form>
			</div>
			
			<!--右侧栏 图片 数量-->
			<div class="col-sm-3">
				<div class="side-bar-card">
					<div class="card-title">
						图书码
					</div>
					<div class="card-body">
						${requestScope.book.bookCode}
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
    
</body>
</html>
