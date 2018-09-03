<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/book.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/18
  Time: 20:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>${requestScope.book.bookName}</title>
</head>
<body>
    <%--导航栏--%>
    <%@ include file="../common/header.jsp"%>
    <div class="container">
        <h1 class="book-name">${requestScope.book.bookName}</h1>
    </div>
    <div class="container">
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

        <!--中间-->
        <div class="col-sm-6">
            <div class="book-type">
                类别:
                <div class="label label-default">
                    ${requestScope.book.bookType}
                </div>
            </div>

            <div class="book-content">
                <blockquote>
                    <p>
                        作者:${requestScope.book.author}
                    </p>
                </blockquote>
                <blockquote>
                    <p>
                        简介:${requestScope.book.content}
                    </p>
                </blockquote>
                <img src="<%=request.getContextPath()%>/img/bookPictures/${requestScope.book.booksId}.jpg">
            </div>
        </div>

        <!--右侧栏-->
        <div class="col-sm-3">
            <div class="side-bar-card">
                <div class="card-title">
                    剩余数量
                </div>
                <div class="card-body">
                    ${requestScope.book.leftAmount}
                </div>
            </div>
            <a class="btn btn-primary btn-block" href="<%=request.getContextPath()%>/user/BorrowBooksServlet?m=changeUser&booksID=${requestScope.book.booksId}">借书</a>
        </div>
    </div>

    <%--底栏--%>
    <%@ include file="../common/footer.jsp"%>
</body>
</html>
