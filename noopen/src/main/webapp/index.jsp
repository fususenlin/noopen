<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<jsp:useBean id="user" class="com.carp.weixin.pojo.WeixinUserInfo"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
    你的openid:<%= request.getParameter("openid").toString()%><br/>
    你的昵称:<%= request.getParameter("nickname").toString()%><br/>
    <a href= "weixin://contacts/profile/gh_8b3646b245e8">关注</a>
</body>
</html>
