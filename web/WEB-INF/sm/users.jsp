<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/17
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>用户管理</title>
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
        <h2>用户管理</h2>
        <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
        <form class="form-inline" action="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers" method="post">
            <div class="form-group">
                <input class="form-control" type="text" name="keyWord" value="${requestScope.keyWord}">
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

            <%--中间 用户列表--%>
            <div class="col-sm-8">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>用户名</th>
                        <th>用户邮箱</th>
                        <th>编辑操作</th>
                        <th>删除操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.userPage.pageMap}" var="users">
                        <tr>
                            <c:forEach items="${users.value}" var="user">
                                <td>${user.userName}</td>
                                <td>${user.email}</td>
                                <td>
                                    <a href="<%=request.getContextPath()%>/sm/uiServlet?m=getUser&userId=${user.userId}">
                                        修改
                                    </a>
                                </td>
                                <td>
                                    <a onclick="return deleteUser();" href="<%=request.getContextPath()%>/sm/uiServlet?m=deleteUser&userId=${user.userId}">
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

    <%--分页--%>
    <div class="container">
        <nav>
            <ul class="pager">
                <li class="disabled">
                    <a href="#">共${requestScope.userPage.totalPageNumber}页</a>
                </li>

                <c:if test="${requestScope.userPage.hasPrev}">
                    <li>
                        <a href="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers&pageNo=1&keyWord=${requestScope.keyWord}">
                            首页
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers&pageNo=${requestScope.userPage.prevPageNo}&keyWord=${requestScope.keyWord}">
                            上一页
                        </a>
                    </li>
                </c:if>
                <li class="disabled">
                    <a href="#">当前${requestScope.userPage.pageNo}页</a>
                </li>
                <c:if test="${requestScope.userPage.hasNext}">
                    <li>
                        <a href="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers&pageNo=${requestScope.userPage.nextPageNo}&keyWord=${requestScope.keyWord}">
                            下一页
                        </a>
                    </li>
                    <li>
                        <a href="<%=request.getContextPath()%>/sm/uiServlet?m=getUsers&pageNo=${requestScope.userPage.totalPageNumber}&keyWord=${requestScope.keyWord}">
                            末页
                        </a>
                    </li>
                </c:if>
            </ul>
        </nav>
    </div>

    <%--底栏--%>
    <%@include file="../common/footer.jsp"%>
</body>
</html>
