<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/18
  Time: 15:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>图书列表</title>
</head>
<body>
    <%--导航栏--%>
    <%@ include file="../common/header.jsp"%>

    <%--搜索框--%>
    <div class="container container-small">
        <h2>图书列表</h2>
        <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
        <form class="form-inline" action="<%=request.getContextPath()%>/user/ubServlet?m=getBooks" method="post">
            <div class="form-group">
                <select class="form-control" name="bookType">
                    <option value="ALL">全部</option>
                    <c:forEach items="${requestScope.typeList}" var="type">
                        <option value="${type.bookType}">${type.typeName}</option>
                    </c:forEach>
                </select>
                <input class="form-control" type="text" name="keyWord" value="${requestScope.keyWord}" placeholder="请输入书名或简介" >
                <button class="btn btn-primary" type="submit">搜索</button>
            </div>
        </form>
    </div>

    <div class="container">
        <div class="row">
            <%--左侧栏--%>
            <div class="col-sm-3">
                <div class="list-group side-bar">
                    <a class="list-group-item active" href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks">
                        图书列表
                    </a>
                    <a class="list-group-item" href="<%=request.getContextPath()%>/user/ubServlet?m=getRecords&userId=${sessionScope.user.userId}">
                        借书记录
                    </a>
                    <c:choose>
                        <c:when test="${sessionScope.user.userType == 1}">
                            <a class="list-group-item" href="<%=request.getContextPath()%>/bm/bsServlet?m=getBooks">
                                图书管理
                            </a>
                        </c:when>
                        <c:when test="${sessionScope.user.userType == 2}">
                            <a class="list-group-item" href="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers">
                                用户管理
                            </a>
                        </c:when>
                    </c:choose>
                </div>
            </div>

            <%--图书列表--%>
            <div class="col-sm-8">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>图片</th>
                        <th>书名</th>
                        <th>作者</th>
                    </tr>
                    </thead>

                    <tbody>
                    <c:forEach items="${requestScope.booksPage.pageMap}" var="books">
                        <tr>
                            <c:forEach items="${books.value}" var="book">
                                <td>
                                    <a href="<%=request.getContextPath()%>/user/ubServlet?m=getBook&booksId=${book.booksId}">
                                        <img src="<%=request.getContextPath()%>/img/bookPictures/${book.booksId}.jpg">
                                    </a>
                                </td>
                                <td>
                                    ${book.bookName}
                                </td>
                                <td>
                                    ${book.author}
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </div>

    </div>

    <%--分页--%>
    <c:if test="${empty requestScope.errorMessage}">
        <div class="container">
            <nav>
                <ul class="pager">
                    <li  class="disabled">
                        <a href="#">共${requestScope.booksPage.totalPageNumber}页</a>
                    </li>
                    <c:if test="${requestScope.booksPage.hasPrev}">
                        <li>
                            <a href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks&pageNo=1&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
                                首页
                            </a>
                        </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks&pageNo=${requestScope.booksPage.pageNo-1}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
                                上一页
                            </a>
                        </li>
                    </c:if>
                    <li class="disabled">
                        <a href="#">当前${requestScope.booksPage.pageNo}页</a>
                    </li>
                    <c:if test="${requestScope.booksPage.hasNext}">
                        <li>
                            <a href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks&pageNo=${requestScope.booksPage.pageNo+1}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
                                下一页
                            </a>
                        </li>
                        <li>
                            <a href="<%=request.getContextPath()%>/user/ubServlet?m=getBooks&pageNo=${requestScope.booksPage.totalPageNumber}&keyWord=${requestScope.keyWord}&bookType=${requestScope.bookType}">
                                末页
                            </a>
                        </li>
                    </c:if>
                </ul>
            </nav>
        </div>
    </c:if>

    <%--底栏--%>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
