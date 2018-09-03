<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/10/1
  Time: 14:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${requestScope.registerResult}</title>
</head>
<body>
<!--导航栏-->
<%@include file="../common/header.jsp"%>

    <center>
        <h2>${requestScope.registerResult}</h2>
        <a href="<%=request.getContextPath() %>/user/registerServlet?m=forward&p=login">去登陆</a>
    </center>

    <%--底栏--%>
    <%@include file="../common/footer.jsp"%>
</body>
</html>
