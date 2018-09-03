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
  Date: 2017/10/7
  Time: 14:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
		<meta charset="utf-8">
    <title>修改图书信息</title>
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
		<h2>修改图书</h2>
    <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
		<div class="row">
			<!--左侧栏 具体图书管理 图片-->
			<div class="col-sm-2">
				<div class="list-group side-bar">
					<a  class="list-group-item active" href="<%=request.getContextPath()%>/bm/bkServlet?m=getBookPage&keyWord=${requestScope.book.bookType}${requestScope.book.booksId}">
						修改某一本
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
				<form enctype="multipart/form-data" action="<%=request.getContextPath()%>/bm/bsServlet?m=updateBooks" method="post" onsubmit="setType()">
					<input type="hidden" name="booksId" value="${requestScope.book.booksId}">
          <input type="hidden" name="bookType" id="typeHidden">
          <div class="form-group">
						<label>书名</label>
						<input name="bookName" class="form-control" type="text" value="${requestScope.book.bookName}">
					</div>
					<div class="form-group">
						<label>作者</label>
						<input name="author" class="form-control" type="text" value="${requestScope.book.author}">
					</div>
					<div class="form-group">
						<label>简介</label>
						<input name="content" class="form-control" type="text" value="${requestScope.book.content}">
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
						<input class="form-control" name="price" type="text" value="${requestScope.book.price}">
					</div>
					<div class="form-group">
						<label>修改数量</label>
						<input name="amount" class="form-control" type="text" placeholder="输入非0整数,正数增加,负数减少">
					</div>
					<div class="form-group">
						<label>封面</label>
						<input name="bookPicture" class="file" type="file" data-show-preview="false" placeholder="选择图片">
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
						<img src="<%=request.getContextPath()%>/img/bookPictures/${requestScope.book.booksId}.jpg" />
					</div>
					<div class="card-body">
						总数量: ${requestScope.book.totalAmount}
					</div>
					<div class="card-body">
						剩余数量: ${requestScope.book.leftAmount}
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>

</body>
</html>
