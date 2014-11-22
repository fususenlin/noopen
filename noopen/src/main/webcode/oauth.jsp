<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@page import="com.carp.weixin.pojo.SNSUserInfo;" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <%
    SNSUserInfo user = (SNSUserInfo)request.getAttribute("snsUserInfo");
    if(null!=user){
    %>
    <%=user.getOpenId() %><br>
    <%=user.getNickname() %>
    <%
    }
    else
        out.print("未获取到信息");
    %>
</body>
</html>