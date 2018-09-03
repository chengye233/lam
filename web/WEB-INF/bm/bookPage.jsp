<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/7
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>图书列表</title>
    <meta charset="utf-8" />
    <script type="text/javascript">
        function deleteBook() {
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

	<!--搜索-->
	<div class="container container-small">
		<h2>图书列表</h2>
    <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
    <form class="form-inline" action="<%=request.getContextPath()%>/bm/bkServlet?m=getBookPage" method="post">
    	<div class="form-group">
    		<input type="text" name="keyWord" class="form-control" 
    			value="${requestScope.keyWord}" placeholder="请输入图书的bookCode">
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
			
			<!--中间 列表-->
			<div class="col-sm-8">
				<table class="table table-striped table-hover">
	    	<thead>
	    		<tr>
	    			<th>图书码</th>
	    			<th>编辑操作</th>
	    			<th>删除操作</th>
	    		</tr>
	    	</thead>
	    	<tbody>
	    		<c:forEach items="${requestScope.bookPage.pageMap}" var="books">
	        	<tr>
	            <c:forEach items="${books.value}" var="book">
	                <td>
										${book.bookCode}
	                </td>
	                <td>
	                	<a href="<%=request.getContextPath()%>/bm/bkServlet?m=getBookContent&bookId=${book.bookId}&keyWord=${requestScope.keyWord}">
	                		修改
	                	</a>
	                </td>
	                <td>
	                	<a onclick="return deleteBook();" href="<%=request.getContextPath()%>/bm/bkServlet?m=deleteBook&bookId=${book.bookId}&keyWord=${requestScope.keyWord}">
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
	<div class="container">
		<c:if test="${empty requestScope.errorMessage}">
			<nav>
    	<ul class="pager">
    		<li class="disabled">
    			<a>共${requestScope.bookPage.totalPageNumber}页</a>
    		</li>
    		<c:if test="${requestScope.bookPage.hasPrev}">
    			<li>
    				<a href="<%=request.getContextPath()%>/bm/bk?m=getBookPage&pageNo=1&keyWord=${requestScope.keyWord}">
    					首页
    				</a>
    			</li>
    			<li>
    				<a href="<%=request.getContextPath()%>/bm/bkServlet?m=getBookPage&pageNo=${requestScope.bookPage.prevPageNo}&keyWord=${requestScope.keyWord}">
    					上一页
    				</a>
    			</li>
      	</c:if>
      	<li class="disabled"><a>当前第${requestScope.bookPage.pageNo}页</a></li>
      	<c:if test="${requestScope.bookPage.hasNext}">
      		<li>
      			<a href="<%=request.getContextPath()%>/bm/bkServlet?m=getBookPage&pageNo=${requestScope.bookPage.nextPageNo}&keyWord=${requestScope.keyWord}">
      				下一页
      			</a>
	      	</li>
	        <li>
	        	<a href="<%=request.getContextPath()%>/bm/bkServlet?m=getBookPage&pageNo=${requestScope.bookPage.totalPageNumber}&keyWord=${requestScope.keyWord}">
	        		末页
	        	</a>
	        </li>        
      	</c:if>
    	</ul>
    </nav> 
		</c:if>
		
	</div>

<%--底栏--%>
<%@include file="../common/footer.jsp"%>

</body>
</html>
