<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/26
  Time: 19:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
  <form action="<c:url value="/users"/>" method="POST">
    username:<input type="text" name="username" placeholder="请输入用户名"/><br/>
    password:<input type="text" name="password" placeholder="请输入密码"/><br/>
    mobile:<input type="text" name="mobile" placeholder="电话号码"/><br/>
    email:<input type="text" name="email" placeholder="电子邮件"/><br/>
    <input type="submit" value="提交"/>
  </form>
</body>
</html>
