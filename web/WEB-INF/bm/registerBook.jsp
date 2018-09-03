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
  Date: 2017/10/3
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登记图书</title>
    <meta charset="utf-8" />
    <script type="text/javascript">
        function setType() {
            document.getElementById("typeHidden").value = document.getElementById("bookType").value;
        };
    </script>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

	<div class="container">
		<h2>登记图书</h2>
    	<h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<div class="row">
			<!--左侧栏 具体图书管理-->
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
			
			<!--表单-->
			<div class="col-sm-6">
				<form enctype="multipart/form-data" action="<%=request.getContextPath()%>/bm/bsServlet?m=registerBook" method="post" onsubmit="setType()">
					<input type="hidden" name="booksId" value="${requestScope.book.booksId}">
          			<input type="hidden" name="bookType" id="typeHidden">
         			 <div class="form-group">
						<label>书名</label>
						<input class="form-control" name="bookName" type="text" value="${requestScope.bookName}">
					</div>
					<div class="form-group">
						<label>作者</label>
						<input class="form-control" name="author" type="text" value="${requestScope.author}">
					</div>
					<div class="form-group">
						<label>简介</label>
						<input class="form-control" name="content" type="text" value="${requestScope.content}">
					</div>
					<div class="form-group">
						<label>类别</label>
						<select class="form-control" id="bookType">
                <c:forEach items="${requestScope.typeList}" var="type">
                    <option value="${type.bookType}">${type.typeName}</option>
                </c:forEach>
            </select>
					</div>
					<div class="form-group">
						<label>价格</label>
						<input class="form-control" name="price" type="text" value="${requestScope.price}">
					</div>
					<div class="form-group">
						<label>数量</label>
						<input class="form-control" name="totalAmount" type="text" value="${requestScope.totalAmount}">
					</div>
					<div class="form-group">
						<label>封面</label>
						<input class="file" name="bookPicture" type="file" placeholder="选择图片">
					</div>
					<div class="form-group">
						<button class="btn btn-primary btn-block">登记</button>
					</div>
				</form>
			</div>
		</div>
	</div>


	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
</body>
</html>
