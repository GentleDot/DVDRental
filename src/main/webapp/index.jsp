<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html lang="ko">
<%
    String contextPath = request.getContextPath();
%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>기능 바로가기</title>
</head>
<body>
    <div id="container">
        <a href="<%= contextPath %>/member/memberList.do">회원 페이지</a>
        <a href="<%= contextPath %>/product/productList.do">제품 페이지</a>
    </div>
</body>
</html>