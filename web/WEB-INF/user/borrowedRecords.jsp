<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/23
  Time: 20:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>还书</title>
</head>
<body>
<%--导航栏--%>
<%@ include file="../common/header.jsp"%>

<div class="container">
    <h2>借书记录</h2>
    <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
    <div class="row">
        <%--左侧栏--%>
        <div class="col-sm-3">
            <div class="list-group side-bar">
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
        </div>

        <%--中间 table--%>
        <div class="col-sm-8">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>书名</th>
                    <th>借书时间</th>
                    <th>还书时间</th>
                    <th>还书操作</th>
                    <th>续借操作</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${requestScope.records}" var="record">
                    <tr>
                        <td>
                                ${record.bookName}
                        </td>
                        <td>
                                ${record.borrowedTime}
                        </td>
                        <td>
                                ${record.backTime}
                        </td>
                        <td>
                                <a class="btn btn-primary" href="<%=request.getContextPath()%>/user/backBooksServlet?m=backBooks&recordID=${record.recordId}">还书</a>
                        </td>
                        <td>
                                <a class="btn btn-primary" href="<%=request.getContextPath()%>//user/renewBooksServlet?m=renewBooks&recordID=${record.recordId}">续借</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

    </div>

</div>

<%--底栏--%>
<%@ include file="../common/footer.jsp"%>
</body>
</html>
