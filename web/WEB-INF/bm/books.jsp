<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/3
  Time: 16:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<meta charset="utf-8" />
    <title>图书管理</title>
    <script type="text/javascript">
    	function deleteUser() {
        var flag = confirm("确定要删除吗?");
        if (flag)
        {
            return true;
        }
        return false;
    };
    </script>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

	<%--搜索框--%>
	<div class="container container-small">
	<h2>图书管理</h2>
    <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
    <form class="form-inline" action="<%=request.getContextPath() %>/bm/bsServlet?m=getBooks" method="post">
    	<div class="form-group">
    		<select class="form-control" name="bookType">
                <option value="ALL">全部</option>
                <c:forEach items="${requestScope.typeList}" var="type">
                    <option value="${type.bookType}">${type.typeName}</option>
                </c:forEach>
        	</select>
    		<input class="form-control" type="text" name="keyWord" 
    			value="${requestScope.keyWord}" placeholder="请输入书名或简介" >
    			<button class="btn btn-primary">搜索</button>
    	</div>
    </form>
    
	</div>
	
	<div class="container">
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
		
		<!--图书列表-->
		<div class="col-sm-8">
			<table class="table table-striped table-hover">
	    	<thead>
	    		<tr>
	    			<th>图片</th>
	    			<th>书名</th>
	    			<th>编辑操作</th>
	    			<th>删除操作</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${requestScope.booksPage.pageMap}" var="books">
	        	<tr>
	            <c:forEach items="${books.value}" var="book">
	                <td>
						<img src="<%=request.getContextPath() %>/img/bookPictures/${book.booksId}.jpg">
	                </td>
	                <td>
	                	${book.bookName}
	                </td>
	                <td>
	                	<a href="<%=request.getContextPath()%>/bm/bsServlet?m=getBook&booksId=${book.booksId}">
	                		编辑
	                	</a>
	                </td>
	                <td>
	                	<a onclick="return deleteUser();" href="<%=request.getContextPath()%>/bm/bsServlet?m=deleteBooks&booksId=${book.booksId}">
	                		删除
	                	</a>
	                </td>
	        		</c:forEach>
	        	</tr>
	        </c:forEach>	
	    	</tbody>
	    </table>
		</div>
		</div>
	</div>
	
	<!--分页-->
	<c:if test="${empty requestScope.errorMessage}">
		<div class="container">
			<nav>
				<ul class="pager">
					<li class="disabled">
						<a>共${requestScope.booksPage.totalPageNumber}页</a>
					</li>
					<c:if test="${requestScope.booksPage.hasPrev}">
						<li>
							<a href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks&pageNo=1&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
								首页
							</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks&pageNo=${requestScope.booksPage.prevPageNo}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
								上一页
							</a>
						</li>
					</c:if>
					<li class="disabled"><a>当前第${requestScope.booksPage.pageNo}页</a></li>
					<c:if test="${requestScope.booksPage.hasNext}">
						<li>
							<a href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks&pageNo=${requestScope.booksPage.nextPageNo}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
								下一页
							</a>
						</li>
						<li>
							<a href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks&pageNo=${requestScope.booksPage.totalPageNumber}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
								末页
							</a>
						</li>
					</c:if>
				</ul>
			</nav>
		</div>
	</c:if>


	<%--底栏--%>
	<%@include file="../common/footer.jsp"%>
</body>
</html>
