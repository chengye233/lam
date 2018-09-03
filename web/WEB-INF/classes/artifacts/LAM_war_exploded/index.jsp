<%--
  Created by IntelliJ IDEA.
  User: 20688
  Date: 2017/9/4
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
  </head>
  <body>
    <% response.sendRedirect(request.getContextPath() +"/user/registerServlet?m=forward&p=login"); %>
  </body>
</html>
