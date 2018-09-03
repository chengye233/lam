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
  Date: 2017/10/23
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>${requestScope.user.userName}</title>
</head>
<body>
    <%--导航栏--%>
    <%@ include file="../common/header.jsp"%>

    <div class="container">
        <h2>修改个人信息</h2>
        <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>
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

        <%--中间 表单--%>
        <div class="col-sm-6">
            <form enctype="multipart/form-data" action="<%=request.getContextPath()%>/user/ubServlet?m=updateUser" method="post">
                <input type="hidden" name="userId" value="${requestScope.user.userId}">
                <div class="form-group">
                    <label>用户名</label>
                    <input name="userName" class="form-control" type="text" value="${requestScope.user.userName}">
                </div>
                <div class="form-group">
                    <label>邮箱</label>
                    <input name="email" class="form-control" type="text" value="${requestScope.user.email}">
                </div>
                <div class="form-group">
                    <label>密码</label>
                    <input name="password" class="form-control" type="password" value="${requestScope.user.password}" >
                </div>
                <div class="form-group">
                    <label>头像</label>
                    <input name="userPicture" class="file" type="file" >
                </div>
                <div class="form-group">
                    <button class="btn btn-primary btn-block" type="submit">修改</button>
                </div>
            </form>

        </div>

        <%--右侧--%>
        <div class="col-sm-3">
            <div class="side-bar-card">
                <div class="card-title">
                    <img src="<%=request.getContextPath()%>/img/userPictures/${requestScope.user.userId}.jpg" />
                </div>
            </div>
        </div>

    </div>

    <%--底栏--%>
    <%@include file="../common/footer.jsp"%>
</body>
</html>
