<%@page import="net.gentledot.rental.vo.MemberVO"%>
<%@ page import="java.util.List" %>
<%@ page import="org.springframework.web.servlet.support.RequestContextUtils" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko-KR">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
<title>회원 목록 조회</title>
</head>
<%
	String contextPath = request.getContextPath();
	List<MemberVO> resultList = (List<MemberVO>) request.getAttribute("resultList");
	String mId = (String) request.getAttribute("mId");
	int pageNo = (Integer) request.getAttribute("pageNo");
%>
<body>
<div id="container">
	<header></header>
	<section class="main">
		<section class="row">
			<h2>회원 목록</h2>
			<div class="col-md-12">
				<form action="<%= contextPath %>/member/memberList.do" method="post" class="form-control">
					<fieldset>
						<legend>회원 검색</legend>
						<label for="memberSearchById">회원 아이디 : </label>
						<input type="text" id="memberSearchById" class="form-control" name="memberSearchById">
					</fieldset>
			</div>
		</section>
		<section class="row">
			<div class="col-md-12">

			</div>
		</section>
	</section>
	<footer></footer>
</div>
</body>
<script src="webjars/jquery/3.2.1/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.7-1/js/bootstrap.min.js"></script>
</html>