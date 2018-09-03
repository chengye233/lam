<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/3
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.errorMessage}</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

<center>
    <h1>${requestScope.errorMessage}</h1>
</center>

<%--底栏--%>
<%@include file="../common/footer.jsp"%>
</body>
</html>
