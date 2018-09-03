<script src="../js/jquery-3.2.1.js"></script>
<link href="../css/bootstrap.css" rel="stylesheet">
<script src="../js/bootstrap.js"></script>
<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/3
  Time: 16:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>错误</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

<center>
    <h1><span class="text text-warning">${requestScope.errorMessage}</span></h1>
</center>

<%--底栏--%>
<%@include file="../common/footer.jsp"%>
</body>
</html>
