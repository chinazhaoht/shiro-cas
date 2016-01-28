<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/27
  Time: 11:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<table>
  <thead>
  <tr>
    <th>用户名</th>
    <th>密码</th>
  </tr>
  </thead>
  <tbody>
    <c:forEach var="user" items="${userList}">
      <tr>
        <td>${user.username}</td>
        <td>${user.password}</td>
      </tr>
    </c:forEach>
  </tbody>
</table>

</body>
</html>
