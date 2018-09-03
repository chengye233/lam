<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/login.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/15
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>增加图书类别</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

    <div class="container">
        <div class="row">
            <h2>增加类别</h2>
            <h3><span class="text text-warning">${requestScope.errorMessage}</span></h3>

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

            <%--中间 表单--%>
            <div class="col-sm-6">
                <form action="<%=request.getContextPath()%>/bm/btServlet?m=addType" method="post">
                    <div class="form-group">
                        <label>类别代码</label>
                        <input class="form-control" name="bookType" type="text"
                               value="${requestScope.bookType}" placeholder="请输入1个英文字母eg:K">
                    </div>
                    <div class="form-group">
                        <label>类别名称</label>
                        <input class="form-control" name="typeName" type="text"
                               value="${requestScope.typeName}" placeholder="eg:文史类">
                    </div>
                    <div class="form-group">
                        <button class="btn btn-primary btn-block" type="submit">添加</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

<%--底栏--%>
<%@include file="../common/footer.jsp"%>
</body>
</html>
