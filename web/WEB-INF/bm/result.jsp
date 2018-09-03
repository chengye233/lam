<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="<%=request.getContextPath()%>/js/jquery-3.2.1.min.js"></script>
<link href="<%=request.getContextPath()%>/css/bootstrap.min.css"rel="stylesheet">
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/books.css" />
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/15
  Time: 20:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>结果</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

    <div class="container">
        <div class="row">
            <h1>成功</h1>
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
            <div class="col-sm-7">

            </div>
        </div>
    </div>

<%--底栏--%>
<%@include file="../common/footer.jsp"%>
</body>
</html>
